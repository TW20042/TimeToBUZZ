package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Camera;
import org.firstinspires.ftc.teamcode.Cannon;
import org.firstinspires.ftc.teamcode.IMU;
import org.firstinspires.ftc.teamcode.RobotBuild;
import org.firstinspires.ftc.teamcode.Wheelbase;

@Config
@Autonomous(name = "Main_Autonomous")
public class Auto extends LinearOpMode {
    MultipleTelemetry multi_telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(),
                                                                                            telemetry);
    ElapsedTime runtime = new ElapsedTime();
    public static double kd = 0.29;
    public static double ki = 0;
    public static double kp = 0.0035;
    @Override
    public void runOpMode() {
        RobotBuild r = new RobotBuild();
        Camera cam = new Camera();
        Wheelbase wheel = new Wheelbase();
        Cannon cannon = new Cannon();
        IMU imu = new IMU();
        r.init(hardwareMap, multi_telemetry, gamepad1,
                gamepad2, imu, cannon, cam, wheel, this);

        wheel.reset_encoders();
        waitForStart();
        cam.set_processor();
        //********************************************** s0
        //______________________________________________ m1
        r.move_xy(0, 0, 0, -53, 0, 0.003, ki, kd, 0.015);
        r.stable_camera(600);
        cannon.fw_control_np(1, 705);
        double[] detect = cam.get_position();
        r.alliance = detect[4];

        //********************************************** s1
        if(r.alliance == 20){
            //__________________________________________ m1
            r.turn(40, 0.02, 1000);
            r.move_xy(0, -52, 0, 37, 40, kp, ki, kd, 0.028);
            cannon.bw_control(1);
            r.delay(100);
            r.move_xy(0, 0, 0, 49, 40, 0.002, ki, 0.2, 0.028);
            cannon.bw_control(0);
            //__________________________________________ m2
            r.delay(100);
            r.move_xy(0, 0, 0, -49, 40, 0.002, ki, 0.2, 0.028);
            r.move_xy(0, 48, 0, -30, 40, kp, ki, kd, 0.028);
            r.turn(0, 0.02, 1000);
            r.stable_camera(600);
        //********************************************** s2
        }else if (r.alliance == 24){
            //__________________________________________ m1
            r.turn(-38, 0.02, 1000);
            r.move_xy(0, 48, 0, 38, -38, kp, ki, kd, 0.028);
            cannon.bw_control(1);
            r.delay(100);
            r.move_xy(0, 0, 0, 49, -38, 0.002, ki, 0.2, 0.028);
            cannon.bw_control(0);
            //__________________________________________ m2
            r.delay(100);
            r.move_xy(0, 0, 0, -30, -38, kp, ki, 0.2, 0.028);
            r.move_xy(0, -48, 0, -30, -38, kp, ki, kd, 0.028);
            r.turn(0, 0.02, 1000);
            r.stable_camera(600);
        }
        //********************************************** finally
        cannon.fw_control_np(1, 705);
        r.move_xy(0, -20, 0, 0, 0, kp, ki, kd, 0.02);
        cam.stop_stream();
    }
}