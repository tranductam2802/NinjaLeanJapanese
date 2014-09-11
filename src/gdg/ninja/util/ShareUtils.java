package gdg.ninja.util;

import gdg.nat.ninjalearnjapanese.R;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;

public class ShareUtils {
	public static String DEFAULT_SCREENSHOT_PATH = Environment
			.getExternalStorageDirectory() + "/ScreenShot.jpg";

	public static enum SHARE_TYPE {
		FACEBOOK, GOOGLE
	}

	public static void postPhotoToFacebook(Activity activity, File file) {
		try {
			Resources resources = activity.getResources();
			Intent sharingIntent = new Intent(Intent.ACTION_SEND);
			sharingIntent.setType("image/jpeg");
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					resources.getString(R.string.share_text));
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
					resources.getString(R.string.share_text));
			sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
			PackageManager pm = activity.getPackageManager();
			List<ResolveInfo> resolvedInfoList = pm.queryIntentActivities(
					sharingIntent, PackageManager.MATCH_DEFAULT_ONLY);
			boolean resolved = false;
			for (ResolveInfo resolveInfo : resolvedInfoList) {
				if (resolveInfo.activityInfo.packageName
						.startsWith("com.facebook.katana")) {
					sharingIntent.setClassName(
							resolveInfo.activityInfo.packageName,
							resolveInfo.activityInfo.name);
					resolved = true;
					break;
				}
			}
			if (resolved) {
				activity.startActivity(sharingIntent);
			} else {

			}
		} catch (Exception e) {
			// DO NOTHING
		}

	}

	public static void postPhotoToGoogle(Activity activity, File file) {
		try {
			Resources resources = activity.getResources();
			Intent sharingIntent = new Intent(Intent.ACTION_SEND);
			sharingIntent.setType("image/jpeg");
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					resources.getString(R.string.share_text));
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
					resources.getString(R.string.share_text));
			sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
			PackageManager pm = activity.getPackageManager();
			List<ResolveInfo> resolvedInfoList = pm.queryIntentActivities(
					sharingIntent, PackageManager.MATCH_DEFAULT_ONLY);
			boolean resolved = false;
			for (ResolveInfo resolveInfo : resolvedInfoList) {
				if (resolveInfo.activityInfo.packageName
						.startsWith("com.google.android.apps.plus")) {
					sharingIntent.setPackage("com.google.android.apps.plus");
					resolved = true;
					break;
				}
			}
			if (resolved) {
				activity.startActivity(sharingIntent);
			} else {

			}
		} catch (Exception e) {
			// DO NOTHING
		}
	}
}
