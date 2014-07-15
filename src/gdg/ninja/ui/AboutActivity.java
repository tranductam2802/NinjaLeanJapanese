package gdg.ninja.ui;

import gdg.nat.R;
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
				finish();
			}
		});
	}
}
