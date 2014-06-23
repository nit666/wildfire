package com.elemental;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;

import com.elemental.sprite.GameThing;
import com.elemental.sprite.PlayerCharacter;
import com.elemental.util.RecordedTouch;
import com.elemental.world.World;

public class GameContext {

	Context context;
	int screenWidth;
	int screenHeight;
	boolean gameOver;
	Score score;
	List<GameThing> touchables = new LinkedList<GameThing>();
	PlayerCharacter player;
	RecordedTouch latestTouch;
	World currentWorld; // default world
	long timeSinceLastUpdate;

	public long getTimeSinceLastUpdate() {
		return timeSinceLastUpdate;
	}
	public void setTimeSinceLastUpdate(long timeSinceLastUpdate) {
		this.timeSinceLastUpdate = timeSinceLastUpdate;
	}

	List<GameThing> spawnList = new LinkedList<GameThing>();
	
	public World getCurrentWorld() {
		return currentWorld;
	}
	public void setCurrentWorld(World currentWorld) {
		this.currentWorld = currentWorld;
	}
	public RecordedTouch getLatestTouch() {
		return latestTouch;
	}
	public void setLatestTouch(RecordedTouch latestTouch) {
		this.latestTouch = latestTouch;
	}
	public List<GameThing> getTouchables() {
		return touchables;
	}
	public void setTouchables(List<GameThing> touchables) {
		this.touchables = touchables;
	}
	public boolean isGameOver() {
		return gameOver;
	}
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	public Score getScore() {
		return score;
	}
	public void setScore(Score score) {
		this.score = score;
	}
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public int getScreenWidth() {
		return screenWidth;
	}
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	public PlayerCharacter getPlayer() {
		return player;
	}
	public void setPlayer(PlayerCharacter player) {
		this.player = player;
	}
	
	public void spawn(GameThing spawn) {
		spawnList.add(spawn);
	}
	
	public void updateSpawnList() {
		Iterator<GameThing> it = spawnList.iterator();
		while (it.hasNext()) {
			getTouchables().add(it.next());
			it.remove();
		}
	}
}
