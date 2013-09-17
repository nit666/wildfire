package nit.history.data;

public interface Time extends HistoryDataType {
	boolean after(Time time); // this time is after the parameter time
	boolean before(Time time); // this time is before the parameter time
}
