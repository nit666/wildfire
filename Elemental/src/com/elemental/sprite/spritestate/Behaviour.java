package com.elemental.sprite.spritestate;

import android.graphics.Canvas;

import com.elemental.GameContext;
import com.elemental.sprite.Sprite;

public interface Behaviour {

	// update the state of the current behaviour
	Behaviour update(GameContext context);
	
	// draw whatever you need to based on the behaviour
	void draw(Canvas canvas);
	
	// switch to a new behaviour due to some foreign event
	// there might be things needed to be done before we switch
	// this method should be as short as possible
	Behaviour switchBehaviour(Behaviour switchTo);
}
