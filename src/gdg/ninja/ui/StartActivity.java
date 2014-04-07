package gdg.ninja.ui;

import gdg.nat.R;
import gdg.ninja.framework.BaseActivity;
import android.os.Bundle;

/**
 * Start activity that control and navigation app screen.
 */
public class StartActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle arg0){
		super.onCreate(arg0);
		setContentView(R.layout.ac_start);
	}
}