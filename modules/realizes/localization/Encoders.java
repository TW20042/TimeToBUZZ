package org.firstinspires.ftc.teamcode.modules.realizes.localization;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.abstractions.Encoder;

public class Encoders extends Encoder {
    DcMotor leftEncoder;
    DcMotor rightEncoder;

    public Encoders(String leftEncName, String rightEncName){
        this.leftEncoder = hardwareMap.get(DcMotor.class, leftEncName);
        this.rightEncoder = hardwareMap.get(DcMotor.class, rightEncName);

        leftEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public double getDistance() {
        return Math.max(leftEncoder.getCurrentPosition(), rightEncoder.getCurrentPosition());
    }

    @Override
    public void initClasses(HardwareMap hardwareMap, Telemetry telemetry, LinearOpMode L) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.L = L;
    }
}
