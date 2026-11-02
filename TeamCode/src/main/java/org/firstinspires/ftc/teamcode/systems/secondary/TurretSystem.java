package org.firstinspires.ftc.teamcode.systems.secondary;

import org.firstinspires.ftc.teamcode.RobotContext;
import org.firstinspires.ftc.teamcode.modules.abstractions.SecondaryMotor;
import org.firstinspires.ftc.teamcode.systems.System;

public class TurretSystem extends System {
    public final SecondaryMotor turret;
    private double lastError = 0;
    private double power = 0;
    public TurretSystem(RobotContext context){
        this.turret = context.turret;
    }
    public void setPower(double power){
        this.power = power;
    }
    @Override
    public void update(){
        double kp = 0.01;
        double kd = 0.001;

        double error = 2115-turret.motor.getCurrentPosition();

        double differential = error - lastError;


        double p = error * kp;
        double d = differential * kd;

        lastError = error;

        double out = p + d;

        turret.control(out);
    }
}
