package com.elemental.gamestate;

import java.util.LinkedList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.elemental.GameContext;
import com.elemental.Score;
import com.elemental.sprite.GameThing;
import com.elemental.sprite.PlayerCharacter;
import com.elemental.world.World;

public class GameStartState implements GameState {

	boolean readyToStart = false;
	boolean readyToTest = false;
	
	@Override
	public boolean handleTouchEvent(MotionEvent event, GameContext context) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (event.getY() < 100 && event.getX() < 100) {
				readyToTest = true;
			} else {
				readyToStart = true;
			}
		}
		return false;
	}

	@Override
	public GameState update(GameContext context) {
		if (readyToTest) {
			return new GameTestState(context);
		}
		if (readyToStart) {
			setup(context);
			return new GamePlayingState();
		}
		return this;
	}

	@Override
	public void draw(Canvas canvas, GameContext context) {
		canvas.drawColor(Color.BLACK);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setTextSize(40);
		p.setColor(Color.WHITE);
		p.setTextAlign(Paint.Align.CENTER);

		canvas.drawText("Welcome, Press anywhere to play!!", context.getScreenWidth() / 2,
				context.getScreenHeight() / 2, p);
	}

	void setup(GameContext context) {
		context.setCurrentWorld(new World(context));
		
		// this is the starting player
		Rect bounds = new Rect(0, context.getScreenHeight() - 150, context.getScreenWidth(), context.getScreenHeight() - 30);
		PlayerCharacter player = new PlayerCharacter(context, context.getScreenWidth() /2, context.getCurrentWorld().getDefaultYPosition(), bounds);		
		context.setPlayer(player);
		
		// Reinitialise the list of mobs, just in case this isn't the first game
		context.setTouchables(new LinkedList<GameThing>());
		context.setScore(new Score());
		context.setGameOver(false);
	}
}
