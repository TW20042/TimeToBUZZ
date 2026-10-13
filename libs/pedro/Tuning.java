package org.firstinspires.ftc.teamcode.libs.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.pedropathing.tuning.autotune.Procedure;
import com.pedropathing.tuning.autotune.Tuner;

import org.firstinspires.ftc.teamcode.libs.pedro.Constants;
import org.firstinspires.ftc.teamcode.libs.pedro.procedures.MecanumTuner;
import org.firstinspires.ftc.teamcode.libs.pedro.procedures.PinpointTuner;
import org.firstinspires.ftc.teamcode.libs.pedro.procedures.Tests;

public class Tuning {

    @Tuner
    public static Procedure mecanumTuner() {
        return new MecanumTuner();
    }

    @Tuner
    public static Procedure pinpointTuner() {
        return new PinpointTuner();
    }
    @Tuner
    public static Procedure tests() {
        return new Tests(
                hardwareMap ->
                        new Mecanum(hardwareMap, Constants.drivetrainConfig),
                hardwareMap ->
                        new PinpointLocalizer(
                                hardwareMap, Constants.localizerConfig),
                () -> new Foresight(Constants.foresightConfig)
        );
    }
}