package modelLogic;

import java.util.EnumMap;
import java.util.LinkedList;

import modelInterfaces.Entity;
import modelInterfaces.EntityType;
import modelInterfaces.Queue;

public final class EntityHolder implements Queue {
    //storing events + gathering some statistics
    private LinkedList<Entity> queue;
    private EnumMap<EntityType, Integer> maxStats;

    private EnumMap<EntityType, Integer> entityAmmount;
    private int maxLength;

    public EntityHolder() {
        super();
        queue = new LinkedList<Entity>();
        this.maxLength = 0;
        maxStats = new EnumMap<EntityType, Integer>(EntityType.class);
        entityAmmount = new EnumMap<EntityType, Integer>(EntityType.class);
        for (EntityType type : EntityType.values()) {
            maxStats.put(type,0);
            entityAmmount.put(type,0);
        }
    }

    @Override
    public boolean add(Entity entity) {
        queue.add(entity);
        entityAmmount.put(entity.getType(), entityAmmount.get(entity.getType()) + 1);
        if (maxStats.get(entity.getType()) < this.entityAmmount.get(entity.getType())) {
            maxStats.put(entity.getType(), entityAmmount.get(entity.getType()));
        }
        if (queue.size() > maxLength) {
            maxLength = queue.size();
        }
        return true;
    }

    @Override
    public int length() {
        return queue.size();
    }

    @Override
    public Entity getLast() {
        if (!queue.isEmpty()) {
            Entity e = queue.removeLast();
            entityAmmount.put(e.getType(), entityAmmount.get(e.getType()) - 1);
            return e;
        } else {
            return null;
        }
    }

    @Override
    public Entity[] getQueueContents() {
        return new Entity[0];
    }
    @Override
    public int getMaxLength() {
        return maxLength;
    }
    @Override
    public int getMaxStats(EntityType type) {
        return maxStats.get(type);
    }
}
