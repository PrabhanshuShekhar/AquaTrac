package com.example.parameters;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

public class AddParameterValueActivity extends Activity {
	TextView slider,slider_value,instruction;
	SeekBar seekbar;
	String table_id,create_date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_parameter_value);
		slider_value = (TextView) findViewById(R.id.slider_value);
//		 slider = (TextView) findViewById(R.id.slider);
		 instruction = (TextView)findViewById(R.id.instruction);
		 instruction.setText("Set Value for "+getIntent().getStringExtra("param_name"));
//		slider.setText(getIntent().getStringExtra("param_name"));
		table_id = getIntent().getStringExtra("location");
		create_date = getIntent().getStringExtra("create_date");
		 seekbar = (SeekBar) findViewById(R.id.seekbar);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			
			public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser)
			{
			    int id = getIntent().getIntExtra("id", 0);
			    switch(id)
			    {
			    case 1:
			    	seekbar = (SeekBar) findViewById(R.id.seekbar);
			    	seekbar.setMax(2000);
			    	slider_value.setText(""+Float.toString((float)(progress)/10));
			    	break;
			    case 2:
			    	seekbar = (SeekBar) findViewById(R.id.seekbar);
			    	seekbar.setMax(800);
			    	slider_value.setText(""+Float.toString((float)(progress-250)/10));
			    	 break;
			    case 3:
			    	seekbar = (SeekBar) findViewById(R.id.seekbar);
			    	seekbar.setMax(6000);
			    	slider_value.setText(""+Float.toString((float)(progress)/10));
			    	  break;
			    case 5:
			    	seekbar = (SeekBar) findViewById(R.id.seekbar);
			    	seekbar.setMax(1070);
			    	slider_value.setText(""+Float.toString((float)(progress-70)/10));			    	
			    	 break;
			    case 4:
			    	seekbar = (SeekBar) findViewById(R.id.seekbar);
			    	seekbar.setMax(200);
			    	slider_value.setText(""+Float.toString((float)(progress)/10));
			    	 break;
			    case 6:
			    	seekbar = (SeekBar) findViewById(R.id.seekbar);
			    	seekbar.setMax(200);
			    	slider_value.setText(""+Float.toString((float)(progress)/10));
			    	 break;
			    case 7:
			    	seekbar = (SeekBar) findViewById(R.id.seekbar);
			    	seekbar.setMax(200);
			    	slider_value.setText(""+Float.toString((float)(progress)/10));
			    	 break;
			    case 8:
			    	seekbar = (SeekBar) findViewById(R.id.seekbar);
			    	seekbar.setMax(200);
			    	slider_value.setText(""+Float.toString((float)(progress)/10));
			    	  break;
			    case 9:
			    	seekbar = (SeekBar) findViewById(R.id.seekbar);
			    	seekbar.setMax(200);
			    	slider_value.setText(""+Float.toString((float)(progress)/10));
			    	  break;
			    case 10:
			    	seekbar = (SeekBar) findViewById(R.id.seekbar);
			    	seekbar.setMax(140);
			    	slider_value.setText(""+Float.toString((float)(progress)/10));
			    	 break;
			    case 0:
			    	 break;
			    }
				
			}
			
			public void onStartTrackingTouch(SeekBar seekbar)
			{
				
			}
			
			public void onStopTrackingTouch(SeekBar seekbar)
			{
				
			}
		});
	}
	
