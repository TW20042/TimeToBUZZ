package org.firstinspires.ftc.teamcode.modules.realizes.localization;

public class Point {
    private double[] data = {};

    public Point(double x, double y, double heading){
        this.data = new double[] {x, y, heading};
    }

    public double getX(){
        return data[0];
    }

    public double getY() {
        return data[1];
    }

    public double getHeading(){
        return data[2];
    }
}
