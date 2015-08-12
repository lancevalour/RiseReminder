package yicheng.android.app.rise.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import yicheng.android.app.rise.receiver.EventIntervalAlarmReceiver;

public class EventAlarmIntentService extends IntentService {

	String eventName;
	String eventContent;
	int eventID;
	int eventIntervalID;
	String eventLocationList;

	String eventInterval;
	String eventEndTime;
	String eventStartTime;

	final static String GROUP_KEY_EVENT = "notification_group_event";

	public EventAlarmIntentService() {
		super("EventAlarmIntentService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		eventName = intent.getStringExtra("event_name");
		eventContent = intent.getStringExtra("event_content");
		eventID = intent.getIntExtra("event_id", -1);
		eventLocationList = intent.getStringExtra("event_location_list");

		eventInterval = intent.getStringExtra("event_interval");
		eventEndTime = intent.getStringExtra("event_end_time");
		eventStartTime = intent.getStringExtra("event_start_time");

		eventIntervalID = (eventID + 1) * 100000;

		// readLocationListFromDatabase();

		// getCurrentPlace();
		DateFormat df = new SimpleDateFormat("HH:mm");
		String curTime = df.format(Calendar.getInstance().getTime());

		startEventIntervalAlarm();
	}

	AlarmManager alarmManager;
	PendingIntent pendingIntent;

	private void startEventIntervalAlarm() {

		Intent alarmIntent = new Intent(this, EventIntervalAlarmReceiver.class);

		alarmIntent.putExtra("event_name", this.eventName);
		alarmIntent.putExtra("event_content", this.eventContent);
		alarmIntent.putExtra("event_interval_id", eventIntervalID);
		alarmIntent.putExtra("event_location_list", this.eventLocationList);
		alarmIntent.putExtra("event_interval", this.eventInterval);
		alarmIntent.putExtra("event_end_time", this.eventEndTime);
		alarmIntent.putExtra("event_start_time", this.eventStartTime);

		pendingIntent = PendingIntent.getBroadcast(this, eventIntervalID,
				alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		String[] startTime = eventStartTime.split(":");

		Calendar calendar = Calendar.getInstance();

		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(startTime[0]));
		calendar.set(Calendar.MINUTE, Integer.valueOf(startTime[1]) + 2);

		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(),
				Integer.parseInt(eventInterval) * 1000 * 60, pendingIntent);

	}

	/*	private void readLocationListFromDatabase() {
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
		}*/
	/*
		private void showNotification() {
			notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					this).setSmallIcon(R.drawable.ic_action_event)
					.setContentTitle("Current Place").setContentText(eventContent)
					.setGroup(GROUP_KEY_EVENT);
			
					Intent resultIntent = new Intent(context, NavigationDrawerActvity.class);

					PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
							0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

					mBuilder.setContentIntent(resultPendingIntent);

			notificationManager.notify(eventID, mBuilder.build());
		}*/

	/*GoogleApiClient mGoogleApiClient;

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

						for (Location location : locationList) {
							if (location.distanceTo(mLastLocation) < 50) {

								showNotification();
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
	}*/
}
