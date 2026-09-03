package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.teamcode.RobotContext;

public abstract class Action {
    public abstract void setContext(RobotContext robotContext);
    public abstract void execute();
}
