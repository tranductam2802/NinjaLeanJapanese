package gdg.ninja.navigate;

import gdg.nat.ninjalearnjapanese.R;
import gdg.ninja.ui.QuestFragment;
import gdg.ninja.util.NLog;

import java.util.Stack;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;

public class NavigationManager{
	private final String TAG = "NavigationManager";
	
	private FragmentActivity mActivity;
	private final Stack<Fragment> mBackStack = new MainThreadStack();
	
	private boolean isNavigable = true;
	private int mPlaceholder = R.id.place_holder;
	private OnBackStackChangedListener mOnBackStackChangedListener;
	
	public NavigationManager(FragmentActivity activity){
		mActivity = activity;
	}
	
	public void setChangable(boolean isNavigable){
		this.isNavigable = isNavigable;
	}
	
	public boolean isNavigable(){
		return this.isNavigable;
	}
	
	public void setPlaceHolder(int placeholder){
		this.mPlaceholder = placeholder;
	}
	
	public void addBackStackChangeListener(OnBackStackChangedListener listener){
		if(mOnBackStackChangedListener != null)
			removemBackStackChangedListener();
		FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
		fragmentManager.addOnBackStackChangedListener(listener);
	}
	
	public void removemBackStackChangedListener(){
		FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
		if(mOnBackStackChangedListener != null)
			fragmentManager
					.removeOnBackStackChangedListener(mOnBackStackChangedListener);
		mOnBackStackChangedListener = null;
	}
	
	public void showPage(Fragment fragment, String requestCode){
		if(mActivity == null || !isNavigable) return;
		int size = mBackStack.size();
		NLog.i(TAG,
				"Fragment(" + mBackStack.size() + "): " + fragment.toString());
		FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		if(fragment instanceof QuestFragment && size > 0){
			if(mBackStack.peek() instanceof QuestFragment){
				mBackStack.pop();
				fragmentManager.popBackStack();
			}
		}
		if(fragmentManager.getBackStackEntryCount() == 0){
			transaction.setCustomAnimations(0, R.anim.fragment_exit,
					R.anim.fragment_pop_enter, R.anim.fragment_pop_exit);
		}else{
			transaction.setCustomAnimations(R.anim.fragment_enter,
					R.anim.fragment_exit, R.anim.fragment_pop_enter,
					R.anim.fragment_pop_exit);
		}
		transaction.replace(mPlaceholder, fragment);
		mBackStack.push(fragment);
		transaction.addToBackStack(requestCode);
		transaction.commit();
	}
	
	public boolean goBack(){
		if(mActivity == null || !isNavigable) return false;
		int size = mBackStack.size();
		if(size <= 1){
			NLog.i(TAG, "Fragment finish activity");
			if(size == 1
					&& mBackStack.peek() instanceof INavigationManagerListener){
				INavigationManagerListener fragment = (INavigationManagerListener) mBackStack
						.peek();
				fragment.returnData();
			}
			mActivity.finish();
		}else{
			NLog.i(TAG, "Fragment back to (" + (size - 2) + "): "
					+ mBackStack.peek().toString());
			mBackStack.pop();
			mActivity.getSupportFragmentManager().popBackStack();
		}
		return true;
	}
	
	public Fragment getActiveFragment(){
		if(mBackStack.isEmpty()) return null;
		NLog.i(TAG, "Fragment: " + mBackStack.peek().toString());
		return mBackStack.peek();
	}
	
	public interface INavigationManagerListener{
		public void returnData();
	}
}