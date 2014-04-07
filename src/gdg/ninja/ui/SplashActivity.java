package gdg.ninja.ui;

import gdg.nat.R;
import gdg.ninja.framework.BaseActivity;
import android.os.Bundle;

/**
 * Loading class. That load preference application data. Checking and validate
 * application data.
 */
public class SplashActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle arg0){
		super.onCreate(arg0);
		setContentView(R.layout.ac_splash);
	}
}