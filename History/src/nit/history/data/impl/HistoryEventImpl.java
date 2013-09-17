package nit.history.data.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import nit.history.data.Entity;
import nit.history.data.HistoryEvent;
import nit.history.data.Location;
import nit.history.data.TimeSpan;

public class HistoryEventImpl implements HistoryEvent {

	String ID;
	Location location;
	TimeSpan timespan;
	String description;

	public HistoryEventImpl(String ID, Location location, TimeSpan timespan) {
		this.location = location;
		this.timespan = timespan;
		this.ID = ID;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public TimeSpan getTimeSpan() {
		return timespan;
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
