package nit.history;

import java.util.List;

import nit.history.data.HistoryDataType;
import nit.history.data.HistoryEvent;
import nit.history.data.LocationRelationShip;

/**
 * Service that acts as an interface between the UI and the data store. 
 * 
 * @author Neil
 */
public interface HistoryService {

	<D extends HistoryDataType> boolean exists(String id, Class<D> type);
	
	void save(HistoryDataType data);
	
	<D extends HistoryDataType> D load(String id, Class<D> type);
	
	List<HistoryEvent> getHistoryEvents(HistoryDataType ... types);

	List<LocationRelationShip> getRelationShips(HistoryDataType ... data);
}
