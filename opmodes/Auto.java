package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.Camera;
import org.firstinspires.ftc.teamcode.modules.IMU;
import org.firstinspires.ftc.teamcode.RobotBuild;
import org.firstinspires.ftc.teamcode.modules.Wheelbase;

@Config
@Autonomous(name = "Main_Autonomous")
public class Auto extends LinearOpMode {
    MultipleTelemetry multiple_telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(),
                                                                                            telemetry);
    ElapsedTime runtime = new ElapsedTime();
    RobotBuild r = new RobotBuild();
    Camera camera = new Camera();
    Wheelbase wheelbase = new Wheelbase();
    IMU imu = new IMU();

    @Override
    public void runOpMode() {
        r.init(hardwareMap, multiple_telemetry, gamepad1,
                gamepad2, this, imu, camera, wheelbase);

        //***< log ports and start stream >***
        wheelbase.reset_encoders();
        camera.set_processor(camera.USE_LIMELIGHT);

        waitForStart();

        camera.stop_stream();
    }
}