package gdg.ninja.ui;

import gdg.nat.R;
import gdg.ninja.croplib.Crop;
import gdg.ninja.database.DatabaseHandler;
import gdg.ninja.framework.BaseActivity;
import gdg.ninja.gameinfo.QuestInfo;
import gdg.ninja.util.App;
import gdg.ninja.util.NLog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateCustomGameActivity extends BaseActivity implements
		OnClickListener, OnItemSelectedListener {
	
	private final int MEDIA_TYPE_IMAGE = 0;
	private final int GALLERY_PIC_REQUEST = 1;
	private final int CAMERA_PIC_REQUEST = 2;
	private final String IMG_HINT_URI = "saved_image_uri";
	
	private FrameLayout mTxtCreateButton;
	private FrameLayout mTxtTakePictureButton;
	private FrameLayout mTxtChoosePictureButton;
	
	private EditText mEditTxtNewWord;
	private Spinner mSpinChooseCategory;
	private ImageView mImgChoosedPicture;
	
	private Uri inputImagePath;
	private Uri outputImagePath;
	
	private String TAG = "CREATE CUSTOM GAME ACTIVITY";
	
	private Activity mActivity;
	private String inputImagePathString;
	
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
		if(outputImagePath != null && !outputImagePath.getPath().isEmpty())
			outState.putParcelable(IMG_HINT_URI, outputImagePath);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		outputImagePath = (Uri) savedInstanceState.getParcelable(IMG_HINT_URI);
		mImgChoosedPicture.setImageURI(outputImagePath);
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	private void initView(){
		mTxtCreateButton = (FrameLayout) findViewById(R.id.btn_start);
		mTxtTakePictureButton = (FrameLayout) findViewById(R.id.btn_share_facebook);
		mTxtChoosePictureButton = (FrameLayout) findViewById(R.id.btn_share_google);
		mEditTxtNewWord = (EditText) findViewById(R.id.txt_welcome);
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
		DatabaseHandler db = new DatabaseHandler(App.getContext());

		// Spinner Drop down elements
		List<String> labels = db.getAllCustomCategoryName();
		labels.add(App.getContext().getResources()
				.getString(R.string.create_new_category));

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mActivity,
				android.R.layout.simple_spinner_item, labels);
		mSpinChooseCategory.setOnItemSelectedListener(this);

		mSpinChooseCategory.setAdapter(dataAdapter);
	}
	
	/* Validate data and create new game */
	private void createGame(){
		String new_word = mEditTxtNewWord.getText().toString();
		if(isDataValidated(new_word, inputImagePath, "Default")){
			NLog.i(TAG, "Word: " + new_word + " Image Path: " + inputImagePath);
			QuestInfo newQuest = new QuestInfo(0, inputImagePathString,
					new_word, "", 0);
			DatabaseHandler db = new DatabaseHandler(App.getContext());
			long check = db.createCustomQuest(newQuest, mSpinChooseCategory
					.getSelectedItem().toString());
			App.setListCustomCategories(new DatabaseHandler(mActivity)
					.getAllCustomCategory());
			if (check != -1)
				Toast.makeText(mActivity, "Create Successfully",
						Toast.LENGTH_SHORT).show();
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
		
		/* Validate image path */
		if(imagePath == null || imagePath.getPath().isEmpty()){
			// Set ImageView to some image which cute and can remind user to
			// input image
			return false;
		}
		
		// If everything is ok, return true
		return true;
	}
	
	/* Choose picture from Gallery and put into mImgChoosedPicture */
	private void choosePictureFromGallery(){
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent,
				getString(R.string.intent_select_picture)), GALLERY_PIC_REQUEST);
	}
	
	/* Helper function for Take Picture from camera: create file name */
	private File getOutputMediaFile(int type){
		// For future implementation: store videos in a separate directory
		File mediaStorageDir = new File(
				Environment.getExternalStorageDirectory(), "NinjaLearnJapanese");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.
		
		// Create the storage directory if it does not exist
		if(!mediaStorageDir.exists()){
			if(!mediaStorageDir.mkdirs()){
				NLog.e("Create new game", "failed to create directory. Code: ");
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
		if (newFile == null) {
			Toast.makeText(mActivity,
					"Your phone doesn't support external storage",
					Toast.LENGTH_SHORT).show();
			return;
		}
		inputImagePath = Uri.fromFile(newFile);
		inputImagePathString = "file://" + newFile.getAbsolutePath();
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
			case R.id.btn_start:
				createGame();
				break;
			case R.id.btn_share_facebook:
				takePictureFromCamera();
				break;
			case R.id.btn_share_google:
				choosePictureFromGallery();
				break;
			default:
				break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		String label = parent.getItemAtPosition(position).toString();
		Resources resource = App.getContext().getResources();
		if (label.equals(resource.getString(R.string.create_new_category))) {
			View dgView = View.inflate(mActivity,
					R.layout.dg_create_new_category, null);
			final EditText mCatName = (EditText) dgView
					.findViewById(R.id.txt_welcome);
			final EditText mCatDes = (EditText) dgView
					.findViewById(R.id.txt_title_navi);

			AlertDialog.Builder build = new AlertDialog.Builder(mActivity);
			build.setTitle(resource
					.getString(R.string.dg_create_category_title));
			build.setView(dgView);
			build.setPositiveButton(R.string.dg_accept_button,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							String newCatName = mCatName.getText().toString();
							String newCatDes = mCatDes.getText().toString();

							DatabaseHandler db = new DatabaseHandler(mActivity);
							db.createCustomCategory(newCatName, newCatDes);
							initSpinner();
						}
					});
			build.setNegativeButton(R.string.dg_cancel_button,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							mSpinChooseCategory.setSelection(0);
							dialog.dismiss();
						}
					});
			build.setCancelable(false);

			dialog = build.create();
			dialog.show();
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
	
}