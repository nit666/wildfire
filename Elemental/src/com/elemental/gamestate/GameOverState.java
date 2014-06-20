package com.elemental.gamestate;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.elemental.GameContext;

public class GameOverState implements GameState {

	boolean drawn = false;
	
	@Override
	public GameState update(GameContext context) {
		if (drawn) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new GameStartState();
	}

	@Override
	public void draw(Canvas canvas, GameContext context) {
		canvas.drawColor(Color.BLACK);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setTextSize(60);
		p.setColor(Color.WHITE);
		p.setTextAlign(Paint.Align.CENTER);

		canvas.drawText("Game Over", context.getScreenWidth() / 2,
				context.getScreenHeight() / 2, p);
		context.getScore().draw(canvas);
		drawn = true;
	}

	@Override
	public boolean handleTouchEvent(MotionEvent event, GameContext context) {
		// nothing to do here
		return false;
	}
}
