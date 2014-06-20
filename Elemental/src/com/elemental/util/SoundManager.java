package com.elemental.util;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.elemental.R;

public class SoundManager {

	static boolean soundLoadComplete = false;

	public static boolean soundLoaded() {
		return soundLoadComplete;
	}

	public enum Sound {
		GunShot, BombFuse, BombExplosion
	}

	private static SoundPool soundPool;
	private static HashMap<Sound, Integer> soundPoolMap;

	/** Populate the SoundPool */
	public static void initSounds(Context context) {
		soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 100);
		soundPoolMap = new HashMap<Sound, Integer>();

		soundPoolMap.put(Sound.GunShot,
				soundPool.load(context, R.raw.gunshot, 1));
		soundPoolMap.put(Sound.BombFuse,
				soundPool.load(context, R.raw.bomb_fuse, 1));
		soundPoolMap.put(Sound.BombExplosion,
				soundPool.load(context, R.raw.bomb_explosion, 1));

		soundPool
				.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {

					@Override
					public void onLoadComplete(SoundPool soundPool,
							int sampleId, int status) {
						soundLoadComplete = true;
					}
				});
	}

	/** Play a given sound in the soundPool */
	public static int playSound(Context context, Sound soundID, float balance) {
		if (soundPool == null || soundPoolMap == null) {
			initSounds(context);
		}
		if (balance > 1 || balance < 0) {
			balance = 0.5f;
		}

		// play sound with same right and left volume, with a priority of 1,
		// zero repeats (i.e play once), and a playback rate of 1f
		return soundPool.play(soundPoolMap.get(soundID), 1 - balance, balance,
				1, 0, 1f);
	}

	public static void stopSound(int streamId) {
		soundPool.stop(streamId);
	}

}
