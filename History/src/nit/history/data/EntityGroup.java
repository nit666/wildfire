package nit.history.data;

public interface EntityGroup extends HistoryDataType {

	String getGroupID();
	
	EntityGroup getParentGroup();
}
