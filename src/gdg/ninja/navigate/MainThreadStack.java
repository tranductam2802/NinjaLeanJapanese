package gdg.ninja.navigate;

import java.util.Stack;

import android.os.Looper;
import android.support.v4.app.Fragment;

public class MainThreadStack extends Stack<Fragment> {
	private static final long serialVersionUID = 1L;

	@Override
	public synchronized boolean isEmpty() {
		ensureOnMainThread();
		return super.isEmpty();
	}

	@Override
	public synchronized Fragment peek() {
		ensureOnMainThread();
		return super.peek();
	}

	public synchronized Fragment pop() {
		ensureOnMainThread();
		return super.pop();
	}

	public synchronized Fragment push(Fragment fragment) {
		ensureOnMainThread();
		return super.push(fragment);
	}

	/*
	 * Method to ensure that run on main thread.
	 */
	private void ensureOnMainThread() {
		if (Looper.myLooper() != Looper.getMainLooper())
			throw new IllegalStateException(
					"Illegal State Exception! This method must be called from the UI thread.");
		else
			return;
	}
}