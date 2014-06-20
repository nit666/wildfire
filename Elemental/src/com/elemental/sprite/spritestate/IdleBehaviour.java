package com.elemental.sprite.spritestate;

import android.graphics.Canvas;

import com.elemental.GameContext;
import com.elemental.sprite.GameThing;

public class IdleBehaviour implements Behaviour {

	GameThing thing;
	
	public IdleBehaviour(GameThing thing) {
		this.thing = thing;
	}
	
	@Override
	public Behaviour update(GameContext context) {
		// no updates required at this time, randomly blink or something
		return this;
	}

	@Override
	public void draw(Canvas canvas) {
		thing.getSprite().draw(canvas);
	}

	@Override
	public Behaviour switchBehaviour(Behaviour switchTo) {
		return switchTo;
	}

}
