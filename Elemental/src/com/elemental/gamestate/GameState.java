package com.elemental.gamestate;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.elemental.GameContext;

public interface GameState {
	
	boolean handleTouchEvent(MotionEvent event, GameContext context);
	
	GameState update(GameContext context);
	
	void draw(Canvas canvas, GameContext context);
}
