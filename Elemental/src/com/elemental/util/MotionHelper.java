package com.elemental.util;

import android.graphics.Point;
import android.view.MotionEvent;

public class MotionHelper {

	RecordedTouch latestTouch = null;
	
	private float initialX = 0;
	private float initialY = 0;
	private long startTime = 0;
	
	public boolean onTouch(MotionEvent event) {
		// This prevents touchscreen events from flooding the main thread
		synchronized (event) {
			try {
				// Waits 16ms.
				event.wait(16);

				// when user touches the screen
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					startTime = event.getEventTime();
					
					// get initial positions
					initialX = event.getRawX();
					initialY = event.getRawY();
				}

				// when screen is released
				if (event.getAction() == MotionEvent.ACTION_UP) {
					float x = event.getRawX();
					float y = event.getRawY();					
					
					// make a new record of this touch
					RecordedTouch rt = new RecordedTouch();
					rt.setStartX(initialX);
					rt.setStartY(initialY);
					rt.setFinishX(x);
					rt.setFinishY(y);
					rt.setDeltaX(x - initialX);
					rt.setDeltaY(y - initialY);
					rt.setTouchTime(event.getEventTime() - startTime);
					
					latestTouch = rt;
				}
			}
			catch (InterruptedException e) {
				// nothing to do here
			}
			return true;
		}
	}
	
	public RecordedTouch getLatestTouch() {

		if (latestTouch != null) {
		
			// at this point figure out the angle and distance using trig
			float distance = (float) Math.sqrt(latestTouch.getDeltaX() * latestTouch.getDeltaX() + latestTouch.getDeltaY() + latestTouch.getDeltaY());
			if (distance == Float.NaN) {
				distance = 0f;
			}
			latestTouch.setDistance(distance);
			
			double x = Math.abs(latestTouch.getDeltaX());
			double y = Math.abs(latestTouch.getDeltaY());
			
			double angle = getAngle(latestTouch.finishX, latestTouch.startX, latestTouch.finishY, latestTouch.startY);
			
			latestTouch.setAngle((float) angle);
		}
		
		RecordedTouch retVal = latestTouch;
		latestTouch = null; // need to erase this now as it has been used
		
		return retVal;
	}
	
	public boolean hasTouchRecorded() {
		return latestTouch != null;
	}
	
	public double getAngle(double x1, double x2, double y1, double y2) {
	    double angle = (double) Math.toDegrees(Math.atan2(x2 - x1, y2 - y1));

	    if(angle < 0){
	        angle += 360;
	    }

	    return angle;
	}
}