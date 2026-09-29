package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.actions.tests.TestWheelbase;
import org.firstinspires.ftc.teamcode.modules.realizes.wheelbase.MecanumWheelbase;
import org.firstinspires.ftc.teamcode.utils.benchmark.Benchmark;
import org.firstinspires.ftc.teamcode.utils.benchmark.BenchmarkStats;

import java.util.List;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="testTeleOp")
@Photon
public class testTeleOp extends LinearOpMode {
    MecanumWheelbase wheelbase = new MecanumWheelbase();
    MultipleTelemetry multi_telemetry = new MultipleTelemetry(telemetry,
            FtcDashboard.getInstance().getTelemetry());
    @Override
    public void runOpMode() {
        Robot robot = new Robot(hardwareMap, multi_telemetry, gamepad1, gamepad2, this)
                .setWheelbase(wheelbase)
                .buildSecondary();

        robot.doAction(new TestWheelbase());

        Benchmark.clearSnapshots();
        Benchmark.setSnapshotLimit(5000);

        waitForStart();
        List<Float> snapshots = Benchmark.getSnapshots();
        while(opModeIsActive() && !robot.isFinished() && snapshots.size() < 5000){
            snapshots = Benchmark.getSnapshots();
            if (!snapshots.isEmpty()) {
                multi_telemetry.addData("Snapshot ", snapshots.get(snapshots.size() - 1));
            }
            multi_telemetry.update();
            robot.update();
        }

        Benchmark.sortSnapshots();
        multi_telemetry.addData("Median: ", BenchmarkStats.getMedian());
        multi_telemetry.addData("p90: ", BenchmarkStats.getPercentStat(0.9F));
        multi_telemetry.addData("p99: ", BenchmarkStats.getPercentStat(0.99F));
        multi_telemetry.addData("max: ", BenchmarkStats.getMax());
        multi_telemetry.update();

        robot.stop();
    }
}