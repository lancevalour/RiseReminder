package yicheng.android.app.rise.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import yicheng.android.app.rise.service.EventIntervalAlarmIntentService;

public class EventIntervalAlarmReceiver extends BroadcastReceiver {
    Context context;

    String eventName;
    String eventContent;
    int eventIntervalID;
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
        eventIntervalID = intent.getIntExtra("event_interval_id", -1);
        eventLocationList = intent.getStringExtra("event_location_list");

        eventInterval = intent.getStringExtra("event_interval");
        eventEndTime = intent.getStringExtra("event_end_time");
        eventStartTime = intent.getStringExtra("event_start_time");

        startEventIntervalAlarmIntentService();
    }

    private void startEventIntervalAlarmIntentService() {
        Intent intent = new Intent(context,
                EventIntervalAlarmIntentService.class);

        intent.putExtra("event_name", this.eventName);
        intent.putExtra("event_content", this.eventContent);
        intent.putExtra("event_interval_id", eventIntervalID);
        intent.putExtra("event_location_list", this.eventLocationList);
        intent.putExtra("event_interval", this.eventInterval);
        intent.putExtra("event_end_time", this.eventEndTime);
        intent.putExtra("event_start_time", this.eventStartTime);

        context.startService(intent);
    }
}
