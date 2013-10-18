package com.example.parameters;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.security.auth.callback.Callback;


import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddRecordActivity extends Activity  {
  Intent intent;
  String param_name,location_id,param_value,action_name,create_date;
  static final int GET_PARAMETER_VALUE = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Date date = new Date();
		create_date = getIntent().getStringExtra("create_date");
    	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	Parse.initialize(AddRecordActivity.this, "hjYMRHgjBNK6fzcltOMtnmglaDYIQIU3PJfdCMF3", "xgBSMsHThQK5kzLvqSwDznSrpH9Gq8bW7ZYl6YoA");
		action_name = getIntent().getStringExtra("action_name");
		if(action_name.equals("Record"))
		{
		setContentView(R.layout.record_add);
		}
		else
		{
			setContentView(R.layout.view_record);
		}
		RelativeLayout outer_RL = (RelativeLayout) findViewById(R.id.parameter);
    	location_id = getIntent().getStringExtra("location");
    	
    	try{
    	
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParameterValue");
        query.whereEqualTo("location_objectid", location_id);
        if(create_date!=null)
        {
        	int dd =Integer.parseInt(create_date.split("/")[0]) ;
        	int mm =Integer.parseInt(create_date.split("/")[1]);
        	int yy = Integer.parseInt(create_date.split("/")[2]);
        	Date current_date =new Date();
        	int current_dd = current_date.getDate();
        	int current_mm = current_date.getMonth()+1;
        	int current_yy = Calendar.getInstance().get(Calendar.YEAR);
        	if(yy<=current_yy)
        	{
        	 if(mm <= current_mm)
        	 {  
        		 if(dd<=current_dd){
        			 query.whereEqualTo("create_date",create_date); 
        		 }
        		 else
        		 {
        			 if(yy<current_yy)
        			 {
        				 query.whereEqualTo("create_date",create_date);
        			 }
        			 else
        			 {
        				 if(mm<current_mm)
        				 {
        					 query.whereEqualTo("create_date",create_date);
        				 }
        				 else
        				 {
		        			 Toast.makeText(AddRecordActivity.this, "Future date does not allowed", Toast.LENGTH_SHORT).show();
		        			 return;
        				 }
        			 }
        		 }
        	 }
        	 else
        	 {
        		 if(yy < current_yy)
        		 {
        			 query.whereEqualTo("create_date",create_date);
        		 }
        		 else
        		 {
        			 
        		 }
        	 }
        	}
        	else
        	{
        		Toast.makeText(AddRecordActivity.this, "Future date does not allowed", Toast.LENGTH_SHORT).show();
        		return;
        	}
        	
        }
        else
        {
        	query.whereEqualTo("create_date",df.format(date));
        }
        
      query.findInBackground(new FindCallback<ParseObject>() {
			  public void done(List<ParseObject> objects, ParseException e) {
      		if (e == null && objects.size()>0)
      		{
      			TextView ammonia = (TextView) findViewById(R.id.ammonia_value);
      			if(objects.get(0).getNumber("NH4")!= null)
      			{
      			ammonia.setText(objects.get(0).getNumber("NH4").toString());
      			}
      			Log.d("record","===="+objects.get(0).getNumber("NH4"));
      			TextView biochemical = (TextView) findViewById(R.id.biochemical_value);
      			if(objects.get(0).getNumber("BOD")!= null)
      			{
      			biochemical.setText(objects.get(0).getNumber("BOD").toString());
      			}
      			
      			
      			TextView chloride = (TextView) findViewById(R.id.chloride_value);
      			if(objects.get(0).getNumber("CL")!= null)
      			{
      			chloride.setText(objects.get(0).getNumber("CL").toString());
      			}
      			
      			TextView colorimeter = (TextView)findViewById(R.id.colorimeter_value);
      			if(objects.get(0).getNumber("CP")!= null)
      			{
      			colorimeter.setText(objects.get(0).getNumber("CP").toString());
      			}
      			
      			TextView conductivity = (TextView) findViewById(R.id.conductivity_value);
      			if(objects.get(0).getNumber("CTVT")!= null)
      			{
      			conductivity.setText(objects.get(0).getNumber("CTVT").toString());
      			}
      			
      			TextView dissolved_oxygen = (TextView) findViewById(R.id.dissolved_oxygen_value);
      			if(objects.get(0).getNumber("DO")!= null)
      			{
      			dissolved_oxygen.setText(objects.get(0).getNumber("DO").toString());
      			}
      			
      			TextView  free_chlorine = (TextView)findViewById(R.id.free_chlorine_value);
      			if(objects.get(0).getNumber("FCL")!= null)
      			{
      			free_chlorine.setText(objects.get(0).getNumber("FCL").toString());
      			}
      			
      			TextView nitrate = (TextView) findViewById(R.id.nitrate_value);
      			if(objects.get(0).getNumber("NO3")!= null)
      			{
      			nitrate.setText(objects.get(0).getNumber("NO3").toString());
      			}
      			
      			TextView orp = (TextView) findViewById(R.id.orp_value);
      			if(objects.get(0).getNumber("ORP")!= null)
      			{
      			orp.setText(objects.get(0).getNumber("ORP").toString());
      			}
      			
      			TextView ph = (TextView) findViewById(R.id.ph_value);
      			if(objects.get(0).getNumber("PH")!= null)
      			{
      			ph.setText(objects.get(0).getNumber("PH").toString());
      			}
      		}
      	}
		});
    }
    	catch(Exception e)
    	{
    		
    	}

	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_record, menu);
		return true;
	}
	
	public void calendar(View v)
	{
		Intent intent = new Intent(AddRecordActivity.this,CalendarActivity.class);
		intent.putExtra("location", location_id);
		intent.putExtra("action_name", action_name);
		startActivity(intent);
	}
	
	public void clicker(View v)
	{
		switch(v.getId())
		{
		case R.id.ammonia:
//			Toast.makeText(this,"id:"+"water_level", Toast.LENGTH_SHORT).show();
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "NH4");
			if(create_date!=null)
			{intent.putExtra("create_date", create_date);}
			intent.putExtra("location", location_id);
			intent.putExtra("id", 1);
			startActivity(intent);
			break;
			
		case R.id.biochemical:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "BOD");
			if(create_date!=null)
			{intent.putExtra("create_date", create_date);}
			intent.putExtra("location", location_id);
			intent.putExtra("id", 2);
			startActivity(intent);
			 	break;
		case R.id.chloride:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "CL");
			if(create_date!=null)
			{intent.putExtra("create_date", create_date);}
			intent.putExtra("location", location_id);
			intent.putExtra("id", 3);
			startActivity(intent);
		 	break;	
		case R.id.colorimeter:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "CP");
			if(create_date!=null)
			{intent.putExtra("create_date", create_date);}
			intent.putExtra("location", location_id);
			intent.putExtra("id",4);
			startActivity(intent);
		 	break;
		case R.id.conductivity:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "CTVT");
			if(create_date!=null)
			{intent.putExtra("create_date", create_date);}
			intent.putExtra("location", location_id);
			intent.putExtra("id", 5);
			startActivity(intent);
		 	break;
		case R.id.dissolved_oxygen:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "DO");
			if(create_date!=null)
			{intent.putExtra("create_date", create_date);}
			intent.putExtra("location", location_id);
			intent.putExtra("id", 6);
			startActivity(intent);
		 	break;
		case R.id.free_chlorine:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "FCL");
			if(create_date!=null)
			{intent.putExtra("create_date", create_date);}
			intent.putExtra("location", location_id);
			intent.putExtra("id", 7);
			startActivity(intent);
		 	break;
		case R.id.nitrate:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "NO3");
			if(create_date!=null)
			{intent.putExtra("create_date", create_date);}
			intent.putExtra("location", location_id);
			intent.putExtra("id", 8);
			startActivity(intent);
		 	break;
		case R.id.orp:
			intent = new Intent(this, AddParameterValueActivity.class);
		    intent.putExtra("param_name", "ORP");
		    if(create_date!=null)
			{intent.putExtra("create_date", create_date);}
		    intent.putExtra("location", location_id);
		    intent.putExtra("id", 9);
		    startActivity(intent);
		 	break;
		case R.id.ph:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "PH");
			if(create_date!=null)
			{intent.putExtra("create_date", create_date);}
			intent.putExtra("location", location_id);
			intent.putExtra("id", 10);
			startActivity(intent);
		 	break;
		}
		
	}
  
}