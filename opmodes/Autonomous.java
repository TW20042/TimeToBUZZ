package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.actions.sync.PedroMovingTo;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="MainAutonomous")
public class Autonomous extends LinearOpMode {
    MultipleTelemetry multi_telemetry = new MultipleTelemetry(telemetry,
            FtcDashboard.getInstance().getTelemetry());
    @Override
    public void runOpMode() {
        Robot robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2, this)
                .setOdometry();

        robot.pedroMovingSystem.setPose(new Pose(0, 0, 0));
        PoseFactory poseFactory = PoseFactory.degrees();
        Pose start = poseFactory.of(0, 0, 0);
        Pose goal = poseFactory.of(24, 0, 0);

        Path path = Paths.line(start, goal).constant(start);

        robot.doAction(new PedroMovingTo(path));
        waitForStart();

        while(opModeIsActive() && !robot.isFinished()){
            multi_telemetry.addData("Current pos x: 2", robot.pedroMovingSystem.follower.pose().x());
            multi_telemetry.addData("Current pos y: ", robot.pedroMovingSystem.follower.pose().y());
            multi_telemetry.update();
            robot.update();
        }

        robot.stop();
    }
}
