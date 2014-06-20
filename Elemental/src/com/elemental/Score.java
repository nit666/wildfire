package com.elemental;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Score implements Drawable {
	int score;
	
	public Score() {
		score = 0;
	}

	public void addScore(int score) {
		this.score += score;
	}
	
	@Override
	public void draw(Canvas canvas) {
		  Paint p = new Paint();
		  p.setAntiAlias(true);
		  p.setTextSize(20);
		  p.setColor(Color.WHITE);
		  p.setTextAlign(Paint.Align.CENTER);

		  canvas.drawText("Score: " + score, 50, 22, p);
	}	
}
