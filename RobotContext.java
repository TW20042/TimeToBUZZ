package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.modules.abstractions.Camera;
import org.firstinspires.ftc.teamcode.modules.abstractions.Encoder;
import org.firstinspires.ftc.teamcode.modules.abstractions.IMU;
import org.firstinspires.ftc.teamcode.modules.abstractions.Wheelbase;
import org.firstinspires.ftc.teamcode.systems.EncodersSystem;
import org.firstinspires.ftc.teamcode.systems.StabilizationSystem;
import org.firstinspires.ftc.teamcode.systems.VisionSystem;
import org.firstinspires.ftc.teamcode.systems.WithoutHeadSystem;

public class RobotContext {
    public Camera camera = null;
    public IMU imu = null;
    public Wheelbase wheelbase = null;
    public Encoder encoders = null;
    public Gamepad gamepad1;
    public Gamepad gamepad2;
    public WithoutHeadSystem withoutHeadSystem;
    public EncodersSystem encodersSystem;
    public EncodersSystem localizationSystem;
    public StabilizationSystem stabilizationSystem;
    public VisionSystem visionSystem;
}
