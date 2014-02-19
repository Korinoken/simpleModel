package modelLogic;

public final class ModelStats {
    private Event event;
    private int queueSize;
    double nextA, nextB,processingDone;
    int serverFree;
    public ModelStats(Event event,int queueSize,double nextA,double nextB,double processingDone,boolean serverFree) {
        super();
        this.event = event;
        this.queueSize = queueSize;
        this.nextA = nextA;
        this.nextB = nextB;
        this.processingDone = processingDone;
        if (serverFree){
            this.serverFree=1;
        } else {
            this.serverFree = 0;
        }
    }

    public Event getEvent() {
        return event;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public double getNextA() {
        return nextA;
    }

    public double getNextB() {
        return nextB;
    }

    public double getProcessingDone() {
        return processingDone;
    }

    public int getServerFree() {
        return serverFree;
    }
}
