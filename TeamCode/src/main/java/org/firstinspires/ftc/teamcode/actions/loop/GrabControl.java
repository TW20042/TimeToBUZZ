package org.firstinspires.ftc.teamcode.actions.loop;

import static java.lang.Math.min;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.RobotContext;
import org.firstinspires.ftc.teamcode.actions.LoopAction;
import org.firstinspires.ftc.teamcode.systems.secondary.GrabSystem;
import org.firstinspires.ftc.teamcode.utils.PowerSupplier;

public class GrabControl extends LoopAction {
    GrabSystem grabSystem;
    Gamepad gamepad1;
    @Override
    public void setContext(RobotContext robotContext) {
        PowerSupplier.setHardwareMap(robotContext.hardwareMap);
        this.grabSystem = robotContext.grabSystem;
        this.gamepad1 = robotContext.gamepad1;
    }

    @Override
    public void start() {}

    @Override
    public void update() {
        grabSystem.setPower(PowerSupplier.supplyVoltage(gamepad1.left_trigger
                + gamepad1.right_trigger * 0.67, 14));
    }

    @Override
    public void stop() {}

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isFailed() {
        return false;
    }

    @Override
    public void doIfFailed() {

    }
}
