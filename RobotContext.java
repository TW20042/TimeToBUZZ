package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

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

import java.util.ArrayList;
import java.util.List;

public class RobotContext {
    public ActionScheduler actionScheduler;
    final List<System> systems = new ArrayList<>();
    public Camera camera;
    public IMU imu;
    public Wheelbase wheelbase;
    public Shooter shooter;
    public Grab grab;
    public GrabSystem grabSystem;
    public ShootingSystem shootingSystem;
    public Gamepad gamepad1;
    public Gamepad gamepad2;
    public StabilizationSystem stabilizationSystem;
    public VisionSystem visionSystem;
    public MovingSystem movingSystem;
}





