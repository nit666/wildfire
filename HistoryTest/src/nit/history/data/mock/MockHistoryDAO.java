package nit.history.data.mock;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import nit.history.HistoryDAO;
import nit.history.data.Entity;
import nit.history.data.HistoryDataType;
import nit.history.data.HistoryEvent;
import nit.history.data.Location;
import nit.history.data.Time;
import nit.history.data.TimeSpan;

public class MockHistoryDAO implements HistoryDAO {

    List<HistoryEvent> events = new LinkedList<HistoryEvent>();


    @Override
    public void createOrUpdateHistoryEvent(HistoryEvent event) {
        events.add(event);
    }


    @Override
    public List<HistoryEvent> getHistoryEvents(HistoryDataType... types) {
        Set<HistoryEvent> retVal = new HashSet<HistoryEvent>(events);

        for (HistoryDataType type : types) {
            if (type instanceof Location) {
                retVal.retainAll(getHistoryEventsByLocation((Location)type));
            } else if (type instanceof Time) {
                retVal.retainAll(getHistoryEventsByTime((Time)type));
            } else if (type instanceof TimeSpan) {
                retVal.retainAll(getHistoryEventsByTimeSpan((TimeSpan)type));
            } else if (type instanceof Entity) {
                retVal.retainAll(getHistoryEventsByEntity((Entity)type));
            }
        }
        return new LinkedList<HistoryEvent>(retVal);
    }


    private Collection<HistoryEvent> getHistoryEventsByEntity(Entity entity) {
        Collection<HistoryEvent> retVal = new LinkedList<HistoryEvent>();

        // for (HistoryEvent event : events) {
        // if (event.getEntities().contains(entity)) {
        // retVal.add(event);
        // }
        // }

        return retVal;
    }


    private Collection<HistoryEvent> getHistoryEventsByLocation(Location location) {
        Collection<HistoryEvent> retVal = new LinkedList<HistoryEvent>();

        for (HistoryEvent event : events) {
            if (event.getLocation().equals(location)) {
                retVal.add(event);
            }
        }

        return retVal;
    }


    private Collection<HistoryEvent> getHistoryEventsByTime(Time time) {
        Collection<HistoryEvent> retVal = new LinkedList<HistoryEvent>();

        for (HistoryEvent event : events) {
            if (event.getTimeSpan().isWithinTimeSpan(time)) {
                retVal.add(event);
            }
        }

        return retVal;
    }


    private Collection<HistoryEvent> getHistoryEventsByTimeSpan(TimeSpan timeSpan) {
        Collection<HistoryEvent> retVal = new LinkedList<HistoryEvent>();

        for (HistoryEvent event : events) {
            if (event.getTimeSpan().isWithinTimeSpan(timeSpan)) {
                retVal.add(event);
            }
        }

        return retVal;
    }

}
