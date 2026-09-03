package org.firstinspires.ftc.teamcode.modules.abstractions;

import org.firstinspires.ftc.teamcode.modules.Module;

public abstract class Camera extends Module {
    public abstract void initAprilTag();
    public abstract void setProcessor();
    public abstract void stopStream();
    public abstract double[] getRawPos();
    public abstract double[] get3DPos();
}
