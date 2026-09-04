package org.firstinspires.ftc.teamcode.actions.sync;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.RobotContext;
import org.firstinspires.ftc.teamcode.actions.Action;
import org.firstinspires.ftc.teamcode.systems.MovingSystem;
import org.firstinspires.ftc.teamcode.systems.WithoutHeadSystem;

public class WithoutHeadControl extends Action {
    WithoutHeadSystem withoutHeadSystem;
    MovingSystem movingSystem;
    Gamepad gamepad1;
    @Override
    public void setContext(RobotContext robotContext) {
        this.withoutHeadSystem = robotContext.withoutHeadSystem;
        this.movingSystem = robotContext.movingSystem;
        this.gamepad1 = robotContext.gamepad1;
    }

    @Override
    public void execute() {
        double x =  gamepad1.left_stick_x * (1 - gamepad1.right_trigger);
        double y = gamepad1.left_stick_y * (1 - gamepad1.right_trigger);
        double yaw = gamepad1.right_stick_x;

        double[] axis = withoutHeadSystem.getAxis(x, y);
        movingSystem.move(axis[0], axis[1], yaw);
    }
}
