package nit.history;

import java.util.Collection;
import java.util.List;

import nit.history.data.Entity;
import nit.history.data.HistoryDataType;
import nit.history.data.HistoryEvent;
import nit.history.data.Location;
import nit.history.data.LocationRelationShip;
import nit.history.data.TimeSpan;

/**
 * Service that acts as an interface between the UI and the data store. 
 * 
 * @author Neil
 */
public interface HistoryService {

	<D extends HistoryDataType> boolean exists(String id, Class<D> type);
	
	<D extends HistoryDataType> void saveOrUpdate(D data, Class<D> type);
	
	<D extends HistoryDataType> D fetch(String id, Class<D> type);
	
	List<HistoryEvent> getHistoryEvents(HistoryDataType ... types);

	List<LocationRelationShip> getRelationShips(HistoryDataType ... data);
	
	Collection<Entity> getEntitiesForLocationForTimeSpan(Location location, TimeSpan time);
}
