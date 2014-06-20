package com.elemental.sprite;

import android.graphics.Bitmap;

public class PlayOnceAnimationSprite extends AnimatedSprite {

	boolean finished = false;
	
	public PlayOnceAnimationSprite(Bitmap bitmap, int x, int y, int width,
			int height, int fps, int frameCount) {
		super(bitmap, x, y, width, height, fps, frameCount);
	}

	@Override
	public void update(long gameTime) {
		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
			// increment the frame
			currentFrame++;
			if (currentFrame >= frameNr) {
				currentFrame = frameNr;
				finished = true;
			}
		}
		// define the rectangle to cut out sprite
		this.sourceRect.left = currentFrame * getSpriteWidth();
		this.sourceRect.right = this.sourceRect.left + getSpriteWidth();	
	}

	public boolean isFinished() {
		return finished;
	}
}
