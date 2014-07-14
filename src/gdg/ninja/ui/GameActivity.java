package gdg.ninja.ui;

import gdg.nat.R;
import gdg.ninja.framework.BaseActivity;
import gdg.ninja.navigate.NavigationBar;
import gdg.ninja.navigate.NavigationManager;
import gdg.ninja.util.FacebookUtil;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public class GameActivity extends BaseActivity{
	private NavigationBar mNaviBar;
	
	@Override
	protected void onCreate(Bundle arg0){
		// TODO Implement code soon
		super.onCreate(arg0);
		setContentView(R.layout.ac_game);
		initView();
		showListGameFragment();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		FacebookUtil.onActivityResult(this, requestCode, resultCode, data);
	}
	
	public NavigationManager getNavigationManager(){
		return mNaviManager;
	}
	
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private void initView(){
		mNaviManager = new NavigationManager(this);
		mNaviBar = (NavigationBar) findViewById(R.id.navigation_bar);
		mNaviBar.initNaviBar(mNaviManager);
	}
	
	private void showListGameFragment(){
		ListGameFragment fragment = new ListGameFragment();
		mNaviManager.showPage(fragment, "");
	}
}