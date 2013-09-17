package nit.history.dao.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import nit.history.data.HistoryDataType;

public class MemoryStorage<E extends HistoryDataType> {

	Map<String, E> data;
	
	public MemoryStorage() {
		data = new HashMap<String, E>();
	}
	
	public void save(E type) {
		data.put(type.getID(), type);
	}
	
	public E load(String id) {
		if (data.containsKey(id)) {
			return data.get(id);
		}
		throw new NoSuchElementException("The key: " + id + ", was not found");
	}
	
	public boolean contains(String id) {
		return data.containsKey(id);
	}
}
