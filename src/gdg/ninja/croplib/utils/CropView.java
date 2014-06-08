package gdg.ninja.croplib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

public class CropView extends ImageView{
	
	Paint cornerOfRectPaint, circlePointPaint, shadowLayerPaint;
	private final float CIRCLE_SIZE = 25;
	private final float FRAME_STROKE_WIDTH = 5;
	private int outsideColor = Color.argb(200, 0, 0, 0);
	private int frameColor = Color.WHITE;
	private int defaultCroppedSize = 300;
	private int imageScaledWidth;
	private int imageScaledHeight;
	
	private final String TAG = "CropView";
	private boolean DEBUG = false;
	private Point leftTop, rightBottom, center, previous;
	private Point cornerTopLeft, cornerBottomRight;
	private int BUFFER = 20;
	
	private Handler mHandler;
	
	private enum AffectedSide{
		LEFT, RIGHT, TOP, BOTTOM, DRAG, NONE
	}
	
	AffectedSide affectedSide = AffectedSide.NONE;

	public CropView(Context context){
		super(context);
		initCropView();
	}
	
	public CropView(Context context, AttributeSet attrs){
		super(context, attrs, 0);
		initCropView();
	}
	
	public CropView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		initCropView();
	}

	private void initCropView(){
		cornerOfRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		cornerOfRectPaint.setColor(frameColor);
		cornerOfRectPaint.setStyle(Style.STROKE);
		cornerOfRectPaint.setStrokeWidth(FRAME_STROKE_WIDTH);
		
		circlePointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circlePointPaint.setColor(frameColor);
		circlePointPaint.setStyle(Style.FILL);
		
		shadowLayerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		shadowLayerPaint.setColor(outsideColor);

		leftTop = new Point();
		rightBottom = new Point();
		previous = new Point();
		center = new Point();
		
		cornerTopLeft = new Point();
		cornerBottomRight = new Point();
		
		mHandler = new Handler();

	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		if(leftTop.equals(0, 0) && getDrawable() != null)
			mHandler.postDelayed(new Runnable(){
				
				@Override
				public void run(){
					resetPoints();
					
				}
			}, 100);

		canvas.drawRect(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y,
				cornerOfRectPaint);
		drawShadowOverlay(canvas);
		draw4circle(canvas, affectedSide);

		if(DEBUG){
			Log.i(TAG, "LeftTop(" + leftTop.x + "," + leftTop.y + ")");
			Log.i(TAG, "RightBottom(" + rightBottom.x + "," + rightBottom.y
					+ ")");
		}
	}
	
	/* Draw shadow drop outside crop frame */
	private void drawShadowOverlay(Canvas canvas){
		Rect shadow[] = new Rect[4];
		shadow[0] = new Rect(cornerTopLeft.x, cornerTopLeft.y, rightBottom.x,
				leftTop.y);
		shadow[1] = new Rect(rightBottom.x, cornerTopLeft.y,
				cornerBottomRight.x, cornerBottomRight.y);
		shadow[2] = new Rect(cornerTopLeft.x, rightBottom.y, rightBottom.x,
				cornerBottomRight.y);
		shadow[3] = new Rect(cornerTopLeft.x, leftTop.y, leftTop.x,
				rightBottom.y);
		for(int i = 0; i < 4; i++){
			canvas.drawRect(shadow[i], shadowLayerPaint);
		}
	}
	
	/* Draw four circles in center of each corner of crop frame */
	private void draw4circle(Canvas canvas, AffectedSide affectedSide){
		switch(affectedSide){
			case TOP:
				canvas.drawCircle((leftTop.x + rightBottom.x) / 2, leftTop.y,
						CIRCLE_SIZE, circlePointPaint);
				break;
			case BOTTOM:
				canvas.drawCircle((leftTop.x + rightBottom.x) / 2,
						rightBottom.y, CIRCLE_SIZE, circlePointPaint);
				break;
			case LEFT:
				canvas.drawCircle(leftTop.x, (leftTop.y + rightBottom.y) / 2,
						CIRCLE_SIZE, circlePointPaint);
				break;
			case RIGHT:
				canvas.drawCircle(rightBottom.x,
						(leftTop.y + rightBottom.y) / 2, CIRCLE_SIZE,
						circlePointPaint);
				break;
			default:
				canvas.drawCircle((leftTop.x + rightBottom.x) / 2, leftTop.y,
						CIRCLE_SIZE, circlePointPaint);
				canvas.drawCircle((leftTop.x + rightBottom.x) / 2,
						rightBottom.y, CIRCLE_SIZE, circlePointPaint);
				canvas.drawCircle(leftTop.x, (leftTop.y + rightBottom.y) / 2,
						CIRCLE_SIZE, circlePointPaint);
				canvas.drawCircle(rightBottom.x,
						(leftTop.y + rightBottom.y) / 2, CIRCLE_SIZE,
						circlePointPaint);
				break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		int eventAction = event.getAction();
		switch(eventAction){
			case MotionEvent.ACTION_DOWN:
				
				if(isTouchInsideRectangle(event.getX(), event.getY())){
					affectedSide = getAffectedSide(event.getX(), event.getY());
				}else
					affectedSide = AffectedSide.NONE;

				previous.set((int) event.getX(), (int) event.getY());
				
				break;
			case MotionEvent.ACTION_MOVE:
				
				if(DEBUG){
					Log.i(TAG, affectedSide.toString());
				}
				
				adjustRectangle(affectedSide, (int) event.getX(),
						(int) event.getY());

				invalidate();

				previous.set((int) event.getX(), (int) event.getY());
				
				break;
			case MotionEvent.ACTION_UP:
				affectedSide = AffectedSide.NONE;
				invalidate();
			default:
				previous = new Point();
				break;
		}
		
		return true;
	}
	
	/* Check if user is touch within the Rectangle (maybe resize or drag it) */
	private boolean isTouchInsideRectangle(float x, float y){
		return (x >= (leftTop.x - BUFFER) && x <= (rightBottom.x + BUFFER)
				&& y >= (leftTop.y - BUFFER) && y <= (rightBottom.y + BUFFER));
	}
	
	/* Expand the rectangle or drag it according to user's touch */
	private void adjustRectangle(AffectedSide affectedSide, int x, int y){
		int movement = 0;
		int newX;
		int newY;
		switch(affectedSide){
			case NONE:
				break;
			case LEFT:
				movement = x - leftTop.x;
				newX = leftTop.x + movement;
				newY = leftTop.y + movement;
				if(isInImageRange(newX, newY)) leftTop.set(newX, newY);
				break;
			case TOP:
				movement = y - leftTop.y;
				newX = leftTop.x + movement;
				newY = leftTop.y + movement;
				if(isInImageRange(newX, newY)) leftTop.set(newX, newY);
				break;
			case RIGHT:
				movement = x - rightBottom.x;
				newX = rightBottom.x + movement;
				newY = rightBottom.y + movement;
				if(isInImageRange(newX, newY)) rightBottom.set(newX, newY);
				break;
			case BOTTOM:
				movement = y - rightBottom.y;
				newX = rightBottom.x + movement;
				newY = rightBottom.y + movement;
				if(isInImageRange(newX, newY)) rightBottom.set(newX, newY);
				break;
			case DRAG:
				movement = x - previous.x;
				int movementY = y - previous.y;
				if(isInImageRange(leftTop.x + movement, leftTop.y + movementY)
						&& isInImageRange(rightBottom.x + movement,
								rightBottom.y + movementY)){
					leftTop.set(leftTop.x + movement, leftTop.y + movementY);
					rightBottom.set(rightBottom.x + movement, rightBottom.y
							+ movementY);
				}
				break;
		}
	}
	
	/* Detect what side of Rectangle user is trying expand */
	private AffectedSide getAffectedSide(float x, float y){
		if(x >= leftTop.x - BUFFER && x <= leftTop.x + BUFFER) return AffectedSide.LEFT;
		else if(y >= (leftTop.y - BUFFER) && y <= (leftTop.y + BUFFER)) return AffectedSide.TOP;
		else if(x >= (rightBottom.x - BUFFER) && x <= (rightBottom.x + BUFFER)) return AffectedSide.RIGHT;
		else if(y >= (rightBottom.y - BUFFER) && y <= (rightBottom.y + BUFFER)) return AffectedSide.BOTTOM;
		else
			return AffectedSide.DRAG;
	}
	
	/* Detect whether user move to outside of Image or not */
	private boolean isInImageRange(int x, int y){
		
		if(DEBUG)
			Log.i(TAG, "imageScaledWidth = " + imageScaledWidth
					+ " imageScaledHeight = " + imageScaledHeight);

		return (x >= (center.x - (imageScaledWidth / 2))
				&& x <= (center.x + (imageScaledWidth / 2))
				&& y >= (center.y - (imageScaledHeight / 2)) && y <= (center.y + (imageScaledHeight / 2)));
		
	}

	/* Set the default axis for crop frame base on current image */
	private void resetPoints(){
		float[] f = new float[9];
		getImageMatrix().getValues(f);
		
		// Calculate the scaled dimensions
		imageScaledWidth = Math.round(getDrawable().getIntrinsicWidth()
				* f[Matrix.MSCALE_X]);
		imageScaledHeight = Math.round(getDrawable().getIntrinsicHeight()
				* f[Matrix.MSCALE_Y]);

		// Set the default crop frame to the largest dimension
		defaultCroppedSize = (imageScaledHeight < imageScaledWidth)? (int) (imageScaledHeight / 1.25)
				: (int) (imageScaledWidth / 1.25);

		center.set(getWidth() / 2, getHeight() / 2);
		leftTop.set((getWidth() - defaultCroppedSize) / 2,
				(getHeight() - defaultCroppedSize) / 2);
		rightBottom.set(leftTop.x + defaultCroppedSize, leftTop.y
				+ defaultCroppedSize);
		
		// Calculate cornerTopLeft and cornerTopRight coordinates
		cornerTopLeft.set(center.x - (imageScaledWidth / 2), center.y
				- (imageScaledHeight / 2));
		cornerBottomRight.set(center.x + (imageScaledWidth / 2), center.y
				+ (imageScaledHeight / 2));
		
		invalidate();
	}
	
	/**
	 * @return The cropped bitmap in the crop frame
	 */
	public Bitmap getCroppedImage(){
		Bitmap originalBitmap = getCurrentBitmap();
		
		float ratioOfBmpVsScreen_X = originalBitmap.getWidth()
				/ (float) imageScaledWidth;
		float ratioOfBmpVsScreen_Y = originalBitmap.getHeight()
				/ (float) imageScaledHeight;
		
		float x = leftTop.x - (center.x - imageScaledWidth / 2);
		float y = leftTop.y - (center.y - imageScaledHeight / 2);
		
		if(DEBUG){
			Log.i(TAG, "x= " + x + " y = " + y);
			Log.i(TAG, "ratioOfBmpVsScreen_X= " + ratioOfBmpVsScreen_X
					+ " ratioOfBmpVsScreen_Y = " + ratioOfBmpVsScreen_Y);
			Log.i(TAG,
					"Length = "
							+ (int) ((rightBottom.x - leftTop.x) * (originalBitmap
									.getWidth() / (float) imageScaledWidth)));
		}

		int croppedImgWidth = (int) ((rightBottom.x - leftTop.x) * ratioOfBmpVsScreen_X);
		
		Bitmap croppedBmp = Bitmap.createBitmap(originalBitmap,
				(int) (x * ratioOfBmpVsScreen_X),
				(int) (y * ratioOfBmpVsScreen_Y), croppedImgWidth,
				croppedImgWidth);
		
		originalBitmap.recycle(); // Recycle bitmap as soon as we don't use it

		return croppedBmp;
	}
	
	/**
	 * Rotate the image angle degrees
	 */
	public void rotateImage(float angle){
		Bitmap source = getCurrentBitmap();
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);

		this.setImageBitmap(Bitmap.createBitmap(source, 0, 0,
				source.getWidth(), source.getHeight(), matrix, true));
		resetPoints();
	}
	
	/* return current bitmap holding by ImageView */
	private Bitmap getCurrentBitmap(){
		BitmapDrawable drawable = (BitmapDrawable) getDrawable();
		return drawable.getBitmap();
	}

}

