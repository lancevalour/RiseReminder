package yicheng.android.app.rise.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

	private final static int DATABASE_VERSION = 1;
	private final static String DATABASE_NAME = "riseDatabase.db";

	public final static String TABLE_PLACE = "placeTable";
	public final static String TABLE_EVENT = "eventTable";

	private final String KEY_PRIMARY_ID = "primary_id";

	private final String KEY_TABLE_PLACE_NAME = "place_name";
	private final String KEY_TABLE_PLACE_ADDRESS = "place_address";
	private final String KEY_TABLE_PLACE_ID = "place_id";
	private final String KEY_TABLE_PLACE_LATITUDE = "place_latitude";
	private final String KEY_TABLE_PLACE_LONGITUDE = "place_longitude";
	private final String KEY_TABLE_PLACE_TYPES = "place_types";

	private final String KEY_TABLE_EVENT_NAME = "event_name";
	private final String KEY_TABLE_EVENT_CONTENT = "event_content";
	private final String KEY_TABLE_EVENT_CREATE_DATE = "event_create_date";
	private final String KEY_TABLE_EVENT_PRIORITY = "event_priority";
	private final String KEY_TABLE_EVENT_START_TIME = "event_start_time";
	private final String KEY_TABLE_EVENT_END_TIME = "event_end_time";
	private final String KEY_TABLE_EVENT_CYCLE_INTERVAL = "event_cycle_interval";
	private final String KEY_TABLE_EVENT_IS_EVENT_FINISHED = "is_event_finished";
	private final String KEY_TABLE_EVENT_IS_NOTIFICATION_ON = "is_notification_on";
	private final String KEY_TABLE_EVENT_LOCATION_LIST = "event_location_list";

	private String tableName;

	public SQLiteHelper(Context context, String tableName) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		this.tableName = tableName;

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_TABLE;
		/*		switch (tableName) {
				case TABLE_PLACE: {
					CREATE_TABLE = "CREATE TABLE " + this.tableName + "("
							+ KEY_PRIMARY_ID + " INTEGER PRIMARY KEY,"
							+ KEY_TABLE_PLACE_NAME + " TEXT," + KEY_TABLE_PLACE_ADDRESS
							+ " TEXT," + KEY_TABLE_PLACE_ID + " TEXT,"
							+ KEY_TABLE_PLACE_LATITUDE + " TEXT,"
							+ KEY_TABLE_PLACE_LONGITUDE + " TEXT,"
							+ KEY_TABLE_PLACE_TYPES + " TEXT" + ")";
					db.execSQL(CREATE_TABLE);

				}
					break;
				case TABLE_EVENT: {
					CREATE_TABLE = "CREATE TABLE " + this.tableName + "("
							+ KEY_PRIMARY_ID + " INTEGER PRIMARY KEY,"
							+ KEY_TABLE_EVENT_NAME + " TEXT," + KEY_TABLE_EVENT_CONTENT
							+ " TEXT," + KEY_TABLE_EVENT_CREATE_DATE + " TEXT,"
							+ KEY_TABLE_EVENT_PRIORITY + " TEXT,"
							+ KEY_TABLE_EVENT_START_TIME + " TEXT,"
							+ KEY_TABLE_EVENT_END_TIME + " TEXT,"
							+ KEY_TABLE_EVENT_CYCLE_INTERVAL + " TEXT,"
							+ KEY_TABLE_EVENT_IS_EVENT_FINISHED + " TEXT,"
							+ KEY_TABLE_EVENT_IS_NOTIFICATION_ON + " TEXT,"
							+ KEY_TABLE_EVENT_LOCATION_LIST + " TEXT" + ")";
					db.execSQL(CREATE_TABLE);

				}
					break;
				}*/

		CREATE_TABLE = "CREATE TABLE " + TABLE_PLACE + "(" + KEY_PRIMARY_ID
				+ " INTEGER PRIMARY KEY," + KEY_TABLE_PLACE_NAME + " TEXT,"
				+ KEY_TABLE_PLACE_ADDRESS + " TEXT," + KEY_TABLE_PLACE_ID
				+ " TEXT," + KEY_TABLE_PLACE_LATITUDE + " TEXT,"
				+ KEY_TABLE_PLACE_LONGITUDE + " TEXT," + KEY_TABLE_PLACE_TYPES
				+ " TEXT" + ")";
		db.execSQL(CREATE_TABLE);

		CREATE_TABLE = "CREATE TABLE " + TABLE_EVENT + "(" + KEY_PRIMARY_ID
				+ " INTEGER PRIMARY KEY," + KEY_TABLE_EVENT_NAME + " TEXT,"
				+ KEY_TABLE_EVENT_CONTENT + " TEXT,"
				+ KEY_TABLE_EVENT_CREATE_DATE + " TEXT,"
				+ KEY_TABLE_EVENT_PRIORITY + " TEXT,"
				+ KEY_TABLE_EVENT_START_TIME + " TEXT,"
				+ KEY_TABLE_EVENT_END_TIME + " TEXT,"
				+ KEY_TABLE_EVENT_CYCLE_INTERVAL + " TEXT,"
				+ KEY_TABLE_EVENT_IS_EVENT_FINISHED + " TEXT,"
				+ KEY_TABLE_EVENT_IS_NOTIFICATION_ON + " TEXT,"
				+ KEY_TABLE_EVENT_LOCATION_LIST + " TEXT" + ")";
		db.execSQL(CREATE_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + this.tableName);

		// Create tables again
		onCreate(db);
	}

	public void addPlace(RisePlace place) {
		if (!this.tableName.equals(TABLE_PLACE)) {
			throw new UnsupportedOperationException();
		}
		else {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(this.KEY_TABLE_PLACE_NAME, place.getPlaceName());
			values.put(this.KEY_TABLE_PLACE_ADDRESS, place.getPlaceAddress());
			values.put(this.KEY_TABLE_PLACE_ID, place.getPlaceID());
			values.put(this.KEY_TABLE_PLACE_LATITUDE, place.getPlaceLatitude());
			values.put(this.KEY_TABLE_PLACE_LONGITUDE,
					place.getPlaceLongitude());
			values.put(this.KEY_TABLE_PLACE_TYPES, place.getPlaceTypes());

			db.insert(this.tableName, null, values);
			db.close();
		}
	}

	public void addEvent(RiseEvent event) {
		if (!this.tableName.equals(TABLE_EVENT)) {
			throw new UnsupportedOperationException();
		}
		else {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(this.KEY_TABLE_EVENT_NAME, event.getEventName());
			values.put(this.KEY_TABLE_EVENT_CONTENT, event.getEventContent());
			values.put(this.KEY_TABLE_EVENT_CREATE_DATE,
					event.getEventCreateDate());
			values.put(this.KEY_TABLE_EVENT_PRIORITY, event.getEventPriority());
			values.put(this.KEY_TABLE_EVENT_START_TIME,
					event.getEventStartTime());
			values.put(this.KEY_TABLE_EVENT_END_TIME, event.getEventEndTime());
			values.put(this.KEY_TABLE_EVENT_CYCLE_INTERVAL,
					event.getEventCycleInterval());
			values.put(this.KEY_TABLE_EVENT_IS_EVENT_FINISHED,
					event.getIsEventFinished());
			values.put(this.KEY_TABLE_EVENT_IS_NOTIFICATION_ON,
					event.getIsNotificationOn());
			values.put(this.KEY_TABLE_EVENT_LOCATION_LIST,
					event.getEventLocationList());

			db.insert(this.tableName, null, values);
			db.close();
		}
	}

	public RisePlace getPlaceByName(String placeName) {
		if (!this.tableName.equals(TABLE_PLACE)) {
			throw new UnsupportedOperationException();
		}
		else {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.query(this.tableName, new String[] {
					KEY_PRIMARY_ID, KEY_TABLE_PLACE_NAME,
					KEY_TABLE_PLACE_ADDRESS, KEY_TABLE_PLACE_ID,
					KEY_TABLE_PLACE_LATITUDE, KEY_TABLE_PLACE_LONGITUDE,
					KEY_TABLE_PLACE_TYPES }, KEY_TABLE_PLACE_NAME + "=?",
					new String[] { placeName }, null, null, null, null);
			if (cursor != null) {
				if (!cursor.moveToFirst()) {
					return null;
				}
			}

			RisePlace place = new RisePlace(cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4), cursor.getString(5),
					cursor.getString(6));

			return place;
		}
	}

	public String getPlacePrimaryIDByName(String placeName) {
		String id;
		if (!this.tableName.equals(TABLE_PLACE)) {
			throw new UnsupportedOperationException();
		}
		else {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.query(this.tableName, new String[] {
					KEY_PRIMARY_ID, KEY_TABLE_PLACE_NAME,
					KEY_TABLE_PLACE_ADDRESS, KEY_TABLE_PLACE_ID,
					KEY_TABLE_PLACE_LATITUDE, KEY_TABLE_PLACE_LONGITUDE,
					KEY_TABLE_PLACE_TYPES }, KEY_TABLE_PLACE_NAME + "=?",
					new String[] { placeName }, null, null, null, null);
			if (cursor != null) {
				if (!cursor.moveToFirst()) {
					return null;
				}
			}

			id = cursor.getString(0);

			return id;
		}
	}

	public RisePlace getPlaceByPrimaryKeyID(int id) {
		if (!this.tableName.equals(TABLE_PLACE)) {
			throw new UnsupportedOperationException();
		}
		else {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db
					.query(this.tableName, new String[] { KEY_PRIMARY_ID,
							KEY_TABLE_PLACE_NAME, KEY_TABLE_PLACE_ADDRESS,
							KEY_TABLE_PLACE_ID, KEY_TABLE_PLACE_LATITUDE,
							KEY_TABLE_PLACE_LONGITUDE, KEY_TABLE_PLACE_TYPES },
							KEY_PRIMARY_ID + "=?",
							new String[] { String.valueOf(id) }, null, null,
							null, null);
			if (cursor != null) {
				if (!cursor.moveToFirst()) {
					return null;
				}
			}
			RisePlace place = new RisePlace(cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4), cursor.getString(5),
					cursor.getString(6));

			return place;
		}

	}

	public String getEventPrimaryIDByName(String eventName) {
		String id;
		if (!this.tableName.equals(TABLE_EVENT)) {
			throw new UnsupportedOperationException();
		}
		else {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.query(this.tableName, new String[] {
					KEY_PRIMARY_ID, KEY_TABLE_EVENT_NAME,
					KEY_TABLE_EVENT_CONTENT, KEY_TABLE_EVENT_CREATE_DATE,
					KEY_TABLE_EVENT_PRIORITY, KEY_TABLE_EVENT_START_TIME,
					KEY_TABLE_EVENT_END_TIME, KEY_TABLE_EVENT_CYCLE_INTERVAL,
					KEY_TABLE_EVENT_IS_EVENT_FINISHED,
					KEY_TABLE_EVENT_IS_NOTIFICATION_ON,
					KEY_TABLE_EVENT_LOCATION_LIST }, KEY_TABLE_EVENT_NAME
					+ "=?", new String[] { eventName }, null, null, null, null);
			if (cursor != null) {
				if (!cursor.moveToFirst()) {
					return null;
				}
			}

			id = cursor.getString(0);
			return id;
		}
	}

	public RiseEvent getEventByName(String eventName) {
		if (!this.tableName.equals(TABLE_EVENT)) {
			throw new UnsupportedOperationException();
		}
		else {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.query(this.tableName, new String[] {
					KEY_PRIMARY_ID, KEY_TABLE_EVENT_NAME,
					KEY_TABLE_EVENT_CONTENT, KEY_TABLE_EVENT_CREATE_DATE,
					KEY_TABLE_EVENT_PRIORITY, KEY_TABLE_EVENT_START_TIME,
					KEY_TABLE_EVENT_END_TIME, KEY_TABLE_EVENT_CYCLE_INTERVAL,
					KEY_TABLE_EVENT_IS_EVENT_FINISHED,
					KEY_TABLE_EVENT_IS_NOTIFICATION_ON,
					KEY_TABLE_EVENT_LOCATION_LIST }, KEY_TABLE_EVENT_NAME
					+ "=?", new String[] { eventName }, null, null, null, null);
			if (cursor != null) {
				if (!cursor.moveToFirst()) {
					return null;
				}
			}
			RiseEvent event = new RiseEvent(cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4), cursor.getString(5),
					cursor.getString(6), cursor.getString(7),
					cursor.getString(8), cursor.getString(9),
					cursor.getString(10));
			return event;
		}
	}

	public RiseEvent getEventByPrimaryKeyID(int id) {
		if (!this.tableName.equals(TABLE_EVENT)) {
			throw new UnsupportedOperationException();
		}
		else {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db
					.query(this.tableName, new String[] { KEY_PRIMARY_ID,
							KEY_TABLE_EVENT_NAME, KEY_TABLE_EVENT_CONTENT,
							KEY_TABLE_EVENT_CREATE_DATE,
							KEY_TABLE_EVENT_PRIORITY,
							KEY_TABLE_EVENT_START_TIME,
							KEY_TABLE_EVENT_END_TIME,
							KEY_TABLE_EVENT_CYCLE_INTERVAL,
							KEY_TABLE_EVENT_IS_EVENT_FINISHED,
							KEY_TABLE_EVENT_IS_NOTIFICATION_ON,
							KEY_TABLE_EVENT_LOCATION_LIST }, KEY_PRIMARY_ID
							+ "=?", new String[] { String.valueOf(id) }, null,
							null, null, null);
			if (cursor != null) {
				if (!cursor.moveToFirst()) {
					return null;
				}
			}
			RiseEvent event = new RiseEvent(cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4), cursor.getString(5),
					cursor.getString(6), cursor.getString(7),
					cursor.getString(8), cursor.getString(9),
					cursor.getString(10));

			return event;
		}
	}

	public List<RisePlace> getAllPlaces() {
		if (!this.tableName.equals(TABLE_PLACE)) {
			throw new UnsupportedOperationException();
		}
		else {
			List<RisePlace> placeList = new ArrayList<RisePlace>();

			String selectQuery = "SELECT  * FROM " + this.tableName;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				do {

					placeList.add(new RisePlace(cursor.getString(1), cursor
							.getString(2), cursor.getString(3), cursor
							.getString(4), cursor.getString(5), cursor
							.getString(6)));
				}
				while (cursor.moveToNext());
			}

			return placeList;
		}

	}

	public List<RiseEvent> getAllEvents() {
		if (!this.tableName.equals(TABLE_EVENT)) {
			throw new UnsupportedOperationException();
		}
		else {
			List<RiseEvent> eventList = new ArrayList<RiseEvent>();

			String selectQuery = "SELECT  * FROM " + this.tableName;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				do {

					eventList.add(new RiseEvent(cursor.getString(1), cursor
							.getString(2), cursor.getString(3), cursor
							.getString(4), cursor.getString(5), cursor
							.getString(6), cursor.getString(7), cursor
							.getString(8), cursor.getString(9), cursor
							.getString(10)));
				}
				while (cursor.moveToNext());
			}

			return eventList;
		}
	}

	public int updatePlaceByID(int id, RisePlace place) {
		if (!this.tableName.equals(TABLE_PLACE)) {
			throw new UnsupportedOperationException();
		}
		else {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(this.KEY_TABLE_PLACE_NAME, place.getPlaceName());
			values.put(this.KEY_TABLE_PLACE_ADDRESS, place.getPlaceAddress());
			values.put(this.KEY_TABLE_PLACE_ID, place.getPlaceID());
			values.put(this.KEY_TABLE_PLACE_LATITUDE, place.getPlaceLatitude());
			values.put(this.KEY_TABLE_PLACE_LONGITUDE,
					place.getPlaceLongitude());
			values.put(this.KEY_TABLE_PLACE_TYPES, place.getPlaceTypes());

			// updating row
			return db.update(this.tableName, values, KEY_PRIMARY_ID + " = ?",
					new String[] { String.valueOf(id) });
		}

	}

	public int updatePlaceByName(String name, RisePlace place) {
		if (!this.tableName.equals(TABLE_PLACE)) {
			throw new UnsupportedOperationException();
		}
		else {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(this.KEY_TABLE_PLACE_NAME, place.getPlaceName());
			values.put(this.KEY_TABLE_PLACE_ADDRESS, place.getPlaceAddress());
			values.put(this.KEY_TABLE_PLACE_ID, place.getPlaceID());
			values.put(this.KEY_TABLE_PLACE_LATITUDE, place.getPlaceLatitude());
			values.put(this.KEY_TABLE_PLACE_LONGITUDE,
					place.getPlaceLongitude());
			values.put(this.KEY_TABLE_PLACE_TYPES, place.getPlaceTypes());

			// updating row
			return db.update(this.tableName, values, KEY_TABLE_PLACE_NAME
					+ " = ?", new String[] { name });
		}
	}

	public int updateEventByID(int id, RiseEvent event) {
		if (!this.tableName.equals(TABLE_EVENT)) {
			throw new UnsupportedOperationException();
		}
		else {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(this.KEY_TABLE_EVENT_NAME, event.getEventName());
			values.put(this.KEY_TABLE_EVENT_CONTENT, event.getEventContent());
			values.put(this.KEY_TABLE_EVENT_CREATE_DATE,
					event.getEventCreateDate());
			values.put(this.KEY_TABLE_EVENT_PRIORITY, event.getEventPriority());
			values.put(this.KEY_TABLE_EVENT_START_TIME,
					event.getEventStartTime());
			values.put(this.KEY_TABLE_EVENT_END_TIME, event.getEventEndTime());
			values.put(this.KEY_TABLE_EVENT_CYCLE_INTERVAL,
					event.getEventCycleInterval());
			values.put(this.KEY_TABLE_EVENT_IS_EVENT_FINISHED,
					event.getIsEventFinished());
			values.put(this.KEY_TABLE_EVENT_IS_NOTIFICATION_ON,
					event.getIsNotificationOn());
			values.put(this.KEY_TABLE_EVENT_LOCATION_LIST,
					event.getEventLocationList());

			// updating row
			return db.update(this.tableName, values, KEY_PRIMARY_ID + " = ?",
					new String[] { String.valueOf(id) });
		}
	}

	public int updateEventByName(String name, RiseEvent event) {
		if (!this.tableName.equals(TABLE_EVENT)) {
			throw new UnsupportedOperationException();
		}
		else {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(this.KEY_TABLE_EVENT_NAME, event.getEventName());
			values.put(this.KEY_TABLE_EVENT_CONTENT, event.getEventContent());
			values.put(this.KEY_TABLE_EVENT_CREATE_DATE,
					event.getEventCreateDate());
			values.put(this.KEY_TABLE_EVENT_PRIORITY, event.getEventPriority());
			values.put(this.KEY_TABLE_EVENT_START_TIME,
					event.getEventStartTime());
			values.put(this.KEY_TABLE_EVENT_END_TIME, event.getEventEndTime());
			values.put(this.KEY_TABLE_EVENT_CYCLE_INTERVAL,
					event.getEventCycleInterval());
			values.put(this.KEY_TABLE_EVENT_IS_EVENT_FINISHED,
					event.getIsEventFinished());
			values.put(this.KEY_TABLE_EVENT_IS_NOTIFICATION_ON,
					event.getIsNotificationOn());
			values.put(this.KEY_TABLE_EVENT_LOCATION_LIST,
					event.getEventLocationList());

			// updating row
			return db.update(this.tableName, values, KEY_TABLE_EVENT_NAME
					+ " = ?", new String[] { name });
		}
	}

	public void deletePlaceById(int id) {

		if (!this.tableName.equals(TABLE_PLACE)) {
			throw new UnsupportedOperationException();
		}
		else {
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(this.tableName, KEY_PRIMARY_ID + " = ?",
					new String[] { String.valueOf(id) });
			db.close();
		}

	}

	public void deletePlaceByName(String name) {
		if (!this.tableName.equals(TABLE_PLACE)) {
			throw new UnsupportedOperationException();
		}
		else {
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(this.tableName, KEY_TABLE_PLACE_NAME + " = ?",
					new String[] { name });
			db.close();
		}

	}

	public void deleteEventById(int id) {
		if (!this.tableName.equals(TABLE_EVENT)) {
			throw new UnsupportedOperationException();
		}
		else {
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(this.tableName, KEY_PRIMARY_ID + " = ?",
					new String[] { String.valueOf(id) });
			db.close();
		}
	}

	public void deleteEventByName(String name) {
		if (!this.tableName.equals(TABLE_EVENT)) {
			throw new UnsupportedOperationException();
		}
		else {
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(this.tableName, KEY_TABLE_EVENT_NAME + " = ?",
					new String[] { name });
			db.close();
		}

	}

	public int getPlacesCount() {
		if (!this.tableName.equals(TABLE_PLACE)) {
			throw new UnsupportedOperationException();
		}
		else {
			String countQuery = "SELECT  * FROM " + this.tableName;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(countQuery, null);
			// cursor.close();

			// return count
			return cursor.getCount();
		}

	}

	public int getEventsCount() {
		if (!this.tableName.equals(TABLE_EVENT)) {
			throw new UnsupportedOperationException();
		}
		else {
			String countQuery = "SELECT  * FROM " + this.tableName;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(countQuery, null);
			// cursor.close();

			// return count
			return cursor.getCount();
		}
	}

}
