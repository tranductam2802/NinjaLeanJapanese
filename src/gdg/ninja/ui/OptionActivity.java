package gdg.ninja.ui;

import gdg.nat.ninjalearnjapanese.R;
import gdg.ninja.framework.BaseActivity;
import gdg.ninja.util.ConfigPreference;
import gdg.ninja.util.SoundUtils;
import gdg.ninja.util.SoundUtils.SOUND_NAME;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class OptionActivity extends BaseActivity implements OnClickListener {
	ImageView mBtnSound, mBtnAbout, mBtnBack;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.ac_option);

		mBtnSound = (ImageView) findViewById(R.id.btn_left_navi);
		mBtnAbout = (ImageView) findViewById(R.id.btn_right_navi);
		mBtnBack = (ImageView) findViewById(R.id.btn_start);
		mBtnAbout.setOnClickListener(this);
		mBtnBack.setOnClickListener(this);
		mBtnSound.setOnClickListener(this);

		updateSoundBtn();
	}

	private void updateSoundBtn() {
		if (ConfigPreference.getInstance().isSoundOn()) {
			mBtnSound.setImageResource(R.drawable.on);
		} else
			mBtnSound.setImageResource(R.drawable.off);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.btn_left_navi:
				if (ConfigPreference.getInstance().isSoundOn()) {
					ConfigPreference.getInstance().turnSoundOff();
				} else {
					ConfigPreference.getInstance().turnSoundOn();
					SoundUtils.getInstance().play(SOUND_NAME.OTHER_BTN);
				}
				updateSoundBtn();
				break;
			case R.id.btn_right_navi:
				SoundUtils.getInstance().play(SOUND_NAME.OTHER_BTN);
				Intent intent = new Intent(this, AboutActivity.class);
				startActivity(intent);
				break;
			case R.id.btn_start:
				SoundUtils.getInstance().play(SOUND_NAME.OTHER_BTN);
				finish();
			default:
				break;
		}

	}
}