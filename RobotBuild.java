package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.Camera;
import org.firstinspires.ftc.teamcode.modules.IMU;
import org.firstinspires.ftc.teamcode.modules.Wheelbase;

public class RobotBuild {
    public HardwareMap hardwareMap;
    public Telemetry telemetry;
    public Gamepad gamepad1, gamepad2;
    public LinearOpMode L;
    public ElapsedTime runtime;
    IMU Imu;
    Wheelbase wb;
    Camera cam;
    public double alliance;
    public void init(HardwareMap hardwareMap, Telemetry telemetry,
                     Gamepad gamepad1, Gamepad gamepad2, LinearOpMode L, Module... classes) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.L = L;
        this.runtime = new ElapsedTime();
        for (Module clazz : classes){
            if (clazz != null){
                clazz.init_classes(hardwareMap, telemetry, gamepad1, gamepad2, L);
            }
        }
    }

    public void stable(double a, double l, double stable, long time, double kt) {
        runtime.reset();
        while (L.opModeIsActive() && runtime.milliseconds() < time) {
            double getangle = Imu.get_st_err(stable, kt);
            double axial = a;
            double lateral = l;
            double yaw = getangle * kt;

            double lfp = axial + lateral + yaw;
            double rfp = axial - lateral - yaw;
            double lbp = axial - lateral + yaw;
            double rbp = axial + lateral - yaw;

            wb.setMPower(rbp, rfp, lfp, lbp);
        }
        wb.setMPower(0, 0, 0, 0);
        wb.setZPB();
    }

    void stable180(double a, double l, double stable, long time, double kt) {
        runtime.reset();
        double getangle = 0;
        while (L.opModeIsActive() && runtime.milliseconds() < time) {
            if (Imu.getTurnAngle() > 0) {
                getangle = stable - Imu.getTurnAngle();
            }
            if (Imu.getTurnAngle() < 0) {
                getangle = -stable - Imu.getTurnAngle();
            }
            double axial = a;
            double lateral = l;
            double yaw = getangle * kt;

            double lfp = axial + lateral + yaw;
            double rfp = axial - lateral - yaw;
            double lbp = axial - lateral + yaw;
            double rbp = axial + lateral - yaw;

            wb.setMPower(rbp, rfp, lfp, lbp);
        }
        wb.setMPower(0, 0, 0, 0);
        wb.setZPB();
    }
    public void stable_camera(double time) {//Функция поворота
        double yaw;
        double axial = 0;
        runtime.reset();
        while (L.opModeIsActive() && runtime.milliseconds() < time) {
            //Вычисление угла стабилизации
            axial = -(cam.get_distance()-18) * 0.021;
            yaw = cam.get_tag_err(0.0062, 0.0001);
            //Вычисление мощности
            double lfp = (axial+yaw);
            double rfp = (axial-yaw);
            double lbp = (axial+yaw);
            double rbp = (axial-yaw);
            double grd_tel = Imu.getTurnAngle();

            wb.setMPower(rbp, rfp, lfp, lbp);
            telemetry.addData("Now is (degrees):", "%4f", grd_tel);
            telemetry.update();
        }
        wb.setMPower(0, 0, 0, 0);
        wb.setZPB();
    }
    public void move_xy(double x, double x1, double y, double y1, double angle,
                                                        double kp, double ki, double kd, double kt){
        wb.reset_encoders();
        double tic_per_cm  = 30.458/480;
        x1                  /= tic_per_cm;
        x                   /= tic_per_cm;
        y1                  /= tic_per_cm;
        y                   /= tic_per_cm;

        double sx            = x1 - x;
        double sy            = y1 - y;

        double s = Math.sqrt(Math.pow(sx, 2) + Math.pow(sy, 2));

        double old_t = runtime.milliseconds(), integral = 0, err_last = 0;

        while((Math.abs(wb.get_enc_pos()) < s && Math.abs(wb.get_enc_pos_res()) < s) && L.opModeIsActive()) {
            double[] detect = cam.get_position();
            telemetry.addData("Detected id: ", detect[4]);
            if(detect[4] != 0){
                alliance = detect[4];
            }
            double enc_value = Math.max(Math.abs(wb.get_enc_pos()),
                                        Math.abs(wb.get_enc_pos_res()));
            double error = s - enc_value;
            double now = runtime.milliseconds();

            double dt = (now - old_t);
            integral += error == 0 ? 0 : error * dt;

            double differential = (error - err_last) / dt;

            double p = error * kp;
            double i = integral * ki;
            double d = differential * kd;

            double axial    = sy/s * (p + i + d);
            double lateral  = sx/s * p;
            double yaw      = Imu.get_st_err(angle, kt);

            double lfp = axial + lateral + yaw;
            double rfp = axial - lateral - yaw;
            double lbp = axial - lateral + yaw;
            double rbp = axial + lateral - yaw;

            wb.setMPower(rbp, rfp, lfp, lbp);

            telemetry.addData("Now is (tics), needs:", "%4f, %4f", Math.abs(wb.get_enc_pos()),
                                                                                                    s);
            telemetry.addData("Angle is", "%4f, needs %4f",
                                                            Imu.getTurnAngle(), angle);
            telemetry.addData("Stable error", "%4f",
                                                Imu.get_st_err(angle, kt));
            telemetry.addData("ALY: ", "%4f, %4f, %4f", axial, lateral, yaw);
            telemetry.update();

            err_last = error;
            old_t = now;
        }
        stable(0, 0, angle, 200, 0.009);
        wb.setMPower(0, 0, 0, 0);
        wb.setZPB();
    }
    public void delay(long millis){
        try{
            Thread.sleep(millis);
        } catch (InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }
}