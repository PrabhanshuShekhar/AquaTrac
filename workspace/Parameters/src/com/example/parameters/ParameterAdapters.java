package com.example.parameters;

import java.util.List;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

public class ParameterAdapters extends BaseAdapter {
	private Context context;
	private String [] names = {"Ammonia" , "BOD" , "CP" ,"Cl","water level" ,"ph"};
	private ParseObject [] values = {};
	String length;
	private int [] images = {R.drawable.image_2,R.drawable.image_3,R.drawable.image_4,R.drawable.image_5,R.drawable.image_6,R.drawable.image_7,R.drawable.image_8,R.drawable.image_9,R.drawable.image_10,R.drawable.image_11};
	public ParameterAdapters(Context c) {
		// TODO Auto-generated constructor stub
		this.context=c;
		Parse.initialize(context, "hjYMRHgjBNK6fzcltOMtnmglaDYIQIU3PJfdCMF3", "xgBSMsHThQK5kzLvqSwDznSrpH9Gq8bW7ZYl6YoA");
//		ParseObject test = new ParseObject("Location");
//		test.put("location_name", "Location1");
//		test.put("description", "created for testing only");
//		test.saveInBackground();
		
		
	}
	
	public int getCount()
	{
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParameterValue");
		query.whereEqualTo("BOD", 13.10);
		query.findInBackground(new FindCallback<ParseObject>() {
			  public void done(List<ParseObject> locationList, ParseException e) {
			    // locationList has record 
				  if (e == null) {
			            Log.d("record", "Retrieved " + locationList.size() + " length");
			            values[0] = locationList.get(0);
			            values[1] = locationList.get(1);
			            values[2] = locationList.get(2);
			            values[3] = locationList.get(3);
			            values[4] = locationList.get(4);
			            values[5] = locationList.get(5);
			        } else {
			            Log.d("record", "Error: " + e.getMessage());
			        }
			  }
			});
		
		return names.length;
	}
	
	public long getItemId(int pos)
	{
		return 0;
	}
	
	public Object getItem(int pos)
	{
		return null;
	}
	
	public View getView(int position , View view , ViewGroup parent)
	{
	   TextView name_view,value_view;
	   LinearLayout linear_layout;
	   ImageView arrow_image,param_image;
	   if(view == null)
	   {
		   linear_layout = new LinearLayout(context);
		   linear_layout.setOrientation(LinearLayout.HORIZONTAL);
		   linear_layout.setLayoutParams(new GridView.LayoutParams(200,150));
		   linear_layout.setBackgroundColor(Color.parseColor("#2E64FE"));
           param_image = new ImageView(context);
           param_image.setImageResource(images[position]);
           param_image.setLayoutParams(new LayoutParams(100,150));
           param_image.setPadding(10, 10, 10, 0);
		   
		   value_view = new TextView(context);
		   value_view.setText(values[position].toString());
		   value_view.setTextColor(Color.WHITE);
		   value_view.setPadding(50, 10, 0, 80);
//		   value_view.setLeft(90);
//		   value_view.setTop(5);
		   
		   name_view = new TextView(context);
		   name_view.setText(names[position]);
//		   name_view.setPadding(15, 120,10 , 5);
		   name_view.setLeft(5);
		   name_view.setTop(200);
		   name_view.setTextColor(Color.WHITE);
		   linear_layout.addView(param_image);
		   linear_layout.addView(value_view);
		   linear_layout.addView(name_view);
		   // didgiwgd
	   }
	   else
	   {
		   linear_layout = (LinearLayout) view;
	   }
		
		return linear_layout;
	}
	
	// git@github.com:PrabhanshuShekhar/AquaTrac.git

}
