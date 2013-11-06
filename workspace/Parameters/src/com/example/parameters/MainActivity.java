package com.example.parameters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends Activity  {

	

//grgrghtr5tyhrgrgt
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		GridView gridview = (GridView)findViewById(R.id.gridview);
//		gridview.setBackgroundResource(R.drawable.water1);
		gridview.setAdapter(new ImageAdapter(this));
		gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//	            Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        	Intent intent;
	        	switch(position)
	        	{
	        	case 0:
	        		 intent = new Intent(MainActivity.this , AddActivity.class);
	        		intent.putExtra("action_name", "Record");
	        		startActivity(intent);
//	        		   startActivity(new Intent(MainActivity.this,AddActivity.class));
//	        		Toast.makeText(MainActivity.this, "Add window" , Toast.LENGTH_SHORT).show();
	        		 break;
	        	case 1:
	        		 intent = new Intent(MainActivity.this , AddActivity.class);
	        		intent.putExtra("action_name", "View");
	        		startActivity(intent);
//	        		Toast.makeText(MainActivity.this, "View window" , Toast.LENGTH_SHORT).show();
	        		break;
	        	case 2:
	        		 intent = new Intent(MainActivity.this , SettingsActivity.class);
	        		 startActivity(intent);
	        		break;
	        	case 3:
	        		intent = new Intent(MainActivity.this , ReportsActivity.class);
	        		 startActivity(intent);
	        		 break;
	        	}
	        }
	    });
	}
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
