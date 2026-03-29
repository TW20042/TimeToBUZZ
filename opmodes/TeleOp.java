package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Camera;
import org.firstinspires.ftc.teamcode.Cannon;
import org.firstinspires.ftc.teamcode.IMU;
import org.firstinspires.ftc.teamcode.RobotBuild;
import org.firstinspires.ftc.teamcode.Wheelbase;
import java.lang.Math;
@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Main_TeleOp")
public class TeleOp extends LinearOpMode {
    boolean flag;
    double axial, lateral, yaw, min_speed = 1600;
    Telemetry dash_tel = FtcDashboard.getInstance().getTelemetry();
    MultipleTelemetry multiple_tel = new MultipleTelemetry(telemetry, dash_tel);

    @Override
    public void runOpMode() throws InterruptedException {
        RobotBuild r = new RobotBuild();
        IMU imu = new IMU();
        Cannon cannon = new Cannon();
        Camera cam = new Camera();
        Wheelbase wheel = new Wheelbase();
        r.init(hardwareMap, telemetry, gamepad1,
                gamepad2, imu, cannon, cam, wheel, this);

        wheel.telemetry_ports();
        cam.set_processor();
        cannon.srv1_control(0.96);
        waitForStart();
        cannon.fw_control(2000);
        while(opModeIsActive()){
            cam.telemetryAprilTag();
            double x =  gamepad1.left_stick_x * (1 - gamepad1.right_trigger);
            double y = -gamepad1.left_stick_y * (1 - gamepad1.right_trigger);

            double deg = imu.getTurnAngle();
            double l_alpha = 90 + deg;
            double a_alpha = 90 - deg;

            double a_rads = Math.toRadians(a_alpha);
            double l_rads = Math.toRadians(l_alpha);

            lateral = x * Math.sin(l_rads) + y * Math.cos(a_rads);

            boolean btn_a = gamepad1.a;
            if(btn_a){
                if(flag) imu.calibrate_imu();
                flag = false;
            } else flag = true;

            // Проверка стабизизации
            if(gamepad1.right_bumper){
                axial = -(cam.get_distance()-18) * 0.025;
                yaw = cam.get_tag_err(0.006, 0.00025);
            }else { //without head
                axial = x * Math.cos(l_rads) + y * Math.sin(a_rads);
                yaw = gamepad1.right_stick_x;

                multiple_tel.addData("Axial is", axial);
                multiple_tel.addData("Lateral is", lateral);
                multiple_tel.addData("Yaw is", yaw);
                //telemetry.addData("Shooter velocity", cannon.get_shooter_vel());
            }
            double lfp = axial + lateral + yaw;
            double rfp = axial - lateral - yaw;
            double lbp = axial - lateral + yaw;
            double rbp = axial + lateral - yaw;

            cannon.shoot_value = gamepad2.right_bumper;
            cannon.ShooterPID_sync(gamepad2.left_stick_y, 705, 0.4, 0.00001, 0.075);
            cannon.bw_control(gamepad2.right_stick_y);

            wheel.setMPower(rbp, rfp, lfp, lbp);
            wheel.setZPB();
            multiple_tel.addData("S0",0);
            multiple_tel.addData("Starget speed",725);
            multiple_tel.addData("S900",900);
            multiple_tel.addData("Camera stabilization", gamepad1.right_bumper);
            multiple_tel.addData("Camera error", cam.get_tag_err(0.0058, 0.0001));
            multiple_tel.addData("Shooter speed", cannon.get_shooter_vel());
            multiple_tel.addData("Distance", cam.get_distance()-20);
            multiple_tel.update();
            //wheel.telemetry_power();
        }
        cannon.stop_shooting_process();
        cam.stop_stream();
    }
}
