package org.firstinspires.ftc.teamcode.systems.moving;

import org.firstinspires.ftc.teamcode.RobotContext;
import org.firstinspires.ftc.teamcode.modules.abstractions.IMU;
import org.firstinspires.ftc.teamcode.systems.System;

public class StabilizationSystem extends System {
    private final IMU imu;
    double lastError = 0;
    private double angle, kp, kd;
    public double out = 0;
    public StabilizationSystem(RobotContext context){
        this.imu = context.imu;
    }
    public void setTurnPD(double angle, double kp, double kd){
        this.angle = angle;
        this.kp = kp;
        this.kd = kd;
    }

    public void update(){
        double error = -(angle - imu.getAngle());

        double p = error * kp;
        double d = (error - lastError) * kd;

        out =  p + d;
    }
}
