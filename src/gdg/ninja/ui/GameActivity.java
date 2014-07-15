package gdg.ninja.ui;

import gdg.nat.R;
import gdg.ninja.framework.BaseActivity;
import gdg.ninja.navigate.NavigationBar;
import gdg.ninja.navigate.NavigationManager;
import gdg.ninja.util.App;
import gdg.ninja.util.FacebookUtil;
import gdg.ninja.util.ShareUtils;
import gdg.ninja.util.ShareUtils.SHARE_TYPE;

import java.io.File;
import java.io.FileOutputStream;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class GameActivity extends BaseActivity {
	private NavigationBar mNaviBar;
	private View screenView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.ac_game);
		initView();
		showListGameFragment();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		FacebookUtil.onActivityResult(this, requestCode, resultCode, data);
	}

	public NavigationManager getNavigationManager() {
		return mNaviManager;
	}

	public void takeScreenShot(ShareUtils.SHARE_TYPE shareType) {
		new TakeScreenShot(shareType).execute();
	}

	public class TakeScreenShot extends AsyncTask<Void, Void, Void> {

		AlertDialog replaceDialog;

		ShareUtils.SHARE_TYPE shareType;

		public TakeScreenShot(ShareUtils.SHARE_TYPE shareType) {
			super();
			this.shareType = shareType;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			replaceDialog = dialog;
			dialog = new ProgressDialog(GameActivity.this);
			dialog.setMessage(App.getContext().getResources()
					.getString(R.string.please_wait));
			dialog.setCancelable(false);
			dialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			Bitmap b = Bitmap.createBitmap(screenView.getWidth(),
					screenView.getHeight(), Config.ARGB_8888);

			Canvas canvas = new Canvas(b);
			screenView.draw(canvas);

			File file = new File(ShareUtils.DEFAULT_SCREENSHOT_PATH);
			try {
				file.createNewFile();
				FileOutputStream ostream = new FileOutputStream(file);

				b.compress(CompressFormat.PNG, 80, ostream);
				ostream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (shareType == SHARE_TYPE.FACEBOOK)
				ShareUtils.postPhotoToFacebook(GameActivity.this, new File(
						ShareUtils.DEFAULT_SCREENSHOT_PATH));
			else
				ShareUtils.postPhotoToGoogle(GameActivity.this, new File(
						ShareUtils.DEFAULT_SCREENSHOT_PATH));
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (dialog.isShowing())
				dialog.dismiss();
			dialog = replaceDialog;
		}

	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private void initView() {
		mNaviManager = new NavigationManager(this);
		mNaviBar = (NavigationBar) findViewById(R.id.navigation_bar);
		mNaviBar.initNaviBar(mNaviManager);
		screenView = findViewById(R.id.btn_choose_from_galary);
	}

	private void showListGameFragment() {
		ListGameFragment fragment = new ListGameFragment();
		mNaviManager.showPage(fragment, "");
	}
}