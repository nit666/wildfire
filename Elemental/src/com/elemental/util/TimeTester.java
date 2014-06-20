package com.elemental.util;

import java.util.HashMap;
import java.util.Map;

/**
 * This class allows you to test for time and run a condition if that time has passed. 
 * if you want to check if a certain amount of time has passed since the line of code was last touched, 
 * then use this class. 
 * 
 * you need a unique string for your time.
 */
public class TimeTester {
	
	long interval;
	private static Map<String, Long> intervals = new HashMap<String, Long>();
	
	// no constucter as it is a helper class
	private TimeTester() {}
		
	// tests if the time has passed, or if this is the first time called, sets the time and returns false
	public static boolean test(String key, long timeInterval) {
		if (intervals.containsKey(key)) {
			if (getTime() > intervals.get(key)) {
				intervals.remove(key);
				return true;
			}
		} else {
			intervals.put(key, getTime() + timeInterval);
		}
		return false;
	}
	
	public static void reset(String key, long timeInterval) {
		if (intervals.containsKey(key)) {
			intervals.remove(key);
		}
		intervals.put(key, getTime() + timeInterval);
	}
	
	private static long getTime() {
		return System.currentTimeMillis();
	}
}
