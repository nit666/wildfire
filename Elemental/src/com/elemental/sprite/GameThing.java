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
	private int x; // the X coordinate
	private int y; // the Y coordinate
	private boolean touched; // if droid is touched/picked up
	private Speed speed = new Speed();
	CurrentState state = CurrentState.OK;
	Rect bounds;

	public GameThing(int x, int y, Rect bounds) {
		this.x = x;
		this.y = y;
		speed.setXv(5);
		speed.setYv(5);
		this.bounds = bounds;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		getSprite().setX(x);
	}

	public Rect getBounds() {
		return bounds;
	}

	public void setBounds(Rect bounds) {
		this.bounds = bounds;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
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
		setX((int) (getX() + getSpeed().getXv() * getSpeed().getxDirection()));
		setY((int) (getY() + getSpeed().getYv() * getSpeed().getyDirection()));
	}

	public float getVelocity() {
		return 7;
	}

	public void handleCollision(GameContext context, GameThing t) {
		// override only
	}
	
	public int getScoreValue() {
		return 0;
	}

	public synchronized void changeDirection(int newX, int newY, int oldX,
			int oldY) {
		// we want to move the object in the direction of the stroke at the
		// velocity
		// we know the X and Y directions but not the speed as yet
		double xv = newX - oldX;
		double yv = newY - oldY;
		double velocity = getVelocity();

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
			xv = (Math.cos(angle) * velocity);
			yv = (Math.sin(angle) * velocity);
		}

		getSpeed().setXv((int) xv);
		getSpeed().setYv((int) yv);
	}

	public boolean pointIsWithin(int eventX, int eventY) {
		if (eventX >= (getX() - getSprite().getSpriteWidth() / 2)
				&& (eventX <= (getX() + getSprite().getSpriteWidth() / 2))) {
			if (eventY >= (getY() - getSprite().getSpriteHeight() / 2)
					&& (eventY <= (getY() + getSprite().getSpriteHeight() / 2))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean pointIsWithinBounds(int x, int y) {
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
	protected boolean collidesWith(int x1, int x2, int y1, int y2, int w1,
			int w2, int h1, int h2) {
		return ((x2 >= x1 && x2 < x1 + w1) || (x1 >= x2 && x1 < x2 + w2))
				&& ((y2 >= y1 && y2 < y1 + h1) || (y1 >= y2 && y1 < y2 + h2));
	}

	// is a circle so test like a circle
	protected boolean collidesWith(int x1, int x2, int y1, int y2, int r1,
			int r2) {
		return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) < (r1 / 2 + r2 / 2)
				* (r1 / 2 + r2 / 2);
	}
}
