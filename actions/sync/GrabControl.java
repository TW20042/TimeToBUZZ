package org.firstinspires.ftc.teamcode.actions.sync;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.RobotContext;
import org.firstinspires.ftc.teamcode.actions.Action;
import org.firstinspires.ftc.teamcode.actions.LoopAction;
import org.firstinspires.ftc.teamcode.actions.SyncAction;
import org.firstinspires.ftc.teamcode.systems.secondary.GrabSystem;

public class GrabControl extends LoopAction {
    GrabSystem grabSystem;
    Gamepad gamepad2;
    @Override
    public void setContext(RobotContext robotContext) {
        this.grabSystem = robotContext.grabSystem;
        this.gamepad2 = robotContext.gamepad2;
    }

    @Override
    public void start() {}

    @Override
    public void update() {
        grabSystem.setPower(gamepad2.left_stick_y);
    }

    @Override
    public void stop() {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
