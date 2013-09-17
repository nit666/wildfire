package nit.history.dao.memory;

import nit.history.DAOFactory;
import nit.history.EntityDAO;
import nit.history.data.Entity;

public class MemoryEntityDAOImpl extends MemoryStorage<Entity> implements EntityDAO {

	DAOFactory factory;
	
	public MemoryEntityDAOImpl(DAOFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public void createOrUpdateEntity(Entity entity) {
		save(entity);
	}

	@Override
	public Entity getEntity(String key) {
		return load(key);
	}

	@Override
	public boolean exists(String key) {
		return contains(key);
	}


}
