package nit.history.dao.memory;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import nit.history.DAOFactory;
import nit.history.HistoryDAO;
import nit.history.LocationDAO;
import nit.history.data.Entity;
import nit.history.data.HistoryDataType;
import nit.history.data.HistoryEvent;
import nit.history.data.Location;
import nit.history.data.Time;
import nit.history.data.TimeSpan;

public class MemoryHistoryDAOImpl extends MemoryStorage<HistoryEvent> implements HistoryDAO {

	DAOFactory factory;
	
	public MemoryHistoryDAOImpl(DAOFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public void createOrUpdate(HistoryEvent event) {
		factory.getLocationDAO().createOrUpdate(event.getLocation());
		super.createOrUpdate(event);
	}
	
	@Override
	public List<HistoryEvent> getHistoryEvents(HistoryDataType ... types) {
		Set<HistoryEvent> retVal = new HashSet<HistoryEvent>(getValues());
		
		for (HistoryDataType type : types) {
			if (type instanceof Location) {
				retVal.retainAll(getHistoryEventsByLocation((Location) type));
			}
			else if (type instanceof Time) {
				retVal.retainAll(getHistoryEventsByTime((Time) type));
			}
			else if (type instanceof TimeSpan) {
				retVal.retainAll(getHistoryEventsByTimeSpan((TimeSpan) type));
			}
			else if (type instanceof Entity) {
				retVal.retainAll(getHistoryEventsByEntity((Entity) type));
			}
		}
		
		return new LinkedList<HistoryEvent>(retVal);
	}

	private Collection<HistoryEvent> getHistoryEventsByEntity(Entity entity) {
		Collection<HistoryEvent> retVal = new LinkedList<HistoryEvent>();
		
		for (HistoryEvent event : getValues()) {
			if (factory.getRelationShipDAO().entityWasAtLocationAtTime(event.getLocation(), entity, event.getTimeSpan())) {
				retVal.add(event);	
			}
		}
		
		return retVal;
	}

	private Collection<HistoryEvent> getHistoryEventsByLocation(Location location) {
		Collection<HistoryEvent> retVal = new LinkedList<HistoryEvent>();
		
		for (HistoryEvent event : getValues()) {
			if (event.getLocation().equals(location)) {
				retVal.add(event);
			}
		}
		
		return retVal;
	}

	private Collection<HistoryEvent> getHistoryEventsByTime(Time time) {
		Collection<HistoryEvent> retVal = new LinkedList<HistoryEvent>();
		
		for (HistoryEvent event : getValues()) {
			if (event.getTimeSpan().isWithinTimeSpan(time)) {
				retVal.add(event);
			}
		}
		
		return retVal;
	}

	private Collection<HistoryEvent> getHistoryEventsByTimeSpan(TimeSpan timeSpan) {
		Collection<HistoryEvent> retVal = new LinkedList<HistoryEvent>();
		
		for (HistoryEvent event : getValues()) {
			if (event.getTimeSpan().isWithinTimeSpan(timeSpan)) {
				retVal.add(event);
			}
		}
		
		return retVal;
	}
}
