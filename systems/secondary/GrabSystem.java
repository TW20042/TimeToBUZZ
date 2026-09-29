package org.firstinspires.ftc.teamcode.systems.secondary;

import org.firstinspires.ftc.teamcode.RobotContext;
import org.firstinspires.ftc.teamcode.modules.realizes.secondary.Grab;
import org.firstinspires.ftc.teamcode.systems.System;

public class GrabSystem extends System {
    private final Grab grab;
    private double power = 0;
    public GrabSystem(RobotContext context){
        this.grab = context.grab;
    }
    public void setPower(double power){
        this.power = power;
    }
    @Override
    public void update(){
        grab.control(power);
    }
}
