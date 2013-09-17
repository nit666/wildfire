package nit.history.data.impl;

import nit.history.data.Entity;
import nit.history.data.EntityGroup;

public class EntityImpl implements Entity {

	EntityGroup group;
	String name;

	public EntityImpl(String name) {
		group = null;
		this.name = name;
	}
	
	@Override
	public EntityGroup getGroup() {
		return group;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getID() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Entity) {
			return ((Entity) obj).getID().equals(getID());
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	
}
