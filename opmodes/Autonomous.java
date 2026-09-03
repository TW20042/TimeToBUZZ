package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.actions.sync.MoveEncoders;
import org.firstinspires.ftc.teamcode.modules.realizes.imu.ImuBNO055;
import org.firstinspires.ftc.teamcode.modules.realizes.localization.Encoders;
import org.firstinspires.ftc.teamcode.modules.realizes.localization.Point;
import org.firstinspires.ftc.teamcode.modules.realizes.wheelbase.MecanumWheelbase;

public class Autonomous extends LinearOpMode {
    ImuBNO055 imu = new ImuBNO055();
    MecanumWheelbase wheelbase = new MecanumWheelbase();
    Encoders encoders = new Encoders("lf", "rf");

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2, this)
                .setEncoders(encoders)
                .setWheelbase(wheelbase)
                .setImu(imu)
                .buildSystems();

        robot.doAction(new MoveEncoders(new Point(0, 0, 0), new Point(10, 10, 0)));
    }
}
