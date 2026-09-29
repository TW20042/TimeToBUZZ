package org.firstinspires.ftc.teamcode.modules.realizes.wheelbase;

import com.outoftheboxrobotics.photoncore.hardware.motor.PhotonDcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.abstractions.Wheelbase;

public class MecanumWheelbase extends Wheelbase {
    PhotonDcMotor leftFront;
    PhotonDcMotor leftBack;
    public PhotonDcMotor rightFront;
    PhotonDcMotor rightBack;
    @Override
    public void drive(double axial, double lateral, double yaw) {
        double frontLeftPower  = axial + lateral + yaw;
        double frontRightPower = axial - lateral + yaw;
        double backLeftPower   = axial - lateral - yaw;
        double backRightPower  = axial + lateral - yaw;

        leftFront.setPower(frontLeftPower);
        leftBack.setPower(backLeftPower);
        rightFront.setPower(frontRightPower);
        rightBack.setPower(backRightPower);
    }
    @Override
    public void initClasses(HardwareMap hardwareMap, Telemetry telemetry, LinearOpMode L) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.L = L;
        rightBack = (PhotonDcMotor) hardwareMap.dcMotor.get("rb");
        rightFront = (PhotonDcMotor) hardwareMap.dcMotor.get("rf");
        leftFront = (PhotonDcMotor) hardwareMap.dcMotor.get("lf");
        leftBack = (PhotonDcMotor) hardwareMap.dcMotor.get("lb");
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setMode(PhotonDcMotor.RunMode.RUN_USING_ENCODER);
    }


}
