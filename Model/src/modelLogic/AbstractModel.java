package modelLogic;

import java.util.EnumMap;
import java.util.Iterator;

import java.util.LinkedList;
import java.util.Observable;
import java.util.concurrent.ConcurrentSkipListSet;

import modelInterfaces.Activity;
import modelInterfaces.Entity;
import modelInterfaces.EntityType;
import modelInterfaces.EventType;
import modelInterfaces.Queue;
import modelInterfaces.Randomizer;

public abstract class AbstractModel extends Observable {
    protected double modelTime;
    protected Queue queue;
    protected Activity consumer;
    protected EnumMap<EntityType, EntityGenerator> generators;
    protected long seed;
    protected EnumMap<EntityType, Randomizer> lawSet = new EnumMap<EntityType, Randomizer>(EntityType.class);
    protected EnumMap<EntityType, Double> nextEntityArrives = new EnumMap<EntityType, Double>(EntityType.class);
    protected double lifeTime;
    protected int genCount;
    private ConcurrentSkipListSet<Event> calendar;
    private LinkedList<Event> collisions;

    public AbstractModel(double lifeTime, Activity activity, EntityHolder queue) {
        super();
        this.modelTime = 0;
        this.genCount = 0;
        this.seed = 2343246; //default seed
        this.lifeTime = lifeTime;
        this.calendar = new ConcurrentSkipListSet<Event>(new EventComparator()); //structure to store events
        this.calendar.add(new Event(EventType.MODEL_END, lifeTime, null));
        this.queue = queue;
        this.collisions = new LinkedList<Event>();
        this.consumer = activity;
        this.calendar.add(new Event(EventType.MODEL_START, 0f, null));
        generators = new EnumMap<EntityType, EntityGenerator>(EntityType.class);
    }

    private void createEntities() {
        //creating first set of entities
        Entity r;
        Iterator<EntityType> enumKeySet = generators.keySet().iterator();
        while (enumKeySet.hasNext()) {
            EntityType curGen = enumKeySet.next();
            EntityGenerator generator = generators.get(curGen);
            if (generator != null) {
                r = generator.nextEntity();
                nextEntityArrives.put(r.getType(), r.getSpawnTime());
                this.calendarAdd(new Event(EventType.ENTITY_ARRIVES, r.getSpawnTime(), r));
            }
        }
    }

    private void calendarAdd(Event event) {
        if (!calendar.add(event)) {
            //collision
            collisions.add(event);
            event.setTime(event.getTime() + 0.00000001);
            calendarAdd(event);
        }
    }

    protected ModelStats createStats(Event e) {
        this.setChanged();
        return null;
    }

    private void processEvent(Event e) {
        Event en;
        EventType type = e.getType();
        switch (type) {
        case ENTITY_ARRIVES:
            {
                EntityGenerator generator = generators.get(e.getEntity().getType());
                Entity r = generator.nextEntity();
                nextEntityArrives.put(r.getType(), r.getSpawnTime());
                this.calendarAdd(new Event(EventType.ENTITY_ARRIVES, r.getSpawnTime(), r));
                if (consumer.isFull()) {
                    queue.add(e.getEntity());
                    this.notifyObservers(createStats(new Event(EventType.ENQUEUE_ENTITY, this.modelTime,
                                                               e.getEntity())));
                } else {
                    en = (Event)consumer.process(e.getEntity());
                    //adding PROCESSING_DONE event into calendar
                    this.calendarAdd(en);
                    this.notifyObservers(createStats(new Event(EventType.PROCESSING_START, this.modelTime,
                                                               e.getEntity())));
                }

                break;
            }
        case PROCESSING_DONE:
            {
                Entity ent = queue.getLast();
                if (ent != null) {
                    en = (Event)consumer.process(ent);
                    this.notifyObservers(createStats(new Event(EventType.TAKE_FROM_QUEUE, this.modelTime, ent)));
                    //add PROCESSING_DONE event to calendar
                    calendarAdd(en);
                    this.notifyObservers(createStats((new Event(EventType.PROCESSING_START, this.modelTime, ent))));
                }
                break;
            }
        }
    }

    public void run() {
        Event e;
        this.consumer.setLawSet(this.lawSet);
        this.createEntities();
        e = calendar.first();
        //main loop
        while (e.getType() != EventType.MODEL_END) {
            //we do not store calendar in memory, only few events to come
            e = calendar.first();
            calendar.remove(e);
            this.modelTime = e.time;
            this.consumer.refreshTime(this.modelTime); //update model time in request processor
            this.notifyObservers(createStats(e));
            processEvent(e);
        }
    }


    public void setLifeTime(double lifeTime) {
        this.lifeTime = lifeTime;
    }

    public double getLifeTime() {
        return lifeTime;
    }

    public void setGeneratorLaws(EnumMap<EntityType, Randomizer> generators) {
        Iterator<EntityType> enumKeySet = generators.keySet().iterator();
        while (enumKeySet.hasNext()) {
            EntityType curGen = enumKeySet.next();
            Randomizer law = generators.get(curGen);
            if (law != null) {
                this.generators.put(curGen, new EntityGenerator(curGen, law));
            }
        }
    }

    public EnumMap<EntityType, EntityGenerator> getGenerators() {
        return generators;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public long getSeed() {
        return seed;
    }
}
