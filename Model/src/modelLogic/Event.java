package modelLogic;

import modelInterfaces.Entity;
import modelInterfaces.EventType;

public class Event implements modelInterfaces.Event {
    private EventType type;
    protected double time;
    private Entity entity;
    public Event(EventType type,double eventTime, Entity affectedEntity) {
        super();
        this.type = type;
        this.time = eventTime;
        this.entity = affectedEntity;
    }

    @Override
    public double getTime() {
        return this.time;
    }

    @Override
    public EventType getType() {
        return this.type;
    }

    @Override
    public Entity getEntity() {
        return this.entity;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
