package org.firstinspires.ftc.teamcode.actions.sync;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.RobotContext;
import org.firstinspires.ftc.teamcode.actions.Action;
import org.firstinspires.ftc.teamcode.actions.LoopAction;
import org.firstinspires.ftc.teamcode.systems.secondary.GrabSystem;
import org.firstinspires.ftc.teamcode.systems.secondary.ShootingSystem;

public class ShooterControl extends LoopAction {
    ShootingSystem shootingSystem;
    Gamepad gamepad2;
    @Override
    public void setContext(RobotContext robotContext) {
        this.shootingSystem = robotContext.shootingSystem;
        this.gamepad2 = robotContext.gamepad2;
    }

    @Override
    public void start() {}

    @Override
    public void update() {
        shootingSystem.setPower(gamepad2.right_stick_y);
    }

    @Override
    public void stop() {
        shootingSystem.setPower(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
