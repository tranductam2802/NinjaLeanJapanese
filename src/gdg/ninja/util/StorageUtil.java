package gdg.ninja.util;

import java.io.File;

import android.content.Context;

public class StorageUtil {
	private final static String TAG = "StorageUtil";
	private final static String ROOT_FOLDER = "Ninja/Language";
	private final static String PHOTO_FOLDER = "Ninja_Photo";
	private final static String FILE_SENT = "Send";
	private static final String DEFAULT_IMAGE_FOLDER = "Ninja";
	private static final String NAME_FORMAT = "yyyyMMddHHmmssSSS";
	private static final String IMAGE_FORMAT_TYPE = "png";
	
	/** Save image quest create from user */
	public static boolean saveFileByUser(Context context, File file){
		boolean isSaved = false;
		return isSaved;
	}
	
	/** Save image quest send from another user */
	public static boolean saveFileSend(Context context, File file){
		boolean isSaved = false;
		return isSaved;
	}
	
	/** Get file from user */
//	public static File getFileByUser(Context context, String filePath) {
//	}
	/** Get file send by another user */
//	public static File getFileSent(Context context, String filePath) {
//	}
}