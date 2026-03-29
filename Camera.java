package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import android.annotation.SuppressLint;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Camera {
    ElapsedTime runtime = new ElapsedTime();
    HardwareMap hardwareMap;
    Telemetry telemetry;
    Gamepad gamepad1;
    Gamepad gamepad2;
    LinearOpMode L;
    double old_t = runtime.milliseconds();
    double err_last = 0, integral = 0, D;
    public static final boolean USE_WEBCAM = true;
    private final Position cameraPosition = new Position(DistanceUnit.INCH,
            10, 0, 0, 0);
    private final YawPitchRollAngles cameraOrientation = new YawPitchRollAngles(AngleUnit.DEGREES,
            0, 20, 0, 0);

    AprilTagProcessor aprilTag;
    VisionPortal visionPortal;
    ExposureControl exposure;
    GainControl gain;
    private void initAprilTag() {
        double fx = 1447.20666452;
        double fy = 1445.36496334;
        double cx = 938.27422;
        double cy = 596.46596293;

        aprilTag = new AprilTagProcessor.Builder()
                .setCameraPose(cameraPosition, cameraOrientation)
                //.setLensIntrinsics(fx, fy, cx, cy)
                .setTagLibrary(AprilTagGameDatabase.getCurrentGameTagLibrary())
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }
        builder.addProcessor(aprilTag);
        builder.enableLiveView(true);
        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
        visionPortal = builder.build();

        while (L.opModeInInit() && visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            telemetry.addLine("Camera init...");
            telemetry.update();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        exposure = visionPortal.getCameraControl(ExposureControl.class);
        gain = visionPortal.getCameraControl(GainControl.class);

        exposure.setMode(ExposureControl.Mode.Manual);
        exposure.setExposure(1, TimeUnit.MILLISECONDS);
        gain.setGain(255);
    }
    public void set_processor(){
        FtcDashboard.getInstance().startCameraStream(visionPortal, 30);
        visionPortal.setProcessorEnabled(aprilTag, true);
    }
    public void stop_stream(){
        FtcDashboard.getInstance().stopCameraStream();
        visionPortal.setProcessorEnabled(aprilTag, false);
        visionPortal.close();
    }
    @SuppressLint("DefaultLocale")
    public void telemetryAprilTag() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                // Only use tags that don't have Obelisk in them
                if (!detection.metadata.name.contains("Obelisk")) {
                    telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)",
                            detection.ftcPose.x,    //* 2.54 + shift[0],
                            detection.ftcPose.y,    //* 2.54 + shift[1],
                            detection.ftcPose.z));  //* 2.54 + shift[2]));
                    telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)",
                            detection.robotPose.getOrientation().getPitch(AngleUnit.DEGREES),
                            detection.robotPose.getOrientation().getRoll(AngleUnit.DEGREES),
                            detection.robotPose.getOrientation().getYaw(AngleUnit.DEGREES)));
                    telemetry.update();
                }
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
                telemetry.update();
            }
        }

        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.update();
    }
    public double[] get_position(){
        double x = 0, z = 0, ang = 0;
        double id = 0;
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                // Only use tags that don't have Obelisk in them
                if (!detection.metadata.name.contains("Obelisk")) {
                    x = detection.ftcPose.x * 2.54;
                    z = detection.ftcPose.z * 2.54;
                    ang = Math.atan2(x, z);
                    id = detection.id;
                } else id = detection.id;
            }
        }
        return new double[]{currentDetections.size(), x, z, ang, id};
    }
    public double get_tag_err(double kp, double kd){
        double[] pos = get_position();

        double err = pos[1];
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

        double err = pos[2];

        return err;
    }
    public void init_classes(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2,
                             LinearOpMode L){
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.L = L;
        initAprilTag();
    }
}
