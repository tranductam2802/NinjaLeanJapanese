package gdg.ninja.framework;

import gdg.ninja.navigate.NavigationManager;
import gdg.ninja.ui.GameActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;

/** Base fragment of the framework */
public class BaseFragment extends Fragment{
	protected AlertDialog dialog;
	protected NavigationManager mNaviManager;
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		if(activity instanceof GameActivity)
			mNaviManager = ((GameActivity) activity).getNavigationManager();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		if(dialog != null && dialog.isShowing()){
			dialog.dismiss();
		}
	}
}