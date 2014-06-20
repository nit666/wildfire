package com.elemental.sprite;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.elemental.GameContext;
import com.elemental.sprite.spritestate.Behaviour;
import com.elemental.sprite.spritestate.DefaultBehaviour;
import com.elemental.sprite.spritestate.IdleBehaviour;
import com.elemental.sprite.spritestate.JumpingBehaviour;
import com.elemental.sprite.spritestate.MovingToBehaviour;
import com.elemental.util.RecordedTouch;
import com.elemental.util.TouchType;

/**
 * This class allows a character to do stuff. 
 */
public class PlayerCharacter extends GameThing {

	Behaviour currentBehaviour;
	
	public PlayerCharacter(GameContext context, int x, int y, Rect bounds) {
		super(x, y, bounds);
		currentBehaviour = new IdleBehaviour(this);
		setSprite(new PlayerSprite(context, x, y));
	}
	
	@Override
	public void update(GameContext context) {
		if (context.getLatestTouch() != null) {
			TouchType type = TouchType.findTouchType(context.getLatestTouch());
			switch (type) {
			case Touch:
				// move to the point, or fire if the point is higher than a certain point
				touch(context);
				break;
			case RollLeft:
			case RollRight:
				// rolling
				roll(context, type);
				break;
			case JumpLeft:
			case JumpRight:
			case JumpUp:
				// jumping
				jump(context, type);
				break;
			case Bury:
				// burying
				bury(context);
				break;
			default:
				idle(context);
			}
		}
		currentBehaviour = currentBehaviour.update(context);
	}

	@Override
	public void draw(Canvas canvas) {
		currentBehaviour.draw(canvas);
	}
	
	private void touch(GameContext context) {
		RecordedTouch touch = context.getLatestTouch();
		if (pointIsWithinBounds((int) touch.getFinishX(), (int) touch.getFinishY())) {
			currentBehaviour = currentBehaviour.switchBehaviour(new MovingToBehaviour(this, context));
		} else {
			// fire at point
			BulletThing bullet = new PlayerBulletThing(context, getX(), getY(), new Point((int) touch.getFinishX(), (int) touch.getFinishY()));
			context.spawn(bullet);
		}
	}
	
	private void jump(GameContext context, TouchType touchType) {
		currentBehaviour = currentBehaviour.switchBehaviour(new JumpingBehaviour(this, touchType));
	}
	
	private void roll(GameContext context, TouchType touchType) {
		currentBehaviour = currentBehaviour.switchBehaviour(new DefaultBehaviour(this, "Rolling"));	
	}
	
	private void bury(GameContext context) {
		currentBehaviour = currentBehaviour.switchBehaviour(new DefaultBehaviour(this, "Burying"));
	}
	
	private void idle(GameContext context) {
		currentBehaviour = currentBehaviour.switchBehaviour(new IdleBehaviour(this));		
	}

	@Override
	public void handleCollision(GameContext context, GameThing t) {
		if (!(t instanceof PlayerBulletThing)) {
			setState(CurrentState.Dead);
			context.setGameOver(true);
		}
	}
	
	
}
