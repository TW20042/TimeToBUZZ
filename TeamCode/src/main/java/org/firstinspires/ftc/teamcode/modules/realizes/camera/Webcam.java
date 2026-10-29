package org.firstinspires.ftc.teamcode.modules.realizes.camera;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.modules.abstractions.Camera;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Webcam extends Camera {
    WebcamName camera;
    AprilTagProcessor aprilTag;
    VisionPortal visionPortal;
    ExposureControl exposure;
    GainControl gain;
    final double fx = 1447.20666452;
    final double fy = 1445.36496334;
    final double cx = 938.27422;
    final double cy = 596.46596293;
    final Position cameraPosition = new Position(DistanceUnit.INCH,
            0, 0, 0, 0);
    final YawPitchRollAngles cameraOrientation = new YawPitchRollAngles(AngleUnit.DEGREES,
            0, 20, 0, 0);
    @Override
    public void initAprilTag() {
        aprilTag = new AprilTagProcessor.Builder()
                .setCameraPose(cameraPosition, cameraOrientation)
                //.setLensIntrinsics(fx, fy, cx, cy)
                .setTagLibrary(AprilTagGameDatabase.getCurrentGameTagLibrary())
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();

        builder.setCamera(camera);
        builder.addProcessor(aprilTag);
        builder.enableLiveView(true);
        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
        visionPortal = builder.build();

        while (L.opModeInInit() && visionPortal.getCameraState() !=
                VisionPortal.CameraState.STREAMING) {
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

    @Override
    public void setProcessor() {
        FtcDashboard.getInstance().startCameraStream(visionPortal, 30);
        visionPortal.setProcessorEnabled(aprilTag, true);
    }

    @Override
    public void stopStream() {
        FtcDashboard.getInstance().stopCameraStream();
        visionPortal.setProcessorEnabled(aprilTag, false);
        visionPortal.close();
    }

    @Override
    public double[] getRawPos() {
        double x = 0, z = 0;
        double id = 0;
//        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
//        telemetry.addData("# AprilTags Detected", currentDetections.size());
//        for (AprilTagDetection detection : currentDetections) {
//            if (detection.metadata != null) {
//                // Only use tags that don't have Obelisk in them
//                if (!detection.metadata.name.contains("Obelisk")) {
//                    x = detection.ftcPose.x * 2.54;
//                    z = detection.ftcPose.z * 2.54;
//                }
//                id = detection.id;
//            }
//        }
        return new double[] {x, z, id};
    }

    @Override
    public double[] get3DPos() {
        return new double[0];
    }

    @Override
    public void initClasses(HardwareMap hardwareMap, Telemetry telemetry,
                            LinearOpMode L) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.L = L;
        this.camera = hardwareMap.get(WebcamName.class, "Webcam 1");
    }
}
