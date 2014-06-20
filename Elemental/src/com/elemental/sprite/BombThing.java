package com.elemental.sprite;

import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.elemental.GameContext;
import com.elemental.util.SoundManager;
import com.example.elemental.R;

public class BombThing extends GameThing {

	boolean exploded = false;
	PlayOnceAnimationSprite bombSprite;
	PlayOnceAnimationSprite explosionSprite;
	
	int fuseSoundStream;
	
	public BombThing(GameContext context, int x, int y, Rect bounds) {
		super(x, y, bounds);
		getSpeed().setXv(0);
		getSpeed().setYv(0);
		explosionSprite = new PlayOnceAnimationSprite(BitmapFactory
				.decodeResource(
						context.getContext().getResources(),
						R.drawable.explosion_anim), x, y, 88, 99, 20, 3);
		bombSprite = new PlayOnceAnimationSprite(BitmapFactory
				.decodeResource(
						context.getContext().getResources(),
						R.drawable.bomb_anim), x, y, 45, 45, 5, 8);
		
		setSprite(bombSprite);
		fuseSoundStream = SoundManager.playSound(context.getContext(), SoundManager.Sound.BombFuse, 
				(getX() == 0)? 0 : context.getScreenWidth() / getX());
	}
	
	@Override
	public void update(GameContext context) {
		super.update(context);
		PlayOnceAnimationSprite currentSprite = (PlayOnceAnimationSprite) getSprite();
		if (currentSprite.isFinished()) {
			if (exploded) {
				setState(CurrentState.Dead);
			} else {
				exploded = true;
				setSprite(explosionSprite);
				SoundManager.stopSound(fuseSoundStream);
				SoundManager.playSound(context.getContext(), SoundManager.Sound.BombExplosion, 
						(getX() == 0)? 0 : context.getScreenWidth() / getX());
			}
		} 

	}



	@Override
	public void handleCollision(GameContext context, GameThing t) {
		if (t.isPlayer() && exploded) {
			t.setState(CurrentState.Dead);
		}
	}
	
	// we only collide when the bomb has gone off
	@Override
	public boolean collidesWith(GameThing t) {
		if (!exploded) {
			return false;
		}
		return super.collidesWith(t);
	}

}
