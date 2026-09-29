package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.actions.sync.GrabControl;
import org.firstinspires.ftc.teamcode.actions.sync.RobotCentricControl;
import org.firstinspires.ftc.teamcode.actions.sync.ShooterControl;
import org.firstinspires.ftc.teamcode.modules.realizes.imu.ImuBNO055;
import org.firstinspires.ftc.teamcode.modules.realizes.wheelbase.MecanumWheelbase;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="MainTeleOp")
@Photon
public class TeleOp extends LinearOpMode {
    MecanumWheelbase wheelbase = new MecanumWheelbase();
    ImuBNO055 imu = new ImuBNO055();
    MultipleTelemetry multi_telemetry = new MultipleTelemetry(telemetry,
            FtcDashboard.getInstance().getTelemetry());
    @Override
    public void runOpMode() {
        Robot robot = new Robot(hardwareMap, multi_telemetry, gamepad1, gamepad2, this)
                .setWheelbase(wheelbase)
                .setImu(imu)
                .buildSecondary();

        robot.doAction(new GrabControl());
        robot.doAction(new ShooterControl());
        robot.doAction(new RobotCentricControl());

        waitForStart();
        while(opModeIsActive() && !robot.isFinished()){
            robot.update();
        }

        robot.stop();
    }
}