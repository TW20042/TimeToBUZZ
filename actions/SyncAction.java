package org.firstinspires.ftc.teamcode.actions;

public abstract class SyncAction extends Action{
    public abstract void start();
    public abstract void update();
    public abstract void stop();
    public abstract boolean isFinished();
}
