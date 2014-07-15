package gdg.ninja.ui;

import gdg.nat.R;
import gdg.ninja.framework.BaseActivity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

enum SCREEN_STATE {
	START_GAME, CUSTOM_GAME
}

/**
 * Start activity that control and navigation application screen.
 */
public class StartActivity extends BaseActivity implements OnClickListener {
	private boolean isExitable = false;

	private SCREEN_STATE mScreenState;

	private TextView mTxtStartButton;
	private TextView mTxtCustomButton;
	private TextView mTxtHishScoreButton;
	private ImageView mTxtOptionButton;
	private ImageView mTxtHelpButton;
	private ImageView mTxtExitButton;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.ac_start);
		mScreenState = SCREEN_STATE.START_GAME;
		initView();
	}

	private void initView() {
		mTxtStartButton = (TextView) findViewById(R.id.btn_start);
		mTxtCustomButton = (TextView) findViewById(R.id.btn_custom);
		mTxtHishScoreButton = (TextView) findViewById(R.id.btn_high_score);
		mTxtOptionButton = (ImageView) findViewById(R.id.btn_option);
		mTxtHelpButton = (ImageView) findViewById(R.id.btn_left_navi);
		mTxtExitButton = (ImageView) findViewById(R.id.btn_back);

		// Set on click listener
		mTxtStartButton.setOnClickListener(this);
		mTxtCustomButton.setOnClickListener(this);
		mTxtHishScoreButton.setOnClickListener(this);
		mTxtOptionButton.setOnClickListener(this);
		mTxtHelpButton.setOnClickListener(this);
		mTxtExitButton.setOnClickListener(this);

		changeScreenState(mScreenState);
	}

	/* Load data and show Start game screen */
	private void initViewStartGame() {
		mTxtStartButton.setText(R.string.menu_start);
		mTxtCustomButton.setText(R.string.menu_custom_game);
		mTxtHishScoreButton.setText(R.string.menu_hight_score);
		mTxtHishScoreButton.setVisibility(View.VISIBLE);
		// mTxtOptionButton.setText(R.string.menu_option);
		mTxtOptionButton.setVisibility(View.VISIBLE);
		// mTxtExitButton.setText(R.string.menu_exit);
	}

	/* Load data and show Custom game screen */
	private void initViewCustomGame() {
		mTxtStartButton.setText(R.string.menu_start_custom);
		mTxtCustomButton.setText(R.string.menu_make_game);
		mTxtHishScoreButton.setVisibility(View.GONE);
		mTxtOptionButton.setVisibility(View.GONE);
		// mTxtExitButton.setText(R.string.menu_back);
	}

	// Handler an runnable to delay exit time.
	private Handler mHandler = new Handler();
	private Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			isExitable = false;
		}
	};

	/*
	 * Handle user back button pressed event. The first, check it self for
	 * showing dialog. If have any dialog is showing, we have to do nothing.
	 * Next, it request user press back two times to make sure that's not be an
	 * user mistake. The last, we have a goodbye dialog.
	 */
	@Override
	public void onBackPressed() {
		if (dialog != null && dialog.isShowing())
			return;
		if (mScreenState == SCREEN_STATE.CUSTOM_GAME) {
			changeScreenState(SCREEN_STATE.START_GAME);
			return;
		}
		mHandler.removeCallbacks(mRunnable);
		final int EXIT_AVAILABLE_TIME_DELAY = 2 * 1000;
		if (isExitable) {
			isExitable = false;
			exitGame();
		} else {
			isExitable = true;
			mHandler.postDelayed(mRunnable, EXIT_AVAILABLE_TIME_DELAY);
			Context context = getApplicationContext();
			String msg = getString(R.string.back_press_message);
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}
	}

	private void startGameAc() {
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}

	private void startCustomGameAc() {
		Intent intent = new Intent(this, CustomGameActivity.class);
		startActivity(intent);
	}

	private void changeScreenState(SCREEN_STATE state) {
		mScreenState = state;
		switch (state) {
			case START_GAME:
				initViewStartGame();
				break;
			case CUSTOM_GAME:
				initViewCustomGame();
				break;
			default:
		}
	}

	private void startMakeGameAc() {
		Intent intent = new Intent(this, CreateCustomGameActivity.class);
		startActivity(intent);
	}

	/* Start high score screen */
	private void startHighScoreAc() {
		Intent intent = new Intent(this, HighScoreActivity.class);
		startActivity(intent);
	}

	/* Start option screen */
	private void startOptionAc() {
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

	private void exitGame() {
		if (dialog == null || !dialog.isShowing()) {
			Builder builder = new Builder(this);
			builder.setTitle(R.string.dg_message_title);
			builder.setMessage(R.string.exit_message);
			builder.setNegativeButton(R.string.dg_not_accept_exit_button, null);
			builder.setPositiveButton(R.string.dg_accept_button,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
			dialog = builder.create();
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}
	}

	@Override
	public void onClick(View view) {
		int viewId = view.getId();
		switch (viewId) {
			case R.id.btn_start:
				switch (mScreenState) {
					case START_GAME:
						startGameAc();
						break;
					case CUSTOM_GAME:
						startCustomGameAc();
						break;
					default:
						break;
				}
				break;
			case R.id.btn_custom:
				switch (mScreenState) {
					case START_GAME:
						String msg = getString(R.string.coming_soon);
						Toast.makeText(getApplicationContext(), msg,
								Toast.LENGTH_LONG).show();
						// TODO: implement soon
						// changeScreenState(SCREEN_STATE.CUSTOM_GAME);
						break;
					case CUSTOM_GAME:
						startMakeGameAc();
						break;
					default:
						break;
				}
				break;
			case R.id.btn_high_score:
				switch (mScreenState) {
					case START_GAME:
						startHighScoreAc();
						break;
					case CUSTOM_GAME:
					default:
						break;
				}
				break;
			case R.id.btn_option:
				switch (mScreenState) {
					case START_GAME:
						startOptionAc();
						break;
					case CUSTOM_GAME:
					default:
						break;
				}
				break;
			case R.id.btn_back:
				switch (mScreenState) {
					case START_GAME:
						exitGame();
						break;
					case CUSTOM_GAME:
						changeScreenState(SCREEN_STATE.START_GAME);
					default:
						break;
				}
				break;
			case R.id.btn_left_navi:
				startHelpAc();
				break;
			default:
				break;
		}
	}

	private void startHelpAc() {
		Intent intent = new Intent(this, HelpActivity.class);
		startActivity(intent);
	}
}