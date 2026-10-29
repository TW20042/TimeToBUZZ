package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class Module {
    protected HardwareMap hardwareMap;
    protected Telemetry telemetry;
    public LinearOpMode L;
    public abstract void initClasses(HardwareMap hardwareMap, Telemetry telemetry,
                            LinearOpMode L);
}
