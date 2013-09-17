package nit.history.data;

public interface Location extends HistoryDataType {

	String getName();
	
	Location getParent();
	
}
