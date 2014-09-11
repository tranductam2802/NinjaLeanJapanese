package gdg.ninja.util;

import gdg.nat.ninjalearnjapanese.R;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.SparseIntArray;

public class SoundUtils {
	private static SoundPool mSoundPool;
	private static AudioManager mAudioManager;
	private static SparseIntArray mSoundPoolMap;
	private static ConfigPreference mConfigPreference;

	public static enum SOUND_NAME {
		START_BTN, OTHER_BTN, COMPASS, TYPING, WRONG, RIGHT, BOOM
	}

	private static SoundUtils instance;

	private SoundUtils() {
		mConfigPreference = new ConfigPreference();
		mSoundPool = new SoundPool(SOUND_NAME.values().length,
				AudioManager.STREAM_MUSIC, 0);
		mAudioManager = (AudioManager) App.getContext().getSystemService(
				Context.AUDIO_SERVICE);
		mSoundPoolMap = new SparseIntArray();

		mSoundPoolMap.put(SOUND_NAME.START_BTN.ordinal(),
				mSoundPool.load(App.getContext(), R.raw.start, 1));
		mSoundPoolMap.put(SOUND_NAME.OTHER_BTN.ordinal(),
				mSoundPool.load(App.getContext(), R.raw.push, 1));
		mSoundPoolMap.put(SOUND_NAME.COMPASS.ordinal(),
				mSoundPool.load(App.getContext(), R.raw.laban, 1));
		mSoundPoolMap.put(SOUND_NAME.TYPING.ordinal(),
				mSoundPool.load(App.getContext(), R.raw.type, 1));
		mSoundPoolMap.put(SOUND_NAME.WRONG.ordinal(),
				mSoundPool.load(App.getContext(), R.raw.wrong, 1));
		mSoundPoolMap.put(SOUND_NAME.RIGHT.ordinal(),
				mSoundPool.load(App.getContext(), R.raw.right, 1));
		mSoundPoolMap.put(SOUND_NAME.BOOM.ordinal(),
				mSoundPool.load(App.getContext(), R.raw.boom, 1));
	}

	public static synchronized SoundUtils getInstance() {
		if (instance == null)
			instance = new SoundUtils();
		return instance;
	}

	public void play(SOUND_NAME soundName) {
		if (mConfigPreference.isSoundOn()) {
			float streamVolume = mAudioManager
					.getStreamVolume(AudioManager.STREAM_MUSIC);
			mSoundPool.play(mSoundPoolMap.get(soundName.ordinal()),
					streamVolume, streamVolume, 1, 0, 1f);
		}
	}
}
