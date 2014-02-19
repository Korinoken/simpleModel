package modelInterfaces;

public interface Queue {
    public boolean add (Entity entity);
    public int length();
    public Entity getLast();
    public Entity[] getQueueContents();
    public int getMaxLength();
    public int getMaxStats(EntityType type);
}
