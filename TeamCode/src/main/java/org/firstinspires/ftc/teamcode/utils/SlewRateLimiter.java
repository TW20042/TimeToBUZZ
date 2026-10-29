package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.util.ElapsedTime;

public class SlewRateLimiter {
    private final double maxInput;
    private double lastOutput = 0;
    private double lastTime = System.currentTimeMillis();
    public SlewRateLimiter(double maxInput){
        this.maxInput = maxInput;
    }
    public double limitInput(double input){
        double elapsedTime = (System.currentTimeMillis() - lastTime) / 100;
        lastTime = System.currentTimeMillis();

        double maxStep = maxInput * elapsedTime;
        double error = input - lastOutput;

        if(error > maxStep) error = maxStep;
        else if(error < -maxStep) error = -maxStep;

        lastOutput += error;
        return lastOutput;
    }
}
