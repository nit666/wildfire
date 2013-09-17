package nit.history.dao.memory;

import java.util.List;

import nit.history.DAOFactory;
import nit.history.HistoryService;
import nit.history.data.HistoryDataType;
import nit.history.data.HistoryEvent;
import nit.history.data.LocationRelationShip;

public class MemoryHistoryService implements HistoryService {

	DAOFactory factory = new MemoryDAOFactory();

	
	
	@Override
	public void save(HistoryDataType data) {
		// TODO Auto-generated method stub

	}

	@Override
	public <D extends HistoryDataType> D load(String id, Class<D> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HistoryEvent> getHistoryEvents(HistoryDataType... types) {
		return factory.getHistoryDAO().getHistoryEvents(types);
	}

	@Override
	public List<LocationRelationShip> getRelationShips(HistoryDataType... data) {
		return factory.getLocationDAO().getRelationShips(data);
	}

	@Override
	public <D extends HistoryDataType> boolean exists(String id, Class<D> type) {
		return false;
	}
}
