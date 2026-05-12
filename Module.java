package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class Module {
    protected ElapsedTime runtime = new ElapsedTime();
    protected HardwareMap hardwareMap;
    protected Telemetry telemetry;
    protected Gamepad gamepad1, gamepad2;
    public LinearOpMode L;

    //***< all the classes need to have this method to initialize them in RobotBuild >***
    public abstract void init_classes(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2,
                      LinearOpMode L);

    //**< as sleep, but catching InterruptedException >***
    public void delay(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
