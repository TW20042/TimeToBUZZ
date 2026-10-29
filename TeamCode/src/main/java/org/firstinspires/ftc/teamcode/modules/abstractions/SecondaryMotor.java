package org.firstinspires.ftc.teamcode.modules.abstractions;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.modules.Module;

public abstract class SecondaryMotor extends Module {
    protected DcMotor motor;
    public abstract void control(double power);
}
