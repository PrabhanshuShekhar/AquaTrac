package com.example.parameters;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
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
	TextView slider,slider_value;
	SeekBar seekbar;
	String table_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "hjYMRHgjBNK6fzcltOMtnmglaDYIQIU3PJfdCMF3", "xgBSMsHThQK5kzLvqSwDznSrpH9Gq8bW7ZYl6YoA");
		setContentView(R.layout.activity_add_parameter_value);
		slider_value = (TextView) findViewById(R.id.slider_value);
		 slider = (TextView) findViewById(R.id.slider);
		slider.setText(getIntent().getStringExtra("param_name"));
		table_name = getIntent().getStringExtra("location");
		 seekbar = (SeekBar) findViewById(R.id.seekbar);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser)
			{
				slider_value.setText(""+progress);
			}
			
			public void onStartTrackingTouch(SeekBar seekbar)
			{
				
			}
			
			public void onStopTrackingTouch(SeekBar seekbar)
			{
				
			}
		});
	}
	
	public void submit(View v)
	{
		// insert parameter values in db
		String location_objectid;
		Parse.initialize(AddParameterValueActivity.this, "hjYMRHgjBNK6fzcltOMtnmglaDYIQIU3PJfdCMF3", "xgBSMsHThQK5kzLvqSwDznSrpH9Gq8bW7ZYl6YoA");
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParameterValue");
		query.whereEqualTo("location_objectid", table_name);
		query.findInBackground(new FindCallback<ParseObject>() {
			  public void done(List<ParseObject> locationList, ParseException e) {
			    // locationList has record 
				  if (e == null) {   // update based on object id in parameter values table
//					  ParseObject location = new ParseObject("ParameterValue");
//						location.put("location_objectid", locationList.get(0).getObjectId().toString());
//						location.put(slider.getText().toString(),Float.parseFloat(slider_value.getText().toString()));
//						location.put("Create_date", "12/10/2013");
//						location.saveInBackground();
//						finish();
					  
					  ParseQuery<ParseObject> update_query = ParseQuery.getQuery("ParameterValue");
					  update_query.getInBackground(locationList.get(0).getObjectId(),new GetCallback<ParseObject>() {
						  public void done(ParseObject gameScore, ParseException e) {
							    if (e == null) {
							      // Now let's update it with some new data. In this case, only cheatMode and score
							      // will get sent to the Parse Cloud. playerName hasn't changed.
							      gameScore.put(slider.getText().toString(),Float.parseFloat(slider_value.getText().toString()));
							      gameScore.put("create_date", "12/10/2013");
							      gameScore.saveInBackground();
							      finish();
							    }
							  }
							});
					  
			        } else {
			            Log.d("record", "Error: " + e.getMessage());
			              }
			  }
			});
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_parameter_value, menu);
		return true;
	}

}
