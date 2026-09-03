package org.firstinspires.ftc.teamcode.modules.realizes.wheelbase;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.abstractions.Wheelbase;

public class MecanumWheelbase extends Wheelbase {
    DcMotor leftFront;
    DcMotor leftBack;
    DcMotor rightFront;
    DcMotor rightBack;
    @Override
    public void drive(double axial, double lateral, double yaw) {

        double leftFrontPower = axial + lateral + yaw;
        double rightFrontPower = axial - lateral - yaw;
        double leftBackPower = axial - lateral + yaw;
        double rightBackPower = axial + lateral - yaw;

        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);
    }
    @Override
    public void initClasses(HardwareMap hardwareMap, Telemetry telemetry, LinearOpMode L) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.L = L;
        rightBack = hardwareMap.get(DcMotor.class, "rb");
        rightFront = hardwareMap.get(DcMotor.class, "rf");
        leftFront = hardwareMap.get(DcMotor.class, "lf");
        leftBack = hardwareMap.get(DcMotor.class, "lb");
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
    }


}
