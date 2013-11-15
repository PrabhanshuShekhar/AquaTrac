package com.mw.aquatrack.DAO;

import java.util.List;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class LocationsDAO {
	ParseQuery<ParseObject> query;
	ParseQuery<ParseObject> query2;
Context context;
ValuesDAO valuesDAO;
	// Constructor
	public LocationsDAO(Context context) {
		super();
		this.context = context;
		Parse.initialize(context, "hjYMRHgjBNK6fzcltOMtnmglaDYIQIU3PJfdCMF3",
				"xgBSMsHThQK5kzLvqSwDznSrpH9Gq8bW7ZYl6YoA");
		query = ParseQuery.getQuery("Location");
		query2 = ParseQuery.getQuery("ParameterValue");
	}

	// get
	public List<ParseObject> getAllLocations() {

		List<ParseObject> locationList = null;
		try {
			locationList = query.find();
		} catch (ParseException e) {
			System.out.println("cant find locations");
			e.printStackTrace();
		}
//		System.out.println("locations size is: " + locationList.size());
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
		ParseObject locationToBeDeleted = getLocationById(objectId);
		try {
			if(locationToBeDeleted!=null){
			valuesDAO = new ValuesDAO(context);
			valuesDAO.deleteByID(locationToBeDeleted.getObjectId());
			locationToBeDeleted.delete();
			}
			return locationToBeDeleted;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ParseObject updateLocation(String objectId, String name,
			String description) {
		System.out.println("updating location");
		ParseObject locationToBeUpdated = getLocationById(objectId);
		if(locationToBeUpdated != null)
			{locationToBeUpdated.put("location_name", name);
		locationToBeUpdated.put("description", description);
		try {
			locationToBeUpdated.save();
		} catch (ParseException e) {
			e.printStackTrace();
		}}
		return locationToBeUpdated;
	}

}