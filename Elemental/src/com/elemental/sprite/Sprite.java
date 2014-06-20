package com.elemental.sprite;

import android.graphics.Canvas;

public interface Sprite {

	public int getSpriteWidth();
	public int getSpriteHeight();
	public void setX(int x);
	public void setY(int y);
	public void draw(Canvas canvas);
	public void update(long time);
}
