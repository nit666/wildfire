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
import com.elemental.util.TimeTester;
import com.example.elemental.R;

public class Alien1 extends GameThing {

	Behaviour currentBehaviour;
	
	public Alien1(GameContext context, int x, int y, Rect bounds) {
		super(x, y, bounds);
		Bitmap bitmap = BitmapFactory.decodeResource(context.getContext()
				.getResources(), R.drawable.alien1);
		setSprite(new SimpleSprite(bitmap, x, y));
	}

	@Override
	public void update(GameContext context) {

		if (currentBehaviour == null || currentBehaviour instanceof IdleBehaviour) {
			// move around randomly and fire at the player
			int x = (int) (Math.random() * 1000 % bounds.width());
			int y = (int) (Math.random() * 1000 % bounds.height());
			
			currentBehaviour = new MovingToBehaviour(this, context, new Point(x, y));
		}

		// fire every 1 - 3 seconds
		if (TimeTester.test(this.hashCode() + "alien", 1000 + (int)(Math.random() * 10000 % 2000))) {
			// don't fire for now
			
			//BulletThing bullet = new BulletThing(context, this.getX(), this.getY(), new Point(context.getPlayer().getX(), context.getPlayer().getY()));
			//context.spawn(bullet);
		}
		
		currentBehaviour = currentBehaviour.update(context);
	}

	@Override
	public void draw(Canvas canvas) {
		if (currentBehaviour != null) {
			currentBehaviour.draw(canvas);
		}
	}

	@Override
	public int getScoreValue() {
		return 1;
	}	
	
	
}
