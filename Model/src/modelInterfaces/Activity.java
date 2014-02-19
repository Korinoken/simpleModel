package modelInterfaces;

import java.util.EnumMap;

public interface Activity {
    
    public Event process(Entity entity);
    public boolean isFull();
    public Entity currentEntity();
    public void refreshTime(double time);
    public double getMaxWaitTime();
    public void setLawSet(EnumMap<EntityType, Randomizer> lawSet);
    public double getServerWait();
    public double getProcessingFinished();
}
