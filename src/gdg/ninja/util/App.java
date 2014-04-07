package gdg.ninja.util;

import android.app.Application;
import android.content.Context;

/**
 * Application class stand for the current application. This class using to do
 * basic application function or initial the application controller. Class also
 * get application context at normal java code.
 */
public class App extends Application{
	// Application context be private to prevent change this static data.
	private static Context CurrentApp;
	
	/** Get current application context. */
	public static final Context getContext(){
		return CurrentApp;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		if(CurrentApp == null){
			CurrentApp = this;
		}
	}
}