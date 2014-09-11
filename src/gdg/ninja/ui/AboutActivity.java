package gdg.ninja.ui;

import gdg.nat.ninjalearnjapanese.R;
import gdg.ninja.util.SoundUtils;
import gdg.ninja.util.SoundUtils.SOUND_NAME;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class AboutActivity extends Activity {
	private ImageView mImgPlayButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_about);

		mImgPlayButton = (ImageView) findViewById(R.id.btn_start);
		mImgPlayButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SoundUtils.getInstance().play(SOUND_NAME.OTHER_BTN);
				finish();
			}
		});
	}
}
