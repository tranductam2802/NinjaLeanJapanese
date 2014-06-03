package gdg.ninja.ui;

import java.util.List;

import gdg.nat.R;
import gdg.ninja.framework.BaseFragment;
import gdg.ninja.gameinfo.QuestInfo;
import gdg.ninja.navigate.NavigationBar.BTN_LEFT_MODE;
import gdg.ninja.navigate.NavigationBar.BTN_RIGHT_MODE;
import gdg.ninja.navigate.NavigationBar.INavigationBarListener;
import gdg.ninja.util.App;
import gdg.ninja.util.QuestGenerator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class QuestFragment extends BaseFragment implements
		INavigationBarListener {
	private static final String KEY_CATEGORY_ID = "category";
	private static final String KEY_QUEST_ID = "quest";

	private ImageView mImgAvatar;
	private ImageView mImgShareFacebook;
	private ImageView mImgShareGoogle;
	private ImageView mImgBomb;
	private ImageView mImgFlag;

	private LinearLayout mLnlAnswerLayout;
	private LinearLayout mLnlQuestOne;
	private LinearLayout mLnlQuestTwo;

	private int mCategoryId;
	private int mQuestId;

	public static QuestFragment getInstance(int questId, int cateId) {
		QuestFragment fragment = new QuestFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(KEY_CATEGORY_ID, cateId);
		bundle.putInt(KEY_QUEST_ID, questId);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			mCategoryId = savedInstanceState.getInt(KEY_CATEGORY_ID);
			mQuestId = savedInstanceState.getInt(KEY_QUEST_ID);
		} else {
			Bundle bundle = getArguments();
			mCategoryId = bundle.getInt(KEY_CATEGORY_ID);
			mQuestId = bundle.getInt(KEY_QUEST_ID);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg_quest, container, false);
		initView(view);
		return view;
	}

	public void initView(View v) {
		mImgAvatar = (ImageView) v.findViewById(R.id.img_avatar);
		mImgShareFacebook = (ImageView) v.findViewById(R.id.btn_share_facebook);
		mImgShareGoogle = (ImageView) v.findViewById(R.id.btn_share_google);
		mImgBomb = (ImageView) v.findViewById(R.id.btn_boom);
		mImgFlag = (ImageView) v.findViewById(R.id.btn_flag);

		mLnlAnswerLayout = (LinearLayout) v.findViewById(R.id.ltv_list);
		mLnlQuestOne = (LinearLayout) v.findViewById(R.id.quest_line_one);
		mLnlQuestTwo = (LinearLayout) v.findViewById(R.id.quest_line_two);

		mImgAvatar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				List<QuestInfo> listQuest = App.getListQuestById(mCategoryId);
				boolean isNext = false;
				for (QuestInfo quest : listQuest) {
					if (isNext) {
						QuestFragment fragment = QuestFragment.getInstance(
								quest.getQuestId(), mCategoryId);
						mNaviManager.showPage(fragment, "");
						break;
					} else {
						if (quest.getQuestId() == mQuestId) {
							isNext = true;
						}
					}
				}
			}
		});
	}

	@Override
	public String getTitle() {
		QuestInfo quest = App.getQuestById(mQuestId, mCategoryId);
		QuestGenerator questGenerator = new QuestGenerator();
		return questGenerator.getAnswer(quest.getAnswer()).toString() + mQuestId;
	}

	@Override
	public BTN_LEFT_MODE getButtonLeftMode() {
		return BTN_LEFT_MODE.BACK;
	}

	@Override
	public void onLeftClicked() {
		mNaviManager.goBack();
	}

	@Override
	public BTN_RIGHT_MODE getButtonRightMode() {
		return BTN_RIGHT_MODE.NONE;
	}

	@Override
	public void onRightClicked() {
		// Do nothing
	}
}