package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Module;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.concurrent.TimeUnit;
//Camera
public class Camera extends Module {
    public boolean[] USE_LIMELIGHT = { false };
    public boolean[] USE_WEB = { false };
    boolean LIMELIGHT = false;
    boolean WEBCAM = false;

    // *** < FOR CALCULATING PID OF STABILIZATION >***
    double old_t = runtime.milliseconds();
    double err_last = 0;
    @Override
    public void init_classes(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2,
                             LinearOpMode L){
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.L = L;
    }
    private void initAprilTag() {
        if(LIMELIGHT){
            LL_CONSTANTS.limelight = hardwareMap.get(Limelight3A.class, "limelight");
            telemetry.setMsTransmissionInterval(11);
            LL_CONSTANTS.limelight.pipelineSwitch(0);
        } else if (WEBCAM){
            WEBCAM_CONSTANTS.aprilTag = new AprilTagProcessor.Builder()
                    .setCameraPose(WEBCAM_CONSTANTS.cameraPosition, WEBCAM_CONSTANTS.cameraOrientation)
                    //.setLensIntrinsics(fx, fy, cx, cy)
                    .setTagLibrary(AprilTagGameDatabase.getCurrentGameTagLibrary())
                    .build();

            VisionPortal.Builder builder = new VisionPortal.Builder();
            if (WEBCAM) {
                builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
            } else {
                builder.setCamera(BuiltinCameraDirection.BACK);
            }
            builder.addProcessor(WEBCAM_CONSTANTS.aprilTag);
            builder.enableLiveView(true);
            builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
            WEBCAM_CONSTANTS.visionPortal = builder.build();

            while (L.opModeInInit() && WEBCAM_CONSTANTS.visionPortal.getCameraState() !=
                                                    VisionPortal.CameraState.STREAMING) {
                telemetry.addLine("Camera init...");
                telemetry.update();
                delay(20);
            }
            WEBCAM_CONSTANTS.exposure = WEBCAM_CONSTANTS.visionPortal.getCameraControl(ExposureControl.class);
            WEBCAM_CONSTANTS.gain = WEBCAM_CONSTANTS.visionPortal.getCameraControl(GainControl.class);

            WEBCAM_CONSTANTS.exposure.setMode(ExposureControl.Mode.Manual);
            WEBCAM_CONSTANTS.exposure.setExposure(1, TimeUnit.MILLISECONDS);
            WEBCAM_CONSTANTS.gain.setGain(255);
        }
    }
    public void set_processor(boolean[] type){
        type[0] = true;
        LIMELIGHT   = USE_LIMELIGHT[0];
        WEBCAM      = USE_WEB[0];
        initAprilTag();
        if(WEBCAM){
            FtcDashboard.getInstance().startCameraStream(WEBCAM_CONSTANTS.visionPortal, 30);
            WEBCAM_CONSTANTS.visionPortal.setProcessorEnabled(WEBCAM_CONSTANTS.aprilTag, true);
        } else if (LIMELIGHT){
            LL_CONSTANTS.limelight.start();
        }
    }
    public void stop_stream(){
        if(WEBCAM){
            FtcDashboard.getInstance().stopCameraStream();
            WEBCAM_CONSTANTS.visionPortal.setProcessorEnabled(WEBCAM_CONSTANTS.aprilTag, false);
            WEBCAM_CONSTANTS.visionPortal.close();
        } else if(LIMELIGHT){
            LL_CONSTANTS.limelight.stop();
            LL_CONSTANTS.limelight.close();
        }
    }
    public double[] get_position(){
        double x = 0, z = 0;
        double id = 0;
        if(WEBCAM){
            List<AprilTagDetection> currentDetections = WEBCAM_CONSTANTS.aprilTag.getDetections();
            telemetry.addData("# AprilTags Detected", currentDetections.size());
            for (AprilTagDetection detection : currentDetections) {
                if (detection.metadata != null) {
                    // Only use tags that don't have Obelisk in them
                    if (!detection.metadata.name.contains("Obelisk")) {
                        x = detection.ftcPose.x * 2.54;
                        z = detection.ftcPose.z * 2.54;
                    }
                    id = detection.id;
                }
            }
        } else if(LIMELIGHT){
            LLResult result = LL_CONSTANTS.limelight.getLatestResult();
            if (result != null) {
                if (result.isValid()) {
                    x = result.getTx();
                    z = result.getTa();
                    id = result.getFiducialResults().get(0).getFiducialId();
                }
            }
        }
        return new double[]{x, z, id};
    }
    public double get_tag_err(double kp, double kd){
        double[] pos = get_position();

        double err = pos[0];
        double now = runtime.milliseconds();

        double dt = (now - old_t);

        double differential = (err - err_last) / dt;

        double P = err * kp;
        double D = differential * kd;

        err_last = err;
        old_t = now;

        return P + D;
    }
    public double get_distance(){
        double[] pos = get_position();
        return pos[1];
    }
    public double[] get3Dpos(double distance_unit){
        LLResult result = LL_CONSTANTS.limelight.getLatestResult();
        Pose3D botpose;
        double x = 0, y = 0, yaw = 0;
        if (result != null) {
            if (result.isValid()) {
                botpose = result.getBotpose();
                x       = botpose.getPosition().x * distance_unit;
                y       = botpose.getPosition().y * distance_unit;
                yaw     = botpose.getOrientation().getYaw();
            }
        }
        return new double[] {x, y, yaw};
    }
    public static class WEBCAM_CONSTANTS{
        static AprilTagProcessor aprilTag;
        static VisionPortal visionPortal;
        static ExposureControl exposure;
        static GainControl gain;
        static double fx = 1447.20666452;
        static double fy = 1445.36496334;
        static double cx = 938.27422;
        static double cy = 596.46596293;
        static final Position cameraPosition = new Position(DistanceUnit.INCH,
                0, 0, 0, 0);
        static final YawPitchRollAngles cameraOrientation = new YawPitchRollAngles(AngleUnit.DEGREES,
                0, 20, 0, 0);

    }
    public static class LL_CONSTANTS{
        public static Limelight3A limelight;
    }
    public static class Units{
        public static double METER = 1;
        public static double DEC = 10;
        public static double CM = 100;
        public static double MM = 1000;
    }
}

