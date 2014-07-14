package gdg.ninja.ui;

import gdg.nat.R;
import gdg.ninja.framework.BaseActivity;
import gdg.ninja.gameinfo.CategoriesInfo;
import gdg.ninja.util.App;

import java.util.List;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class HighScoreActivity extends BaseActivity{

	TextView mTxtScore;
	TextView mTxtLabel;
	ImageView mImgTrophy;

	@Override
	protected void onCreate(Bundle arg0){
		super.onCreate(arg0);
		setContentView(R.layout.ac_high_score);

		mTxtScore = (TextView) findViewById(R.id.txt_welcome);
		mTxtLabel = (TextView) findViewById(R.id.txt_title_navi);
		mImgTrophy = (ImageView) findViewById(R.id.btn_start);

		List<CategoriesInfo> cateList = App.getListCategories();
		int totalScore = 0;
		for (CategoriesInfo categoriesInfo : cateList) {
			if (categoriesInfo.getStt() != 0
					|| categoriesInfo.getListQuest().get(0).getQuestStt() != 0) {
				totalScore += categoriesInfo.getTotalStt();
			}
		}

		mTxtScore.setText(String.valueOf(totalScore));

	}
}