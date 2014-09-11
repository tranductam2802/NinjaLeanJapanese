package gdg.ninja.ui;

import gdg.nat.ninjalearnjapanese.R;
import gdg.ninja.gameinfo.CategoriesInfo;
import gdg.ninja.util.App;
import gdg.ninja.util.ShareUtils;
import gdg.ninja.util.ShareUtils.SHARE_TYPE;
import gdg.ninja.util.SoundUtils;
import gdg.ninja.util.SoundUtils.SOUND_NAME;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HighScoreActivity extends Activity implements OnClickListener {

	TextView mTxtScore;
	TextView mTxtLabel;
	TextView mTxtMessage;
	ImageView mImgTrophy;
	ImageView mBtnShareFb;
	ImageView mBtnShareGg;
	RelativeLayout mScreenView;
	Activity mActivity;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.ac_high_score);

		initViews();
		initData();
	}

	private void initData() {
		mActivity = this;

		Resources resources = App.getContext().getResources();

		String[] highScoreLabel = resources
				.getStringArray(R.array.txt_high_score_label);
		String[] highScoreMessage = resources
				.getStringArray(R.array.txt_high_score_message);

		List<CategoriesInfo> cateList = App.getListCategories();
		int totalScore = 0;
		for (CategoriesInfo categoriesInfo : cateList) {
			if (categoriesInfo.getStt() != 0
					|| categoriesInfo.getListQuest().get(0).getQuestStt() != 0) {
				totalScore += categoriesInfo.getTotalStt();
			}
		}

		totalScore /= 6;

		int txtPosition;
		int tropyImgId;

		if (totalScore <= 30) {
			txtPosition = 0;
			tropyImgId = R.drawable.trophy1;
		} else if (totalScore <= 60) {
			txtPosition = 1;
			tropyImgId = R.drawable.trophy2;
		} else if (totalScore <= 120) {
			txtPosition = 2;
			tropyImgId = R.drawable.trophy3;
		} else if (totalScore <= 240) {
			txtPosition = 3;
			tropyImgId = R.drawable.trophy4;
		} else if (totalScore <= 480) {
			txtPosition = 4;
			tropyImgId = R.drawable.trophy5;
		} else if (totalScore <= 960) {
			txtPosition = 5;
			tropyImgId = R.drawable.trophy6;
		} else if (totalScore <= 1500) {
			txtPosition = 6;
			tropyImgId = R.drawable.trophy7;
		} else {
			txtPosition = 7;
			tropyImgId = R.drawable.trophy8;
		}

		mTxtLabel.setText(highScoreLabel[txtPosition]);
		mTxtMessage.setText(highScoreMessage[txtPosition]);
		mTxtScore.setText(String.valueOf(totalScore));
		mImgTrophy.setImageResource(tropyImgId);
		mBtnShareFb.setOnClickListener(this);
		mBtnShareGg.setOnClickListener(this);
		mScreenView.setOnClickListener(this);
	}

	private void initViews() {
		mTxtScore = (TextView) findViewById(R.id.txt_welcome);
		mTxtLabel = (TextView) findViewById(R.id.txt_categories_description);
		mTxtMessage = (TextView) findViewById(R.id.txt_title_navi);
		mImgTrophy = (ImageView) findViewById(R.id.img_avatar);
		mBtnShareFb = (ImageView) findViewById(R.id.btn_share_facebook);
		mBtnShareGg = (ImageView) findViewById(R.id.btn_share_google);
		mScreenView = (RelativeLayout) findViewById(R.id.btn_choose_from_galary);
	}

	class TakeScreenShot extends AsyncTask<Void, Void, Void> {
		AlertDialog mDialog;
		ShareUtils.SHARE_TYPE shareType;
		Bitmap b;

		public TakeScreenShot(ShareUtils.SHARE_TYPE shareType) {
			super();
			this.shareType = shareType;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			mDialog = new ProgressDialog(mActivity);
			mDialog.setMessage(App.getContext().getResources()
					.getString(R.string.please_wait));
			mDialog.setCancelable(false);
			mDialog.show();
			b = Bitmap.createBitmap(mScreenView.getWidth(),
					mScreenView.getHeight(), Config.ARGB_8888);

			Canvas canvas = new Canvas(b);
			mScreenView.draw(canvas);
		}

		@Override
		protected Void doInBackground(Void... params) {

			File file = new File(ShareUtils.DEFAULT_SCREENSHOT_PATH);
			try {
				file.createNewFile();
				FileOutputStream ostream = new FileOutputStream(file);

				b.compress(CompressFormat.JPEG, 80, ostream);
				ostream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (shareType == SHARE_TYPE.FACEBOOK)
				ShareUtils.postPhotoToFacebook(mActivity, new File(
						ShareUtils.DEFAULT_SCREENSHOT_PATH));
			else
				ShareUtils.postPhotoToGoogle(mActivity, new File(
						ShareUtils.DEFAULT_SCREENSHOT_PATH));
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (mDialog.isShowing())
				mDialog.dismiss();
		}

	}

	@Override
	public void onClick(View v) {
		SoundUtils.getInstance().play(SOUND_NAME.OTHER_BTN);
		switch (v.getId()) {
			case R.id.btn_share_facebook:
				new TakeScreenShot(SHARE_TYPE.FACEBOOK).execute();
				break;

			case R.id.btn_share_google:
				new TakeScreenShot(SHARE_TYPE.GOOGLE).execute();
				break;
			case R.id.btn_choose_from_galary:
				finish();
				break;
		}

	}
}