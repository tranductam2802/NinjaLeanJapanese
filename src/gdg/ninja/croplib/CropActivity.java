package gdg.ninja.croplib;

import gdg.nat.R;
import gdg.ninja.croplib.Crop.Extra;
import gdg.ninja.croplib.utils.CropView;
import gdg.ninja.croplib.utils.FileUtils;
import gdg.ninja.croplib.utils.ImageResizer;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CropActivity extends Activity implements OnClickListener{
	
	private Button btnCrop;
	private Button btnRotateLeft;
	private Button btnRotateRight;
	private CropView mCropView;
	
	private Uri mSourceUri;
	private Uri mOutputUri;
	private Activity mActivity;
	
	private int mMaxX;
	private int mMaxY;
	
	private int mQuality;

	private String mSourcePath;
	private String mOutputPath;

	@Override
	protected void onCreate(Bundle arg0){
		super.onCreate(arg0);
		setContentView(R.layout.ac_crop_image);
		
		mActivity = this;

		initViews();
		initData();
	}
	
	/* Initiate data from received Intent */
	private void initData(){
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		
		if(extras != null){
			mMaxX = extras.getInt(Crop.Extra.MAX_X, 1024);
			mMaxY = extras.getInt(Crop.Extra.MAX_Y, 1024);
			mQuality = extras.getInt(Crop.Extra.OUTPUT_QUALITY, 95);
			
			mSourceUri = extras.getParcelable(Extra.IMAGE_SOURCE);
			mOutputUri = extras.getParcelable(Extra.IMAGE_OUTPUT);
			
			mSourcePath = FileUtils.getPath(mActivity, mSourceUri);

			if(mOutputUri == null) mOutputUri = mSourceUri;
			
			mOutputPath = FileUtils.getPath(mActivity, mOutputUri);
		}
		
		// Resize image to mMaxY width and mMaxY height
		// ParcelFileDescriptor imageFileDescriptor;
		
		try{
			// imageFileDescriptor = mActivity.getContentResolver()
			// .openFileDescriptor(mSourceUri, "r");
			Bitmap bmp = ImageResizer.decodeSampledBitmapFromFile(mSourcePath,
					mMaxX, mMaxY);
			
			// Set the new bitmap to mCropView
			mCropView.setImageBitmap(bmp);
		}catch(Exception e){
			finishOnFail();
		}
	}

	private void initViews(){
		btnCrop = (Button) findViewById(R.id.btn_create);
		btnRotateLeft = (Button) findViewById(R.id.btn_left_navi);
		btnRotateRight = (Button) findViewById(R.id.btn_right_navi);
		mCropView = (CropView) findViewById(R.id.img_quest_hint);
		
		btnCrop.setOnClickListener(this);
		btnRotateLeft.setOnClickListener(this);
		btnRotateRight.setOnClickListener(this);
	}
	
	private void finishOnFail(){
		setResult(RESULT_CANCELED);
		finish();
	}
	
	/* Write the cropped image to file at mOutputUri */
	private void writeImageToFile(){
		Bitmap resultBitmap = mCropView.getCroppedImage();
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		resultBitmap.compress(Bitmap.CompressFormat.JPEG, mQuality, bytes);
		resultBitmap.recycle();
		
		try{
			FileOutputStream fos = new FileOutputStream(mOutputPath);
			fos.write(bytes.toByteArray());
			fos.close();
		}catch(IOException e){
			finishOnFail();
		}
	}

	@Override
	public void onClick(View btn){
		int btn_id = btn.getId();
		if(btn_id == R.id.btn_create){
			writeImageToFile();
			Intent resultIntent = new Intent();
			resultIntent.setData(mSourceUri);
			setResult(RESULT_OK, resultIntent);
			finish();
		}else if(btn_id == R.id.btn_left_navi)
			mCropView.rotateImage(-90);
		else if(btn_id == R.id.btn_right_navi) mCropView.rotateImage(90);

	}
}

