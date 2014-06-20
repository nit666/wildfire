package com.elemental.sprite.spritestate;

import android.graphics.Canvas;
import android.graphics.Point;

import com.elemental.GameContext;
import com.elemental.sprite.GameThing;

public class MovingToBehaviour implements Behaviour {

	GameThing thing;
	Point moveToLocation;
	boolean continueAfterTarget = false;
	
	public MovingToBehaviour(GameThing thing, GameContext context) {
		this(thing, context, 
				new Point((int) context.getLatestTouch().getFinishX(), (int) context.getLatestTouch().getFinishY()));
	}

	public MovingToBehaviour(GameThing thing, GameContext context, Point moveToLocation) {
		this.thing = thing;
		this.moveToLocation = moveToLocation;
		thing.changeDirection(moveToLocation.x, moveToLocation.y, thing.getX(), thing.getY());
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
		if (Math.abs(thing.getX() - moveToLocation.x) < thing.getSpeed().getXv()) {
			thing.setX(moveToLocation.x);
		}
		if (Math.abs(thing.getY() - moveToLocation.y) < thing.getSpeed().getYv()) {
			thing.setY(moveToLocation.y);
		};
		
		if (!isContinueAfterTarget()) {
			thing.changeDirection(moveToLocation.x, moveToLocation.y, thing.getX(), thing.getY());
		}
		thing.updatePositionBasedOnSpeed(context);
		
		if (!isContinueAfterTarget()) {
			if (thing.getX() == moveToLocation.x) { // && thing.getY() == moveToLocation.y){
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
