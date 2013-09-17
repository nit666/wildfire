package nit.history.dao.memory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import nit.history.BaseDAO;
import nit.history.data.HistoryDataType;

public class MemoryStorage<E extends HistoryDataType> implements BaseDAO<E> {

	private final Map<String, E> data;
	
	public MemoryStorage() {
		data = new HashMap<String, E>();
	}
	
	public void saveOrUpdate(E type) {
	}

	@Override
	public void createOrUpdate(E dataItem) {
		data.put(dataItem.getID(), dataItem);		
	}

	@Override
	public E fetch(String id) {
		if (data.containsKey(id)) {
			return data.get(id);
		}
		throw new NoSuchElementException("The key: " + id + ", was not found");
	}

	@Override
	public boolean exists(String id) {
		return data.containsKey(id);
	}
	
	protected Collection<E> getValues() {
		return data.values();
	}
}
