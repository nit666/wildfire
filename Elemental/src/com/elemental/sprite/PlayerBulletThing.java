package com.elemental.sprite;

import com.elemental.GameContext;

public class PlayerBulletThing extends BulletThing {

	public PlayerBulletThing(GameContext context, float x, float y, float targetX, float targetY) {
		super(context, x, y, targetX, targetY);
	}

	@Override
	public void handleCollision(GameContext context, GameThing t) {
		t.setState(CurrentState.Dead);
		this.setState(CurrentState.Dead);
	}
}
