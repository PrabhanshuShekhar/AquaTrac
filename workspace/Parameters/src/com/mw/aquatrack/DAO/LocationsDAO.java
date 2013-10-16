package com.mw.aquatrack.DAO;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class LocationsDAO {

	final List<String> locations = new ArrayList<String>();

	public LocationsDAO(Context context) {
		super();
		Parse.initialize(context, "hjYMRHgjBNK6fzcltOMtnmglaDYIQIU3PJfdCMF3",
				"xgBSMsHThQK5kzLvqSwDznSrpH9Gq8bW7ZYl6YoA");

//		 ParseQuery<ParseObject> query = ParseQuery.getQuery("Location");
//		 query.findInBackground(new FindCallback<ParseObject>() {
//		
//		 @Override
//		 public void done(List<ParseObject> objects, ParseException e) {
//		 for (int i = 0; i < objects.size(); i++) {
//		 System.out.println(objects.get(i)
//		 .getString("location_name"));
//		 System.out.println(
//		 locations.add(objects.get(i).getString("location_name")) );
//		 }
//		 }
//		 });

	}

	public void getAllLocations() {
		 ParseQuery<ParseObject> query = ParseQuery.getQuery("Location");
		 query.findInBackground(new FindCallback<ParseObject>() {
		
		 @Override
		 public void done(List<ParseObject> objects, ParseException e) {
		 for (int i = 0; i < objects.size(); i++) {
		 System.out.println(objects.get(i)
		 .getString("location_name"));
		 System.out.println(
		 locations.add(objects.get(i).getString("location_name")) );
		 }
		 }
		 });
	
	}
	
//	public void getAllLocations() {
//		final ParseQuery<ParseObject> query = ParseQuery.getQuery("Location");
//		Thread thread = new Thread() {
//
//			@Override
//			public void run() {
//
//				super.run();
//				System.out.println("run2");
//				query.findInBackground(new FindCallback<ParseObject>() {
//
//					@Override
//					public void done(List<ParseObject> objects, ParseException e) {
//						for (int i = 0; i < objects.size(); i++) {
//							System.out.println(objects.get(i).getString(
//									"location_name"));
//							System.out.println(locations.add(objects.get(i)
//									.getString("location_name")));
//						}
//					}
//				}); // end of findInBackground
//				try {
//					this.join();
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
////				 try {
////				 synchronized (this) {
////					 this.wait(5000);
////					
////				}
////				 } catch (InterruptedException e1) {
////				 // TODO Auto-generated catch block
////				 e1.printStackTrace();
////				 }
////				boolean temp = true;
////				while (temp) {
////					if (!(this.isAlive())) {
////						temp = false;
////					}
////
////				}
//			} // end of run
//		};
//		thread.start();
////		try {
////			thread.join();
////		} catch (InterruptedException e) {
////			e.printStackTrace();
////		}
//		// thread.join();
//		System.out.println("locations.size()   " + locations.size());
//	}
	
	public void addLocation(String locationName, String description)
	{
		System.out.println("adding");
		ParseObject location = new ParseObject("Location");
		location.put("location_name", locationName);
		location.put("description", description);
		location.saveInBackground();
		System.out.println("added");
	}
}
