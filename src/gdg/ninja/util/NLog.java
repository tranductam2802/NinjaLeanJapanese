package gdg.ninja.util;

import android.util.Log;

public final class NLog{
	public static final String TAG = "Ninja_language";
	
	public static final void e(String msg){
		if(Config.IS_DEBUG){
			Log.e(TAG, msg);
		}
	}
	
	public static final void e(String tag, String msg){
		if(Config.IS_DEBUG){
			Log.e(tag, msg);
		}
	}
	
	public static final void w(String msg){
		if(Config.IS_DEBUG){
			Log.w(TAG, msg);
		}
	}
	
	public static final void w(String tag, String msg){
		if(Config.IS_DEBUG){
			Log.w(tag, msg);
		}
	}
	
	public static final void d(String msg){
		if(Config.IS_DEBUG){
			Log.d(TAG, msg);
		}
	}
	
	public static final void d(String tag, String msg){
		if(Config.IS_DEBUG){
			Log.d(tag, msg);
		}
	}
	
	public static final void i(String msg){
		if(Config.IS_DEBUG){
			Log.i(TAG, msg);
		}
	}
	
	public static final void i(String tag, String msg){
		if(Config.IS_DEBUG){
			Log.i(tag, msg);
		}
	}
	
	public static final void v(String msg){
		if(Config.IS_DEBUG){
			Log.v(TAG, msg);
		}
	}
	
	public static final void v(String tag, String msg){
		if(Config.IS_DEBUG){
			Log.v(tag, msg);
		}
	}
	
	public static final void wtf(String msg){
		if(Config.IS_DEBUG){
			Log.wtf(TAG, msg);
		}
	}
	
	public static final void wtf(String tag, String msg){
		if(Config.IS_DEBUG){
			Log.wtf(tag, msg);
		}
	}
}