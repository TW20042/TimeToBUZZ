package org.firstinspires.ftc.teamcode.modules.odometry;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Module;
//Odometry
public class Odometry extends Module {
    Follower follower;
    @Override
    public void init_classes(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2, LinearOpMode L) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.L = L;
    }

    public void create_follower(){
        follower = Constants.createFollower(hardwareMap);
        follower.usePredictiveBraking = true;
        follower.setMaxPower(1.0);
        follower.setStartingPose(new Pose(0, 0, Math.toRadians(0)));
    }
    public void go_to_path(Path path){
        follower.followPath(path);

        while(L.opModeIsActive() && follower.isBusy()){
            follower.update();
        }

        follower.holdPoint(follower.getPose());
    }
    public void set_pose(Pose pose){
        follower.setPose(pose);
    }
    public double[] get_pose(){
        return new double[] {   follower.getPose().getX(),
                                follower.getPose().getY(),
                                follower.getPose().getHeading() };
    }
}
