package com.example.parameters;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.mw.aquatrack.DAO.LocationsDAO;
import com.mw.aquatrack.adapters.LocationAdapter;
import com.parse.ParseObject;

public class SelectLocationActivity extends Activity {
	LocationsDAO dao;
	LocationAdapter locationAdapter;
	ListView listView;
	List<ParseObject> locationList;
	boolean selectedLocations[];
	String[] locationIdArray;
	String[] locationNameArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_location);

		dao = new LocationsDAO(this);
		locationList = dao.getAllLocations();

		listView = (ListView) findViewById(R.id.locations_list);
		locationAdapter = new LocationAdapter(this, locationList, 1, 0);
		listView.setAdapter(locationAdapter);
		((ImageButton) findViewById(R.id.location_add))
				.setVisibility(View.GONE);

	}

	public void onDone(View view) {
		prepareLocations();
		Intent intent = getIntent();
		intent.putExtra("location_id_array", locationIdArray);
		intent.putExtra("location_name_array", locationNameArray);
		setResult(RESULT_OK, intent);
		finish();

	}

	public void prepareLocations() {
		selectedLocations = new boolean[locationList.size()];
		selectedLocations = LocationAdapter.asdf;

		int trueCount = 0;
		for (int i = 0; i < selectedLocations.length; i++) {
			if (selectedLocations[i] == true)
				trueCount++;
		}
		
		locationIdArray = new String[trueCount];
		locationNameArray = new String[trueCount];
		trueCount=0;
		for (int i = 0; i < selectedLocations.length; i++) {
			if (selectedLocations[i] == true) {
				locationIdArray[trueCount] = locationList.get(i).getObjectId();
				locationNameArray[trueCount] = locationList.get(i).getString(
						"location_name");
				trueCount++;
			}
		}
	}
}