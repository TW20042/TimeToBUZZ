package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Cannon {
    ExecutorService shooting_process = Executors.newCachedThreadPool();
    ElapsedTime runtime = new ElapsedTime();
    HardwareMap hardwareMap;
    Telemetry telemetry;
    Gamepad gamepad1;
    Gamepad gamepad2;
    LinearOpMode L;
    public DcMotorEx fw;
    public DcMotor bw;
    public Servo srv1;
    public boolean value = false, shoot_value = false, get_third, vibro;
    double old_t = runtime.milliseconds();
    double err_last = 0, integral = 0, P, I, D;

    public void init_classes(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2,
                             LinearOpMode L){
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.L = L;
        this.fw = hardwareMap.get(DcMotorEx.class, "c1");
        this.bw = hardwareMap.get(DcMotor.class, "c2");
        this.srv1 = hardwareMap.get(Servo.class, "shoot");
        fw.setDirection(DcMotorEx.Direction.REVERSE);
    }
    public void fw_control(double min_speed) {
        ExecutorService shooter_process = Executors.newCachedThreadPool();
        shooting_process.execute(() -> {
            while(L.opModeIsActive()){
                while(shoot_value) {
                    runtime.reset();
                    while(runtime.milliseconds() < 70) bw_control(-1);
                    bw_control(0);
                    srv1_control(0.5);
                    runtime.reset();
                    while(runtime.milliseconds() < 500);
                    srv1_control(0.96);
                    runtime.reset();
                    while(runtime.milliseconds() < 500) bw_control(1);
                    bw_control(0);
                }
                srv1_control(0.96);
            }
        });
    }
    public void fw_control_np(double power, double speed) {
        ExecutorService executor = Executors.newCachedThreadPool();
        value = true;
        ShooterPID_async(power, speed, 0.38, 0.00001, 0.08, executor);
        while(get_shooter_vel() <= speed - 60);
        telemetry.addData("value", value);
        telemetry.update();
        for (int i = 0; i < 3; i += 1) {
            runtime.reset();
            while(runtime.milliseconds() < 70) bw_control(-1);
            bw_control(0);
            srv1_control(0.5);
            runtime.reset();
            while(runtime.milliseconds() < 500);
            srv1_control(0.96);
            runtime.reset();
            while(runtime.milliseconds() < 500) bw_control(1);
            bw_control(0);
            telemetry.addData("shooted", i);
            telemetry.update();
        }
        value = false;
        executor.shutdown();
        try{
            if(!executor.awaitTermination(1, TimeUnit.SECONDS)){
                executor.shutdownNow();
            }
        } catch (InterruptedException e){
            executor.shutdownNow();
        }
        fw.setPower(0);
        telemetry.addData("value", value);
        telemetry.update();
    }
    public void ShooterPID_async(double power, double speed, double kP, double kI, double kD,
                                                                ExecutorService shooter_thread){
        shooter_thread.execute(() -> {
            while(value){
                double err = speed - get_shooter_vel();
                double now = runtime.milliseconds();

                double dt = (now - old_t);
                dt = Math.max(0.001, now - old_t);

                if (Math.abs(P + I + D) < 1) {
                    integral += err * dt;
                }

                double differential = (err - err_last) / dt;

                P = err * kP;
                I = integral * kI;
                D = differential * kD;

                shooter_control(power * (P + I + D), speed);

                err_last = err;
                old_t = now;
                telemetry.addData("real speed",get_shooter_vel());
                telemetry.addData("speed",speed);
                telemetry.addData("err", err);
                telemetry.update();
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void ShooterPID_sync(double power, double speed, double kP, double kI, double kD){
        double err = speed - get_shooter_vel();
        double now = runtime.milliseconds();

        double dt = (now - old_t);
        dt = Math.max(0.001, now - old_t);

        if (Math.abs(P + I + D) < 1) {
            integral += err * dt;
        }

        double differential = (err - err_last) / dt;

        P = err * kP;
        I = integral * kI;
        D = differential * kD;

        shooter_control(power * (P + I + D), speed);

        err_last = err;
        old_t = now;
        telemetry.addData("real speed",get_shooter_vel());
        telemetry.addData("speed",speed);
        telemetry.addData("err", err);
        telemetry.update();

        double time_delay = runtime.milliseconds();
        while(runtime.milliseconds() - time_delay < 2);
    }
    public void stop_shooting_process(){
        shooting_process.shutdown();
        try{
            if(!shooting_process.awaitTermination(1, TimeUnit.SECONDS)){
                shooting_process.shutdownNow();
            }
        } catch (InterruptedException e){
            shooting_process.shutdownNow();
        }
    }
    public void bw_control(double power){
        bw.setPower(power);
    }
    public void shooter_control(double power, double min_speed){
        if(get_shooter_vel() >= min_speed){
            if(vibro) gamepad2.rumble(200);
            vibro = false;
        } else vibro = true;

        fw.setPower(power);
    }
    public void srv1_control(double pos){
        srv1.setPosition(pos);
    }
    public double get_shooter_vel(){
        return fw.getVelocity();
    }
}