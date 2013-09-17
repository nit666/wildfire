package nit.history;

import nit.history.data.Entity;

public interface EntityDAO {
	
	/**
	 * Get an entity based on its key
	 * @param key the key of the entity
	 * @return the full entity
	 */
	Entity getEntity(String key);
	
	/**
	 * Save an entity and create it if it doesn't already exist
	 * @param entity the entity to save
	 */
	void createOrUpdateEntity(Entity entity);
	
	/**
	 * Determines if an entity with the key already exists or not
	 * @param key
	 * @return returns true if the entity exists, false if not
	 */
	boolean exists(String key);
}
