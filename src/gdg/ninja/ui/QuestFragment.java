package gdg.ninja.ui;

import gdg.nat.R;
import gdg.ninja.framework.BaseFragment;
import gdg.ninja.gameinfo.QuestInfo;
import gdg.ninja.navigate.NavigationBar.BTN_LEFT_MODE;
import gdg.ninja.navigate.NavigationBar.BTN_RIGHT_MODE;
import gdg.ninja.navigate.NavigationBar.INavigationBarListener;
import gdg.ninja.util.App;
import gdg.ninja.util.QuestGenerator;

import java.util.List;

import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuestFragment extends BaseFragment implements
		INavigationBarListener, OnClickListener {
	private static final String KEY_CATEGORY_ID = "category";
	private static final String KEY_QUEST_ID = "quest";

	private ImageView mImgAvatar;
	private ImageView mImgShareFacebook;
	private ImageView mImgShareGoogle;
	private ImageView mImgBomb;
	private ImageView mImgFlag;

	private LinearLayout mLnlAnswerLayout;
	private LinearLayout mLnlQuestLayout;
	private Tag[] mListAnswer;
	private Tag[] mListQuest;

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
		mImgShareFacebook.setOnClickListener(this);
		mImgShareGoogle.setOnClickListener(this);
		mImgBomb.setOnClickListener(this);
		mImgFlag.setOnClickListener(this);

		mLnlAnswerLayout = (LinearLayout) v.findViewById(R.id.ltv_list);
		mLnlQuestLayout = (LinearLayout) v.findViewById(R.id.quest);

		mImgAvatar.setImageResource(R.drawable.dummy_image);
		mImgAvatar.setImageResource(R.drawable.dummy_image);
		QuestInfo questInfo = App.getQuestById(mQuestId, mCategoryId);
		QuestGenerator questGenerator = new QuestGenerator(getActivity());
		List<String> quest = questGenerator.getQuest(questInfo.getAnswer(),
				mCategoryId);
		int size = quest.size();
		mListAnswer = new Tag[size];
		for (int i = 0; i < size; i++) {
			mListAnswer[i] = new Tag();
			mListAnswer[i].string = quest.get(i);
			View view = View.inflate(getActivity(), R.layout.tag_answer, null);
			mListAnswer[i].view = (TextView) view.findViewById(R.id.answer_tag);
			mLnlAnswerLayout.addView(view);
			mListAnswer[i].view.setText(mListAnswer[i].string);
		}
	}

	private void onWinGame() {
		Builder builder = new Builder(getActivity());
		// TODO: Create new game
		dialog = builder.create();
		dialog.show();
	}

	private void onWinDialogNextClicked() {
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
		if (isNext)
			mNaviManager.goBack();
	}

	@Override
	public String getTitle() {
		QuestInfo quest = App.getQuestById(mQuestId, mCategoryId);
		QuestGenerator questGenerator = new QuestGenerator(getActivity());
		return questGenerator.getQuest(quest.getAnswer(), mCategoryId)
				.toString() + mQuestId;
	}

	private void shareFacebook() {
		// TODO:
	}

	private void shareGoogle() {
		// TODO:
	}

	private void bombQuest() {
		// TODO:
	}

	private void flagQuest() {
		// TODO:
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.btn_share_facebook:
				shareFacebook();
				break;
			case R.id.btn_share_google:
				shareGoogle();
				break;
			case R.id.btn_boom:
				bombQuest();
				break;
			case R.id.btn_flag:
				flagQuest();
				break;
		}
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

	public class Tag {
		public String string;
		public TextView view;
	}
}