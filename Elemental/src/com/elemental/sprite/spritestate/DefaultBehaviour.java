package com.elemental.sprite.spritestate;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.elemental.GameContext;
import com.elemental.sprite.GameThing;
import com.elemental.util.TimeTester;

public class DefaultBehaviour implements Behaviour {

	GameThing thing;
	boolean actionComplete;
	Behaviour pushedBehaviour = null;
	String name;
	
	long actionTime = 1000L;
	
	public DefaultBehaviour(GameThing thing, String name) {
		this.thing = thing;
		this.name = name;
	}
	
	@Override
	public Behaviour update(GameContext context) {
		if (TimeTester.test("behaviour " + this.hashCode(), actionTime)) {
			actionComplete = true;
		}
		
		if (pushedBehaviour != null) {
			pushedBehaviour.update(context);
		}
		
		if (actionComplete) {
			if (pushedBehaviour != null) {
				return pushedBehaviour;
			}
			return new IdleBehaviour(thing);
		}
		return this;	}

	@Override
	public void draw(Canvas canvas) {
		// remove this later
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setTextSize(40);
		p.setColor(Color.WHITE);
		p.setTextAlign(Paint.Align.CENTER);

		canvas.drawText(name + "!", canvas.getWidth() / 2, 50, p);
		
		// TODO switch to a jumping animation
		thing.getSprite().draw(canvas);
	}

	@Override
	public Behaviour switchBehaviour(Behaviour switchTo) {
		if (actionComplete) {
			return switchTo;
		}
		pushedBehaviour = switchTo;
		return this;
	}

}
