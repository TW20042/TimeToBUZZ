package org.firstinspires.ftc.teamcode.actions.loop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.RobotContext;
import org.firstinspires.ftc.teamcode.actions.LoopAction;
import org.firstinspires.ftc.teamcode.systems.secondary.ShootingSystem;
import org.firstinspires.ftc.teamcode.systems.secondary.TurretSystem;
@Config
public class TurretControl extends LoopAction {
    TurretSystem turretSystem;
    Gamepad gamepad2;
    public static double target;
    @Override
    public void setContext(RobotContext robotContext) {
        this.turretSystem = robotContext.turretSystem;
        this.gamepad2 = robotContext.gamepad2;
    }

    @Override
    public void start() {}

    @Override
    public void update() {
        turretSystem.setTarget(gamepad2.left_stick_x*360);
        if(gamepad2.cross){
            turretSystem.turret.motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            turretSystem.turret.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
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