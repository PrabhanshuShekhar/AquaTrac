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
  String param_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_add);
		RelativeLayout outer_RL = (RelativeLayout) findViewById(R.id.parameter);
		param_name = getIntent().getStringExtra("location");
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
		case R.id.ammonia:
//			Toast.makeText(this,"id:"+"water_level", Toast.LENGTH_SHORT).show();
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "NH4");
			intent.putExtra("location", param_name);
			startActivity(intent);
			break;
			
		case R.id.biochemical:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "BOD");
			intent.putExtra("location", param_name);
			startActivity(intent);
			 	break;
		case R.id.chloride:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "CL");
			intent.putExtra("location", param_name);
			startActivity(intent);
		 	break;	
		case R.id.colorimeter:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "CP");
			intent.putExtra("location", param_name);
			startActivity(intent);
		 	break;
		case R.id.conductivity:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "CTVT");
			intent.putExtra("location", param_name);
			startActivity(intent);
		 	break;
		case R.id.dissolved_oxygen:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "DO");
			intent.putExtra("location", param_name);
			startActivity(intent);
		 	break;
		case R.id.free_chlorine:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "FCL");
			intent.putExtra("location", param_name);
			startActivity(intent);
		 	break;
		case R.id.nitrate:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "NO3");
			intent.putExtra("location", param_name);
			startActivity(intent);
		 	break;
		case R.id.orp:
			intent = new Intent(this, AddParameterValueActivity.class);
		    intent.putExtra("param_name", "ORP");
		    intent.putExtra("location", param_name);
		    startActivity(intent);Toast.makeText(this, "id:Nitrate", Toast.LENGTH_SHORT).show();
		 	break;
		case R.id.ph:
			intent = new Intent(this, AddParameterValueActivity.class);
			intent.putExtra("param_name", "PH");
			intent.putExtra("location", param_name);
			startActivity(intent);
		 	break;
		}
		
	}
}
