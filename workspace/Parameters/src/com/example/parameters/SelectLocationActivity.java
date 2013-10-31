package com.example.parameters;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
	Handler handler;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		final Dialog mainDialog = new Dialog(this);
		mainDialog.setContentView(R.layout.manage_location);
		mainDialog.setTitle("Select Locations");
//		mainDialog.setCancelable(false);
		handler = new Handler();
		dao = new LocationsDAO(this);

		dialog = ProgressDialog.show(this, "Loading",
				"Please wait for a while.");
		Thread thread = new Thread() {
			public void run() {
				System.out.println("thread");
				locationList = dao.getAllLocations();
				locationAdapter = new LocationAdapter(SelectLocationActivity.this,
						locationList, 1);

				handler.post(new Runnable() {
					@Override
					public void run() {
						((LinearLayout)mainDialog.findViewById(R.id.linearLayout1)).setVisibility(View.GONE);
						listView = (ListView) mainDialog.findViewById(R.id.locations_list);
						listView.setAdapter(locationAdapter);
						System.out.println("handler");
						dialog.dismiss();
						((ImageButton) mainDialog.findViewById(R.id.location_add))
						.setVisibility(View.GONE);
						mainDialog.show();
					}
				});//post
			}
		};//thread
		thread.start();
		((Button)mainDialog.findViewById(R.id.location_done)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!validate())
				    return;
				prepareLocations();
				Intent intent = getIntent();
				intent.putExtra("location_id_array", locationIdArray);
				intent.putExtra("location_name_array", locationNameArray);
				setResult(RESULT_OK, intent);
				mainDialog.dismiss();
				finish();
			}
		});
	}

	public void onDone(View view) {
//		if(!validate())
//		    return;
//		prepareLocations();
//		Intent intent = getIntent();
//		intent.putExtra("location_id_array", locationIdArray);
//		intent.putExtra("location_name_array", locationNameArray);
//		setResult(RESULT_OK, intent);
//		finish();

	}

	int trueCount = 0;
	public boolean validate()
	   {
		selectedLocations = new boolean[locationList.size()];
		selectedLocations = LocationAdapter.selectedLocations;

		for (int i = 0; i < selectedLocations.length; i++) {
			if (selectedLocations[i] == true)
				trueCount++;
		}
		   if(trueCount==0)
		   {
			   AlertDialog alertDialog;
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
				alertDialogBuilder.setTitle("Oops").setMessage("Choose a parameter.").setCancelable(false)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
							}
						});
				
				alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			   return false;
		   }
		   return true;
	   }
	
	public void prepareLocations() {
		
		
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