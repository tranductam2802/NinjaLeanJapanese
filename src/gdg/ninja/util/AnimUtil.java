package gdg.ninja.util;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;

public class AnimUtil {
	public static Animation getZoomOut() {
		Animation animation = new ScaleAnimation(0, 0, 0, 0);
		return animation;
	}
}