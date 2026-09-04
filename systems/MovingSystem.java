package org.firstinspires.ftc.teamcode.systems;

import org.firstinspires.ftc.teamcode.modules.abstractions.Wheelbase;

public class MovingSystem {
    private Wheelbase wheelbase;
    public MovingSystem(Wheelbase wheelbase){
        if(wheelbase != null){
            this.wheelbase = wheelbase;
        }
    }
    public void move(double axial, double lateral, double yaw){
        wheelbase.drive(axial, lateral, yaw);
    }
}
