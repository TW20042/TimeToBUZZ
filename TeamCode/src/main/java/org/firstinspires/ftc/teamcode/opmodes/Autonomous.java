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
        Pose start = poseFactory.of(24, 0.7, 0);
        Pose goal = poseFactory.of(72, 72, 0);

        Path path = Paths.line(start, goal).constant(start);

        robot.doAction(new PedroMovingTo(path));
        waitForStart();

        while(opModeIsActive() && !robot.isFinished()){
            robot.update();

            DashboardDrawer.drawRobot(robot.pedroMovingSystem.follower.pose());
        }

        robot.stop();
    }
}
