package com.elemental.sprite;

import android.graphics.Point;

import com.elemental.GameContext;

public class PlayerBulletThing extends BulletThing {

	public PlayerBulletThing(GameContext context, int x, int y, Point target) {
		super(context, x, y, target);
	}

	@Override
	public void handleCollision(GameContext context, GameThing t) {
		t.setState(CurrentState.Dead);
		this.setState(CurrentState.Dead);
	}
}
