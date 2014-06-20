package com.elemental.util;

public class RecordedTouch {
	float startX;
	float startY;
	float finishX;
	float finishY;
	long touchTime;
	float deltaX;
	float deltaY;
	float angle;
	float distance;
	
	public float getAngle() {
		return angle;
	}
	public void setAngle(float angle) {
		this.angle = angle;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getStartX() {
		return startX;
	}
	public void setStartX(float startX) {
		this.startX = startX;
	}
	public float getStartY() {
		return startY;
	}
	public void setStartY(float startY) {
		this.startY = startY;
	}
	public float getFinishX() {
		return finishX;
	}
	public void setFinishX(float finishX) {
		this.finishX = finishX;
	}
	public float getFinishY() {
		return finishY;
	}
	public void setFinishY(float finishY) {
		this.finishY = finishY;
	}
	public long getTouchTime() {
		return touchTime;
	}
	public void setTouchTime(long touchTime) {
		this.touchTime = touchTime;
	}
	public float getDeltaX() {
		return deltaX;
	}
	public void setDeltaX(float deltaX) {
		this.deltaX = deltaX;
	}
	public float getDeltaY() {
		return deltaY;
	}
	public void setDeltaY(float deltaY) {
		this.deltaY = deltaY;
	}
}