package org.firstinspires.ftc.teamcode.actions.sync;

import static java.lang.Math.pow;

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
        double x =  Math.signum(gamepad1.left_stick_x * (1 - gamepad1.right_trigger))*
                Math.pow(gamepad1.left_stick_x * (1 - gamepad1.right_trigger), 2);
        double y = Math.signum(gamepad1.left_stick_y * (1 - gamepad1.right_trigger))*
                Math.pow(gamepad1.left_stick_y * (1 - gamepad1.right_trigger), 2);
        double yaw = Math.signum(gamepad1.right_stick_x) * Math.pow(gamepad1.right_stick_x, 2);
        movingSystem.setInput(y, x, yaw);
    }

    @Override
    public void stop() {
        movingSystem.setInput(0, 0, 0);
    }

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
