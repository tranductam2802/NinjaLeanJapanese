package gdg.ninja.framework;

import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;

/** Base activity of the framework */
public class BaseActivity extends FragmentActivity{
	protected AlertDialog dialog;
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		if(dialog != null && dialog.isShowing()){
			dialog.dismiss();
		}
	}
}