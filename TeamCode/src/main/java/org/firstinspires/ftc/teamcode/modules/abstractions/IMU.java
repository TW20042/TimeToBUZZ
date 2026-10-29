package org.firstinspires.ftc.teamcode.modules.abstractions;

import org.firstinspires.ftc.teamcode.modules.Module;

public abstract class IMU extends Module {
    public abstract double getAngle();
    public abstract void calibrate();
}
