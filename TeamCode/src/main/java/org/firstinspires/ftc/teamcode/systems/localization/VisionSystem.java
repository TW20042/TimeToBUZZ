package org.firstinspires.ftc.teamcode.systems.localization;

import org.firstinspires.ftc.teamcode.RobotContext;
import org.firstinspires.ftc.teamcode.modules.abstractions.Camera;
import org.firstinspires.ftc.teamcode.systems.System;

public class VisionSystem extends System {
    private final Camera camera;
    private double lastError = 0, kp, kd;
    public double out = 0;
    public VisionSystem(RobotContext context){
        this.camera = context.camera;
    }
    public void setTagPD(double kp, double kd){
        this.kp = kp;
        this.kd = kd;
    }

    @Override
    public void update() {
        double[] pos = camera.getRawPos();

        double error = pos[0];

        double differential = error - lastError;

        double p = error * kp;
        double d = differential * kd;

        lastError = error;

        out = p + d;
    }
}
