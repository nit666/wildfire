package com.elemental.sprite;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.elemental.GameContext;
import com.elemental.sprite.spritestate.Behaviour;
import com.elemental.sprite.spritestate.IdleBehaviour;
import com.elemental.sprite.spritestate.MovingToBehaviour;
import com.example.elemental.R;

public class BulletThing extends GameThing {

	boolean hasBeenOnScreen = false;
	float targetX;
	float targetY;
	Behaviour behaviour;
	
	@Override
	public float getVelocity() {
		return 80;
	}
	
	public BulletThing(GameContext context, float x, float y, float targetX, float targetY) {
		super(x, y, new Rect(0, 0, context.getScreenWidth(), context.getScreenHeight()));
		getSpeed().setXv(3);
		getSpeed().setYv(3);		
		Bitmap bitmap = BitmapFactory.decodeResource(context.getContext()
				.getResources(), R.drawable.bullet);
		setSprite(new SimpleSprite(bitmap, x, y));
		this.targetX = targetX;
		this.targetY = targetY;
	}

	@Override
	public void update(GameContext context) {
		if (behaviour == null) {
			behaviour = new MovingToBehaviour(this, context, targetX, targetY);
			((MovingToBehaviour) behaviour).setContinueAfterTarget(true);
		}
		
		if (behaviour instanceof IdleBehaviour) {
			setState(CurrentState.Dead);
		} else if (isOffScreen(context.getScreenWidth(), context.getScreenHeight())) {
			if (hasBeenOnScreen) {
				setState(CurrentState.Dead);
			}
		} else {
			hasBeenOnScreen = true;
		}
		behaviour = behaviour.update(context);
	}

	@Override
	public void draw(Canvas canvas) {
		if (behaviour != null) {
			behaviour.draw(canvas);
		}
	}	
}
