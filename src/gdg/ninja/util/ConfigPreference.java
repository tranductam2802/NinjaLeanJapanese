package gdg.ninja.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ConfigPreference{
	private final String TAG = "ConfigPreference";
	private final int MODE = Context.MODE_PRIVATE;
	
	private static ConfigPreference mPreference = new ConfigPreference();
	
	private final String KEY_IS_FIRST_TIME = "is_first_time";
	
	public static ConfigPreference getInstance(){
		return mPreference;
	}
	
	private SharedPreferences getSharedPreference(){
		return App.getContext().getSharedPreferences(TAG, MODE);
	}
	
	private Editor getEditor(){
		return getSharedPreference().edit();
	}
	
	public void saveIsFirstTime(){
		getEditor().putBoolean(KEY_IS_FIRST_TIME, false);
	}
	
	public boolean isFirstTime(){
		return getSharedPreference().getBoolean(KEY_IS_FIRST_TIME, true);
	}
}