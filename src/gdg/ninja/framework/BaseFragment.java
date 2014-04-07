package gdg.ninja.framework;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;

/** Base fragment of the framework */
public class BaseFragment extends Fragment{
	protected AlertDialog dialog;
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		if(dialog != null && dialog.isShowing()){
			dialog.dismiss();
		}
	}
}