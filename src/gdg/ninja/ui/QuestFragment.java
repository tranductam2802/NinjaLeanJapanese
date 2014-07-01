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
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class QuestFragment extends BaseFragment implements
		INavigationBarListener, OnClickListener {
	private static final String KEY_CATEGORY_ID = "category";
	private static final String KEY_QUEST_ID = "quest";
	private final int DELAY_TIME = 1 * 1000;

	private ImageView mImgAvatar;
	private ImageView mImgShareFacebook;
	private ImageView mImgShareGoogle;
	private ImageView mImgBomb;
	private ImageView mImgFlag;

	private LinearLayout mLnlAnswerLayoutOne;
	private LinearLayout mLnlAnswerLayoutTwo;
	private LinearLayout mLnlQuestLayoutOne;
	private LinearLayout mLnlQuestLayoutTwo;
	private LinearLayout mLnlQuestLayoutThree;
	private Tag[] mListAnswer;
	private Tag[] mListQuest;

	private int mCategoryId;
	private int mQuestId;
	private int sizeLineAnswerOne = 0;
	private int sizeLineAnswerTwo = 0;
	private int sizeLineQuestOne = 0;
	private int sizeLineQuestTwo = 0;
	private int sizeLineQuestThree = 0;
	private boolean isAnimate = false;

	private int numAnswered = 0;

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
		// Declaring view
		mImgAvatar = (ImageView) v.findViewById(R.id.img_avatar);
		mImgShareFacebook = (ImageView) v.findViewById(R.id.btn_share_facebook);
		mImgShareGoogle = (ImageView) v.findViewById(R.id.btn_share_google);
		mImgBomb = (ImageView) v.findViewById(R.id.btn_boom);
		mImgFlag = (ImageView) v.findViewById(R.id.btn_flag);
		mImgShareFacebook.setOnClickListener(this);
		mImgShareGoogle.setOnClickListener(this);
		mImgBomb.setOnClickListener(this);
		mImgFlag.setOnClickListener(this);

		// Declaring group view
		mLnlAnswerLayoutOne = (LinearLayout) v.findViewById(R.id.ltv_list_one);
		mLnlAnswerLayoutTwo = (LinearLayout) v.findViewById(R.id.ltv_list_two);
		mLnlQuestLayoutOne = (LinearLayout) v.findViewById(R.id.quest_one);
		mLnlQuestLayoutTwo = (LinearLayout) v.findViewById(R.id.quest_two);
		mLnlQuestLayoutThree = (LinearLayout) v.findViewById(R.id.quest_three);

		// Get quest info
		QuestInfo questInfo = App.getQuestById(mQuestId, mCategoryId);

		// Set quest image
		Picasso.with(getActivity())
				.load(questInfo.getImgPath().replace("assets",
						"file:///android_asset"))
				.placeholder(R.drawable.dummy_image).into(mImgAvatar);

		QuestGenerator questGenerator = new QuestGenerator(getActivity());
		// Answer generating
		List<String> answer = questGenerator.getAnswer(questInfo.getAnswer());
		int size = answer.size();
		mListAnswer = new Tag[size];
		final int MAX_TAG_PER_ROWS = getResources().getInteger(
				R.integer.max_tag_per_row);
		if (size <= MAX_TAG_PER_ROWS) {
			sizeLineAnswerOne = size;
			sizeLineAnswerTwo = 0;
			for (int i = 0; i < size; i++) {
				setAnswerTag(i, answer.get(i), 1);
			}
			mLnlAnswerLayoutTwo.setVisibility(View.GONE);
		} else if (size <= 2 * MAX_TAG_PER_ROWS) {
			sizeLineAnswerOne = size / 2;
			sizeLineAnswerTwo = size - sizeLineAnswerOne;
			for (int i = 0; i < sizeLineAnswerOne; i++) {
				setAnswerTag(i, answer.get(i), 1);
			}
			for (int i = 0; i < sizeLineAnswerTwo; i++) {
				int index = sizeLineAnswerOne + i;
				setAnswerTag(index, answer.get(index), 2);
			}
		} else {
			mNaviManager.goBack();
		}
		numAnswered = 0;

		// Quest generating
		List<String> quest = questGenerator.getQuest(questInfo.getAnswer(),
				mCategoryId);
		size = quest.size();
		mListQuest = new Tag[size];
		if (size > 3 * MAX_TAG_PER_ROWS)
			mNaviManager.goBack();
		if (size <= MAX_TAG_PER_ROWS) {
			sizeLineQuestOne = size;
			sizeLineQuestTwo = 0;
			sizeLineQuestThree = 0;
			for (int i = 0; i < size; i++) {
				setQuestTag(i, quest.get(i), 1);
			}
			mLnlQuestLayoutTwo.setVisibility(View.GONE);
			mLnlQuestLayoutThree.setVisibility(View.GONE);
		} else if (size <= 2 * MAX_TAG_PER_ROWS) {
			sizeLineQuestOne = size / 2;
			sizeLineQuestTwo = size - sizeLineQuestOne;
			sizeLineQuestThree = 0;
			for (int i = 0; i < sizeLineQuestOne; i++) {
				setQuestTag(i, quest.get(i), 1);
			}
			for (int i = 0; i < sizeLineQuestTwo; i++) {
				int index = sizeLineQuestOne + i;
				setQuestTag(index, quest.get(index), 2);
			}
			mLnlQuestLayoutThree.setVisibility(View.GONE);
		} else if (size <= 3 * MAX_TAG_PER_ROWS) {
			sizeLineQuestOne = size / 3;
			sizeLineQuestTwo = (size - sizeLineQuestOne) / 2;
			sizeLineQuestThree = size - sizeLineQuestOne - sizeLineQuestTwo;
			for (int i = 0; i < sizeLineQuestOne; i++) {
				setQuestTag(i, quest.get(i), 1);
			}
			for (int i = 0; i < sizeLineQuestTwo; i++) {
				int index = sizeLineQuestOne + i;
				setQuestTag(index, quest.get(index), 2);
			}
			for (int i = 0; i < sizeLineQuestThree; i++) {
				int index = sizeLineQuestOne + sizeLineQuestTwo + i;
				setQuestTag(index, quest.get(index), 3);
			}
		} else {
			mNaviManager.goBack();
		}
	}

	private void setAnswerTag(int index, String answer, int viewLine) {
		mListAnswer[index] = new Tag();
		mListAnswer[index].string = answer;
		View view = View.inflate(getActivity(), R.layout.tag_answer, null);
		mListAnswer[index].view = (TextView) view.findViewById(R.id.answer_tag);
		if (viewLine == 1) {
			mLnlAnswerLayoutOne.addView(view);
		} else {
			mLnlAnswerLayoutTwo.addView(view);
		}
		mListAnswer[index].view.setText(mListAnswer[index].string);
		mListAnswer[index].view.setTag(index);
		mListAnswer[index].view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isAnimate)
					return;
				if (v.getTag() instanceof Integer) {
					int answerSize = mListAnswer.length;
					if (numAnswered > answerSize)
						return;
					int index = 0;
					try {
						index = Integer.parseInt(v.getTag().toString());
					} catch (NumberFormatException ex) {
						return;
					}
					if (mListAnswer[index].preView == null)
						return;
					mListAnswer[index].view.setText("");
					mListAnswer[index].view.setVisibility(View.INVISIBLE);
					mListAnswer[index].preView.setVisibility(View.VISIBLE);
					mListAnswer[index].preView = null;
					numAnswered--;
				}

			}
		});
	}

	private void setQuestTag(int index, String quest, int viewLine) {
		mListQuest[index] = new Tag();
		mListQuest[index].string = quest;
		View view = View.inflate(getActivity(), R.layout.tag_quest, null);
		mListQuest[index].view = (TextView) view.findViewById(R.id.quest_tag);
		if (viewLine == 1) {
			mLnlQuestLayoutOne.addView(view);
		} else if (viewLine == 2) {
			mLnlQuestLayoutTwo.addView(view);
		} else {
			mLnlQuestLayoutThree.addView(view);
		}
		mListQuest[index].view.setText(mListQuest[index].string);
		mListQuest[index].view.setTag(index);
		mListQuest[index].view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isAnimate)
					return;
				int answerSize = mListAnswer.length;
				numAnswered++;
				if (numAnswered > answerSize)
					return;
				if (numAnswered == answerSize) {
					// TODO: Check answer
					String questText = ((TextView) v).getText().toString();
					mListAnswer[answerSize - 1].view.setText(questText);
					mListAnswer[answerSize - 1].view
							.setVisibility(View.VISIBLE);
					mListAnswer[answerSize - 1].preView = v;
					v.setVisibility(View.INVISIBLE);
					for (Tag tag : mListAnswer) {
						if (!tag.string.equals(tag.view.getText().toString())) {
							onWrongAnswer();
							return;
						}
					}
					onWinGame();
				} else {
					// TODO: add new quest.
					if (v instanceof TextView) {
						for (Tag tag : mListAnswer) {
							if (tag.preView == null) {
								String questText = ((TextView) v).getText()
										.toString();
								tag.view.setText(questText);
								tag.view.setVisibility(View.VISIBLE);
								tag.preView = v;
								v.setVisibility(View.INVISIBLE);
								break;
							}
						}
					}
				}
			}
		});
	}

	/* When the last answer check wrong. Notice for user and remove this view */
	private void onWrongAnswer() {
		isAnimate = true;
		Handler handler = new Handler();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				int lastIndex = mListAnswer.length - 1;
				mListAnswer[lastIndex].view.setText("");
				mListAnswer[lastIndex].view.setVisibility(View.INVISIBLE);
				mListAnswer[lastIndex].preView.setVisibility(View.VISIBLE);
				mListAnswer[lastIndex].preView = null;
				numAnswered--;
				isAnimate = false;
			}
		};
		handler.postDelayed(runnable, DELAY_TIME);
	}

	private void onWinGame() {
		Builder builder = new Builder(getActivity());
		// TODO: Create new game
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				onWinDialogNextClicked();
			}
		});
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
		return App.getCategoryById(mCategoryId).getCateName();
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
		public View preView;
	}
}