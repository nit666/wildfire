package nit.history.dao.memory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import nit.history.LocationRelationShipDAO;
import nit.history.data.Entity;
import nit.history.data.HistoryDataType;
import nit.history.data.Location;
import nit.history.data.LocationRelationShip;
import nit.history.data.Time;
import nit.history.data.TimeSpan;

public class MemoryLocationRelationShipDAOImpl extends MemoryStorage<LocationRelationShip> implements
		LocationRelationShipDAO {

	@Override
	public Collection<Entity> getEntitiesForLocationForTimeSpan(Location location, TimeSpan time) {
		
		List<Entity> retVal = new LinkedList<Entity>();
		for (LocationRelationShip loc : getValues()) {
			if (loc.getLocation().getID().equals(location.getID())) {
				if (loc.getTimespan().isWithinTimeSpan(time)) {
					retVal.add(loc.getEntity());
				}
			}
		}
		return retVal;
	}

	@Override
	public Collection<Entity> getEntitiesForLocationForTime(Location location, Time time) {
		
		List<Entity> retVal = new LinkedList<Entity>();
		for (LocationRelationShip loc : getValues()) {
			if (loc.getLocation().getID().equals(location.getID())) {
				if (loc.getTimespan().isWithinTimeSpan(time)) {
					retVal.add(loc.getEntity());
				}
			}
		}
		return retVal;	
	}
	
	@Override
	public Collection<Entity> getEntitiesForLocation(Location location) {
		List<Entity> retVal = new LinkedList<Entity>();
		for (LocationRelationShip loc : getValues()) {
			if (loc.getLocation().getID().equals(location.getID())) {
					retVal.add(loc.getEntity());
			}
		}
		return retVal;
	}
	
	@Override
	public boolean entityWasAtLocationAtTime(Location location, Entity entity,
			TimeSpan timespan) {
		for (LocationRelationShip loc : getValues()) {
			if (loc.getLocation().getID().equals(location.getID())) {
				if (loc.getEntity().getID().equals(entity.getID())) {
					if (loc.getTimespan().isWithinTimeSpan(timespan)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public List<LocationRelationShip> getRelationShips(HistoryDataType... data) {
		
		List<LocationRelationShip> retVal = new LinkedList<LocationRelationShip>();
		
		// the relationship must match all data
		for (LocationRelationShip locRel : getValues()) {			
			boolean matched = false;
			boolean matchedEntity = false;
			boolean searchEntity = false;
			for (HistoryDataType type : data) {
				if (type instanceof Entity) {
					searchEntity = true;
					// entities are a bit different because there can be multiple, so we need to just match one. 
					if (locRel.getEntity().equals((Entity) type)) {
						matchedEntity = true;
					}
				}
				if (type instanceof Location) {
					if (locRel.getLocation().equals((Location) type)) {
						matched = true;
					}
				}
				if (type instanceof Time) {
					if (locRel.getTimespan().isWithinTimeSpan((Time) type)) {
						matched = true;
					}
				}
				if (type instanceof TimeSpan) {
					if (locRel.getTimespan().isWithinTimeSpan((TimeSpan) type)) {
						matched = true;
					}
				}
			}
			if (matched == true || (searchEntity == true && matchedEntity == true)) {
				retVal.add(locRel);
			}
		}
		return retVal;
	}

}
