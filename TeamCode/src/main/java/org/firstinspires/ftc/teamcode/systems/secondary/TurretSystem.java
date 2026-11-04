package org.firstinspires.ftc.teamcode.systems.secondary;

import org.firstinspires.ftc.teamcode.RobotContext;
import org.firstinspires.ftc.teamcode.modules.abstractions.SecondaryMotor;
import org.firstinspires.ftc.teamcode.systems.System;

public class TurretSystem extends System {
    public final SecondaryMotor turret;
    private double lastError = 0;
    private double target = 0;
    public TurretSystem(RobotContext context){
        this.turret = context.turret;
    }
    public void setTarget(double target){
        this.target = target;
    }
    @Override
    public void update(){
        double kp = 0.015;
        double kd = 0.009;

        double error = this.target-turret.motor.getCurrentPosition();

        double differential = error - lastError;


        double p = error * kp;
        double d = differential * kd;

        lastError = error;

        double out = p + d;

        turret.control(out);
    }
}
