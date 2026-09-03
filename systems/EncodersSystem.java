package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.abstractions.Encoder;
import org.firstinspires.ftc.teamcode.modules.realizes.localization.Point;

public class EncodersSystem {
    Encoder encoders;
    ElapsedTime runtime = new ElapsedTime();
    public double s = 0, sx = 0, sy = 0;
    double old_t = 0, integral = 0, lastError = 0;
    public EncodersSystem(Encoder encoders){
        if(encoders != null){
            this.encoders = encoders;
        }
    }

    public void setDistance(Point currentPosition, Point goalPosition){
        double tic_per_cm  = 30.458/480;

        double x1 = goalPosition.getX();
        double x = currentPosition.getX();
        double y1 = goalPosition.getY();
        double y = currentPosition.getY();

        x1                  /= tic_per_cm;
        x                   /= tic_per_cm;
        y1                  /= tic_per_cm;
        y                   /= tic_per_cm;

        sx            = x1 - x;
        sy            = y1 - y;

        s = Math.sqrt(Math.pow(sx, 2) + Math.pow(sy, 2));
        old_t = runtime.milliseconds(); integral = 0; lastError= 0;
    }

    public double[] getDistancePID(double kp, double ki, double kd, double headingError){
        double encodersValue = encoders.getDistance();
        double error = s - encodersValue;
        double now = runtime.milliseconds();

        double dt = (now - old_t);
        integral += error == 0 ? 0 : error * dt;

        double differential = (error - lastError) / dt;

        double p = error * kp;
        double i = integral * ki;
        double d = differential * kd;

        double axial    = sy/s * (p + i + d);
        double lateral  = sx/s * (p + i + d);
        double yaw      = headingError;

        lastError = error;
        old_t = now;

        return new double[] {axial, lateral, yaw};
    }
}
