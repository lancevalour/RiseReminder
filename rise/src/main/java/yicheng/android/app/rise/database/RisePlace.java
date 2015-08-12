package yicheng.android.app.rise.database;

import android.os.Parcel;
import android.os.Parcelable;

public class RisePlace  {
	String placeName;
	String placeAddress;
	String placeID;
	String placeLatitude;
	String placeLongitude;
	String placeTypes;

	public RisePlace(String placeName, String placeAddress, String placeID,
			String placeLatitude, String placeLongitude, String placeTypes) {
		this.placeName = placeName;
		this.placeAddress = placeAddress;
		this.placeID = placeID;
		this.placeLatitude = placeLatitude;
		this.placeLongitude = placeLongitude;
		this.placeTypes = placeTypes;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public void setPlaceAddress(String placeAddress) {
		this.placeAddress = placeAddress;
	}

	public void setPlaceID(String placeID) {
		this.placeID = placeID;
	}

	public void setPlaceLatitude(String placeLatitude) {
		this.placeLatitude = placeLatitude;
	}

	public void setPlaceLongitude(String placeLongitude) {
		this.placeLongitude = placeLongitude;
	}

	public void setPlaceTypes(String placeTypes) {
		this.placeTypes = placeTypes;
	}

	public String getPlaceName() {
		return this.placeName;
	}

	public String getPlaceAddress() {
		return this.placeAddress;
	}

	public String getPlaceID() {
		return this.placeID;
	}

	public String getPlaceLatitude() {
		return this.placeLatitude;
	}

	public String getPlaceLongitude() {
		return this.placeLongitude;
	}

	public String getPlaceTypes() {
		return this.placeTypes;
	}


}
