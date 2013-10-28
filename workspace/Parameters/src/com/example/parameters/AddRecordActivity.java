package com.example.parameters;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if(create_date!=null)
        {
        	TextView date_text = (TextView) findViewById(R.id.date_text);
        	date_text.setText(create_date);
        	int dd =Integer.parseInt(create_date.split("/")[0]) ;
        	int mm =Integer.parseInt(create_date.split("/")[1]);
        	int yy = Integer.parseInt(create_date.split("/")[2]);
        	Date current_date =new Date();
        	int current_dd = current_date.getDate();
        	int current_mm = current_date.getMonth()+1;
        	int current_yy = Calendar.getInstance().get(Calendar.YEAR);
        	int tomorrow_date = dd+2;
        	if(yy<=current_yy)
        	{
        	 if(mm <= current_mm)
        	 {  
        		 if(dd<=current_dd){
//        			 query.whereEqualTo("create_date",create_date); 
        			 query.whereGreaterThanOrEqualTo("date", formatter.parse(create_date));
        			 Toast.makeText(AddRecordActivity.this, "hi", Toast.LENGTH_SHORT).show();
        			 query.whereLessThanOrEqualTo("date", formatter.parse(tomorrow_date+"/"+mm+"/"+yy));
        		 }
        		 else
        		 {
        			 if(yy<current_yy)
        			 {
//        				 query.whereEqualTo("create_date",create_date);
        				 query.whereGreaterThanOrEqualTo("date", formatter.parse(create_date));
            			 query.whereLessThanOrEqualTo("date", formatter.parse(tomorrow_date+"/"+mm+"/"+yy));
        			 }
        			 else
        			 {
        				 if(mm<current_mm)
        				 {
//        					 query.whereEqualTo("create_date",create_date);
        					 query.whereGreaterThanOrEqualTo("date", formatter.parse(create_date));
                			 query.whereLessThanOrEqualTo("date", formatter.parse(tomorrow_date+"/"+mm+"/"+yy));
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
//        			 query.whereEqualTo("create_date",create_date);
        			 query.whereGreaterThanOrEqualTo("date", formatter.parse(create_date));
        			 query.whereLessThanOrEqualTo("date", formatter.parse(tomorrow_date+"/"+mm+"/"+yy));
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
        else // if create_date==null
        {
        	Date date1 = new Date();
//        	query.whereEqualTo("create_date",df.format(date1));
        	TextView date_text = (TextView) findViewById(R.id.date_text);
        	date_text.setText(""+formatter.format(date1));
        	query.whereGreaterThanOrEqualTo("date", formatter.parse(formatter.format(date1)));
        }
        
        query.findInBackground(new FindCallback<ParseObject>() {
        	
			  public void done(List<ParseObject> objects, ParseException e) {
				  
    		if (e == null && objects.size()>0)
    		{
    			
    			try{
    				ParseQuery<ParseObject> query1 = ParseQuery.getQuery("ParameterValue");
    		        query1.whereEqualTo("location_objectid", location_id);
    		        String previous_date1;
    		        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    				if(getIntent().getStringExtra("create_date")!= null)
    				{
    					previous_date1= Integer.parseInt(getIntent().getStringExtra("create_date").split("/")[0]) - 1 +"/"+getIntent().getStringExtra("create_date").split("/")[1]+"/"+getIntent().getStringExtra("create_date").split("/")[2];
    					query1.whereGreaterThanOrEqualTo("date", formatter.parse(previous_date1));
    					query1.whereLessThanOrEqualTo("date", formatter.parseObject(getIntent().getStringExtra("create_date")));
    					//Toast.makeText(AddRecordActivity.this,"previous_date"+previous_date1, Toast.LENGTH_SHORT).show();
    				}
    				else
    				{
    					Date d1 = new  Date();
    					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    					previous_date1= (d1.getDate() -1)+"/"+(d1.getMonth()+1)+"/"+(1900+d1.getYear());
    					query1.whereGreaterThanOrEqualTo("date", formatter.parse(previous_date1));
    					//Toast.makeText(AddRecordActivity.this,""+previous_date1, Toast.LENGTH_SHORT).show();
    				}
  				 List<ParseObject> results = query1.find();
    			
  				 
  				
    			
    			
    			// NH4
  				TextView ammonia = (TextView) findViewById(R.id.ammonia_value);
    			if(objects.get(0).getNumber("NH4")!= null)
    			{
//    				Toast.makeText(AddRecordActivity.this, "BOD:"+objects.get(0).getNumber("BOD").toString(), Toast.LENGTH_SHORT).show();
    			   ammonia.setText(objects.get(0).getNumber("NH4").toString());
    			   if(objects.get(0).getNumber("NH4").floatValue()<=50.0||objects.get(0).getNumber("NH4").floatValue()>150.0)
    			   {
    				   RelativeLayout ammonia_layout = (RelativeLayout) findViewById(R.id.ammonia);
    				   ammonia_layout.setBackgroundColor(Color.parseColor("#FD5484"));
    			   }
    			   try{
    			   if(results.size()>0&&objects.get(0).getNumber("NH4").floatValue()>results.get(0).getNumber("NH4").floatValue())
    			   {
    				   ImageView ammonia_arrow = (ImageView)findViewById(R.id.ammonia_arrow);
    				   ammonia_arrow.setImageResource(R.drawable.arrow_top);
    			   }
    			   else if(results.size()>0 && objects.get(0).getNumber("NH4").floatValue()<results.get(0).getNumber("NH4").floatValue())
    			   {
    				   ImageView ammonia_arrow = (ImageView)findViewById(R.id.ammonia_arrow);
    				   ammonia_arrow.setImageResource(R.drawable.arrow_bottom);
    			   }}catch(Exception parse){}
    			}
    			Log.d("record","NH4===="+objects.get(0).getNumber("NH4"));
    			
    			
    			
    			 // BOD
  				TextView biochemical = (TextView) findViewById(R.id.biochemical_value);
    			if(objects.get(0).getNumber("BOD")!= null)
    			{
    			biochemical.setText(objects.get(0).getNumber("BOD").toString());
	    			if(objects.get(0).getNumber("BOD").floatValue()<=-5.0||objects.get(0).getNumber("BOD").floatValue()>35.0)
	 			   {
	    				RelativeLayout biochemical_layout = (RelativeLayout) findViewById(R.id.biochemical);
	    				biochemical_layout.setBackgroundColor(Color.parseColor("#FD5484"));
	 			   }
    			  try{
	    			if(results.size()>0 && objects.get(0).getNumber("BOD").floatValue()>results.get(0).getNumber("BOD").floatValue())
	    			   {
	    				  ImageView biochemical_arrow = (ImageView)findViewById(R.id.biochemical_arrow);
	    				  biochemical_arrow.setImageResource(R.drawable.arrow_top);
	    			   }
	    			else if(results.size()>0 && objects.get(0).getNumber("BOD").floatValue()<results.get(0).getNumber("BOD").floatValue())
	    			{
	    				ImageView biochemical_arrow = (ImageView)findViewById(R.id.biochemical_arrow);
	    				  biochemical_arrow.setImageResource(R.drawable.arrow_bottom);
	    			}
    			  }catch(Exception parse){}
    			}
    			Log.d("record","BOD===="+objects.get(0).getNumber("BOD"));
    			
    			
    			
    			// CL
    			TextView chloride = (TextView) findViewById(R.id.chloride_value);
    			if(objects.get(0).getNumber("CL")!= null)
    			{
    			chloride.setText(objects.get(0).getNumber("CL").toString());
	    			if(objects.get(0).getNumber("CL").floatValue()<=150.0||objects.get(0).getNumber("CL").floatValue()>450.0)
	 			     {
	    				RelativeLayout chloride_layout = (RelativeLayout) findViewById(R.id.chloride);
	    				chloride_layout.setBackgroundColor(Color.parseColor("#FD5484"));
	 			     }
	    			try{
	    			if(results.size()>0 && objects.get(0).getNumber("CL").floatValue()>results.get(0).getNumber("CL").floatValue())
	    			   {
	    				   ImageView chloride_arrow = (ImageView)findViewById(R.id.chloride_arrow);
	    				   chloride_arrow.setImageResource(R.drawable.arrow_top);
	    			   }
	    			else if(results.size()>0 && objects.get(0).getNumber("CL").floatValue()<results.get(0).getNumber("CL").floatValue())
	    			{
	    				ImageView chloride_arrow = (ImageView)findViewById(R.id.chloride_arrow);
	    				   chloride_arrow.setImageResource(R.drawable.arrow_bottom);
	    			}}catch(Exception parse){}
    			}
    			Log.d("record","CL===="+objects.get(0).getNumber("CL"));
    			
    			
    			
    			
    			// CP
    			TextView colorimeter = (TextView)findViewById(R.id.colorimeter_value);
    			if(objects.get(0).getNumber("CP")!= null)
    			{
    			colorimeter.setText(objects.get(0).getNumber("CP").toString());
	    			if(objects.get(0).getNumber("CP").floatValue()<=5.0||objects.get(0).getNumber("CP").floatValue()>15.0)
				     {
	    				RelativeLayout colorimeter_layout = (RelativeLayout) findViewById(R.id.colorimeter);
	    				colorimeter_layout.setBackgroundColor(Color.parseColor("#FD5484"));
				     }
	    			try{
	    			if(results.size()>0 && objects.get(0).getNumber("CP").floatValue()>results.get(0).getNumber("CP").floatValue())
	    			   {
	    				  ImageView colorimeter_arrow = (ImageView)findViewById(R.id.colorimeter_arrow);
	    				  colorimeter_arrow.setImageResource(R.drawable.arrow_top);
	    			   }
	    			else if(results.size()>0 && objects.get(0).getNumber("CP").floatValue()<results.get(0).getNumber("CP").floatValue())
	    			{
	    				ImageView colorimeter_arrow = (ImageView)findViewById(R.id.colorimeter_arrow);
	    				colorimeter_arrow.setImageResource(R.drawable.arrow_bottom);
	    			}}catch(Exception parse){}
    			}
    			Log.d("record","CP===="+objects.get(0).getNumber("CP"));
    			
    			
    			//CTVT
    			TextView conductivity = (TextView) findViewById(R.id.conductivity_value);
    			if(objects.get(0).getNumber("CTVT")!= null)
    			{
    			conductivity.setText(objects.get(0).getNumber("CTVT").toString());
	    			if(objects.get(0).getNumber("CTVT").floatValue()<=23.0||objects.get(0).getNumber("CTVT").floatValue()>70.0)
				     {
	    				RelativeLayout conductivity_layout = (RelativeLayout) findViewById(R.id.conductivity);
	    				conductivity_layout.setBackgroundColor(Color.parseColor("#FD5484"));
				     }
	    			try{
	    			if(results.size()>0 && objects.get(0).getNumber("CTVT").floatValue()>results.get(0).getNumber("CTVT").floatValue())
	    			   {
	    				   ImageView conductivity_arrow = (ImageView)findViewById(R.id.conductivity_arrow);
	    				   conductivity_arrow.setImageResource(R.drawable.arrow_top);
	    			   }
	    			else if(results.size()>0 && objects.get(0).getNumber("CTVT").floatValue()<results.get(0).getNumber("CTVT").floatValue())
	    			{
	    				ImageView conductivity_arrow = (ImageView)findViewById(R.id.conductivity_arrow);
	    				   conductivity_arrow.setImageResource(R.drawable.arrow_bottom);
	    			}}catch(Exception parse){}
    			}
    			Log.d("record","CTVT===="+objects.get(0).getNumber("CTVT"));
    			
    			
    			
    			//DO
    			TextView dissolved_oxygen = (TextView) findViewById(R.id.dissolved_oxygen_value);
    			if(objects.get(0).getNumber("DO")!= null)
    			{
    			dissolved_oxygen.setText(objects.get(0).getNumber("DO").toString());
	    			if(objects.get(0).getNumber("DO").floatValue()<=5.0||objects.get(0).getNumber("DO").floatValue()>15.0)
				     {
	    				RelativeLayout dissolved_layout = (RelativeLayout) findViewById(R.id.dissolved_oxygen);
	    				dissolved_layout.setBackgroundColor(Color.parseColor("#FD5484"));
				     }
	    			try{
	    			if(results.size()>0 && objects.get(0).getNumber("DO").floatValue()>results.get(0).getNumber("DO").floatValue())
	    			   {
	    				   ImageView dissolved_oxygen_arrow = (ImageView)findViewById(R.id.dissolved_oxygen_arrow);
	    				   dissolved_oxygen_arrow.setImageResource(R.drawable.arrow_top);
	    			   }
	    			else if(results.size()>0 && objects.get(0).getNumber("DO").floatValue()<results.get(0).getNumber("DO").floatValue())
	    			{
	    				ImageView dissolved_oxygen_arrow = (ImageView)findViewById(R.id.dissolved_oxygen_arrow);
	    				dissolved_oxygen_arrow.setImageResource(R.drawable.arrow_bottom);
	    			}}catch(Exception parse){}
    			}
    			Log.d("record","DO===="+objects.get(0).getNumber("DO"));
    			
    			
    			
    			//FCL
    			TextView  free_chlorine = (TextView)findViewById(R.id.free_chlorine_value);
    			if(objects.get(0).getNumber("FCL")!= null)
    			{
    			free_chlorine.setText(objects.get(0).getNumber("FCL").toString());
	    			if(objects.get(0).getNumber("FCL").floatValue()<=5.0||objects.get(0).getNumber("FCL").floatValue()>15.0)
				     {
	    				RelativeLayout free_chlorine_layout = (RelativeLayout) findViewById(R.id.free_chlorine);
	    				free_chlorine_layout.setBackgroundColor(Color.parseColor("#FD5484"));
				     }
	    			try{
	    			if(results.size()>0 && objects.get(0).getNumber("FCL").floatValue()>results.get(0).getNumber("FCL").floatValue())
	    			   {
	    				   ImageView free_chlorine_arrow = (ImageView)findViewById(R.id.free_chlorine_arrow);
	    				   free_chlorine_arrow.setImageResource(R.drawable.arrow_top);
	    			   }
	    			else if(results.size()>0 && objects.get(0).getNumber("FCL").floatValue()<results.get(0).getNumber("FCL").floatValue())
	    			{
	    				ImageView free_chlorine_arrow = (ImageView)findViewById(R.id.free_chlorine_arrow);
	    				   free_chlorine_arrow.setImageResource(R.drawable.arrow_bottom);
	    			}}catch(Exception parse){}
    			}
    			Log.d("record","FCL===="+objects.get(0).getNumber("FCL"));
    			
    			
    			// NO3
    			TextView nitrate = (TextView) findViewById(R.id.nitrate_value);
    			if(objects.get(0).getNumber("NO3")!= null)
    			{
    			nitrate.setText(objects.get(0).getNumber("NO3").toString());
	    			if(objects.get(0).getNumber("NO3").floatValue()<=5.0||objects.get(0).getNumber("NO3").floatValue()>15.0)
				     {
	    				RelativeLayout nitrate_layout = (RelativeLayout) findViewById(R.id.nitrate);
	    				nitrate_layout.setBackgroundColor(Color.parseColor("#FD5484"));
				     }
	    			try{
	    			if(results.size()>0 && objects.get(0).getNumber("NO3").floatValue()>results.get(0).getNumber("NO3").floatValue())
	    			   {
	    				   ImageView nitrate_arrow = (ImageView)findViewById(R.id.nitrate_arrow);
	    				   nitrate_arrow.setImageResource(R.drawable.arrow_top);
	    			   }
	    			else if(results.size()>0 && objects.get(0).getNumber("NO3").floatValue()<results.get(0).getNumber("NO3").floatValue())
	    			{
	    				ImageView nitrate_arrow = (ImageView)findViewById(R.id.nitrate_arrow);
	    				   nitrate_arrow.setImageResource(R.drawable.arrow_bottom);
	    			}}catch(Exception parse){}
    			}
    			Log.d("record","NO3===="+objects.get(0).getNumber("NO3"));
    			
    			
    			//ORP
    			TextView orp = (TextView) findViewById(R.id.orp_value);
    			if(objects.get(0).getNumber("ORP")!= null)
    			{
    			orp.setText(objects.get(0).getNumber("ORP").toString());
	    			if(objects.get(0).getNumber("ORP").floatValue()<=5.0||objects.get(0).getNumber("ORP").floatValue()>15.0)
				     {
	    				RelativeLayout orp_layout = (RelativeLayout) findViewById(R.id.orp);
	    				orp_layout.setBackgroundColor(Color.parseColor("#FD5484"));
				     }
	    			try{
	    			if(results.size()>0 && objects.get(0).getNumber("ORP").floatValue()>results.get(0).getNumber("ORP").floatValue())
	    			   {
	    				   ImageView orp_arrow = (ImageView)findViewById(R.id.orp_arrow);
	    				   orp_arrow.setImageResource(R.drawable.arrow_top);
	    			   }
	    			else if(results.size()>0 && objects.get(0).getNumber("ORP").floatValue()<results.get(0).getNumber("ORP").floatValue())
	    			{
	    				ImageView orp_arrow = (ImageView)findViewById(R.id.orp_arrow);
	    				   orp_arrow.setImageResource(R.drawable.arrow_bottom);	
	    			}}catch(Exception parse){}
    			}
    			Log.d("record","ORP===="+objects.get(0).getNumber("ORP"));
    			
    			
    			// PH
    			TextView ph = (TextView) findViewById(R.id.ph_value);
    			if(objects.get(0).getNumber("PH")!= null)
    			{
    			ph.setText(objects.get(0).getNumber("PH").toString());
	    			if(objects.get(0).getNumber("PH").floatValue()<=3.5||objects.get(0).getNumber("PH").floatValue()>10.5)
				     {
	    				RelativeLayout ph_layout = (RelativeLayout) findViewById(R.id.ph);
	    				ph_layout.setBackgroundColor(Color.parseColor("#FD5484"));
				     }
	    			try{
	    			if(results.size()>0 && objects.get(0).getNumber("PH").floatValue()>results.get(0).getNumber("PH").floatValue())
	    			   {
	    				   ImageView ph_arrow = (ImageView)findViewById(R.id.ph_arrow);
	    				   ph_arrow.setImageResource(R.drawable.arrow_top);
	    			   }
	    			else if(results.size()>0 && objects.get(0).getNumber("PH").floatValue()<results.get(0).getNumber("PH").floatValue())
	    			{
	    				ImageView ph_arrow = (ImageView)findViewById(R.id.ph_arrow);
	    				   ph_arrow.setImageResource(R.drawable.arrow_bottom);
	    			}}catch(Exception parse){}
    			}
    			Log.d("record","PH===="+objects.get(0).getNumber("PH"));
    			}catch(Exception ex){}
    		}
    		else
    		{
    			Toast.makeText(AddRecordActivity.this, "Not found today Record", Toast.LENGTH_SHORT).show();
    		}
				 
    	}
        	 
		});
  }catch(Exception e){}

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