package gdg.ninja.framework;

import gdg.ninja.navigate.NavigationManager;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;

/** Base activity of the framework */
@SuppressLint("Registered")
public class BaseActivity extends FragmentActivity{
	protected AlertDialog dialog;
	
	protected NavigationManager mNaviManager;
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		if(dialog != null && dialog.isShowing()){
			dialog.dismiss();
		}
	}
	
	@Override
	public void onBackPressed(){
		if(mNaviManager == null){
			super.onBackPressed();
		}else{
			mNaviManager.goBack();
		}
	}
}