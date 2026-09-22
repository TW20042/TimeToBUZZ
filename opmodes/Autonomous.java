package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.modules.realizes.imu.ImuBNO055;
import org.firstinspires.ftc.teamcode.modules.realizes.wheelbase.MecanumWheelbase;
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="MainAutonomous")
public class Autonomous extends LinearOpMode {
    ImuBNO055 imu = new ImuBNO055();
    MecanumWheelbase wheelbase = new MecanumWheelbase();
    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2, this)
                .setWheelbase(wheelbase)
                .setImu(imu);

    }
}
