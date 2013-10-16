package com.example.parameters;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mw.aquatrack.DAO.LocationsDAO;
import com.mw.aquatrack.adapters.LocationAdapter;

public class ManageLocation extends Activity {
	// LocationAdapter locationAdapter;
	public static final int ADD_LOCATION = 1;
	public static final int UPDATE_LOCATION = 10;
	LocationsDAO dao;
	// String[] mainlocation;
	String[] location = { "loc1", "loc2", "sa", "asd", "loc1", "loc2", "sa",
			"asd", "loc1", "loc2", "sa", "asd", "loc1", "loc2", "sa", "asd" };
	ListView listView;
	LocationAdapter locationAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_location);
//		dao = new LocationsDAO(this);
//		dao.getAllLocations();
		
		listView = (ListView) findViewById(R.id.unique);
		locationAdapter = new LocationAdapter(this, location);
		listView.setAdapter(locationAdapter);
	}

	public void onSingleDelete(View view) {
		final int postion = listView.getPositionForView(view);
		System.out.println("postion selected is : " + postion);
	}

	public void onAddLocation(View view) {
		System.out.println("add");
		Intent intent = new Intent(this, AddLocation.class);
		startActivityForResult(intent, ADD_LOCATION);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		if (requestCode == ADD_LOCATION)
			if (resultCode == RESULT_OK) {
				
				String locationName = intent.getStringExtra("name");
				String locationDescription = intent.getStringExtra("description");
				//		add location
				dao = new LocationsDAO(this);
				dao.addLocation(locationName, locationDescription);
				locationAdapter.notifyDataSetChanged();
				super.onActivityResult(requestCode, resultCode, intent);
			}

		if (requestCode == UPDATE_LOCATION)
			if (resultCode == RESULT_OK) {
			
				super.onActivityResult(requestCode, resultCode, intent);
			}

	}

}
