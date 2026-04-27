package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.Camera;
import org.firstinspires.ftc.teamcode.modules.IMU;
import org.firstinspires.ftc.teamcode.RobotBuild;
import org.firstinspires.ftc.teamcode.modules.Wheelbase;
@Config
@Autonomous(name="test_Autonomous")
public class test_auto extends LinearOpMode {
    public static double kd = 0.25;
    public static double ki = 0;
    public static double kp = 0.0021;
    MultipleTelemetry multiple_telemetry = new MultipleTelemetry(telemetry,
                    FtcDashboard.getInstance().getTelemetry());
    @Override
    public void runOpMode() {
        RobotBuild r = new RobotBuild();
        IMU imu = new IMU();
        Wheelbase wheel = new Wheelbase();
        Camera cam = new Camera();
        r.init(hardwareMap, multiple_telemetry, gamepad1,
                gamepad2, this, imu, cam, wheel);
        wheel.reset_encoders();
        wheel.telemetry_ports();
        waitForStart();
    }
}