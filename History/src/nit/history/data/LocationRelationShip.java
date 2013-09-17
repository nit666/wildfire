package nit.history.data;

/**
 * the location relationship places an entity at a certain location at a certain time. 
 */
public class LocationRelationShip implements HistoryDataType {

	TimeSpan timespan;
	Entity entity;
	Location location; 
	
	public LocationRelationShip(TimeSpan timespan, Entity entity, Location location) {
		this.timespan = timespan;
		this.entity = entity;
		this.location = location;
	}

	public TimeSpan getTimespan() {
		return timespan;
	}

	public void setTimespan(TimeSpan timespan) {
		this.timespan = timespan;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String getID() {
		System.out.println(entity.getID());
		System.out.println(location.getID());
		System.out.println(timespan.getID());
		return 
		entity.getID() + 
		location.getID() + 
		timespan.getID();
	}
}
