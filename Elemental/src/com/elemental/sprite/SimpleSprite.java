package com.elemental.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SimpleSprite implements Sprite {

	Bitmap bitmap;
	float x;
	float y;
	
	public SimpleSprite(Bitmap bitmap, float x, float y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
	}

	@Override
	public int getSpriteWidth() {
		return bitmap.getWidth();
	}

	@Override
	public int getSpriteHeight() {
		return bitmap.getHeight();
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);	}

	@Override
	public void update(long time) {
		
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}
}
