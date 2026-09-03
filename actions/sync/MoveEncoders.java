package org.firstinspires.ftc.teamcode.actions.sync;

import org.firstinspires.ftc.teamcode.RobotContext;
import org.firstinspires.ftc.teamcode.actions.Action;
import org.firstinspires.ftc.teamcode.modules.abstractions.Encoder;
import org.firstinspires.ftc.teamcode.modules.abstractions.Wheelbase;
import org.firstinspires.ftc.teamcode.modules.realizes.localization.Point;
import org.firstinspires.ftc.teamcode.systems.EncodersSystem;
import org.firstinspires.ftc.teamcode.systems.StabilizationSystem;

public class MoveEncoders extends Action {
    EncodersSystem encodersSystem;
    StabilizationSystem stabilizationSystem;
    Wheelbase wheelbase;
    Encoder encoder;
    private final Point startPoint;
    private final Point endPoint;
    public MoveEncoders(Point startPoint, Point endPoint){
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }
    @Override
    public void setContext(RobotContext robotContext) {
        this.encodersSystem = robotContext.encodersSystem;
        this.stabilizationSystem = robotContext.stabilizationSystem;
        this.encoder = robotContext.encoders;
        this.wheelbase = robotContext.wheelbase;
    }

    @Override
    public void execute() {
        encodersSystem.setDistance(startPoint, endPoint);
        while (encoder.getDistance() < encodersSystem.s){
            double heading = stabilizationSystem.getTurnPD(endPoint.getHeading(), 0.1, 0);
            double[] axes = encodersSystem.getDistancePID(0.1, 0, 0, heading);
            wheelbase.drive(axes[0], axes[1], axes[2]);
        }
    }
}
