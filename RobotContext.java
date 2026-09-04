package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.modules.abstractions.Camera;
import org.firstinspires.ftc.teamcode.modules.abstractions.Encoder;
import org.firstinspires.ftc.teamcode.modules.abstractions.IMU;
import org.firstinspires.ftc.teamcode.modules.abstractions.Wheelbase;
import org.firstinspires.ftc.teamcode.systems.EncodersSystem;
import org.firstinspires.ftc.teamcode.systems.MovingSystem;
import org.firstinspires.ftc.teamcode.systems.StabilizationSystem;
import org.firstinspires.ftc.teamcode.systems.VisionSystem;
import org.firstinspires.ftc.teamcode.systems.WithoutHeadSystem;

public class RobotContext {
    Camera camera;
    IMU imu;
    Wheelbase wheelbase;
    public Encoder encoders;
    public Gamepad gamepad1;
    public Gamepad gamepad2;
    public WithoutHeadSystem withoutHeadSystem;
    public EncodersSystem encodersSystem;
    public StabilizationSystem stabilizationSystem;
    public VisionSystem visionSystem;
    public MovingSystem movingSystem;
}





