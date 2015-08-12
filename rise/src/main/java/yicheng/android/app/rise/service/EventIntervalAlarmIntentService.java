package yicheng.android.app.rise.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.database.RisePlace;
import yicheng.android.app.rise.database.SQLiteHelper;
import yicheng.android.app.rise.receiver.EventIntervalAlarmReceiver;

public class EventIntervalAlarmIntentService extends IntentService {

	NotificationManager notificationManager;

	String placeName;
	String placeLatitude;
	String placeLongitude;
	String alarm_interval;

	String eventName;
	String eventContent;
	int eventIntervalID;
	String eventLocationList;

	String eventInterval;
	String eventEndTime;

	SQLiteHelper placeSQLiteHelper;
	List<Location> locationList;

	final static String GROUP_KEY_EVENT = "notification_group_event";

	public EventIntervalAlarmIntentService() {
		super("EventIntervalAlarmIntentService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		eventName = intent.getStringExtra("event_name");
		eventContent = intent.getStringExtra("event_content");
		eventIntervalID = intent.getIntExtra("event_interval_id", -1);
		eventLocationList = intent.getStringExtra("event_location_list");
		eventEndTime = intent.getStringExtra("event_end_time");

		if (isEventFinished()) {
			cancelIntervalAlarm();
		}
		else {
			readLocationListFromDatabase();
			getCurrentPlace();
		}

	}

	private boolean isEventFinished() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,
				Integer.valueOf(eventEndTime.split(":")[0]));
		cal.set(Calendar.MINUTE, Integer.valueOf(eventEndTime.split(":")[1]));

		Date endDate = cal.getTime();

		return new Date().after(endDate);

	}

	AlarmManager alarmManager;
	PendingIntent pendingIntent;

	private void cancelIntervalAlarm() {
		Intent alarmIntent = new Intent(this, EventIntervalAlarmReceiver.class);

		pendingIntent = PendingIntent.getBroadcast(this, eventIntervalID,
				alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		alarmManager.cancel(pendingIntent);
	}

	private void readLocationListFromDatabase() {
		String[] locations = eventLocationList.split(",");
		locationList = new ArrayList<Location>();
		if (locations.length > 0 && locations[0].trim().length() > 0) {

			placeSQLiteHelper = new SQLiteHelper(getBaseContext(),
					SQLiteHelper.TABLE_PLACE);
			System.out.println(Arrays.toString(locations));
			for (String s : locations) {
				RisePlace place = placeSQLiteHelper.getPlaceByName(s);
				Location location = new Location("");
				location.setLatitude(Double.parseDouble(place
						.getPlaceLatitude()));
				location.setLongitude(Double.parseDouble(place
						.getPlaceLongitude()));
				locationList.add(location);
			}
		}
	}

	private void showNotification() {
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.ic_action_notification_event)
				.setColor(getResources().getColor(R.color.theme_primary))
				.setContentTitle(eventName)
				.setVibrate(new long[] { 1000, 1000 })
				.setContentText(eventContent).setLights(Color.RED, 1000, 1000)
				.setGroup(GROUP_KEY_EVENT);
		/*
				Intent resultIntent = new Intent(context, NavigationDrawerActvity.class);

				PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
						0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

				mBuilder.setContentIntent(resultPendingIntent);*/

		notificationManager.notify(eventIntervalID, mBuilder.build());
	}

	GoogleApiClient mGoogleApiClient;

	private void getCurrentPlace() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addApi(Places.GEO_DATA_API)
				.addApi(Places.PLACE_DETECTION_API)
				.addApi(LocationServices.API)
				.addConnectionCallbacks(new ConnectionCallbacks() {

					@Override
					public void onConnected(Bundle arg0) {
						// TODO Auto-generated method stub
						Location mLastLocation = LocationServices.FusedLocationApi
								.getLastLocation(mGoogleApiClient);
						if (mLastLocation != null) {
							placeLatitude = String.valueOf(mLastLocation
									.getLatitude());
							placeLongitude = String.valueOf(mLastLocation
									.getLongitude());

						}

						if (locationList.size() == 0) {
							showNotification();
						}
						else {

							for (Location location : locationList) {
								if (location.distanceTo(mLastLocation) < 50) {
									showNotification();
								}
							}
						}

					}

					@Override
					public void onConnectionSuspended(int arg0) {
						// TODO Auto-generated method stub

					}

				})
				.addOnConnectionFailedListener(
						new OnConnectionFailedListener() {

							@Override
							public void onConnectionFailed(ConnectionResult arg0) {
								// TODO Auto-generated method stub

							}

						}).build();
		mGoogleApiClient.connect();
	}
}
