package com.elemental.gamestate;

import java.util.Iterator;
import java.util.List;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.elemental.GameContext;
import com.elemental.sprite.GameThing;
import com.elemental.sprite.PlayerBulletThing;
import com.elemental.util.MotionHelper;

public class GamePlayingState implements GameState {

	long spawnRate = 1500L; // spawn every second or so
	long spawnTollerance = 500L; // + or - 500ms
	int spawnFrequencyIncrease = 50; // increase spawn rate per spawn
	
	MotionHelper motionHelper = new MotionHelper();
	
	public GamePlayingState() {
	}

	@Override
	public boolean handleTouchEvent(MotionEvent event, GameContext context) {
		// this method does everything we need
		return motionHelper.onTouch(event);	}
	
	@Override
	public GameState update(GameContext context) {
		
		List<GameThing> touchables = context.getTouchables();
		for (GameThing t : touchables) {
			if (t instanceof PlayerBulletThing) {
				for (GameThing gt : touchables) {
					if (!(gt instanceof PlayerBulletThing) && gt.collidesWith(t)) {
						t.handleCollision(context, gt);
					}
				}
			} else if (t.collidesWith(context.getPlayer())) {
				context.getPlayer().handleCollision(context, t);
			}
		}
		
		// update the dead list
		Iterator<GameThing> it = touchables.iterator();
		while (it.hasNext()) {
			GameThing gt = it.next();
			if (gt.getState() == GameThing.CurrentState.Dead) {
				it.remove();
				context.getScore().addScore(gt.getScoreValue());
			}
		}

		// update any player moves
		if (motionHelper.hasTouchRecorded()) {
			context.setLatestTouch(motionHelper.getLatestTouch());
		}
		
		// update the sprites
		context.getPlayer().update(context);
		for (GameThing t : touchables) {
			t.update(context);
		}
		
		context.getCurrentWorld().update(context);
		
		// make sure all the spawns are available on the screen
		context.updateSpawnList();
		// don't want to reuse this...
		context.setLatestTouch(null);
				
		
		if (context.isGameOver()) {
			return new GameOverState();
		}
		return this;
	}

	@Override
	public void draw(Canvas canvas, GameContext context) {
		context.getCurrentWorld().draw(canvas);
		
		// draw the sprites
		for (GameThing t : context.getTouchables()) {
			t.draw(canvas);
		}
		
		// draw the player
		context.getPlayer().draw(canvas);
		
		// draw the score
		context.getScore().draw(canvas);
	}
}
