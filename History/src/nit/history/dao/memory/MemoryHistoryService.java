package nit.history.dao.memory;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nit.history.BaseDAO;
import nit.history.DAOFactory;
import nit.history.HistoryService;
import nit.history.data.Entity;
import nit.history.data.HistoryDataType;
import nit.history.data.HistoryEvent;
import nit.history.data.Location;
import nit.history.data.LocationRelationShip;
import nit.history.data.TimeSpan;
import nit.history.data.impl.EntityImpl;
import nit.history.data.impl.HistoryEventImpl;
import nit.history.data.impl.LocationImpl;

public class MemoryHistoryService implements HistoryService {

	final DAOFactory factory;;
	final Map<Class<? extends HistoryDataType>, BaseDAO<?>> daos;
	
	public MemoryHistoryService(DAOFactory factory) {
		this.factory = factory;
		daos = new HashMap<Class<? extends HistoryDataType>, BaseDAO<?>>();
		daos.put(Entity.class, factory.getEntityDAO());
		daos.put(Location.class, factory.getLocationDAO());
		daos.put(HistoryEvent.class, factory.getHistoryDAO());
		daos.put(LocationRelationShip.class, factory.getRelationShipDAO());
	}
	
	@Override
	public <D extends HistoryDataType> void saveOrUpdate(D data, Class<D> type) {
		BaseDAO<D> dao = (BaseDAO<D>) daos.get(type);
		if (dao != null) {
			dao.createOrUpdate(data);
		} else {
			throw new IllegalArgumentException("The data type of " + data.getClass() + " doesn't have a DAO");
		}
	}

	@Override
	public <D extends HistoryDataType> D fetch(String id, Class<D> type) {
		BaseDAO<D> dao = (BaseDAO<D>) daos.get(type);
		if (dao != null) {
			return dao.fetch(id);
		} else {
			throw new IllegalArgumentException("The data type of " + type + " doesn't have a DAO");
		}
	}

	@Override
	public List<HistoryEvent> getHistoryEvents(HistoryDataType... types) {
		return factory.getHistoryDAO().getHistoryEvents(types);
	}

	@Override
	public List<LocationRelationShip> getRelationShips(HistoryDataType... data) {
		return factory.getRelationShipDAO().getRelationShips(data);
	}

	@Override
	public <D extends HistoryDataType> boolean exists(String id, Class<D> type) {
		BaseDAO<D> dao = (BaseDAO<D>) daos.get(type);
		if (dao != null) {
			return dao.exists(id);
		} else {
			throw new IllegalArgumentException("The data type of " + type + " doesn't have a DAO");
		}	
	}

	@Override
	public Collection<Entity> getEntitiesForLocationForTimeSpan(
			Location location, TimeSpan time) {
		return factory.getRelationShipDAO().getEntitiesForLocationForTimeSpan(location, time);
	}
}
