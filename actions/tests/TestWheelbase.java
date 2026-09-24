package org.firstinspires.ftc.teamcode.actions.tests;

import com.qualcomm.robotcore.hardware.Gamepad;


import org.firstinspires.ftc.teamcode.RobotContext;
import org.firstinspires.ftc.teamcode.actions.LoopAction;
import org.firstinspires.ftc.teamcode.systems.moving.MovingSystem;

public class TestWheelbase extends LoopAction {
    MovingSystem movingSystem;
    Gamepad gamepad1;
    @Override
    public void setContext(RobotContext robotContext) {
        this.movingSystem = robotContext.movingSystem;
        this.gamepad1 = robotContext.gamepad1;
    }

    @Override
    public void start() {}

    @Override
    public void update() {
        movingSystem.setInput(0, 1, 0);
    }

    @Override
    public void stop() {
        movingSystem.setInput(0, 0, 0);
    }

    @Override
    public boolean isFinished() {
        return gamepad1.cross;
    }

}
