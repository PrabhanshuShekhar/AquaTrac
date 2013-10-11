package com.example.parameters;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AddRecordActivity extends Activity  {
  Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_add);
		RelativeLayout outer_RL = (RelativeLayout) findViewById(R.id.parameter);
//		outer_RL.setOnClickListener(this);
//		setContentView(R.layout.activity_add_record);
//		GridView gridview1 = (GridView)findViewById(R.id.gridview1);
//		gridview1.setAdapter(new ParameterAdapters(this));
	}
	
//	public void onClick(View arg0) {
//	    Toast.makeText(this,"hi:", Toast.LENGTH_SHORT).show();
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_record, menu);
		return true;
	}
	
	public void clicker(View v)
	{
		switch(v.getId())
		{
		case R.id.water_level:
//			Toast.makeText(this,"id:"+"water_level", Toast.LENGTH_SHORT).show();
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "Water Level");
			startActivity(intent);
			break;
			
		case R.id.temperature:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "Temperature");
			startActivity(intent);
			 	break;
		case R.id.alkalinity:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "Alkalinity");
			startActivity(intent);
		 	break;	
		case R.id.ammonia:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "Ammonia");
			startActivity(intent);
		 	break;
		case R.id.biochemical:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "Biochemical");
			startActivity(intent);
		 	break;
		case R.id.chloride:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "Chloride");
			startActivity(intent);
		 	break;
		case R.id.dissolved_oxygen:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "Dissolved Oxygen");
			startActivity(intent);
		 	break;
		case R.id.free_chlorine:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "Free Chlorine");
			startActivity(intent);
		 	break;
		case R.id.nitrate:
			intent = new Intent(this, AddParameterValueActivity.class);
		intent.putExtra("param_name", "Nitrate");
		startActivity(intent);Toast.makeText(this, "id:Nitrate", Toast.LENGTH_SHORT).show();
		 	break;
		case R.id.ph:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "Ph");
			startActivity(intent);
		 	break;
		}
		
	}
}
