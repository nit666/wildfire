package com.elemental;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.elemental.gamestate.GameStartState;
import com.elemental.gamestate.GameState;

public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private MainThread thread;
	GameState gameState;
	GameContext gameContext = new GameContext();
	
	public MainGamePanel(Context context) {
		super(context);
		getHolder().addCallback(this);

		// get the current screen size
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		gameContext.setScreenWidth(metrics.widthPixels);
		gameContext.setScreenHeight(metrics.heightPixels);
		gameContext.setContext(getContext());
		gameState = new GameStartState();
		
		// create the game loop thread
		thread = new MainThread(getHolder(), this);
		
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
	}

	public void pauseGame() {
		thread.setRunning(false);
	}

	public void unPauseGame() {
		thread = new MainThread(getHolder(), this);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gameState.handleTouchEvent(event, gameContext);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		gameState.draw(canvas, gameContext);
	}

	public void render(Canvas canvas) {
		onDraw(canvas);
	}

	public void update() {
		gameState = gameState.update(gameContext);
	}
	

}
