package com.elemental.sprite;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.elemental.GameContext;
import com.example.elemental.R;

public class PlayerSprite extends SimpleSprite {
	
	public PlayerSprite(GameContext context, float x, float y) {
		super(
				Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
						context.getContext().getResources(), R.drawable.character2), 70, 90, true)
				, x, y);
		
		//setBitmap(Bitmap.createScaledBitmap(background, context.getScreenWidth(), context.getScreenHeight(), true));	

	}
}
