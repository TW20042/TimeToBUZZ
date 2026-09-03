package org.firstinspires.ftc.teamcode.systems;

import org.firstinspires.ftc.teamcode.modules.abstractions.Camera;

public class VisionSystem {
    Camera camera;
    double lastError = 0;
    public VisionSystem(Camera camera){
        if(camera != null){
            this.camera = camera;
        }
    }
    public double getTagPD(double kp, double kd){
        double[] pos = camera.getRawPos();

        double error = pos[0];

        double differential = error - lastError;

        double p = error * kp;
        double d = differential * kd;

        lastError = error;

        return p + d;
    }
}
