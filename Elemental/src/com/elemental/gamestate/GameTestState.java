package com.elemental.gamestate;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.elemental.GameContext;
import com.elemental.sprite.PlayerCharacter;
import com.elemental.util.MotionHelper;
import com.elemental.util.RecordedTouch;
import com.elemental.util.TouchType;

public class GameTestState implements GameState {

	boolean endTest = false;
	PlayerCharacter testToon;
	MotionHelper motionHelper = new MotionHelper();
	String messageToPrint = "";
	
	public GameTestState(GameContext context) {
		Rect bounds = new Rect(0, 0, context.getScreenWidth(), context.getScreenHeight());
		testToon = new PlayerCharacter(context, context.getScreenWidth() / 2, context.getScreenHeight() - 100, bounds);
		context.setPlayer(testToon);
	}
	
	@Override
	public boolean handleTouchEvent(MotionEvent event, GameContext context) {
		// this method does everything we need
		return motionHelper.onTouch(event);
	}

	@Override
	public GameState update(GameContext context) {
		if (endTest) {
			return new GameStartState();
		}
		
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//
//			@Override
//			public void run() {
//				endTest = true;
//			}
//			
//		}, 2000L);
			
		if (motionHelper.hasTouchRecorded()) {
			RecordedTouch touch = motionHelper.getLatestTouch();
			messageToPrint = "Touch Distance = " + touch.getDistance() 
					+ ", Angle = " + touch.getAngle() 
					+ ", Time = " + touch.getTouchTime() 
					+ ", touch type = " + TouchType.findTouchType(touch); 
			
		}
		
		return this;
	}

	@Override
	public void draw(Canvas canvas, GameContext context) {
		canvas.drawColor(Color.BLACK);
		
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setTextSize(20);
		p.setColor(Color.WHITE);
		p.setTextAlign(Paint.Align.CENTER);

		canvas.drawText(messageToPrint, canvas.getWidth() / 2, canvas.getHeight() / 2, p);
		
		context.getPlayer().draw(canvas);
	}

}
