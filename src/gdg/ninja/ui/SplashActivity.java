package gdg.ninja.ui;

import gdg.nat.R;
import gdg.ninja.database.DatabaseHandler;
import gdg.ninja.framework.BaseActivity;
import gdg.ninja.gameinfo.CategoriesInfo;
import gdg.ninja.util.App;
import gdg.ninja.util.Config;
import gdg.ninja.util.NLog;

import java.util.List;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/**
 * Loading class. That load preference application data. Checking and validate
 * application data.
 */
public class SplashActivity extends BaseActivity{
	private final String TAG = "AC_SPLASH";
	private boolean isLoadedData = false;
	private boolean isLoadingTimeEnd = false;
	
	private final int MIN_LOADING_TIME = 3 * 1000;
	
	@Override
	protected void onCreate(Bundle arg0){
		super.onCreate(arg0);
		NLog.i(TAG, "App status: On create");
		setContentView(R.layout.ac_splash);
		Handler handler = new Handler();
		Runnable runnable = new Runnable(){
			@Override
			public void run(){
				if(isLoadedData) redirectToMain();
				isLoadingTimeEnd = true;
			}
		};
		handler.postDelayed(runnable, MIN_LOADING_TIME);
		initView();
		initData();
	}
	
	private void initView(){
		// TODO: initial view and view animation
		TextView txtWelcome = (TextView) findViewById(R.id.txt_welcome);
		Typeface tf = Typeface
				.createFromAsset(getAssets(), Config.DEFAULT_FONT);
		txtWelcome.setTypeface(tf);
	}
	
	private void initData(){
		isLoadedData = false;
		isLoadingTimeEnd = false;
		loadingAppData();
		if(isLoadingTimeEnd) redirectToMain();
		isLoadedData = true;
	}
	
	private void loadingAppData(){
		// TODO: Loading data from preference and validate data
		DatabaseHandler mDatabase = new DatabaseHandler(this);
		List<CategoriesInfo> listCategories = mDatabase.getAllCategory();
		
		// Resources resources = getResources();
		
		// String[] listImg = resources.getStringArray(R.array.cate_img);
		//
		// int[] listId = resources.getIntArray(R.array.cate_id);
		// String[] listName = resources.getStringArray(R.array.cate_name);
		// String[] listDesc = resources.getStringArray(R.array.cate_desc);

		// int[] listStt = resources.getIntArray(R.array.cate_stt);
		// int length = listImg.length;

		// for(int i = 0; i < length; i++){
		// CategoriesInfo cateItem = new CategoriesInfo(i, listImg[i],
		// listName[i], listDesc[i], listStt[i]);
		// String currentCateAnswer = "cate_" + i + "_answer";
		// String currentCateImg = "cate_" + i + "_img";
		// String[] listAnswer = resources.getStringArray(R.array.);
		// for(int j = 0; j < length; j++){
		// QuestInfo questItem = new QuestInfo(j, listId[j],
		// listAnswer[j], listStt[j]);
		// cateItem.addListQuest(questItem);
		// }
		// listCategories.add(cateItem);
		// }
		App.setListCate(listCategories);
	}
	
	/* Redirect using to main application screen */
	private void redirectToMain(){
		Intent intent = new Intent(this, StartActivity.class);
		startActivity(intent);
		finish();
	}
}