package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.actions.sync.PedroMovingTo;
import org.firstinspires.ftc.teamcode.libs.pedro.DashboardDrawer;

@Config
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="MainAutonomous")
public class Autonomous extends LinearOpMode {
    MultipleTelemetry multi_telemetry = new MultipleTelemetry(telemetry,
            FtcDashboard.getInstance().getTelemetry());

    @Override
    public void runOpMode() {
        Robot robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2, this)
                .setOdometry();

        robot.pedroMovingSystem.setPose(new Pose(24, 0.7, 0));
        PoseFactory poseFactory = PoseFactory.degrees();
        Pose pose1 = poseFactory.of(0, 0, 0);
        Pose pose2 = poseFactory.of(48, 0, 90);
        Pose pose3 = poseFactory.of(48, 48, 180);
        Pose pose4 = poseFactory.of(0, 48, 270);

        Path path1 = Paths.line(pose1, pose2).linear(pose1, pose2);
        Path path2 = Paths.line(pose2, pose3).linear(pose1, pose2);
        Path path3 = Paths.line(pose3, pose4).linear(pose3, pose4);
        Path path4 = Paths.line(pose4, pose1).linear(pose4, pose1);

        robot.doAction(new PedroMovingTo(path1));
        robot.doAction(new PedroMovingTo(path2));
        robot.doAction(new PedroMovingTo(path3));
        robot.doAction(new PedroMovingTo(path4));
        waitForStart();

        while(opModeIsActive() && !robot.isFinished()){
            robot.update();

            DashboardDrawer.drawRobot(robot.pedroMovingSystem.follower.pose());
        }

        robot.stop();
    }
}
