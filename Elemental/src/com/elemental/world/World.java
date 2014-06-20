package com.elemental.world;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.elemental.GameContext;
import com.elemental.sprite.Alien1;
import com.elemental.sprite.GameThing;
import com.elemental.util.TimeTester;
import com.example.elemental.R;

public class World {

	int defaultYPosition = 30; // pixels from the bottom to draw the toon
	Bitmap background;
	long spawntime = 2000; // ms
	
	final int maxAliensOnTheScreen = 3;
	final int aliensNeeded = 10;
	
	int aliensKilled = 0;
	
	List<GameThing> spawnedAliens = new LinkedList<GameThing>();
	
	public World(GameContext context) {
		background = BitmapFactory
		.decodeResource(
				context.getContext().getResources(),
				R.drawable.background2);
		background = Bitmap.createScaledBitmap(background, context.getScreenWidth(), context.getScreenHeight(), true);

		defaultYPosition = context.getScreenHeight() - 80;
	}
	
	public int getDefaultYPosition() {
		return defaultYPosition;
	}

	public void setDefaultYPosition(int defaultYPosition) {
		this.defaultYPosition = defaultYPosition;
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(background, 0, 0, null);
	}
	
	public void update(GameContext context) {
		
		// remove any dead aliens
		Iterator<GameThing> it = spawnedAliens.iterator();
		while (it.hasNext()) {
			if (it.next().getState() == GameThing.CurrentState.Dead) {
				aliensKilled++;
				it.remove();
			}
		}
		
		if (TimeTester.test("WorldSpawn", spawntime)) {
			// spawn a new dude if it is time
			if (spawnedAliens.size() < maxAliensOnTheScreen) {			
				int randomY = (int)(Math.random() * 10000) % context.getScreenWidth() / 2;
				Alien1 alien = new Alien1(context, 0, randomY, new Rect(0, 0, context.getScreenWidth(), context.getScreenHeight() / 2));
				context.spawn(alien);
				spawnedAliens.add(alien);
			}
			
			// check for the next spawn time
			long nextSpawn = spawntime + (long) (Math.random() * 10000 % spawntime);
			TimeTester.reset("WorldSpawn", nextSpawn);
		}
		
		if (aliensKilled >= aliensNeeded) {
			context.setGameOver(true);
		}
	}
}
