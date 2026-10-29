package org.firstinspires.ftc.teamcode.systems.moving;

import org.firstinspires.ftc.teamcode.RobotContext;
import org.firstinspires.ftc.teamcode.modules.abstractions.Wheelbase;
import org.firstinspires.ftc.teamcode.systems.System;

public class MovingSystem extends System {
    private final Wheelbase wheelbase;
    private double axial = 0;
    private double lateral = 0;
    private double yaw = 0;
    public MovingSystem(RobotContext context){
        this.wheelbase = context.wheelbase;
    }

    public void setInput(double axial, double lateral, double yaw){
        this.axial = axial;
        this.lateral = lateral;
        this.yaw = yaw;
    }

    @Override
    public void update() {
        wheelbase.drive(axial, lateral, yaw);
    }
}
