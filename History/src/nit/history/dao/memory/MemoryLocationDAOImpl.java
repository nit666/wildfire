package nit.history.dao.memory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import nit.history.DAOFactory;
import nit.history.LocationDAO;
import nit.history.data.Entity;
import nit.history.data.HistoryDataType;
import nit.history.data.Location;
import nit.history.data.LocationRelationShip;
import nit.history.data.Time;
import nit.history.data.TimeSpan;

public class MemoryLocationDAOImpl extends MemoryStorage<Location> implements
		LocationDAO {

	DAOFactory factory;
	MemoryStorage<LocationRelationShip> relationships = new MemoryStorage<LocationRelationShip>();
	
	public MemoryLocationDAOImpl(DAOFactory factory) {
		this.factory = factory;
	}

	@Override
	public Collection<Location> getLocationForParent(String name) {
		List<Location> retVal = new LinkedList<Location>();
		for (Location location : getValues()) {
			if (location.getParent().getID().equals(name)) {
				retVal.add(location);
			}
		}
		return retVal;
	}
}
