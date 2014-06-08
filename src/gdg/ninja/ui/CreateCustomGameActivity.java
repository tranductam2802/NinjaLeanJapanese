package gdg.ninja.ui;

import gdg.nat.R;
import gdg.ninja.croplib.Crop;
import gdg.ninja.framework.BaseActivity;
import gdg.ninja.util.NLog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateCustomGameActivity extends BaseActivity implements
		OnClickListener{

	public static final String IMAGE_PATH = "IMAGE_PATH";
	private static final int MEDIA_TYPE_IMAGE = 0;
	private final int GALLERY_PIC_REQUEST = 1;
	private final int CAMERA_PIC_REQUEST = 2;
	private final String IMG_HINT_URI = "saved_image";

	private TextView mTxtCreateButton;
	private TextView mTxtTakePictureButton;
	private TextView mTxtChoosePictureButton;
	
	private EditText mEditTxtNewWord;
	private Spinner mSpinChooseCategory;
	private ImageView mImgChoosedPicture;
	
	private Uri inputImagePath;
	private Uri outputImagePath;

	private String TAG = "CREATE CUSTOM GAME ACTIVITY";

	private Activity mActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_create_custom_game);
		
		mActivity = this;

		initView();
	}
	
	/* Handle screen orientation changes */
	@Override
	protected void onSaveInstanceState(Bundle outState){
		if(inputImagePath != null && !inputImagePath.getPath().isEmpty())
			outState.putParcelable(IMG_HINT_URI, inputImagePath);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		inputImagePath = (Uri) savedInstanceState.getParcelable(IMG_HINT_URI);
		mImgChoosedPicture.setImageURI(inputImagePath);
		super.onRestoreInstanceState(savedInstanceState);
	}

	private void initView(){
		mTxtCreateButton = (TextView) findViewById(R.id.btn_create);
		mTxtTakePictureButton = (TextView) findViewById(R.id.btn_take_picture);
		mTxtChoosePictureButton = (TextView) findViewById(R.id.btn_choose_from_galary);
		mEditTxtNewWord = (EditText) findViewById(R.id.txt_input_new_word);
		mImgChoosedPicture = (ImageView) findViewById(R.id.img_input_hint_picture);
		mSpinChooseCategory = (Spinner) findViewById(R.id.spinner_choose_category);
		
		// Set on click listener
		mTxtCreateButton.setOnClickListener(this);
		mTxtTakePictureButton.setOnClickListener(this);
		mTxtChoosePictureButton.setOnClickListener(this);
		
		// Get category list and put into Spinner
		initSpinner();
	}
	
	private void initSpinner(){
		// TODO: Implement query to database and put category list into spinner
	}
	
	/* Validate data and create new game */
	private void createGame(){
		String new_word = mEditTxtNewWord.getText().toString();
		if(isDataValidated(new_word, inputImagePath, "Default")){
			NLog.i(TAG, "Word: " + new_word + " Image Path: " + inputImagePath);
			// TODO: Implement save new word to database
		}
	}
	
	private boolean isDataValidated(String newWord, Uri imagePath,
			String Category){
		/* Validate input new word */
		if(newWord.equals("") || newWord == null){ // Check for blank input
			mEditTxtNewWord.setError(getString(R.string.error_input_blank));
			mEditTxtNewWord.requestFocus();
			return false;
		}
		// else if (false){ // TODO: Check new word already exist!
		// mEditTxtNewWord.setError(getString(R.string.error_input_already_exist));
		// mEditTxtNewWord.requestFocus();
		// return false;
		// }
		
		/* Validate image path */
		// TODO: implement validate image
		if(imagePath == null || imagePath.getPath().isEmpty()){
			// Set ImageView to some image which cute and can remind user to
			// input image
			return false;
		}
		
		/* Validate for category */
		// TODO: Implement validate for category, it should exist!
		
		// If everything is ok, return true
		return true;
	}
	
	/* Choose picture from Gallery and put into mImgChoosedPicture */
	private void choosePictureFromGallery(){
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent,
					getString(R.string.intent_select_picture)),
					GALLERY_PIC_REQUEST);
	}
	
	/* Helper function for Take Picture from camera: create file name */
	private static File getOutputMediaFile(int type){
		// For future implementation: store videos in a separate directory
		File mediaStorageDir = new File(
				Environment.getExternalStorageDirectory(), "NinjaLearnJapanese");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.
		
		// Create the storage directory if it does not exist
		if(!mediaStorageDir.exists()){
			if(!mediaStorageDir.mkdirs()){
				NLog.d("Create new game", "failed to create directory");
				return null;
			}
		}
		
		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
				.format(new Date());
		File mediaFile;
		if(type == MEDIA_TYPE_IMAGE){
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		}else{
			NLog.e("Create new game",
					"type of media file not supported: type was:" + type);
			return null;
		}
		
		return mediaFile;
	}
	
	/* Take picture from camera */
	private void takePictureFromCamera(){
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		File newFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
		inputImagePath = Uri.fromFile(newFile);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, inputImagePath);
		startActivityForResult(intent, CAMERA_PIC_REQUEST);
	}
	
	/* Receive image from Camera or Gallery */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(resultCode == RESULT_OK){
			if(requestCode == GALLERY_PIC_REQUEST){
				inputImagePath = data.getData();
				
				File output = getOutputMediaFile(MEDIA_TYPE_IMAGE);
				outputImagePath = Uri.fromFile(output);
				
				new Crop(inputImagePath).output(outputImagePath).quality(90)
						.withMaxSize(1024, 1024).start(mActivity);
			}else if(requestCode == CAMERA_PIC_REQUEST){
				outputImagePath = inputImagePath;
				new Crop(inputImagePath).quality(90).withMaxSize(1024, 1024)
						.start(mActivity);
			}else if(requestCode == Crop.REQUEST_CROP){
				mImgChoosedPicture.setImageURI(outputImagePath);
			}

		}
	}

	@Override
	public void onClick(View view){
		int viewID = view.getId();
		switch(viewID){
			case R.id.btn_create:
				createGame();
				break;
			case R.id.btn_take_picture:
				takePictureFromCamera();
				break;
			case R.id.btn_choose_from_galary:
				choosePictureFromGallery();
				break;
			default:
				break;
		}
	}

}