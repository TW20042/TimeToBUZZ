package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.actions.sync.GrabControl;
import org.firstinspires.ftc.teamcode.actions.sync.RobotCentricControl;
import org.firstinspires.ftc.teamcode.actions.sync.ShooterControl;
import org.firstinspires.ftc.teamcode.modules.realizes.imu.ImuBNO055;
import org.firstinspires.ftc.teamcode.modules.realizes.wheelbase.MecanumWheelbase;
import org.firstinspires.ftc.teamcode.utils.Benchmark;
import org.firstinspires.ftc.teamcode.utils.BenchmarkStats;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="MainTeleOp")
public class TeleOp extends LinearOpMode {
    MecanumWheelbase wheelbase = new MecanumWheelbase();
    ImuBNO055 imu = new ImuBNO055();
    MultipleTelemetry multi_telemetry = new MultipleTelemetry(telemetry,
            FtcDashboard.getInstance().getTelemetry());
    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(hardwareMap, multi_telemetry, gamepad1, gamepad2, this)
                .setWheelbase(wheelbase)
                .setImu(imu)
                .buildSecondary();

        robot.doAction(new GrabControl());
        robot.doAction(new ShooterControl());
        robot.doAction(new RobotCentricControl());

        Benchmark.clearSnapshots();
        Benchmark.setSnapshotLimit(5000);

        waitForStart();

        while(opModeIsActive() && !robot.isFinished()){
            double angle = imu.getAngle();
            multi_telemetry.addData("Angle: ", angle);
            multi_telemetry.update();
            robot.update();
        }

        Benchmark.sortSnapshots();
        BenchmarkStats.toMillis();
        multi_telemetry.addData("Median: ", BenchmarkStats.getMedian());
        multi_telemetry.addData("p90: ", BenchmarkStats.getPercentStat(0.9F));
        multi_telemetry.addData("max: ", BenchmarkStats.getMax());
        multi_telemetry.update();

        robot.stop();
    }
}