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
    MultipleTelemetry multiple_telemetry = new MultipleTelemetry(telemetry,
                    FtcDashboard.getInstance().getTelemetry());
    RobotBuild r = new RobotBuild();
    IMU imu = new IMU();
    Wheelbase wheelbase = new Wheelbase();
    Camera camera = new Camera();

    @Override
    public void runOpMode() {
        r.init(hardwareMap, multiple_telemetry, gamepad1,
                gamepad2, this, imu, camera, wheelbase);
        //***< log ports and reset encoders >***
        wheelbase.reset_encoders();
        wheelbase.telemetry_ports();

        waitForStart();
    }
}