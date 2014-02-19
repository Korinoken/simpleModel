package modelLogic;

import modelInterfaces.Entity;
import modelInterfaces.EntityType;

public final class Request implements Entity {
    private EntityType type;
    private double spawnTime;

    public Request(EntityType type, double spawnTime) {
        super();
        this.type = type;
        this.spawnTime = spawnTime;
    }

    @Override
    public EntityType getType() {
        return this.type;
    }

    @Override
    public double getSpawnTime() {
        return this.spawnTime;
    }
}
