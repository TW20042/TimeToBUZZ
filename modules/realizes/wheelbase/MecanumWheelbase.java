package org.firstinspires.ftc.teamcode.modules.realizes.wheelbase;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.abstractions.Wheelbase;
import org.firstinspires.ftc.teamcode.utils.Benchmark;

public class MecanumWheelbase extends Wheelbase {
    DcMotor leftFront;
    DcMotor leftBack;
    DcMotor rightFront;
    DcMotor rightBack;
    @Override
    public void drive(double axial, double lateral, double yaw) {

        double frontLeftPower  = axial + lateral + yaw;
        double frontRightPower = axial - lateral + yaw;
        double backLeftPower   = axial - lateral - yaw;
        double backRightPower  = axial + lateral - yaw;

        float start = Benchmark.getSnapshot();
        leftFront.setPower(frontLeftPower);
        float end = Benchmark.getSnapshot();
        leftBack.setPower(backLeftPower);
        rightFront.setPower(frontRightPower);
        rightBack.setPower(backRightPower);
        float compareResult = Benchmark.compareSnapshots(start, end);
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
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
    }


}
