package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.actions.AsyncAction;
import org.firstinspires.ftc.teamcode.actions.LoopAction;
import org.firstinspires.ftc.teamcode.actions.SyncAction;
import org.firstinspires.ftc.teamcode.modules.abstractions.Camera;
import org.firstinspires.ftc.teamcode.modules.abstractions.IMU;
import org.firstinspires.ftc.teamcode.modules.abstractions.Wheelbase;
import org.firstinspires.ftc.teamcode.modules.realizes.secondary.Grab;
import org.firstinspires.ftc.teamcode.modules.realizes.secondary.Shooter;
import org.firstinspires.ftc.teamcode.systems.System;
import org.firstinspires.ftc.teamcode.systems.moving.MovingSystem;
import org.firstinspires.ftc.teamcode.systems.moving.StabilizationSystem;
import org.firstinspires.ftc.teamcode.systems.localization.VisionSystem;
import org.firstinspires.ftc.teamcode.systems.secondary.GrabSystem;
import org.firstinspires.ftc.teamcode.systems.secondary.ShootingSystem;

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
        this.actionScheduler = new ActionScheduler();
    }
    public Robot setCamera(Camera camera){
        this.camera = camera;
        camera.initClasses(hardwareMap, telemetry, linearOpMode);
        this.visionSystem = new VisionSystem(this);
        addSystem(visionSystem);
        return this;
    }
    public Robot setWheelbase(Wheelbase wheelbase){
        this.wheelbase = wheelbase;
        wheelbase.initClasses(hardwareMap, telemetry, linearOpMode);
        this.movingSystem = new MovingSystem(this);
        addSystem(movingSystem);
        return this;
    }
    public Robot setImu(IMU imu){
        this.imu = imu;
        imu.initClasses(hardwareMap, telemetry, linearOpMode);
        this.stabilizationSystem = new StabilizationSystem(this);
        addSystem(stabilizationSystem);
        return this;
    }
    public Robot buildSecondary(){
        this.shooter = new Shooter();
        this.grab = new Grab();
        this.shooter.initClasses(hardwareMap, telemetry, linearOpMode);
        this.grab.initClasses(hardwareMap, telemetry, linearOpMode);

        this.shootingSystem = new ShootingSystem(this);
        this.grabSystem = new GrabSystem(this);
        addSystem(shootingSystem);
        addSystem(grabSystem);

        return this;
    }
    public void doAction(SyncAction action){
        action.setContext(this);
        actionScheduler.addAction(action);
    }
    public void doAction(AsyncAction action){
        action.setContext(this);
        actionScheduler.addAction(action);
    }
    public void doAction(LoopAction action){
        action.setContext(this);
        actionScheduler.addAction(action);
    }
    public void update(){
        actionScheduler.updateActions();
        for(System system : systems){
            system.update();
        }
    }
    public void stop(){
        actionScheduler.stop();
    }
    public void addSystem(System system){
        systems.add(system);
    }
    public boolean isFinished(){
        return actionScheduler.isFinished();
    }
}
