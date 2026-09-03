package org.firstinspires.ftc.teamcode.modules.realizes.camera;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.modules.abstractions.Camera;

public class LimeLight extends Camera {
    Limelight3A limelight;
    @Override
    public void initAprilTag() {
        telemetry.setMsTransmissionInterval(11);
        this.limelight.pipelineSwitch(0);
    }

    @Override
    public void setProcessor() {
        limelight.start();
    }

    @Override
    public void stopStream() {
        limelight.stop();
        limelight.close();
    }

    @Override
    public double[] getRawPos() {
        double x = 0;
        double z = 0;
        double id = 0;
        LLResult result = limelight.getLatestResult();
        if (result != null) {
            if (result.isValid()) {
                x = result.getTx();
                z = result.getTa();
                id = result.getFiducialResults().get(0).getFiducialId();
            }
        }
        return new double[] {x, z, id};
    }

    @Override
    public double[] get3DPos() {
        LLResult result = limelight.getLatestResult();
        Pose3D botpose;
        double x = 0, y = 0, yaw = 0;
        if (result != null) {
            if (result.isValid()) {
                botpose = result.getBotpose();
                x       = botpose.getPosition().x;
                y       = botpose.getPosition().y;
                yaw     = botpose.getOrientation().getYaw();
            }
        }
        return new double[] {x, y, yaw};
    }

    @Override
    public void initClasses(HardwareMap hardwareMap, Telemetry telemetry,
                             LinearOpMode L){
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.L = L;
        this.limelight = hardwareMap.get(Limelight3A.class, "limelight");
    }
}
