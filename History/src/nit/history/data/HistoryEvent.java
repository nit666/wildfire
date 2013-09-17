package nit.history.data;

import java.util.Collection;


public interface HistoryEvent extends HistoryDataType {

	String getID();
	
	String getDescription();
	
	Location getLocation();
	
	TimeSpan getTimeSpan();
}
