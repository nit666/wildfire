package com.elemental;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

	// desired fps
	private final static int MAX_FPS = 60;
	// maximum number of frames to be skipped
	private final static int MAX_FRAME_SKIPS = 5;
	// the frame period
	private final static int FRAME_PERIOD = 1000 / MAX_FPS;

	private static final String TAG = MainThread.class.getSimpleName();

	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanel;
	private boolean running;

	public void setRunning(boolean running) {
		this.running = running;
	}

	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	@Override
	public void run() {
		Canvas canvas;
		Log.d(TAG, "Starting game loop");

		long beginTime; // the time when the cycle begun
		long lastBeginTime; // the time the last cycle started
		long timeDiff; // the time it took for the cycle to execute
		int sleepTime; // ms to sleep (<0 if we're behind)

		sleepTime = 0;

		beginTime = System.currentTimeMillis();
		while (running) {
			lastBeginTime = beginTime;
			beginTime = System.currentTimeMillis();
			
			canvas = null;
			// try locking the canvas for exclusive pixel editing
			// in the surface
			synchronized (surfaceHolder) {
				this.gamePanel.update(beginTime - lastBeginTime);
				try {
					canvas = this.surfaceHolder.lockCanvas();
					this.gamePanel.render(canvas);
				} finally {
					if (canvas != null) {
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}

				// calculate how long did the cycle take
				timeDiff = (System.currentTimeMillis() - beginTime);
				sleepTime = (int) (FRAME_PERIOD - timeDiff);

				if (sleepTime > 0) {
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
					}
				}
			}
		}
	}

}
