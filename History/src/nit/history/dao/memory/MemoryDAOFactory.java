package nit.history.dao.memory;

import nit.history.DAOFactory;
import nit.history.EntityDAO;
import nit.history.HistoryDAO;
import nit.history.LocationDAO;

public class MemoryDAOFactory implements DAOFactory {

	EntityDAO entityDAO = new MemoryEntityDAOImpl(this);
	HistoryDAO historyDAO = new MemoryHistoryDAOImpl(this);
	LocationDAO locationDAO = new MemoryLocationDAOImpl(this);
	
	@Override
	public EntityDAO getEntityDAO() {
		return entityDAO;
	}

	@Override
	public HistoryDAO getHistoryDAO() {
		return historyDAO;
	}

	@Override
	public LocationDAO getLocationDAO() {
		return locationDAO;
	}

	
	
}
