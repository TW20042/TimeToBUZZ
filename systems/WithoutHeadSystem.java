package org.firstinspires.ftc.teamcode.systems;

import org.firstinspires.ftc.teamcode.modules.abstractions.IMU;

public class WithoutHeadSystem {
    public IMU imu;
    double axial = 0, lateral = 0;
    public WithoutHeadSystem(IMU imu){
        if(imu != null){
            this.imu = imu;
        }
    }
    public double[] getAxis(double xControl, double yControl){

        //***< calculate angular data for headless mode >***
        double deg = imu.getAngle();
        double l_alpha = 90 + deg;
        double a_alpha = 90 - deg;

        //***< and convert it to radians >***
        double a_rads = Math.toRadians(a_alpha);
        double l_rads = Math.toRadians(l_alpha);

        lateral = xControl * Math.sin(l_rads) + yControl * Math.cos(a_rads);
        axial = xControl * Math.cos(l_rads) + yControl * Math.sin(a_rads);

        return new double[] {axial, lateral};
    }
}
