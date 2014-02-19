package modelInterfaces;

public interface Event {
    public double getTime();
    public EventType getType();
    public Entity getEntity();
    public void setTime(double time);
}
