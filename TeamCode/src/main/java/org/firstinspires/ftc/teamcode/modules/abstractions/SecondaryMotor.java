package org.firstinspires.ftc.teamcode.modules.abstractions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.modules.Module;

public abstract class SecondaryMotor extends Module {
    public DcMotorEx motor;
    public abstract void control(double power);
}
