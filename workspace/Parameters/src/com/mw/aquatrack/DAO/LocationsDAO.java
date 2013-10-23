package com.mw.aquatrack.DAO;

import java.util.List;
import android.content.Context;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class LocationsDAO {
	ParseQuery<ParseObject> query;

	// Constructor
	public LocationsDAO(Context context) {
		super();
		Parse.initialize(context, "hjYMRHgjBNK6fzcltOMtnmglaDYIQIU3PJfdCMF3",
				"xgBSMsHThQK5kzLvqSwDznSrpH9Gq8bW7ZYl6YoA");
		query = ParseQuery.getQuery("Location");
	}

	// get
	public List<ParseObject> getAllLocations() {

		List<ParseObject> locationList = null;
		try {
			locationList = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("locations size is: " + locationList.size());
		return locationList;
	}

	// get
	public ParseObject getLocationById(String objectId) {
		try {
			return query.get(objectId);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// get
	public ParseObject getLastAddedLocation() {
		query.orderByDescending("createdAt");
		try {
			return query.getFirst();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	public ParseObject addLocation(String locationName, String description) {
		ParseObject location = new ParseObject("Location");
		location.put("location_name", locationName);
		location.put("description", description);
		try {
			location.save();
			System.out.println("added");
			return getLastAddedLocation();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ParseObject deleteLocation(String objectId) {
		ParseObject object = getLocationById(objectId);
		try {
			object.delete();
			return object;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ParseObject updateLocation(String objectId, String name,
			String description) {
		System.out.println("updating location");
		ParseObject object = getLocationById(objectId);
		object.put("location_name", name);
		object.put("description", description);
		try {
			object.save();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return object;
	}

}