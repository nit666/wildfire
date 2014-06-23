package com.elemental.sprite.spritestate;

import android.graphics.Canvas;
import android.graphics.Point;

import com.elemental.GameContext;
import com.elemental.sprite.GameThing;
import com.elemental.util.RecordedTouch;

public class MovingToBehaviour implements Behaviour {

	GameThing thing;
	float moveToX;
	float moveToY;
	boolean continueAfterTarget = false;
	
	public MovingToBehaviour(GameThing thing, GameContext context) {
		this(thing, context, context.getLatestTouch().getFinishX(), context.getLatestTouch().getFinishY());
	}

	public MovingToBehaviour(GameThing thing, GameContext context, float moveX, float moveY) {
		this.thing = thing;
		this.moveToX = moveX;
		this.moveToY = moveY;
		thing.changeDirection(moveToX, moveToY, thing.getX(), thing.getY());
	}
	
	public boolean isContinueAfterTarget() {
		return continueAfterTarget;
	}

	public void setContinueAfterTarget(boolean continueAfterTarget) {
		this.continueAfterTarget = continueAfterTarget;
	}

	@Override
	public Behaviour update(GameContext context) {	
		
		// the next move will get us there
		if (Math.abs(thing.getX() - moveToX) < thing.getSpeed().getXv() / context.getTimeSinceLastUpdate()) {
			thing.setX(moveToX);
		}
		if (Math.abs(thing.getY() - moveToY) < thing.getSpeed().getYv() / context.getTimeSinceLastUpdate()) {
			thing.setY(moveToY);
		};
		
		if (!isContinueAfterTarget()) {
			thing.changeDirection(moveToX, moveToY, thing.getX(), thing.getY());
		}
		thing.updatePositionBasedOnSpeed(context);
		
		if (!isContinueAfterTarget()) {
			if ((int) thing.getX() == (int) moveToX) { // && thing.getY() == moveToLocation.y){
				return new IdleBehaviour(thing);
			}
		}
		return this;
	}

	@Override
	public void draw(Canvas canvas) {
		// in future switch the sprite to a moving animation depending on the direction
		// for now just draw the sprite
		thing.getSprite().draw(canvas);
	}

	@Override
	public Behaviour switchBehaviour(Behaviour switchTo) {
		// jumps and moves can happen at the same time, so push the move into the jump
		if (switchTo instanceof JumpingBehaviour) {
			switchTo.switchBehaviour(this);
		} else if (switchTo instanceof FireBehaviour) {
			// you can also fire and move at the same time
			switchTo.switchBehaviour(this);
		}
		return switchTo;
	}
}
