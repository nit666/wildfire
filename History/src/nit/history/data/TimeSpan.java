package nit.history.data;

public class TimeSpan implements HistoryDataType {

	Time startTime;
	Time endTime;
	
	public TimeSpan(Time startTime, Time endTime) {
		if (startTime == null) {
			throw new NullPointerException("Start time can not be null");
		}
		if (endTime == null) {
			throw new NullPointerException("End time can not be null");
		}
		if (startTime.after(endTime)) {
			throw new IllegalArgumentException("The Start time needs to be Before the End time");
		}
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public Time getStartTime() {
		return startTime;
	}
	
	public Time getEndTime() {
		return endTime;
	}
	
	/**
	 * Check if a time lies between the start and end times of this time span
	 * @param time the time to check
	 * @return true if the time is within the time span period
	 */
	public boolean isWithinTimeSpan(Time time) {
		if ((time.equals(getStartTime()) || time.after(getStartTime())) 
			&& (time.equals(getEndTime()) || time.before(getEndTime()))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Check if the time span overlaps this time span. This basically check if the start time or end time are within this time span
	 * @param timeSpan the time span to check
	 * @return true if the time span passed in lies within this time span
	 */
	public boolean isWithinTimeSpan(TimeSpan timeSpan) {
		if (timeSpan.getStartTime().before(getStartTime()) && timeSpan.getEndTime().after(getEndTime())) {
			return true;
		}
		return isWithinTimeSpan(timeSpan.getStartTime()) || isWithinTimeSpan(timeSpan.getEndTime());
	}

	@Override
	public String getID() {
		return startTime.getID() + "+" + endTime.getID();
	}
}
