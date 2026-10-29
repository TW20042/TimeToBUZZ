package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.actions.Action;
import org.firstinspires.ftc.teamcode.actions.AsyncAction;
import org.firstinspires.ftc.teamcode.actions.LoopAction;
import org.firstinspires.ftc.teamcode.actions.SyncAction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ActionScheduler {
    AtomicInteger actionCounter = new AtomicInteger();
    ExecutorService executorService = Executors.newCachedThreadPool();
    final List<LoopAction> loopActions = new ArrayList<>();
    final List<SyncAction> syncActions = new ArrayList<>();
    public void addAction(SyncAction action){
        actionCounter.incrementAndGet();
        action.start();
        syncActions.add(action);
    }
    public void addAction(AsyncAction action){
        actionCounter.incrementAndGet();

        executorService.submit(() -> {
            try {
                action.start();
                while(!action.isFinished()){
                    updateWithFailsafe(action);
                }
            } finally {
                action.stop();
                actionCounter.decrementAndGet();
            }
        });
    }
    public void addAction(LoopAction action){
        actionCounter.incrementAndGet();
        action.start();
        loopActions.add(action);
    }
    void updateLoopActions(){
        for (int i = 0; i < loopActions.size(); i++){
            LoopAction loopAction = loopActions.get(i);
            if(loopAction.isFinished()){
                loopAction.stop();
                loopActions.remove(i);
                actionCounter.decrementAndGet();
                i--;
            } else {
                updateWithFailsafe(loopAction);
            }
        }
    }
    void updateSyncActions(){
        if(syncActions.isEmpty()){
            return;
        }

        SyncAction currentAction = syncActions.get(0);

        if (!currentAction.isFinished()) {
            updateWithFailsafe(currentAction);
            return;
        }

        currentAction.stop();
        syncActions.remove(0);
        actionCounter.decrementAndGet();
    }
    public void updateActions(){
        updateSyncActions();
        updateLoopActions();
    }
    public void updateWithFailsafe(Action action){
        if(action.isFailed()){
            action.doIfFailed();
        } else action.update();
    }
    public boolean isFinished(){
        return actionCounter.get() == 0;
    }
    public void stop(){
        for(LoopAction loopAction : loopActions){
            actionCounter.decrementAndGet();
            loopAction.stop();
        }
        executorService.shutdownNow();
    }
}
