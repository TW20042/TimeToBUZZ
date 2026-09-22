package org.firstinspires.ftc.teamcode.modules.realizes.secondary;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.abstractions.SecondaryMotor;

public class Grab extends SecondaryMotor {
    @Override
    public void control(double power) {
        motor.setPower(power);
    }

    @Override
    public void initClasses(HardwareMap hardwareMap, Telemetry telemetry, LinearOpMode L) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.L = L;
        this.motor = hardwareMap.get(DcMotor.class, "grab");
    }
}
