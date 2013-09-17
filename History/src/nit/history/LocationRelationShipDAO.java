package nit.history;

import java.util.Collection;
import java.util.List;

import nit.history.data.Entity;
import nit.history.data.HistoryDataType;
import nit.history.data.Location;
import nit.history.data.LocationRelationShip;
import nit.history.data.Time;
import nit.history.data.TimeSpan;

public interface LocationRelationShipDAO extends BaseDAO<LocationRelationShip> {

	/**
	 * Returns true if the entity was at a particular location at a particular time
	 * @param location
	 * @param entity
	 * @param timespan
	 * @return
	 */
	boolean entityWasAtLocationAtTime(Location location, Entity entity, TimeSpan timespan);
	
	/**
	 * Returns a list of entity relationships for a set of parameters
	 */
	List<LocationRelationShip> getRelationShips(HistoryDataType ... data);
	
	/**
	 * gets a list of entities that are at a particular location at a particular time span
	 * @param location
	 * @param time
	 * @return a list of entities
	 */
	Collection<Entity> getEntitiesForLocationForTimeSpan(Location location, TimeSpan time);

	/**
	 * gets a list of entities that are at a particular location at a particular time
	 * @param location
	 * @param time
	 * @return a list of entities
	 */
	Collection<Entity> getEntitiesForLocationForTime(Location location, Time time);
	
	/**
	 * Get the entities that have been at a location for all time. 
	 * @param location
	 * @return a list of entities
	 */
	Collection<Entity> getEntitiesForLocation(Location location);
	
}
