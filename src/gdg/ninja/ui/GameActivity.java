package gdg.ninja.ui;

import android.os.Bundle;
import gdg.nat.R;
import gdg.ninja.framework.BaseActivity;
import gdg.ninja.navigate.NavigationBar;
import gdg.ninja.navigate.NavigationManager;

public class GameActivity extends BaseActivity {
	private NavigationBar mNaviBar;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Implement code soon
		super.onCreate(arg0);
		setContentView(R.layout.ac_game);
		initView();
		showListGameFragment();
	}

	public NavigationManager getNavigationManager() {
		return mNaviManager;
	}

	private void initView() {
		mNaviManager = new NavigationManager(this);
		mNaviBar = (NavigationBar) findViewById(R.id.navigation_bar);
		mNaviBar.initNaviBar(mNaviManager);
	}

	private void showListGameFragment() {
		ListGameFragment fragment = new ListGameFragment();
		mNaviManager.showPage(fragment, "");
	}
}