package org.firstinspires.ftc.teamcode.actions.sync;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.RobotContext;
import org.firstinspires.ftc.teamcode.actions.LoopAction;
import org.firstinspires.ftc.teamcode.systems.moving.MovingSystem;

public class RobotCentricControl extends LoopAction {
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
        double x =  gamepad1.left_stick_x * (1 - gamepad1.right_trigger);
        double y = -gamepad1.left_stick_y * (1 - gamepad1.right_trigger);
        double yaw = gamepad1.right_stick_x;
        movingSystem.setInput(x, y, yaw);
    }

    @Override
    public void stop() {
        movingSystem.setInput(0, 0, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
