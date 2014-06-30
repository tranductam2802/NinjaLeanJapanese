package gdg.ninja.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

public class ImagePathProcess{
	public static InputStream getImgInputStream(Context mContext,
			String imagePathFromDatabase) throws IOException{
		if(imagePathFromDatabase.substring(0, 7).equals("assets/")) return mContext
				.getAssets().open(imagePathFromDatabase.substring(7));
		else{
			File input = new File(imagePathFromDatabase);
			FileInputStream fileInputStream = new FileInputStream(input);
			return fileInputStream;
		}
		
	}
}