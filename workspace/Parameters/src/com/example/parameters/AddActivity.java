package com.example.parameters;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class AddActivity extends Activity {

// Testing for first commit
	//// Testing for third commit

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		GridView ponds_gridview = (GridView) findViewById(R.id.gridview_ponds);
		PondsAdapter adapter = new PondsAdapter(this);
		ponds_gridview.setAdapter(adapter);
		// listener for ponds_gridview
		
		
		ponds_gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
				Intent intent;
				String action_name = getIntent().getStringExtra("action_name");
				intent = new Intent(AddActivity.this,AddRecordActivity.class);
				intent.putExtra("location", PondsAdapter.location_names.get(position));
				intent.putExtra("action_name", action_name);
			    startActivity(intent);
//				switch(position)
//				{
//				case 0:
//					     intent = new Intent(AddActivity.this,AddRecordActivity.class);
//					    intent.putExtra("location", PondsAdapter.location_names.get(position));
//					    startActivity(intent);
//			            break;
//				case 1:
//					   intent = new Intent(AddActivity.this,AddRecordActivity.class);
//				       intent.putExtra("location", "Location2");
//				       startActivity(intent);
//					   break;
//				
//				case 2:
//					    intent = new Intent(AddActivity.this,AddRecordActivity.class);
//				        intent.putExtra("location", "Location3");
//				        startActivity(intent);
//					    break;
//					    
//				case 3:
//						intent = new Intent(AddActivity.this,AddRecordActivity.class);
//					    intent.putExtra("location", "Location4");
//					    startActivity(intent);
//					    break;
//					     
//				case 4: 
//						intent = new Intent(AddActivity.this,AddRecordActivity.class);
//					    intent.putExtra("location", "Location5");
//					    startActivity(intent);
//					    break;
//				case 5: 
//						intent = new Intent(AddActivity.this,AddRecordActivity.class);
//					    intent.putExtra("location", "Location6");
//					    startActivity(intent);
//					    break;
//				}
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
