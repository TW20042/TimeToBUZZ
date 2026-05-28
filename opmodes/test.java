package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Camera;
import org.firstinspires.ftc.teamcode.modules.IMU;
import org.firstinspires.ftc.teamcode.RobotBuild;
import org.firstinspires.ftc.teamcode.modules.Wheelbase;

@Config
@TeleOp(name="test")
public class test extends LinearOpMode {
    MultipleTelemetry multiple_telemetry = new MultipleTelemetry(telemetry,
            FtcDashboard.getInstance().getTelemetry());
    RobotBuild r = new RobotBuild();
    IMU imu = new IMU();
    Wheelbase wheelbase = new Wheelbase();
    Camera camera = new Camera();

    @Override
    public void runOpMode() {
        r.init(hardwareMap, multiple_telemetry, gamepad1,
                gamepad2, this, imu, wheelbase, camera);

        camera.set_processor(camera.USE_LIMELIGHT);
        waitForStart(); //waiting for start this OpMode

        while (opModeIsActive()) {
            double[] pos = camera.get3Dpos(Camera.Units.CM);
            multiple_telemetry.addData("x: ", pos[0]);
            multiple_telemetry.addData("y: ", pos[1]);
            multiple_telemetry.addData("yaw: ", pos[2]);
            telemetry.update();
        }
        camera.stop_stream();
    }
}