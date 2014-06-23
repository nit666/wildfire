package com.elemental.sprite;

import android.graphics.Canvas;

public interface Sprite {

	public int getSpriteWidth();
	public int getSpriteHeight();
	public void setX(float x);
	public void setY(float y);
	public void draw(Canvas canvas);
	public void update(long time);
}
