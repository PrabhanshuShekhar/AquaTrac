package com.mw.aquatrack.DAO;

import java.util.Date;
import java.util.List;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ValuesDAO {
	ParseQuery<ParseObject> query;

	public ValuesDAO(Context context) {
		super();
		Parse.initialize(context, "hjYMRHgjBNK6fzcltOMtnmglaDYIQIU3PJfdCMF3",
				"xgBSMsHThQK5kzLvqSwDznSrpH9Gq8bW7ZYl6YoA");
		query = ParseQuery.getQuery("ParameterValue");
	}
	
	public List<ParseObject> getDataForReports(String locationObjectId, Date date)
	{
//		Object o=null;
		System.out.println(locationObjectId);
		query.whereEqualTo("location_objectid", locationObjectId);
		query.whereGreaterThan("date", date);
		query.orderByAscending("date");
		try {
			System.out.println("try");
			return query.find();
		} catch (ParseException e) {
			System.out.println("exception");
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ParseObject> getDataByID(String locationObjectId)
	{
		query.whereEqualTo("location_objectid", locationObjectId);
		
		try {
			return query.find();
		} catch (ParseException e) {
			System.out.println("exception ValueDAO getDataByID");
			e.printStackTrace();
			return null;
		}
	}
	
	public void deleteByID(String locationObjectId)
	{
		List<ParseObject> valuesToBeDeleted = (List<ParseObject>)getDataByID(locationObjectId);
		if(valuesToBeDeleted!= null)
		for(int i=0;i<valuesToBeDeleted.size();i++)
		{
			try{
			valuesToBeDeleted.get(i).delete();
			System.out.println("deleted  " + i);
			}catch (ParseException e) {
				e.printStackTrace();
				}
		}
		
	}
	
	
}