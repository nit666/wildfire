package nit.history.data.impl;

import nit.history.data.Location;

public class LocationImpl implements Location {
	String name;
	Location parent;
	
	public LocationImpl(String name) {
		this.name = name;	
	}

	public LocationImpl(String name, Location parent) {
		this(name);
		this.parent = parent;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public Location getParent() {
		return parent;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LocationImpl) {
			LocationImpl loc = (LocationImpl) obj;
			return loc.getName().equals(getName());
		}
		return super.equals(obj);
	}

	@Override
	public String getID() {
		return getName();
	}
}
