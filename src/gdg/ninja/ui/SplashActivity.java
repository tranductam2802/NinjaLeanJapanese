package gdg.ninja.ui;

import gdg.nat.R;
import gdg.ninja.framework.BaseActivity;
import gdg.ninja.util.DebugLog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Loading class. That load preference application data. Checking and validate
 * application data.
 */
public class SplashActivity extends BaseActivity{
	private final String TAG = "AC_SPLASH";
	private boolean isLoadedData = false;
	private boolean isLoadingTimeEnd = false;

	private final int MIN_LOADING_TIME = 3 * 1000;

	@Override
	protected void onCreate(Bundle arg0){
		super.onCreate(arg0);
		DebugLog.i(TAG, "App status: On create");
		setContentView(R.layout.ac_splash);
		initView();
		initData();
		Handler handler = new Handler();
		Runnable runnable = new Runnable(){
			@Override
			public void run(){
				if(isLoadedData) redirectToMain();
				isLoadingTimeEnd = true;
			}
		};
		handler.postDelayed(runnable, MIN_LOADING_TIME);
	}

	private void initView(){
		// TODO: initial view and view animation
	}

	private void initData(){
		isLoadedData = false;
		isLoadingTimeEnd = false;
		loadingAppData();
		if(isLoadingTimeEnd) redirectToMain();
		isLoadedData = true;
	}

	private void loadingAppData(){
		// TODO: Loading data from preference and validate data
	}

	/* Redirect using to main application screen */
	private void redirectToMain(){
		Intent intent = new Intent(this, StartActivity.class);
		startActivity(intent);
		finish();
	}
}