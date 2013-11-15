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
import com.mw.aquatrack.services.CreateDialog;
import com.mw.aquatrack.services.MyApplication;
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
	ProgressDialog progressDialog;
	MyApplication application;
	AlertDialog.Builder alertDialogBuilder;
	CreateDialog createDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		final Dialog mainDialog = new Dialog(this);
		mainDialog.setContentView(R.layout.manage_location);
		mainDialog.setTitle("Select Locations");
		mainDialog.setCancelable(false);
		handler = new Handler();
		application = (MyApplication) getApplication();
		createDialog = new CreateDialog(this);
		dao = new LocationsDAO(this);

		progressDialog = createDialog.createProgressDialog("Loading",
				"Please wait for a while.", true,
				getResources().getDrawable(R.anim.progress_dialog_animation));
		progressDialog.show();
		final Thread thread = new Thread() {
			public void run() {
				System.out.println("thread");
				locationList = dao.getAllLocations();

				handler.post(new Runnable() {
					@Override
					public void run() {
						if (locationList == null) {
							finish();
							Thread.currentThread().interrupt();
						} else {
// these 2 statements can be outside handler too
							((LinearLayout) mainDialog
									.findViewById(R.id.linearLayout1))
									.setVisibility(View.GONE);
							((ImageButton) mainDialog
									.findViewById(R.id.location_add))
									.setVisibility(View.GONE);
							listView = (ListView) mainDialog
									.findViewById(R.id.locations_list);
							locationAdapter = new LocationAdapter(
									SelectLocationActivity.this, locationList, 1);
							listView.setAdapter(locationAdapter);
							System.out.println("handler");
							progressDialog.dismiss();
							mainDialog.show();
						}
					}
				});// post
			}
		};// thread
		thread.start();
		
		Thread thread2 = new Thread(new Runnable() {

			@Override
			public void run() {
				int j = 0;
				while (j == 0) {
					try {
						Thread.currentThread();
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (thread.getState() != Thread.State.TERMINATED)
						if (!internetAlert())
							j = 1;
				}
			}
		});
		thread2.start();
		
		
		((Button) mainDialog.findViewById(R.id.location_done))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (!validate())
							return;
						prepareLocations();
						Intent intent = getIntent();
						intent.putExtra("location_id_array", locationIdArray);
						intent.putExtra("location_name_array",
								locationNameArray);
						setResult(RESULT_OK, intent);
						mainDialog.dismiss();
						finish();
					}
				});
	}

	public void onDone(View view) {
	}

	int trueCount = 0;

	public boolean validate() {
		selectedLocations = new boolean[locationList.size()];
		selectedLocations = LocationAdapter.selectedLocations;

		for (int i = 0; i < selectedLocations.length; i++) {
			if (selectedLocations[i] == true)
				trueCount++;
		}
		if (trueCount == 0) {
			AlertDialog alertDialog;
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder
					.setTitle("Oops")
					.setMessage("Choose a location.")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
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
		trueCount = 0;
		for (int i = 0; i < selectedLocations.length; i++) {
			if (selectedLocations[i] == true) {
				locationIdArray[trueCount] = locationList.get(i).getObjectId();
				locationNameArray[trueCount] = locationList.get(i).getString(
						"location_name");
				trueCount++;
			}
		}
	} // end of prepareLocations()

	public boolean internetAlert() {
		int internetScore = application.combinedInternetTest();
		if (internetScore != 0) {
			System.out.println("problem");
			if (internetScore == 1) {
				alertDialogBuilder = createDialog.createAlertDialog(
						"CONNECTION ERROR", "Connect to WiFi or Data Service.",
						false);
				singleOKButton(alertDialogBuilder);
				return false;
			}
			if (internetScore == 2) {
				alertDialogBuilder = createDialog.createAlertDialog(
						"CONNECTION ERROR", "Internet services required.",
						false);
				singleOKButton(alertDialogBuilder);
				return false;
			}
		}
		return true;
	}

	public void dismissPreviousProgressBar() {
		handler.post(new Runnable() {

			@Override
			public void run() {
				progressDialog.dismiss();
			}
		});
	}

	public void singleOKButton(final AlertDialog.Builder alertDialogBuilder) {
		alertDialogBuilder.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
						finish();
					}
				});

		handler.post(new Runnable() {
			@Override
			public void run() {
				dismissPreviousProgressBar();
				AlertDialog dialog = alertDialogBuilder.create();
				dialog.show();

			}
		});
	}

}