package org.firstinspires.ftc.teamcode.systems.secondary;

import org.firstinspires.ftc.teamcode.RobotContext;
import org.firstinspires.ftc.teamcode.modules.realizes.secondary.Shooter;
import org.firstinspires.ftc.teamcode.systems.System;

public class ShootingSystem extends System {
    private final Shooter shooter;
    private double power = 0;
    public ShootingSystem(RobotContext context){
        this.shooter = context.shooter;
    }
    public void setPower(double power){
        this.power = power;
    }
    @Override
    public void update(){
        shooter.control(power);
    }
}
