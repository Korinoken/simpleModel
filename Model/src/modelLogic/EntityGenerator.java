package modelLogic;

//import modelInterfaces.Entity;
import modelInterfaces.EntityType;
import modelInterfaces.Randomizer;

public final class EntityGenerator {
    private EntityType type;
    private Randomizer randomizer;
    private double lastEntity;

    public EntityGenerator(EntityType type, Randomizer randomizer) {
        super();
        this.type = type;
        this.randomizer = randomizer;
    }

    public Request nextEntity() {
        double i = randomizer.nextNumber();
        Request e = new Request(this.type, this.lastEntity + i);
        this.lastEntity = this.lastEntity + i;
        return e;
    }
}
