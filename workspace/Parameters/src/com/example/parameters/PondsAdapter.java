package com.example.parameters;

import java.util.ArrayList;
import java.util.List;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PondsAdapter extends BaseAdapter {
	
	private Context context;
	public static ArrayList<String> location_ids;
	public static ArrayList<String> location_names;
	
//	 Parse.initialize(this, "hjYMRHgjBNK6fzcltOMtnmglaDYIQIU3PJfdCMF3", "xgBSMsHThQK5kzLvqSwDznSrpH9Gq8bW7ZYl6YoA");
	public PondsAdapter(Context c)
	{
		this.context = c;
		location_names = new ArrayList<String>();
		location_ids = new ArrayList<String>();
		// get locations from Location table
	 try
	 {
		 Parse.initialize(context, "hjYMRHgjBNK6fzcltOMtnmglaDYIQIU3PJfdCMF3", "xgBSMsHThQK5kzLvqSwDznSrpH9Gq8bW7ZYl6YoA");
	  ParseQuery<ParseObject> query = ParseQuery.getQuery("Location");
	  List<ParseObject> results = query.find();
	  
	  for(int i=0;i<results.size();i++)
	  {
		  Log.d("Record","========================"+results.get(i).getString("location_name"));
		  location_names.add(results.get(i).getString("location_name"));
		  location_ids.add(results.get(i).getObjectId());
	  }
//	  location_ids.add("");
//	  location_ids.add("");
//	  if(results.size()%2!= 0)
//		  location_names.add("");
	  Log.d("count", "++++++++++++++++++++++"+location_names.size());
	 }catch(Exception e)
	 {
		
	 }
		
		
	}
	
	public int getCount()
	{
		return location_ids.size();
		
	}
	
	public Object getItem(int position)
	{
		return null;
	}
	public long getItemId(int podition)
	{
		return 0;
	}
	
	public View getView(int position,View view, ViewGroup parent)
	{
		RelativeLayout rl;
		TextView textview;
		Log.d("odd_number", "==========position:" +position);
		if(view == null)
		{

			rl = new RelativeLayout(context);
			RelativeLayout rl1 = new RelativeLayout(context);
			rl1.setLayoutParams(new GridView.LayoutParams(330,250));
			if(location_ids.size()%2!= 0 && position == location_ids.size() - 1){
						rl.setLayoutParams(new GridView.LayoutParams(330, 280));
			}else if(location_ids.size()%2 == 0 && position == location_ids.size() -2 || position == location_ids.size() - 1)
			{
				rl.setLayoutParams(new GridView.LayoutParams(330, 280));
			}
			else
			{
				rl.setLayoutParams(new GridView.LayoutParams(330, 250));
			}
			
			
			rl1.setPadding(10, 10, 10, 10); // new line
			rl1.setBackgroundColor(Color.parseColor("#000066"));
			rl1.setBackgroundResource(R.drawable.rounded);
			RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
			lp.addRule(RelativeLayout.CENTER_IN_PARENT);
			textview = new TextView(context);
			textview.setLayoutParams(lp);
			textview.setTextSize(20);
			textview.setTextColor(Color.parseColor("#F2F2F2"));
			textview.setText(location_names.get(position));
			rl1.addView(textview);
			rl.addView(rl1);
		}
		else
		{

			rl = (RelativeLayout) view;
			rl.removeAllViews();
			if(location_ids.size()%2!= 0 && position == location_ids.size() - 1){
				rl.setLayoutParams(new GridView.LayoutParams(320, 280));
	}else if(location_ids.size()%2 == 0 && position == location_ids.size() -2 || position == location_ids.size() - 1)
	{
		rl.setLayoutParams(new GridView.LayoutParams(330, 280));
	}
	else
	{
		rl.setLayoutParams(new GridView.LayoutParams(330, 250));
	}
			RelativeLayout rl1 = new RelativeLayout(context);
			rl1.setLayoutParams(new GridView.LayoutParams(330,250));
			rl1.setBackgroundColor(Color.parseColor("#000066"));
			rl1.setBackgroundResource(R.drawable.rounded);
			RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
			lp.addRule(RelativeLayout.CENTER_IN_PARENT);
			textview = new TextView(context);
			textview.setLayoutParams(lp);
			textview.setTextSize(20);
			textview.setTextColor(Color.parseColor("#F2F2F2"));
			textview.setText(location_names.get(position)); 
			rl1.addView(textview);
			rl.addView(rl1);
		}
		
		return rl;
	}
	
	
	
}
