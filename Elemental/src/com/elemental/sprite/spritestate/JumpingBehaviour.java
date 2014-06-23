package com.elemental.sprite.spritestate;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

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
	long updatedTime = 0;

	float initialX = -20000;
	float initialY = -20000;
	
	public JumpingBehaviour(GameThing thing, TouchType type) {
		this.thing = thing;
		this.touchType = type;
		jumpStartTime = System.currentTimeMillis();
	}
	
	@Override
	public Behaviour update(GameContext context) {

		if (initialX == -20000 && initialY == -20000) {
			initialX = thing.getX();
			initialY = thing.getY();
		}
		
		float jumpLength = getJumpLength(context);
		float jumpHeight = getJumpHeight(context);
		if (touchType == TouchType.JumpUp) {
			jumpLength = 0;
		}
		
		// figure out how much time has passed
		updatedTime += context.getTimeSinceLastUpdate();
		float percentComplete = (float) updatedTime / getJumpTime();
		
		if (percentComplete >= 1.0) {
			jumpComplete = true;
			thing.setY((int) initialY); // to prevent minor adjustments in height;
			thing.setX((int) thing.getX());
		} else {			
			float xNow = initialX; // keep the same for jump up
			if (touchType == TouchType.JumpRight) {
				xNow = initialX + jumpLength * percentComplete;
			} else if (touchType == TouchType.JumpLeft) {
				xNow = initialX - jumpLength * percentComplete;
			}
			int sadjust = thing.getSprite().getSpriteWidth() / 2;
			if (xNow > context.getScreenWidth() - sadjust) {
				xNow = context.getScreenWidth() - sadjust;
			} else if (xNow < 0 + sadjust) {
				xNow = 0 + sadjust;
			}
			float yNow = (float)(initialY - (jumpHeight * Math.sin(Math.toRadians(180 * percentComplete))));

			Log.d("jump coords", "X = " + xNow +", Y = " + yNow + ", % = " + percentComplete + ", CT = " + updatedTime);
						
			thing.setX(xNow);
			thing.setY(yNow);
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
	protected long getJumpTime() {
		return 700;
	}
	
	protected float getJumpLength(GameContext context) {
		return context.getScreenWidth() / 4; // always a 1/4 of the screen
	}
	
	protected float getJumpHeight(GameContext context) {
		return context.getScreenHeight() / 4;
	}
}
