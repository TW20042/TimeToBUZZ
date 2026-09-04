package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.actions.Action;
import org.firstinspires.ftc.teamcode.modules.abstractions.Camera;
import org.firstinspires.ftc.teamcode.modules.abstractions.Encoder;
import org.firstinspires.ftc.teamcode.modules.abstractions.IMU;
import org.firstinspires.ftc.teamcode.modules.abstractions.Wheelbase;
import org.firstinspires.ftc.teamcode.systems.EncodersSystem;
import org.firstinspires.ftc.teamcode.systems.MovingSystem;
import org.firstinspires.ftc.teamcode.systems.StabilizationSystem;
import org.firstinspires.ftc.teamcode.systems.VisionSystem;
import org.firstinspires.ftc.teamcode.systems.WithoutHeadSystem;

public class Robot extends RobotContext{
    HardwareMap hardwareMap;
    Telemetry telemetry;
    LinearOpMode linearOpMode;
    public Robot(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2,
                                                                        LinearOpMode linearOpMode){
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.linearOpMode = linearOpMode;
    }
    public Robot setCamera(Camera camera){
        this.camera = camera;
        camera.initClasses(hardwareMap, telemetry, linearOpMode);
        this.visionSystem = new VisionSystem(camera);
        return this;
    }
    public Robot setWheelbase(Wheelbase wheelbase){
        this.wheelbase = wheelbase;
        wheelbase.initClasses(hardwareMap, telemetry, linearOpMode);
        this.movingSystem = new MovingSystem(wheelbase);
        return this;
    }
    public Robot setImu(IMU imu){
        this.imu = imu;
        imu.initClasses(hardwareMap, telemetry, linearOpMode);
        this.stabilizationSystem = new StabilizationSystem(imu);
        this.withoutHeadSystem = new WithoutHeadSystem(imu);
        return this;
    }
    public Robot setEncoders(Encoder encoders){
        this.encoders = encoders;
        encoders.initClasses(hardwareMap, telemetry, linearOpMode);
        this.encodersSystem = new EncodersSystem(encoders);
        return this;
    }
    public void doAction(Action action){
        action.setContext(this);
        action.execute();
    }
}