//	public void submit(View v)
//	{
//		// insert parameter values in db
//		String location_objectid;
//		Date date = new Date();
//    	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//		Parse.initialize(AddParameterValueActivity.this, "hjYMRHgjBNK6fzcltOMtnmglaDYIQIU3PJfdCMF3", "xgBSMsHThQK5kzLvqSwDznSrpH9Gq8bW7ZYl6YoA");
//		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParameterValue");
//		query.whereEqualTo("location_objectid", table_id);
//		if(create_date !=null)
//		{query.whereEqualTo("create_date", create_date);}
//		else
//		{ query.whereEqualTo("create_date", df.format(date));}
//		query.findInBackground(new FindCallback<ParseObject>() {
//			  public void done(List<ParseObject> locationList, ParseException e) {
//			    // locationList has record 
//				  if (e == null) {   // update based on object id in parameter values table
////					  
//					  if(locationList.size()>0){
//					  ParseQuery<ParseObject> update_query = ParseQuery.getQuery("ParameterValue");
//					  update_query.getInBackground(locationList.get(0).getObjectId(),new GetCallback<ParseObject>() {
//						  public void done(ParseObject gameScore, ParseException e) {
//							    if (e == null) {
//							      // Now let's update it with some new data. In this case, only cheatMode and score
//							      // will get sent to the Parse Cloud. playerName hasn't changed.
//							    	
//							      gameScore.put(getIntent().getStringExtra("param_name"),Float.parseFloat(slider_value.getText().toString()));
//							      //gameScore.put("create_date", df.format(date));
//							      gameScore.saveInBackground();
//							      Intent intent1 = new Intent(AddParameterValueActivity.this,AddRecordActivity.class);
//							      intent1.putExtra("location", table_id);
//							      intent1.putExtra("action_name", "Record");
//							      intent1.putExtra("create_date", create_date);
////							      intent1.putExtra("param_name",slider.getText().toString() );
////							      intent1.putExtra("param_value",slider_value.getText().toString());
//							      startActivity(intent1);
//							      finish();
//							    	}
//							  }
//							});
//					  }
//					  else
//					  {
//						  Date date = new Date();
//					    	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//						    ParseObject location = new ParseObject("ParameterValue");
//							location.put("location_objectid", table_id);
//							location.put(getIntent().getStringExtra("param_name"),Float.parseFloat(slider_value.getText().toString()));
//							if(create_date != null)
//							{ location.put("create_date", create_date);}
//							else
//							 { location.put("create_date", df.format(date)); }
//							location.saveInBackground();
//							Intent intent1 = new Intent(AddParameterValueActivity.this,AddRecordActivity.class);
//							intent1.putExtra("action_name", "Record");
//							intent1.putExtra("location", table_id);
//						    startActivity(intent1);
//							finish();  
//					  }
//					  
//			        } else {
//			            Log.d("record", "Error: " + e.getMessage());
//			              }
//			  }
//			});
//		
//		
//		
//	}
	
	
	// ********************************************************************
	
	
	public void submit(View view)
	{
		String location_id,create_date;
		location_id = getIntent().getStringExtra("location");
		create_date = getIntent().getStringExtra("create_date");
		if(create_date != null)
		{
			try{
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				ParseQuery<ParseObject> query = ParseQuery.getQuery("ParameterValue");
				query.whereEqualTo("location_objectid", table_id);
				query.whereGreaterThanOrEqualTo("date",formatter.parse(create_date));
				int d = Integer.parseInt(create_date.split("/")[0]) + 1;
				String max_date = ""+d+"/"+create_date.split("/")[1]+"/"+create_date.split("/")[2];
				query.whereLessThanOrEqualTo("date", formatter.parse(max_date));
				query.findInBackground(new FindCallback<ParseObject>() {
					public void done(List<ParseObject> objects,ParseException e)
					{
						if(e == null && objects.size() > 0) // update record 
						{
							try{
								ParseQuery<ParseObject> update_query = ParseQuery.getQuery("ParameterValue");
								update_query.getInBackground(objects.get(0).getObjectId(), new GetCallback<ParseObject>() {
								public void done(ParseObject location,ParseException e)
								{
									if(e==null)
									{
										location.put(getIntent().getStringExtra("param_name"),Float.parseFloat(slider_value.getText().toString()));
										location.saveInBackground();
										Intent intent1 = new Intent(AddParameterValueActivity.this,AddRecordActivity.class);
									      intent1.putExtra("location", getIntent().getStringExtra("location"));
									      intent1.putExtra("action_name", "Record");
									      intent1.putExtra("create_date", getIntent().getStringExtra("create_date"));
									      startActivity(intent1);
									      finish();
									}
								}
								}); // end of getinbackground
							}catch(Exception parse){}
						}else if(e == null)
						{
							// create new record 
							try{
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							ParseObject location = new ParseObject("ParameterValue");
							location.put("date", formatter.parse(getIntent().getStringExtra("create_date")));
							location.put("location_objectid", getIntent().getStringExtra("location"));
							location.put(getIntent().getStringExtra("param_name"),Float.parseFloat(slider_value.getText().toString()));
							location.saveInBackground();
							Intent intent1 = new Intent(AddParameterValueActivity.this,AddRecordActivity.class);
						      Date date = new Date();
							  SimpleDateFormat formatter1 = new SimpleDateFormat("MMM dd, yyyy");
								intent1.putExtra("location", getIntent().getStringExtra("location"));
							    intent1.putExtra("action_name", "Record");
						        intent1.putExtra("create_date", getIntent().getStringExtra("create_date"));
						        startActivity(intent1);
						        finish();
							}catch(Exception parse){}
						}
					}
				}); // end findInbackground
			}catch(Exception parse){}
		}else // create_date != null means if  date is null  operate on current date
		{
			try{
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
				ParseQuery<ParseObject> query = ParseQuery.getQuery("ParameterValue");
				query.whereGreaterThanOrEqualTo("date", formatter.parseObject(formatter.format(date)));
				Calendar c = new GregorianCalendar();
				c.add(Calendar.DAY_OF_MONTH,1);
				Date d = c.getTime();
				query.whereLessThanOrEqualTo("date", formatter.parseObject(formatter.format(d)));
				query.whereEqualTo("location_objectid", getIntent().getStringExtra("location"));
				query.findInBackground(new FindCallback<ParseObject>() {
					public void done(List<ParseObject> location_list,ParseException e)
					{
						if(e == null && location_list.size() > 0)
						{
							// update existing record for current date
							try{
								ParseQuery<ParseObject> update_query = ParseQuery.getQuery("ParameterValue");
								update_query.getInBackground(location_list.get(0).getObjectId(), new GetCallback<ParseObject>() {
									public void done(ParseObject location , ParseException e)
									{
										if(e==null)
										{
											location.put(getIntent().getStringExtra("param_name"),Float.parseFloat(slider_value.getText().toString()));
											location.saveInBackground();
											Intent intent1 = new Intent(AddParameterValueActivity.this,AddRecordActivity.class);
											SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
											Date curr_date = new Date();
										      intent1.putExtra("location", getIntent().getStringExtra("location"));
										      intent1.putExtra("action_name", "Record");
										      intent1.putExtra("create_date", formatter.format(curr_date) );
										      startActivity(intent1);
										      finish();
										}
									}
								});
								
							}catch(Exception parse){}
						}else if(e == null) //
						{
							// create new record
							try{
							Date date = new Date();
							SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
							ParseObject location = new ParseObject("ParameterValue");
							location.put("location_objectid",getIntent().getStringExtra("location"));
							location.put("date", formatter.parseObject(formatter.format(date)));
							location.put(getIntent().getStringExtra("param_name"),Float.parseFloat(slider_value.getText().toString()));
							location.saveInBackground();
							Intent intent1 = new Intent(AddParameterValueActivity.this,AddRecordActivity.class);
							intent1.putExtra("location", getIntent().getStringExtra("location"));
						    intent1.putExtra("action_name", "Record");
						    SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
					        intent1.putExtra("create_date", formatter1.format(date));
					        startActivity(intent1);
					        finish();
							}catch(Exception ex){}
						}
					}
				}); // end of findInBackground
			}catch(Exception parse){}
		}
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_parameter_value, menu);
		return true;
	}

}