package com.elemental.sprite;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.elemental.Drawable;
import com.elemental.GameContext;
import com.elemental.Speed;

public class GameThing implements Drawable {

	public enum CurrentState {
		OK, Dead
	};

	private Sprite sprite; // the actual bitmap
	private float x; // the X coordinate
	private float y; // the Y coordinate
	private boolean touched; // if droid is touched/picked up
	private Speed speed = new Speed();
	CurrentState state = CurrentState.OK;
	Rect bounds;

	public GameThing(float x, float y, Rect bounds) {
		this.x = x;
		this.y = y;
		speed.setXv(10);
		speed.setYv(10);
		this.bounds = bounds;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		getSprite().setX(x);
	}

	public Rect getBounds() {
		return bounds;
	}

	public void setBounds(Rect bounds) {
		this.bounds = bounds;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		getSprite().setY(y);
	}

	public Speed getSpeed() {
		return speed;
	}

	public void setSpeed(Speed speed) {
		this.speed = speed;
	}

	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}

	public CurrentState getState() {
		return state;
	}

	public void setState(CurrentState state) {
		this.state = state;
	}

	public boolean isPlayer() {
		return getSprite() instanceof PlayerSprite;
	}

	public void draw(Canvas canvas) {
		getSprite().draw(canvas);
	}

	public void update(GameContext context) {
		updatePositionBasedOnSpeed(context);
		getSprite().update(System.currentTimeMillis());
	}

	public void updatePositionBasedOnSpeed(GameContext context) {
		long timeFactor = context.getTimeSinceLastUpdate();
		// speed is pixels per second, so we divide by the timeFactor
		setX((getX() + (getSpeed().getXv() / timeFactor) * getSpeed().getxDirection()));
		setY((getY() + (getSpeed().getYv() / timeFactor) * getSpeed().getyDirection()));
	}

	public float getVelocity() {
		return 100;
	}

	public void handleCollision(GameContext context, GameThing t) {
		// override only
	}
	
	public int getScoreValue() {
		return 0;
	}

	public synchronized void changeDirection(float newX, float newY, float oldX,
			float oldY) {
		// we want to move the object in the direction of the stroke at the
		// velocity
		// we know the X and Y directions but not the speed as yet
		float xv = newX - oldX;
		float yv = newY - oldY;
		float velocity = getVelocity();

		getSpeed().setxDirection((xv < 0) ? -1 : 1);
		getSpeed().setyDirection((yv < 0) ? -1 : 1);

		xv = Math.abs(xv);
		yv = Math.abs(yv);

		if (xv == 0) {
			yv = velocity;
		} else if (yv == 0) {
			xv = velocity;
		} else {
			// find one of the angles and then use that to determine the x
			// and y speeds
			double angle = Math.atan(yv / xv);
			xv = (float) (Math.cos(angle) * velocity);
			yv = (float) (Math.sin(angle) * velocity);
		}

		getSpeed().setXv(xv);
		getSpeed().setYv(yv);
	}

	public boolean pointIsWithin(float eventX, float eventY) {
		if (eventX >= (getX() - getSprite().getSpriteWidth() / 2)
				&& (eventX <= (getX() + getSprite().getSpriteWidth() / 2))) {
			if (eventY >= (getY() - getSprite().getSpriteHeight() / 2)
					&& (eventY <= (getY() + getSprite().getSpriteHeight() / 2))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean pointIsWithinBounds(float x, float y) {
		if (x >= bounds.left && x <= bounds.right) {
			if (y >= bounds.top	&& y <= bounds.bottom) {
				return true;
			}
		}
		return false;
	}
		

	protected boolean clipX(int screenWidth, int screenHeight) {
		// check collision with right wall if heading right
		if (getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
				&& getX() + getSprite().getSpriteWidth() / 2 >= screenWidth) {
			return true;
		}
		// check collision with left wall if heading left
		if (getSpeed().getxDirection() == Speed.DIRECTION_LEFT
				&& getX() - getSprite().getSpriteWidth() / 2 <= 0) {
			return true;
		}
		return false;
	}

	protected boolean clipY(int screenWidth, int screenHeight) {
		// check collision with bottom wall if heading down
		if (getSpeed().getyDirection() == Speed.DIRECTION_DOWN
				&& getY() + getSprite().getSpriteHeight() / 2 >= screenHeight) {
			return true;
		}
		// check collision with top wall if heading up
		if (getSpeed().getyDirection() == Speed.DIRECTION_UP
				&& getY() - getSprite().getSpriteHeight() / 2 <= 0) {
			return true;
		}
		return false;
	}

	protected boolean isOffScreen(int screenWidth, int screenHeight) {
		return (getX() > screenWidth || getY() > screenHeight
				|| getX() + getSprite().getSpriteWidth() < 0 || getY()
				+ getSprite().getSpriteHeight() < 0);
	}

	public boolean collidesWith(GameThing t) {
		// actual detection
		return collidesWith(getX(), t.getX(), getY(), t.getY(), getSprite()
				.getSpriteWidth(), t.getSprite().getSpriteWidth());
	}

	// for two rectangles colliding
	protected boolean collidesWith(float x1, float x2, float y1, float y2, float w1,
			float w2, float h1, float h2) {
		return ((x2 >= x1 && x2 < x1 + w1) || (x1 >= x2 && x1 < x2 + w2))
				&& ((y2 >= y1 && y2 < y1 + h1) || (y1 >= y2 && y1 < y2 + h2));
	}

	// is a circle so test like a circle
	protected boolean collidesWith(float x1, float x2, float y1, float y2, float r1,
			int r2) {
		return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) < (r1 / 2 + r2 / 2)
				* (r1 / 2 + r2 / 2);
	}
}
