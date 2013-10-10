package com.example.parameters;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import android.view.*;

public class MainActivity extends Activity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		GridView gridview = (GridView)findViewById(R.id.gridview);
//		gridview.setBackgroundResource(R.drawable.water1);
		gridview.setAdapter(new ImageAdapter(this));
		gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//	            Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        	switch(position)
	        	{
	        	case 0:
	        		   startActivity(new Intent(MainActivity.this,AddActivity.class));
//	        		Toast.makeText(MainActivity.this, "Add window" , Toast.LENGTH_SHORT).show();
	        		 break;
	        	case 1:
	        		Toast.makeText(MainActivity.this, "View window" , Toast.LENGTH_SHORT).show();
	        		break;
	        	case 2:
	        		Toast.makeText(MainActivity.this, "Settings window" , Toast.LENGTH_SHORT).show();
	        		break;
	        	case 3:
	        		Toast.makeText(MainActivity.this, "Report window" , Toast.LENGTH_SHORT).show();
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
