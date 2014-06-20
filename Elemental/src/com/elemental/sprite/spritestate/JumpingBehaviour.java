package com.elemental.sprite.spritestate;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.elemental.GameContext;
import com.elemental.sprite.GameThing;
import com.elemental.util.TouchType;

public class JumpingBehaviour implements Behaviour {

	GameThing thing;
	boolean jumpComplete;
	Behaviour pushedBehaviour = null;
	TouchType touchType;
	
	// final long jumpTotalTime = 800;
	
	long jumpStartTime;
//	double jumpHeight;
//	double jumpLength;
	Point initialLocation = null;
	
	public JumpingBehaviour(GameThing thing, TouchType type) {
		this.thing = thing;
		this.touchType = type;
		jumpStartTime = System.currentTimeMillis();
	}
	
	@Override
	public Behaviour update(GameContext context) {

		if (initialLocation == null) {			
			initialLocation = new Point(thing.getX(), thing.getY());
		}
		
		double jumpLength = getJumpLength(context);
		double jumpHeight = getJumpHeight(context);
		if (touchType == TouchType.JumpUp) {
			jumpLength = 0;
		}
		
		// figure out how much time has passed
		long currentTime = System.currentTimeMillis() - jumpStartTime;
		double percentComplete = (double) currentTime / getJumpTime();
		
		if (percentComplete > 1) {
			jumpComplete = true;
			thing.setY(initialLocation.y); // to prevent minor adjustments in height;
		} else {			
			double xNow = initialLocation.x; // keep the same for jump up
			if (touchType == TouchType.JumpRight) {
				xNow = initialLocation.x + jumpLength * percentComplete;
			} else if (touchType == TouchType.JumpLeft) {
				xNow = initialLocation.x - jumpLength * percentComplete;
			}
			int sadjust = thing.getSprite().getSpriteWidth() / 2;
			if (xNow > context.getScreenWidth() - sadjust) {
				xNow = context.getScreenWidth() - sadjust;
			} else if (xNow < 0 + sadjust) {
				xNow = 0 + sadjust;
			}
			double yNow = initialLocation.y - (jumpHeight * Math.sin(Math.toRadians(180 * percentComplete)));

			// Log.d("jump coords", "X = " + xNow +", Y = " + yNow + ", % = " + percentComplete + ", CT = " + currentTime);
			
			thing.setX((int)xNow);
			thing.setY((int)yNow);
		}
		
		if (pushedBehaviour != null) {
			pushedBehaviour.update(context);
		}
		
		if (jumpComplete) {
			if (pushedBehaviour != null) {
				return pushedBehaviour;
			}
			return new IdleBehaviour(thing);
		}
		return this;
	}

	@Override
	public void draw(Canvas canvas) {

		// remove this later
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setTextSize(40);
		p.setColor(Color.WHITE);
		p.setTextAlign(Paint.Align.CENTER);

		canvas.drawText("Jumping Weee!", canvas.getWidth() / 2,
				50, p);
		
		// TODO switch to a jumping animation
		thing.getSprite().draw(canvas);
	}

	@Override
	public Behaviour switchBehaviour(Behaviour switchTo) {
		if (jumpComplete) {
			return switchTo;
		}
		pushedBehaviour = switchTo;
		return this;
	}

	/**
	 * override these methods to use for other purposes
	 * @return
	 */
	protected double getJumpTime() {
		return 700;
	}
	
	protected double getJumpLength(GameContext context) {
		return context.getScreenWidth() / 4; // always a 1/4 of the screen
	}
	
	protected double getJumpHeight(GameContext context) {
		return context.getScreenHeight() / 4;
	}
}
