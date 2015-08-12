package yicheng.android.app.rise.database;

public class RiseEvent {
	String eventStartTime;
	String eventEndTime;
	String eventCycleInterval;

	String eventName;
	String eventContent;
	String eventCreateDate;
	String eventPriority;

	String isEventFinished;
	String isNotificationOn;

	String eventLocationList;

	public RiseEvent(String eventName, String eventContent,
			String eventCreateDate, String eventPriority,
			String eventStartTime, String eventEndTime,
			String eventCycleInterval, String isEventFinished,
			String isNotificationOn, String eventLocatonList) {

		this.eventName = eventName;
		this.eventContent = eventContent;
		this.eventCreateDate = eventCreateDate;
		this.eventPriority = eventPriority;
		this.eventStartTime = eventStartTime;
		this.eventEndTime = eventEndTime;
		this.eventCycleInterval = eventCycleInterval;
		this.isEventFinished = isEventFinished;
		this.isNotificationOn = isNotificationOn;
		this.eventLocationList = eventLocatonList;
	}

	public void setEventStartTime(String eventStartTime) {
		this.eventStartTime = eventStartTime;
	}

	public void setEventEndTime(String eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

	public void setEventCycleInterval(String eventCycleInterval) {
		this.eventCycleInterval = eventCycleInterval;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}

	public void setEventCreateDate(String eventCreateDate) {
		this.eventCreateDate = eventCreateDate;
	}

	public void setEventPriority(String eventPriority) {
		this.eventPriority = eventPriority;
	}

	public void setIsEventFinished(String isEventFinished) {
		this.isEventFinished = isEventFinished;
	}

	public void setIsNotificationOn(String isNotificationOn) {
		this.isNotificationOn = isNotificationOn;
	}

	public void setEventLocationList(String eventLocationList) {
		this.eventLocationList = eventLocationList;
	}

	public String getEventStartTime() {
		return this.eventStartTime;
	}

	public String getEventEndTime() {
		return this.eventEndTime;
	}

	public String getEventCycleInterval() {
		return this.eventCycleInterval;
	}

	public String getEventName() {
		return this.eventName;
	}

	public String getEventContent() {
		return this.eventContent;
	}

	public String getEventCreateDate() {
		return this.eventCreateDate;
	}

	public String getEventPriority() {
		return this.eventPriority;
	}

	public String getIsEventFinished() {
		return this.isEventFinished;
	}

	public String getIsNotificationOn() {
		return this.isNotificationOn;
	}

	public String getEventLocationList() {
		return this.eventLocationList;
	}

}
