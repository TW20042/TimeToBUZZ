package org.firstinspires.ftc.teamcode.utils;

import java.util.ArrayList;

public class Benchmark {
    static ArrayList<Float> snapshots = new ArrayList<>();
    private static int snapshotLimit = 1000;
    public static void setSnapshotLimit(int snapshotLimit) {
        Benchmark.snapshotLimit = snapshotLimit;
    }
    public static ArrayList<Float> getSnapshots(){
        return Benchmark.snapshots;
    }
    public static void clearSnapshots(){
        Benchmark.snapshots.clear();
    }
    public static float getSnapshot(){
        return System.nanoTime();
    }
    public static float compareSnapshots(float start, float end){
        float comparing = end - start;

        if(snapshots.size() <= snapshotLimit){
            snapshots.add(comparing);
        }

        return comparing;
    }
    public static void sortSnapshots(){
        Benchmark.snapshots.sort(null);
    }
}
