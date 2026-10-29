package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.outoftheboxrobotics.photoncore.Photon;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.actions.sync.RobotCentricControl;
import org.firstinspires.ftc.teamcode.libs.pedro.DashboardDrawer;
import org.firstinspires.ftc.teamcode.modules.realizes.secondary.Grab;
import org.firstinspires.ftc.teamcode.modules.realizes.wheelbase.MecanumWheelbase;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="MainTeleOp")
@Photon
public class TeleOp extends LinearOpMode {
    MecanumWheelbase wheelbase = new MecanumWheelbase();
    Grab grab = new Grab();
    MultipleTelemetry multi_telemetry = new MultipleTelemetry(telemetry,
            FtcDashboard.getInstance().getTelemetry());
    @Override
    public void runOpMode() {
        Robot robot = new Robot(hardwareMap, multi_telemetry, gamepad1, gamepad2, this)
                .setWheelbase(wheelbase)
                .buildGrab(grab)
                .setOdometry();
        robot.pedroMovingSystem.setPose(new Pose(24, 0.7, 0));

        RobotCentricControl robotCentricControl = new RobotCentricControl();
        robot.doAction(robotCentricControl);

        waitForStart();
        while(opModeIsActive() && !robot.isFinished()){
            robot.update();
            //multi_telemetry.addData("Speed ", robotCentricControl.speed);
            //multi_telemetry.update();
            //DashboardDrawer.drawRobot(robot.pedroMovingSystem.follower.pose());
        }

        robot.stop();
    }
}