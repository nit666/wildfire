package nit.history.data;

public interface Entity extends HistoryDataType {

	String getName();
	
	EntityGroup getGroup();
}
