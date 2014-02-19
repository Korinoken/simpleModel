package modelLogic;

import java.util.EnumMap;

import modelInterfaces.Activity;
import modelInterfaces.Entity;
import modelInterfaces.EntityType;
import modelInterfaces.EventType;
import modelInterfaces.Randomizer;

public final class RequestProcessor implements Activity {
    private Entity currentEntity;
    private double currentTime,maxWaitTime,processingFinished,serverWait;
    private boolean isFull;
    private EnumMap<EntityType,Randomizer> lawSet;

    
    public RequestProcessor() {
        super();
        this.processingFinished = 0;
        this.maxWaitTime = 0;
        this.isFull = false;
        this.serverWait = 0;
    }

    @Override
    public modelInterfaces.Event process(Entity entity) {
        double processingTime =0;
        double waitTime;
        if (!this.isFull) {
            this.serverWait += this.currentTime - this.getProcessingFinished();
        }
        waitTime = this.currentTime - entity.getSpawnTime();
        if(waitTime>this.maxWaitTime) {
            this.maxWaitTime = waitTime;
        }
        processingTime = lawSet.get(entity.getType()).nextNumber();
        this.processingFinished = this.currentTime + processingTime;
        this.isFull = true;
        return new Event(EventType.PROCESSING_DONE, this.processingFinished, entity);
    }

    @Override
    public boolean isFull() {
        return this.isFull;
    }

    @Override
    public Entity currentEntity() {
        return this.currentEntity;
    }

    @Override
    public void refreshTime(double time) {
        this.currentTime = time;
        if (this.currentTime > this.processingFinished) {
            this.currentEntity = null;
            this.isFull = false;
        }
    }

    @Override
    public double getMaxWaitTime() {
        return maxWaitTime;
    }
    @Override
    public void setLawSet(EnumMap<EntityType, Randomizer> lawSet) {
        this.lawSet = lawSet;
    }
    @Override
    public double getProcessingFinished() {
        return processingFinished;
    }

    public void setIsFull(boolean isFull) {
        this.isFull = isFull;
    }

    public boolean isIsFull() {
        return isFull;
    }
    @Override
    public double getServerWait() {
        return serverWait;
    }
}
