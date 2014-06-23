package com.elemental.sprite.spritestate;

import android.graphics.Canvas;
import android.graphics.Point;

import com.elemental.GameContext;
import com.elemental.sprite.BulletThing;
import com.elemental.sprite.GameThing;

public class FireBehaviour implements Behaviour {

	Behaviour previousBehaviour;
	
	public FireBehaviour(GameContext context, GameThing target, int startx, int starty) {
		BulletThing bullet = new BulletThing(context, startx, starty, target.getX(), target.getY());
		context.spawn(bullet);
	}
	
	@Override
	public Behaviour update(GameContext context) {
		// don't do anything because we never get here
		return previousBehaviour;
	}

	@Override
	public void draw(Canvas canvas) {
		// no drawing necessary
	}

	@Override
	public Behaviour switchBehaviour(Behaviour switchTo) {
		previousBehaviour = switchTo;
		return this;
	}

}
