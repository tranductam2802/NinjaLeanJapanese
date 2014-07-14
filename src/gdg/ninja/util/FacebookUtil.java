package gdg.ninja.util;

import gdg.nat.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class FacebookUtil{
	private static final List<String> PERMISSIONS = Arrays
			.asList("publish_actions");
	private static boolean pendingPublishReauthorization = false;
	private static boolean pendingReadReauthorization = false;
	private static Bundle postParam = null;
	
	public static void printKeyHash(PackageManager packageManager,
			String packageInfo){
		if(!Config.IS_DEBUG) return;
		try{
			PackageInfo info = packageManager.getPackageInfo(packageInfo,
					PackageManager.GET_SIGNATURES);
			for(Signature signature : info.signatures){
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				NLog.i("KeyHash: ",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		}catch(NameNotFoundException e){
			NLog.e("NameNotFoundException: " + String.valueOf(e.getMessage()));
		}catch(NoSuchAlgorithmException e){
			NLog.e("NoSuchAlgorithmException" + String.valueOf(e.getMessage()));
		}
	}
	
	private static boolean isSubsetOf(Collection<String> subset,
			Collection<String> superset){
		for(String string : subset){
			if(!superset.contains(string)){ return false; }
		}
		return true;
	}
	
	private static GraphUserCallback mGraphUserCallBack = new GraphUserCallback(){
		@Override
		public void onCompleted(GraphUser user, Response response){
			if(user != null){
				NLog.i("Facebook user: ", user.toString());
			}
		}
	};
	
	public static void login(final Activity context){
		StatusCallback onSessionStageChanged = new StatusCallback(){
			@Override
			public void call(Session session, SessionState state,
					Exception exception){
				if(session.isOpened()){
					Request.newMeRequest(session, mGraphUserCallBack)
							.executeAsync();
					if(pendingReadReauthorization){
						pendingReadReauthorization = false;
						List<String> permissions = session.getPermissions();
						if(!isSubsetOf(PERMISSIONS, permissions)){
							pendingPublishReauthorization = true;
							Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
									context, PERMISSIONS);
							session.requestNewPublishPermissions(newPermissionsRequest);
						}else{
							publishStory();
						}
					}else if(pendingPublishReauthorization
							&& state.equals(SessionState.OPENED_TOKEN_UPDATED)){
						pendingPublishReauthorization = false;
						publishStory();
					}
				}
			}
		};
		Session.openActiveSession(context, true, onSessionStageChanged);
	}
	
	public static void logout(){
		Session session = Session.getActiveSession();
		if(session != null) session.closeAndClearTokenInformation();
	}
	
	public static void postStatus(Activity context, Bundle bundle){
		Session session = Session.getActiveSession();
		postParam = bundle;
		if(session != null && !session.isClosed()){
			List<String> permissions = session.getPermissions();
			if(!isSubsetOf(PERMISSIONS, permissions)){
				pendingPublishReauthorization = true;
				Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
						context, PERMISSIONS);
				session.requestNewPublishPermissions(newPermissionsRequest);
			}else{
				publishStory();
			}
		}else{
			pendingReadReauthorization = true;
			login(context);
		}
	}
	
	private static void publishStory(){
		if(postParam == null) return;
		Session session = Session.getActiveSession();
		Request.Callback callback = new Request.Callback(){
			private final String KEY_ID = "id";
			
			public void onCompleted(Response response){
				JSONObject graphResponse = response.getGraphObject()
						.getInnerJSONObject();
				NLog.i("Facebook response: ", graphResponse.toString());
				FacebookRequestError error = response.getError();
				if(error != null){
					// TODO: On error
					Toast.makeText(App.getContext(), error.getErrorMessage(),
							Toast.LENGTH_SHORT).show();
				}else{
					// TODO: On success
					if(postParam != null) postParam = null;
					try{
						if(graphResponse.has(KEY_ID)){
							Toast.makeText(App.getContext(),
									graphResponse.getString(KEY_ID),
									Toast.LENGTH_SHORT).show();
						}
					}catch(JSONException e){
						NLog.e("JSON error: " + e.getMessage());
					}
				}
			}
		};
		Request request = new Request(session, "me/feed", postParam,
				HttpMethod.POST, callback);
		RequestAsyncTask task = new RequestAsyncTask(request);
		task.execute();
		Toast.makeText(App.getContext(), R.string.loading, Toast.LENGTH_SHORT)
				.show();
	}
	
	public static void onActivityResult(Activity context, int requestCode,
			int resultCode, Intent data){
		Session.getActiveSession().onActivityResult(context, requestCode,
				resultCode, data);
	}
}