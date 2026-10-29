package org.firstinspires.ftc.teamcode.actions.sync;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.signum;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.RobotContext;
import org.firstinspires.ftc.teamcode.actions.LoopAction;
import org.firstinspires.ftc.teamcode.systems.moving.MovingSystem;
import org.firstinspires.ftc.teamcode.utils.SlewRateLimiter;

public class RobotCentricControl extends LoopAction {
    MovingSystem movingSystem;
    Gamepad gamepad1;
    public int speed = 2;
    boolean speedFlagLeft = true;
    boolean speedFlagRight = true;
    SlewRateLimiter slewRateLimiterX = new SlewRateLimiter(4);
    SlewRateLimiter slewRateLimiterY = new SlewRateLimiter(10);

    @Override
    public void setContext(RobotContext robotContext) {
        this.movingSystem = robotContext.movingSystem;
        this.gamepad1 = robotContext.gamepad1;
    }

    @Override
    public void start() {
    }

    @Override
    public void update() {
        if (gamepad1.right_bumper) {
            if (speedFlagRight) {
                speed += 1;
                gamepad1.rumbleBlips(speed);
                speed = min(speed, 3);
                speedFlagRight = false;
            }
        } else speedFlagRight = true;

        if (gamepad1.left_bumper) {
            if (speedFlagLeft) {
                speed -= 1;
                gamepad1.rumbleBlips(speed);
                speed = max(1, speed);
                speedFlagLeft = false;
            }
        } else speedFlagLeft = true;

        gamepad1.setLedColor(speed == 1? 1 : 0,
                             speed == 3? 1 : 0,
                             speed == 2? 1 : 0,
                                Integer.MAX_VALUE);

        double x = slewRateLimiterX.limitInput(
                gamepad1.left_stick_x * (speed * 0.33333));
        double y = slewRateLimiterY.limitInput(
                -gamepad1.left_stick_y * (speed * 0.33333));
        double yaw = gamepad1.right_stick_x * (speed * 0.33333);

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
