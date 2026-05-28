package org.firstinspires.ftc.teamcode.modules.odometry;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static FollowerConstants followerConstants(){
        return new FollowerConstants()
                .mass(10);
    }

    public static MecanumConstants mecanumConstants(){
        return new MecanumConstants()
                .leftFrontMotorName("lf")
                .leftRearMotorName("lb")
                .rightFrontMotorName("rf")
                .rightRearMotorName("rb")
                .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
                .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE);
    }
    public static PinpointConstants pinpointConstants(){
        return new PinpointConstants()
                .hardwareMapName("pinpoint")
                .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
                .forwardPodY(0)
                .strafePodX(0);
    }
    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants(), hardwareMap)
                .mecanumDrivetrain(mecanumConstants())
                .pinpointLocalizer(pinpointConstants())
                .build();
    }
}
