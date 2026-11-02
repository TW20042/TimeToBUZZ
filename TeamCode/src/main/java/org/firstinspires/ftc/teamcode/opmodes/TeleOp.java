package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.outoftheboxrobotics.photoncore.Photon;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.actions.loop.GrabControl;
import org.firstinspires.ftc.teamcode.actions.loop.RobotCentricControl;
import org.firstinspires.ftc.teamcode.actions.loop.TurretControl;
import org.firstinspires.ftc.teamcode.libs.pedro.DashboardDrawer;
import org.firstinspires.ftc.teamcode.modules.realizes.secondary.Grab;
import org.firstinspires.ftc.teamcode.modules.realizes.secondary.Turret;
import org.firstinspires.ftc.teamcode.modules.realizes.wheelbase.MecanumWheelbase;
import org.firstinspires.ftc.teamcode.utils.PowerSupplier;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="MainTeleOp")
@Photon
public class TeleOp extends LinearOpMode {
    MecanumWheelbase wheelbase = new MecanumWheelbase();
    Grab grab = new Grab();
    Turret turret = new Turret();
    MultipleTelemetry multi_telemetry = new MultipleTelemetry(telemetry,
            FtcDashboard.getInstance().getTelemetry());
    @Override
    public void runOpMode() {
        Robot robot = new Robot(hardwareMap, multi_telemetry, gamepad1, gamepad2, this)
                .setWheelbase(wheelbase)
                .buildGrab(grab)
                .buildTurretOnly(turret)
                .setOdometry();
        robot.pedroMovingSystem.setPose(new Pose(24, 0.7, 0));

        RobotCentricControl robotCentricControl = new RobotCentricControl();
        GrabControl grabControl = new GrabControl();
        TurretControl turretControl = new TurretControl();

        robot.doAction(robotCentricControl);
        robot.doAction(grabControl);
        robot.doAction(turretControl);

        waitForStart();
        while(opModeIsActive() && !robot.isFinished()){
            robot.update();
            multi_telemetry.addData("Voltage ", PowerSupplier.getVoltage());
            multi_telemetry.update();
            DashboardDrawer.drawRobot(robot.pedroMovingSystem.follower.pose());
        }

        robot.stop();
    }
}