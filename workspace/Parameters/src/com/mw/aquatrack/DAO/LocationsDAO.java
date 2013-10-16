package com.mw.aquatrack.DAO;

import java.util.List;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class LocationsDAO {

	public LocationsDAO(Context context) {
		super();
		Parse.initialize(context, "hjYMRHgjBNK6fzcltOMtnmglaDYIQIU3PJfdCMF3",
				"xgBSMsHThQK5kzLvqSwDznSrpH9Gq8bW7ZYl6YoA");
	}

	public List<ParseObject> getAllLocations() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Location");

		List<ParseObject> locationList = null;
		try {
			locationList = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("size is: " + locationList.size());
		return locationList;
	}

	public void addLocation(String locationName, String description) {
		ParseObject location = new ParseObject("Location");
		location.put("location_name", locationName);
		location.put("description", description);
		location.saveInBackground();
		System.out.println("added");
	}
	
	public void updateLocation()
	{}
}