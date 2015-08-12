package yicheng.android.app.rise.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import yicheng.android.app.rise.service.EventAlarmIntentService;

public class EventAlarmReceiver extends BroadcastReceiver {

	Context context;

	String eventContent;
	String eventName;
	int eventID;
	String eventLocationList;

	String eventInterval;
	String eventEndTime;
	String eventStartTime;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.context = context;

		eventName = intent.getStringExtra("event_name");
		eventContent = intent.getStringExtra("event_content");
		eventID = intent.getIntExtra("event_id", -1);
		eventLocationList = intent.getStringExtra("event_location_list");
		eventInterval = intent.getStringExtra("event_interval");
		eventEndTime = intent.getStringExtra("event_end_time");
		eventStartTime = intent.getStringExtra("event_start_time");

		startEventAlarmIntentService();

	}

	private void startEventAlarmIntentService() {
		Intent intent = new Intent(context, EventAlarmIntentService.class);
		intent.putExtra("event_name", eventName);
		intent.putExtra("event_content", eventContent);
		intent.putExtra("event_id", eventID);
		intent.putExtra("event_location_list", eventLocationList);
		intent.putExtra("event_interval", eventInterval);
		intent.putExtra("event_end_time", eventEndTime);
		intent.putExtra("event_start_time", eventStartTime);

		context.startService(intent);
	}
}
