package org.firstinspires.ftc.teamcode.systems;

import org.firstinspires.ftc.teamcode.modules.abstractions.IMU;

public class StabilizationSystem {
    IMU imu;
    double lastError = 0;
    public StabilizationSystem(IMU imu){
        if(imu != null){
            this.imu = imu;
        }
    }
    public double getTurnPD(double angle, double kp, double kd){
        double error = -(angle - imu.getAngle());

        double p = error * kp;
        double d = (error - lastError) * kd;

        return p + d;
    }
}
