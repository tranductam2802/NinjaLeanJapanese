package gdg.ninja.ui;

import gdg.nat.R;
import gdg.ninja.framework.BaseActivity;
import gdg.ninja.gameinfo.CategoriesInfo;
import gdg.ninja.util.App;

import java.util.List;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class HighScoreActivity extends BaseActivity {

	TextView mTxtScore;
	TextView mTxtLabel;
	TextView mTxtMessage;
	ImageView mImgTrophy;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.ac_high_score);

		initViews();
		initData();
	}

	private void initData() {
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
	}

	private void initViews() {
		mTxtScore = (TextView) findViewById(R.id.txt_welcome);
		mTxtLabel = (TextView) findViewById(R.id.txt_categories_description);
		mTxtMessage = (TextView) findViewById(R.id.txt_title_navi);
		mImgTrophy = (ImageView) findViewById(R.id.img_avatar);
	}
}