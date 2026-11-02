package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.BatteryChecker;

public class PowerSupplier {
    private static VoltageSensor voltageSensor;

    public static void setHardwareMap(HardwareMap hardwareMap){
        voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");
    }
    public static double getVoltage(){
        return voltageSensor.getVoltage();
    }
    public static double supplyVoltage(double power, double nominalVoltage){
        return power * nominalVoltage / voltageSensor.getVoltage();
    }
}
