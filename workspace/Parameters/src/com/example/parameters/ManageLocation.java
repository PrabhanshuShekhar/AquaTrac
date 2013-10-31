package com.example.parameters;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.mw.aquatrack.DAO.LocationsDAO;
import com.mw.aquatrack.adapters.LocationAdapter;
import com.parse.ParseObject;

public class ManageLocation extends Activity {
	public static final int ADD_LOCATION = 1;
	public static final int MODIFY_LOCATION = 10;
	LocationsDAO dao;
	LocationAdapter locationAdapter;
	ListView listView;
	List<ParseObject> locationList;
	Handler handler;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.manage_location);
		handler = new Handler();
		dao = new LocationsDAO(this);
		listView = (ListView) findViewById(R.id.locations_list);

		dialog = ProgressDialog.show(this, "Loading",
				"Please wait for a while.");
		Thread thread = new Thread() {
			public void run() {
				System.out.println("thread");
				locationList = dao.getAllLocations();
				locationAdapter = new LocationAdapter(ManageLocation.this,
						locationList, 0);

				handler.post(new Runnable() {
					@Override
					public void run() {
						listView.setAdapter(locationAdapter);
						System.out.println("handler");
						dialog.dismiss();

						((Button) findViewById(R.id.location_done))
								.setVisibility(View.GONE);
					}
				});// post
			}
		};// thread
		thread.start();
		System.out.println("post thread");

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// System.out.println("item being clicked");
				final int position = listView.getPositionForView(arg1);
				System.out.println("postion selected is : " + position);
				ParseObject selectedLocation = locationList.get(position);
				Intent intent = new Intent(ManageLocation.this,
						EditLocation.class);

				intent.putExtra("current_location_name",
						selectedLocation.getString("location_name"));
				intent.putExtra("current_location_description",
						selectedLocation.getString("description"));
				intent.putExtra("objectId", selectedLocation.getObjectId());
				intent.putExtra("position", position);

				startActivityForResult(intent, MODIFY_LOCATION);
			}
		});

	}

	public void onAddLocation(View view) {
		System.out.println("add");
		Intent intent = new Intent(this, AddLocation.class);
		startActivityForResult(intent, ADD_LOCATION);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			final Intent intent) {

		dao = new LocationsDAO(this);

		if (requestCode == ADD_LOCATION) {
			if (resultCode == RESULT_OK) {

				final String locationName = intent.getStringExtra("name");
				final String locationDescription = intent
						.getStringExtra("description");
				dialog = ProgressDialog.show(this, "Adding Location",
						"Please wait for a while.");
				Thread thread = new Thread() {
					public void run() {
						System.out.println("thread");

						final ParseObject object = dao.addLocation(
								locationName, locationDescription);
						handler.post(new Runnable() {
							@Override
							public void run() {
								locationList.add(object);

								locationAdapter.notifyDataSetChanged();
								System.out.println("handler");
								dialog.dismiss();
								Toast.makeText(ManageLocation.this,
										"Location Added", Toast.LENGTH_SHORT)
										.show();
							}
						});// post
					}
				};// thread
				thread.start();
				super.onActivityResult(requestCode, resultCode, intent);
			}
		}

		if (requestCode == MODIFY_LOCATION) { // for update location
			if (resultCode == RESULT_OK) {

				dialog = ProgressDialog.show(this, "Updating",
						"Please wait for a while.");
				Thread thread = new Thread() {
					public void run() {

						String locationName = intent.getStringExtra("name");
						String locationDescription = intent
								.getStringExtra("description");

						final ParseObject object = dao.updateLocation(
								intent.getStringExtra("objectId"),
								locationName, locationDescription);

						handler.post(new Runnable() {

							@Override
							public void run() {
								locationAdapter.notifyDataSetChanged();
								locationList.set(
										intent.getIntExtra("position", -1),
										object);
								dialog.dismiss();
								Toast.makeText(ManageLocation.this,
										"Location Updated", Toast.LENGTH_SHORT)
										.show();
							}
						}); // end of post
					}
				}; // end of thread
				thread.start();
				super.onActivityResult(requestCode, resultCode, intent);
			} // end of if (resultCode == RESULT_OK)

			// for delete location
			if (resultCode == 10) {

				dialog = ProgressDialog.show(this, "Deleting",
						"Please wait for a while.");
				Thread thread = new Thread() {
					public void run() {

						final ParseObject object = dao.deleteLocation(intent
								.getStringExtra("objectId"));
						handler.post(new Runnable() {
							@Override
							public void run() {
								if (object != null) {
									locationList.remove(intent.getIntExtra(
											"position", -1));
									locationAdapter.notifyDataSetChanged();
									dialog.dismiss();
									Toast.makeText(ManageLocation.this,
											"Location Deleted",
											Toast.LENGTH_SHORT).show();

								} else {

								}// end of if (object != null)
							}
						}); // end of post

					}// end of run()
				}; // end of thread
				thread.start();
			} // end of if (resultCode == 10)

		} // end of if (requestCode == UPDATE_LOCATION)
	} // end of function

	
}