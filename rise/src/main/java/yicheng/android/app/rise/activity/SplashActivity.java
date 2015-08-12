package yicheng.android.app.rise.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;

import yicheng.android.app.rise.R;

public class SplashActivity extends Activity {

	GoogleApiClient googleApiClient;

	static final int RC_SIGN_IN = 0;
	private boolean googleIntentInProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		setGoogleApiClient();
	}

	private void goToLoginActivity() {
		Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	private void goToNavigationDrawerActivity() {
		Intent intent = new Intent(SplashActivity.this,
				NavigationDrawerActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	private void setGoogleApiClient() {
		googleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(new ConnectionCallbacks() {

					@Override
					public void onConnected(Bundle connectionHint) {
						// TODO Auto-generated method stub

						Toast.makeText(getBaseContext(), "Logged in!",
								Toast.LENGTH_SHORT).show();

						goToNavigationDrawerActivity();
					}

					@Override
					public void onConnectionSuspended(int cause) {
						// TODO Auto-generated method stub

					}

				})
				.addOnConnectionFailedListener(
						new OnConnectionFailedListener() {

							@Override
							public void onConnectionFailed(
									ConnectionResult result) {

								goToLoginActivity();
								// TODO Auto-generated method stub
								/*		if (!googleIntentInProgress) {

											// The user has already clicked
											// 'sign-in' so we attempt to resolve
											// all
											// errors until the user is signed in,
											// or they cancel.
											try {
												result.startResolutionForResult(
														SplashActivity.this, RC_SIGN_IN);
												googleIntentInProgress = true;
											}
											catch (SendIntentException e) {
												// The intent was canceled before it
												// was sent. Return to the default
												// state and attempt to connect to
												// get an updated ConnectionResult.
												googleIntentInProgress = false;
												googleApiClient.connect();
											}

										}*/
							}
						}).addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
				.build();
	}

	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (requestCode == RC_SIGN_IN) {

			googleIntentInProgress = false;

			if (!googleApiClient.isConnected()) {
				googleApiClient.reconnect();
			}
		}
	}

	protected void onStart() {
		super.onStart();
		googleApiClient.connect();
	}

	protected void onStop() {
		super.onStop();

		if (googleApiClient.isConnected()) {
			googleApiClient.disconnect();
		}
	}
}
