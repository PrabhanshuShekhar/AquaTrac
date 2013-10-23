package com.mw.aquatrack.DAO;

import java.util.List;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ParameterDAO {
	ParseQuery<ParseObject> query;

	public ParameterDAO(Context context) {
		super();
		Parse.initialize(context, "hjYMRHgjBNK6fzcltOMtnmglaDYIQIU3PJfdCMF3",
				"xgBSMsHThQK5kzLvqSwDznSrpH9Gq8bW7ZYl6YoA");
		query = ParseQuery.getQuery("ParameterDetails");
	}

	public List<ParseObject> getAllParameters() {
		List<ParseObject> parameterList = null;
		try {
			// find can be replaced ny findInBackground
			parameterList = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("parameters size is: " + parameterList.size());
		return parameterList;
	}

	public ParseObject getParameterById(String objectId) {
		try {
			return query.get(objectId);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ParseObject updateParameter(String objectId, int criticalStartRange,
			int criticalEndRange) {
		System.out.println("updating parameter");
		ParseObject object = getParameterById(objectId);
		object.put("criticalStartRange", criticalStartRange);
		object.put("criticalEndRange", criticalEndRange);
		object.saveInBackground();
		System.out.println("complete updation of parameter");

		// return is wrong if unable to save in DB
		return object;
		// System.out.println("check " + object.getNumber("criticalEndRange"));
	}

}