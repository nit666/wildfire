package nit.history;

import java.util.List;

import nit.history.data.HistoryDataType;
import nit.history.data.HistoryEvent;

public interface HistoryDAO {

	/**
	 * Get all history events for a set of data types.
	 * @param types the list of data types to use in the query
	 * @return an event will be added to the list if all the types exist for that event. 
	 */
	List<HistoryEvent> getHistoryEvents(HistoryDataType ... types);
	
	/**
	 * Create a new event in the data set 
	 * @param event the event to include. 
	 */
	void createOrUpdateHistoryEvent(HistoryEvent event);
}
