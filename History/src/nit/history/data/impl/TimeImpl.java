/**
 * 
 */
package nit.history.data.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import nit.history.data.Time;

public final class TimeImpl implements Time {

	long year; // the year that the time represents, 0 is the year '0'
	long milliseconds; // the millisecond within the year in question.
	
	
	public TimeImpl(Date date) {
		this.year = date.getYear();
		this.milliseconds = date.getTime();
	}
	public TimeImpl(long time) {
		this.year = new Date(time).getYear();
		this.milliseconds = time;
	}
	
	public TimeImpl(long year, long time) {
		this.year = year;
		this.milliseconds = time;
	}
	
	@Override
	public boolean after(Time time) {
		TimeImpl ptime = (TimeImpl) time;
		if (year > ptime.year) {
			return true; 
		} else if (year == ptime.year) {
			if (milliseconds > ptime.milliseconds) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean before(Time time) {
		TimeImpl ptime = (TimeImpl) time;
		if (year < ptime.year) {
			return true;
		} else if (year == ptime.year) {
			if (milliseconds < ptime.milliseconds) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TimeImpl) {
			TimeImpl t = (TimeImpl) obj;
			return t.year == year && t.milliseconds == milliseconds;
		}
		return super.equals(obj);
	}
	
	/**
	 * Convert time numbers to a Date String
	 */
	public String getTimeString() {
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return df.format(new Date(milliseconds));
	}
	
	
	public boolean isLeapYear() {
		if (year % 400 == 0) {
		   return true;
		} else if (year % 100 == 0) {
		   return false;
		} else if (year % 4 == 0) {
		   return true;
		} else {
		   return false;
		}
	}

	@Override
	public String getID() {
		return String.valueOf(milliseconds);
	}
	
	static SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
	
	@Override
	public String toString() {
		return sdf.format(new Date(milliseconds));
	}
}