package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.Camera;
import org.firstinspires.ftc.teamcode.modules.IMU;
import org.firstinspires.ftc.teamcode.RobotBuild;
import org.firstinspires.ftc.teamcode.modules.Wheelbase;

@Config
@Autonomous(name = "Main_Autonomous")
public class Auto extends LinearOpMode {
    MultipleTelemetry multi_telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(),
                                                                                            telemetry);
    ElapsedTime runtime = new ElapsedTime();
    public static double kd = 0.29;
    public static double ki = 0;
    public static double kp = 0.0035;
    @Override
    public void runOpMode() {
        RobotBuild r = new RobotBuild();
        Camera cam = new Camera();
        Wheelbase wheel = new Wheelbase();
        IMU imu = new IMU();
        r.init(hardwareMap, multi_telemetry, gamepad1,
                gamepad2, this, imu, cam, wheel);

        wheel.reset_encoders();
        waitForStart();
        cam.set_processor();

    }
}