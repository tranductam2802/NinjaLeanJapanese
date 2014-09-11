package gdg.ninja.ui;

import gdg.nat.ninjalearnjapanese.R;
import gdg.ninja.adapter.ListQuestAdapter;
import gdg.ninja.framework.BaseFragment;
import gdg.ninja.gameinfo.QuestInfo;
import gdg.ninja.navigate.NavigationBar.BTN_LEFT_MODE;
import gdg.ninja.navigate.NavigationBar.BTN_RIGHT_MODE;
import gdg.ninja.navigate.NavigationBar.INavigationBarListener;
import gdg.ninja.util.App;
import gdg.ninja.util.SoundUtils;
import gdg.ninja.util.SoundUtils.SOUND_NAME;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ListQuestFragment extends BaseFragment implements
		INavigationBarListener{
	private static final String KEY_CATEGORY_ID = "category";
	private GridView mGrvListQuests;

	private ListQuestAdapter mAdapter;
	private int mCategoryId = -1;
	private String mScreenTitle = "";
	
	private OnGlobalLayoutListener mOnGlobalLayoutListener = new OnGlobalLayoutListener(){
		@Override
		public void onGlobalLayout(){
			Resources resources = getResources();
			int screenWidth = resources.getDisplayMetrics().widthPixels;
			int itemWidth = resources
					.getDimensionPixelSize(R.dimen.list_game_quest_avatar_size);
			int numColumns = (screenWidth) / (itemWidth);
			mGrvListQuests.setNumColumns(numColumns);
			int itemOffset = 2 * resources
					.getDimensionPixelSize(R.dimen.list_game_categories_item_horizontal_padding);
			int itemSize = (screenWidth / numColumns) - itemOffset;
			mAdapter.setItemSize(itemSize);
			if(numColumns > 0){
				int padding = (screenWidth - numColumns * itemSize)
						/ (numColumns - 1);
				mGrvListQuests.setVerticalSpacing(padding);
				mGrvListQuests.setHorizontalSpacing(padding);
			}
		}
	};
	
	public static ListQuestFragment getInstance(int cateId){
		ListQuestFragment fragment = new ListQuestFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(KEY_CATEGORY_ID, cateId);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null){
			mCategoryId = savedInstanceState.getInt(KEY_CATEGORY_ID, -1);
		}else{
			Bundle bundle = getArguments();
			mCategoryId = bundle.getInt(KEY_CATEGORY_ID, -1);
		}
		mScreenTitle = App.getCategoryById(mCategoryId).getCateName();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fg_list_quest, container, false);
		initView(view);
		return view;
	}

	@Override
	public void onResume(){
		super.onResume();
		initData();
	}
	
	private void initView(View view){
		mGrvListQuests = (GridView) view.findViewById(R.id.ltv_list);
		mGrvListQuests.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id){
				QuestInfo item = mAdapter.getItem(position);
				if(position != 0){
					QuestInfo previousItem = mAdapter.getItem(position - 1);
					if (previousItem.getQuestStt() < 3) {
						SoundUtils.getInstance().play(SOUND_NAME.WRONG);
						return;
					}
				}

				SoundUtils.getInstance().play(SOUND_NAME.OTHER_BTN);
				QuestFragment fragment = QuestFragment.getInstance(
						item.getQuestId(), mCategoryId);
				mNaviManager.showPage(fragment, "");
			}
		});
		mGrvListQuests.getViewTreeObserver().addOnGlobalLayoutListener(
				mOnGlobalLayoutListener);
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	public void onDestroyView(){
		ViewTreeObserver viewTree = mGrvListQuests.getViewTreeObserver();
		if(viewTree.isAlive()){
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
				viewTree.removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
			}else{
				viewTree.removeGlobalOnLayoutListener(mOnGlobalLayoutListener);
			}
		}
		super.onDestroyView();
	}
	
	private void initData(){
		List<QuestInfo> listQuest = App.getListQuestById(mCategoryId);
		mAdapter = new ListQuestAdapter(listQuest, getActivity());
		mGrvListQuests.setAdapter(mAdapter);
	}

	@Override
	public String getTitle(){
		return mScreenTitle;
	}

	@Override
	public BTN_LEFT_MODE getButtonLeftMode(){
		return BTN_LEFT_MODE.BACK;
	}

	@Override
	public BTN_RIGHT_MODE getButtonRightMode(){
		return BTN_RIGHT_MODE.SETTING;
	}

	@Override
	public void onLeftClicked(){
		mNaviManager.goBack();
	}

	@Override
	public void onRightClicked(){
		Intent intent = new Intent(getActivity(), HelpActivity.class);
		startActivity(intent);
	}
}