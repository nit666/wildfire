package nit.history;

import nit.history.data.HistoryDataType;

public interface BaseDAO<T extends HistoryDataType> {

	void createOrUpdate(T dataItem);
	
	T fetch(String id);
	
	boolean exists(String id);
}
