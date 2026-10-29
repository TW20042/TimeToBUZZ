package org.firstinspires.ftc.teamcode.utils.benchmark;

import java.util.ArrayList;

public class BenchmarkStats {
    private final static ArrayList<Float> snapshots = Benchmark.getSnapshots();
    public static void toMillis(){
        snapshots.replaceAll(item -> item / 1_000_000);
    }
    public static float getMedian(){
        int medianIndex = (int) snapshots.size() / 2;
        return snapshots.get(medianIndex);
    }
    public static float getPercentStat(float percent){
        int percentIndex = (int) (snapshots.size() * percent);
        return snapshots.get(percentIndex);
    }
    public static float getMax(){
        return snapshots.get(snapshots.size() - 1);
    }
}
