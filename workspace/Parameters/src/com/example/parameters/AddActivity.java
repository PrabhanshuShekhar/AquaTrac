package com.example.parameters;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class AddActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		GridView ponds_gridview = (GridView) findViewById(R.id.gridview_ponds);
		ponds_gridview.setAdapter(new  PondsAdapter(this));
		// listener for ponds_gridview
		
		
		ponds_gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
				switch(position)
				{
				case 0:
					    Intent intent = new Intent(AddActivity.this,AddRecordActivity.class);
					    intent.putExtra("location", "Location1");
					    startActivity(intent);
				}
			}
		});
		
		
		
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
		return true;
	}

}
