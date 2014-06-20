package com.elemental.util;

public enum TouchType {
	Touch (0,200, 0, 20),
	JumpLeft (20, 80),
	JumpRight (280, 340),
	JumpUp (20, 340), // special case as it covers the boundaries
	Bury (170, 190),
	RollLeft (80, 110),
	RollRight (250, 280),
	NotATouch;
	
	long minTouchTime = 100;
	long maxTouchTime = 400;
	float minAngle = 0;
	float maxAngle = 360;
	float minDistance = 10;
	float maxDistance = Float.MAX_VALUE;
	
	TouchType() {
		minTouchTime = Long.MIN_VALUE;
		maxTouchTime = Long.MAX_VALUE;
		minDistance = Float.MIN_VALUE;
		minDistance = Float.MAX_VALUE;
	}
	
	TouchType(float minAngle, float maxAngle) {
		this.minAngle = minAngle;
		this.maxAngle = maxAngle;
	}
	
	TouchType(long minTouchTime, long maxTouchTime, float minDistance, float maxDistance) {
		this.minTouchTime = minTouchTime;
		this.maxTouchTime = maxTouchTime;
		this.minDistance = minDistance;
		this.maxDistance = maxDistance;
	}
	
	public static TouchType findTouchType(RecordedTouch touch) {
		for (TouchType type : values()) {
			if (touch.getAngle() >= type.minAngle && touch.getAngle() < type.maxAngle) {
				if (touch.getDistance() >= type.minDistance && touch.getDistance() <= type.maxDistance) {
					if (touch.getTouchTime() >= type.minTouchTime && touch.getTouchTime() < type.maxTouchTime) {
						if (type == JumpUp) {
							continue;
						}
						return type;
					}
				}
			}
		}

		if ((touch.getAngle() > 0 && touch.getAngle() < JumpUp.minAngle ||
				touch.getAngle() < 360 && touch.getAngle() > JumpUp.maxAngle) &&
				touch.getTouchTime() > JumpUp.minTouchTime && touch.getTouchTime() < JumpUp.maxTouchTime) {
			return JumpUp;
		}
		
		return NotATouch;
	}
}
