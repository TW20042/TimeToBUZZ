package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.IMU;
import org.firstinspires.ftc.teamcode.RobotBuild;
import org.firstinspires.ftc.teamcode.modules.Wheelbase;
import org.firstinspires.ftc.teamcode.modules.odometry.Odometry;

@Config
@Autonomous(name="test_Autonomous")
public class test_auto extends LinearOpMode {
    MultipleTelemetry multiple_telemetry = new MultipleTelemetry(telemetry,
                    FtcDashboard.getInstance().getTelemetry());
    RobotBuild r = new RobotBuild();
    IMU imu = new IMU();
    Wheelbase wheelbase = new Wheelbase();
    Odometry odometry = new Odometry();

    @Override
    public void runOpMode() {
        r.init(hardwareMap, multiple_telemetry, gamepad1,
                gamepad2, this, imu, wheelbase, odometry);
        //***< log ports and reset encoders >***
        wheelbase.reset_encoders();
        wheelbase.telemetry_ports();
        odometry.create_follower();

        waitForStart();

        Pose start      = new Pose(0, 0);
        Pose control    = new Pose(1, 1);
        Pose end        = new Pose(2, 2);
        Path path       = new Path(new BezierCurve(start, control, end));
        odometry.go_to_path(path);
    }
}