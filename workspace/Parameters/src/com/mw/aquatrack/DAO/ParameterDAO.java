package com.mw.aquatrack.DAO;

import java.util.List;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ParameterDAO {

	public ParameterDAO(Context context) {
		super();
		Parse.initialize(context, "hjYMRHgjBNK6fzcltOMtnmglaDYIQIU3PJfdCMF3",
				"xgBSMsHThQK5kzLvqSwDznSrpH9Gq8bW7ZYl6YoA");
	}

	public List<ParseObject> getAllParameters() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParameterDetails");

		List<ParseObject> parameterList = null;
		try {
			parameterList = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("size is: " + parameterList.size());
		return parameterList;
	}

	public void updateParameter()
	{}


}