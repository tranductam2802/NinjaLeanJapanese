package gdg.ninja.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ConfigPreference {
	private final String TAG = "ConfigPreference";
	private final int MODE = Context.MODE_PRIVATE;

	public static final String NUMBER_OF_BOMB = "numberOfBomb";
	public static final String NUMBER_OF_COMPASS = "numberOfCompass";
	public static final String IS_SOUND_ON = "isSoundOn";

	private static ConfigPreference mPreference = new ConfigPreference();

	private final String KEY_IS_FIRST_TIME = "is_first_time";

	public static ConfigPreference getInstance() {
		return mPreference;
	}

	private SharedPreferences getSharedPreference() {
		return App.getContext().getSharedPreferences(TAG, MODE);
	}

	private Editor getEditor() {
		return getSharedPreference().edit();

	}

	public void saveIsFirstTime() {
		getEditor().putBoolean(KEY_IS_FIRST_TIME, false).commit();
	}

	public boolean isFirstTime() {
		return getSharedPreference().getBoolean(KEY_IS_FIRST_TIME, true);
	}

	public int getNumberOfBomb() {
		return getSharedPreference().getInt(NUMBER_OF_BOMB, 1);
	}

	public void setNumberOfBomb(int numberOfBomb) {
		getEditor().putInt(NUMBER_OF_BOMB, numberOfBomb).commit();
	}

	public int getNumberOfCompass() {
		return getSharedPreference().getInt(NUMBER_OF_COMPASS, 1);
	}

	public void SetNumberOfCompass(int numberOfCompass) {
		getEditor().putInt(NUMBER_OF_COMPASS, numberOfCompass).commit();
	}

	public void turnSoundOn() {
		getEditor().putBoolean(IS_SOUND_ON, true).commit();
	}

	public void turnSoundOff() {
		getEditor().putBoolean(IS_SOUND_ON, false).commit();
	}

	public boolean isSoundOn() {
		return getSharedPreference().getBoolean(IS_SOUND_ON, true);
	}
}