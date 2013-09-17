package nit.history;

import java.util.Collection;
import java.util.List;

import nit.history.data.Entity;
import nit.history.data.HistoryDataType;
import nit.history.data.Location;
import nit.history.data.LocationRelationShip;
import nit.history.data.Time;
import nit.history.data.TimeSpan;

public interface LocationDAO extends BaseDAO<Location> {

	/**
	 * Get all locations for a parent
	 * @param name the name of the parent location
	 * @return all locations that have that parent
	 */
	Collection<Location> getLocationForParent(String name);	

}
