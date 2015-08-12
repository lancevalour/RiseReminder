package yicheng.android.app.rise.activity;

import yicheng.android.app.rise.R;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends Activity {

	SignInButton activity_login_signInButton;

	static final int RC_SIGN_IN = 0;

	GoogleApiClient googleApiClient;

	private boolean mIntentInProgress;

	private boolean mSignInClicked;

	private ConnectionResult mConnectionResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		initiateComponents();

		/*goToNavigationDrawerActivity();*/
		setGoogleApiClient();
		setComponentControl();
	}

	private void initiateComponents() {
		activity_login_signInButton = (SignInButton) findViewById(R.id.activity_login_signInButton);

	}

	private void goToNavigationDrawerActivity() {
		Intent intent = new Intent(LoginActivity.this,
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
						mSignInClicked = false;
						Toast.makeText(getBaseContext(), "Logged in!",
								Toast.LENGTH_SHORT).show();

						goToNavigationDrawerActivity();
					}

					@Override
					public void onConnectionSuspended(int cause) {
						// TODO Auto-generated method stub
						googleApiClient.connect();
					}

				})
				.addOnConnectionFailedListener(
						new OnConnectionFailedListener() {

							@Override
							public void onConnectionFailed(
									ConnectionResult result) {
								// TODO Auto-generated method stub
								/*			if (!mIntentInProgress) {
												if (mSignInClicked
														&& result.hasResolution()) {
													// The user has already clicked
													// 'sign-in' so we attempt to resolve
													// all
													// errors until the user is signed in,
													// or they cancel.
													try {
														result.startResolutionForResult(
																LoginActivity.this,
																RC_SIGN_IN);
														mIntentInProgress = true;
													}
													catch (SendIntentException e) {
														// The intent was canceled before it
														// was sent. Return to the default
														// state and attempt to connect to
														// get an updated ConnectionResult.
														mIntentInProgress = false;
														googleApiClient.connect();
													}
												}
											}*/

								if (!result.hasResolution()) {
									GooglePlayServicesUtil.getErrorDialog(
											result.getErrorCode(),
											LoginActivity.this, 0).show();
									return;
								}

								if (!mIntentInProgress) {
									// Store the ConnectionResult for later
									// usage
									mConnectionResult = result;

									if (mSignInClicked) {
										// The user has already clicked
										// 'sign-in' so we attempt to
										// resolve all
										// errors until the user is signed in,
										// or they cancel.
										resolveSignInError();
									}
								}

							}
						}).addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
				.build();
	}

	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
			}
			catch (SendIntentException e) {
				mIntentInProgress = false;
				googleApiClient.connect();
			}
		}
	}

	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		/*	if (requestCode == RC_SIGN_IN) {
				if (responseCode != RESULT_OK) {
					mSignInClicked = false;
				}

				mIntentInProgress = false;

				if (!googleApiClient.isConnected()) {
					googleApiClient.reconnect();
				}
			}*/

		if (requestCode == RC_SIGN_IN) {
			if (responseCode != RESULT_OK) {
				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!googleApiClient.isConnecting()) {
				googleApiClient.connect();
			}
		}

	}

	private void setComponentControl() {
		setGoogleSignInButtonControl();
	}

	private void setGoogleSignInButtonControl() {
		activity_login_signInButton
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (!googleApiClient.isConnecting()) {
							mSignInClicked = true;
							resolveSignInError();
						}
					}
				});
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
