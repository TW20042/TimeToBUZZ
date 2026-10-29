package org.firstinspires.ftc.teamcode.actions.sync;

import com.pedropathing.paths.Path;

import org.firstinspires.ftc.teamcode.RobotContext;
import org.firstinspires.ftc.teamcode.actions.SyncAction;
import org.firstinspires.ftc.teamcode.systems.moving.PedroMovingSystem;

public class PedroMovingTo extends SyncAction {
    private PedroMovingSystem pedroMovingSystem;
    private final Path path;
    public PedroMovingTo(Path path){
        this.path = path;
    }
    @Override
    public void setContext(RobotContext robotContext) {
        this.pedroMovingSystem = robotContext.pedroMovingSystem;
    }

    @Override
    public void start() {
        this.pedroMovingSystem.follow(this.path);
    }

    @Override
    public void update() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isFinished() {
        return !pedroMovingSystem.isBusy();
    }

    @Override
    public boolean isFailed() {
        return false;
    }

    @Override
    public void doIfFailed() {

    }
}
