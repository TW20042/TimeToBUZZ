package org.firstinspires.ftc.teamcode.systems.moving;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.libs.pedro.Constants;
import org.firstinspires.ftc.teamcode.systems.System;

import java.util.List;

public class PedroMovingSystem extends System {
    public final Follower follower;
    public PedroMovingSystem(HardwareMap hardwareMap){
        this.follower = Constants.create(hardwareMap);
    }
    public void setPose(Pose pose){
        follower.setPose(pose);
    }
    public void follow(Path path){follower.follow(path);}
    public boolean isBusy(){
        return follower.isBusy();
    }
    @Override
    public void update() {
        follower.update();
    }
}
