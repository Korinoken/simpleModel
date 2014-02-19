package modelLogic;

import java.util.Comparator;

public final class EventComparator implements Comparator<Event> {
    public EventComparator() {
        super();
    }

    @Override
    public int compare(Event o1, Event o2) {
        if (o1.time > o2.time) {
            return 1;
        } else if (o1.time < o2.time) {
            return -1;
        }
        return 0;
    }
}
