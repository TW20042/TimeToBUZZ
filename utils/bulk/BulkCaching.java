package org.firstinspires.ftc.teamcode.utils.bulk;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.List;

public class BulkCaching {
    HardwareMap hardwareMap;
    List<LynxModule> hubs;
    public BulkCaching(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
        this.hubs = hardwareMap.getAll(LynxModule.class);
    }
    public void setBulkCachingMode(LynxModule.BulkCachingMode mode){
        for(LynxModule hub : this.hubs){
            hub.setBulkCachingMode(mode);
        }
    }
    public void clearCache(){
        for(LynxModule hub : this.hubs){
            hub.clearBulkCache();
        }
    }
}
