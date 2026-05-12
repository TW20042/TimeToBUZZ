package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.Camera;
import org.firstinspires.ftc.teamcode.modules.IMU;
import org.firstinspires.ftc.teamcode.RobotBuild;
import org.firstinspires.ftc.teamcode.modules.Wheelbase;
import java.lang.Math;
@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Main_TeleOp")
public class TeleOp extends LinearOpMode {
    Telemetry dashboard_telemetry = FtcDashboard.getInstance().getTelemetry();
    MultipleTelemetry multiple_telemetry = new MultipleTelemetry(telemetry, dashboard_telemetry);
    RobotBuild r = new RobotBuild();
    Wheelbase wheelbase = new Wheelbase();
    Camera camera = new Camera();
    IMU imu = new IMU();
    double axial, lateral, yaw;
    boolean flag;

    @Override
    public void runOpMode() throws InterruptedException {
        r.init(hardwareMap, telemetry, gamepad1,
                gamepad2, this, imu, camera, wheelbase);
        //***< log ports and start stream >***
        wheelbase.telemetry_ports();
        camera.set_processor();

        waitForStart(); //**< start an opMode! >***

        while(opModeIsActive()){
            camera.telemetryAprilTag(); //***< update camera pipeline >***
            //***< calculating stick vectors with the right trigger as a control factor >***
            double x =  gamepad1.left_stick_x * (1 - gamepad1.right_trigger);
            double y = -gamepad1.left_stick_y * (1 - gamepad1.right_trigger);

            //***< calculate angular data for headless mode >***
            double deg = imu.getTurnAngle();
            double l_alpha = 90 + deg;
            double a_alpha = 90 - deg;

            //***< and convert it to radians >***
            double a_rads = Math.toRadians(a_alpha);
            double l_rads = Math.toRadians(l_alpha);

            lateral = x * Math.sin(l_rads) + y * Math.cos(a_rads);

            boolean btn_a = gamepad1.a; //***< reading A-button >***
            //***< flag usage of gyro calibration to reset IMU once a pressing >***
            if(btn_a){
                if(flag) imu.calibrate_imu();
                flag = false;
            } else flag = true;

            if(gamepad1.right_bumper){  //***< camera stabilization mode >***
                axial = -(camera.get_distance()-18) * 0.025;
                yaw = camera.get_tag_err(0.006, 0.00025);
            }else {                     //***< clean "without head mode" block >***
                axial = x * Math.cos(l_rads) + y * Math.sin(a_rads);
                yaw = gamepad1.right_stick_x;
                multiple_telemetry.addData("Axial is", axial);
                multiple_telemetry.addData("Lateral is", lateral);
                multiple_telemetry.addData("Yaw is", yaw);
            }
            //***< calculate powers on the motors using axis modules >***
            double lfp = axial + lateral + yaw;
            double rfp = axial - lateral - yaw;
            double lbp = axial - lateral + yaw;
            double rbp = axial + lateral - yaw;
            wheelbase.setMPower(rbp, rfp, lfp, lbp);
            wheelbase.setZPB();

            //***< log needed data >***
            multiple_telemetry.addData("S0",0);
            multiple_telemetry.addData("Target speed",725);
            multiple_telemetry.addData("S900",900);
            multiple_telemetry.addData("Camera stabilization", gamepad1.right_bumper);
            multiple_telemetry.addData("Camera error", camera.get_tag_err(0.0058, 0.0001));
            multiple_telemetry.addData("Distance", camera.get_distance()-20);
            multiple_telemetry.update();
        }
        //***< finally, stop camera stream >***
        camera.stop_stream();
    }
}
