package gdg.ninja.framework;

import gdg.ninja.navigate.NavigationManager;
import gdg.ninja.ui.GameActivity;
import gdg.ninja.util.ShareUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;

/** Base fragment of the framework */
public class BaseFragment extends Fragment{
	protected AlertDialog dialog;
	protected NavigationManager mNaviManager;
	private Activity mActivity;
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		if(activity instanceof GameActivity)
			mActivity = activity;
			mNaviManager = ((GameActivity) activity).getNavigationManager();
	}
	
	public void takeScreenShot(ShareUtils.SHARE_TYPE shareType) {
		((GameActivity) mActivity).takeScreenShot(shareType);
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		if(dialog != null && dialog.isShowing()){
			dialog.dismiss();
		}
	}
}