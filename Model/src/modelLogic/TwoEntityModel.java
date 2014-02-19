package modelLogic;

import java.util.ArrayList;

import modelInterfaces.EntityType;

public final class TwoEntityModel extends AbstractModel {


    public TwoEntityModel(double lifeTime) {
        super(lifeTime, new RequestProcessor(), new EntityHolder());
        //event generators with generation laws
        generators.put(EntityType.A, new EntityGenerator(EntityType.A, new EksponentialDestribution(seed + this.genCount++, 0.2)));
        generators.put(EntityType.B, new EntityGenerator(EntityType.B, new ErlangDistribution(seed + this.genCount++, 0.1, 2)));
        //event processing laws
        lawSet.put(EntityType.A, new NormalDestribution(seed + this.genCount++, 20, 3));
        lawSet.put(EntityType.B, new EksponentialDestribution(seed + this.genCount++, 0.2));
    }

    @Override
    protected ModelStats createStats(Event e) {
        super.createStats(e);
        return new ModelStats(e, queue.length(), this.nextEntityArrives.get(EntityType.A),
                              this.nextEntityArrives.get(EntityType.B), this.consumer.getProcessingFinished(),
                              !this.consumer.isFull());
    }

    public ArrayList<Object[]> gatherStatistics() {
        ArrayList<Object[]> stats;
        stats = new ArrayList<Object[]>(10);

        stats.add(new Object[] { "Max wait: ", Double.toString(consumer.getMaxWaitTime()) });
        stats.add(new Object[] { "Max queue: ", Integer.toString(queue.getMaxLength()) });
        stats.add(new Object[] { "Server load: ",
                                 Double.toString((1 - consumer.getServerWait() / this.lifeTime) * 100) + '%' });
        stats.add(new Object[] { "MaxQ for A: ", Integer.toString(this.queue.getMaxStats(EntityType.A)) });
        stats.add(new Object[] { "MaxQ for B: ", Integer.toString(this.queue.getMaxStats(EntityType.B)) });
        stats.trimToSize();
        return stats;
    }

}
