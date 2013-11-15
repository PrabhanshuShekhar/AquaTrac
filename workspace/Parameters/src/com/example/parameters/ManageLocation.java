package com.example.parameters;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.mw.aquatrack.services.CreateDialog;
import com.mw.aquatrack.services.MyApplication;
import com.parse.ParseObject;

public class ManageLocation extends Activity {
	public static final int ADD_LOCATION = 1;
	public static final int MODIFY_LOCATION = 10;
	LocationsDAO dao;
	LocationAdapter locationAdapter;
	ListView listView;
	List<ParseObject> locationList;
	Handler handler;
	ProgressDialog progressDialog;
	MyApplication application;
	AlertDialog.Builder alertDialogBuilder;
	AlertDialog alertDialog;
	CreateDialog createDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.manage_location);
		handler = new Handler();
		application = (MyApplication) getApplication();
		createDialog = new CreateDialog(this);
		dao = new LocationsDAO(this);
		listView = (ListView) findViewById(R.id.locations_list);

		progressDialog = createDialog.createProgressDialog("Loading",
				"Please wait for a while.", true,
				getResources().getDrawable(R.anim.progress_dialog_animation));
		progressDialog.show();
		((Button) findViewById(R.id.location_done)).setVisibility(View.GONE);
		final Thread thread = new Thread() {
			public void run() {
				System.out.println("thread");
				locationList = dao.getAllLocations();
				if (locationList == null) {
					finish();
					Thread.currentThread().interrupt();
				} else {
					locationAdapter = new LocationAdapter(ManageLocation.this,
							locationList, 0);

					handler.post(new Runnable() {
						@Override
						public void run() {
							listView.setAdapter(locationAdapter);
							progressDialog.dismiss();

						}
					});// post
				}
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
					if (thread.getState() != Thread.State.TERMINATED) {
						if (!internetAlert()) {
							j = 1;
							alertDialogChecker();
							finish();
						}
					}
				}
			}
		});
		thread2.start();

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
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
		Intent intent = new Intent(this, AddLocation.class);
		// ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view,
		// 0,
		// 0,view.getWidth(), view.getHeight());
		// startActivityForResult(intent, ADD_LOCATION, options.toBundle());
		startActivityForResult(intent, ADD_LOCATION);
		overridePendingTransition(R.anim.slide_right, 0);
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
				// progressDialog = ProgressDialog.show(this, "Adding Location",
				// "Please wait for a while.");
				progressDialog = createDialog.createProgressDialog("Loading",
						"Please wait for a while.", true, getResources()
								.getDrawable(R.anim.progress_dialog_animation));
				progressDialog.show();
				final Thread thread = new Thread() {
					public void run() {
						System.out.println("thread");

						final ParseObject locationAdded = dao.addLocation(
								locationName, locationDescription);
						if (locationAdded != null) {
							handler.post(new Runnable() {
								@Override
								public void run() {
									locationList.add(locationAdded);
									locationAdapter.notifyDataSetChanged();
									progressDialog.dismiss();
									Toast.makeText(ManageLocation.this,
											"Location Added",
											Toast.LENGTH_SHORT).show();
								}
							});// post
						} else {
							Thread.currentThread().interrupt();
						}// end of if (object != null)
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
							if (thread.getState() != Thread.State.TERMINATED) {
								if (!internetAlert()) {
									j = 1;
									alertDialogChecker();
//									 while (alertDialog == null) {
//									 try {
//									 Thread.currentThread();
//									 Thread.sleep(1000);
//									 } catch (InterruptedException e) {
//									 e.printStackTrace();
//									 }
//									 }
//									 while (alertDialog.isShowing()) {
//									 try {
//									 Thread.currentThread();
//									 Thread.sleep(1000);
//									 } catch (InterruptedException e) {
//									 e.printStackTrace();
//									 }
//									 }
									handler.post(new Runnable() {
										@Override
										public void run() {
											Toast.makeText(ManageLocation.this,
													"Unable to add location",
													Toast.LENGTH_SHORT).show();

										}
									});// post
								}
							}
						}
					}// while
				});
				thread2.start();
				super.onActivityResult(requestCode, resultCode, intent);
			}
		}

		if (requestCode == MODIFY_LOCATION) { // for update location
			if (resultCode == RESULT_OK) {

				// progressDialog = ProgressDialog.show(this, "Updating",
				// "Please wait for a while.");
				progressDialog = createDialog.createProgressDialog("Loading",
						"Please wait for a while.", true, getResources()
								.getDrawable(R.anim.progress_dialog_animation));
				progressDialog.show();

				final Thread thread = new Thread() {
					public void run() {

						String locationName = intent.getStringExtra("name");
						String locationDescription = intent
								.getStringExtra("description");

						final ParseObject locationModified = dao
								.updateLocation(
										intent.getStringExtra("objectId"),
										locationName, locationDescription);
						if (locationModified != null) {
							handler.post(new Runnable() {

								@Override
								public void run() {
									locationAdapter.notifyDataSetChanged();
									locationList.set(
											intent.getIntExtra("position", -1),
											locationModified);
									progressDialog.dismiss();
									Toast.makeText(ManageLocation.this,
											"Location Updated",
											Toast.LENGTH_SHORT).show();
								}
							}); // end of post
						} else {
							Thread.currentThread().interrupt();
						}// end of if (object != null)
					}
				}; // end of thread
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
							if (thread.getState() != Thread.State.TERMINATED) {
								if (!internetAlert()) {
									j = 1;
									alertDialogChecker();
									handler.post(new Runnable() {
										@Override
										public void run() {
											Toast.makeText(
													ManageLocation.this,
													"Unable to update location",
													Toast.LENGTH_SHORT).show();

										}
									});// post
								}
							}
						}
					}
				});
				thread2.start();

				super.onActivityResult(requestCode, resultCode, intent);
			} // end of if (resultCode == RESULT_OK)

			// for delete location
			if (resultCode == 10) {
				progressDialog = createDialog.createProgressDialog("Loading",
						"Please wait for a while.", true, getResources()
								.getDrawable(R.anim.progress_dialog_animation));
				progressDialog.show();

				final Thread thread = new Thread() {
					public void run() {

						final ParseObject deletedLocation = dao
								.deleteLocation(intent
										.getStringExtra("objectId"));
						handler.post(new Runnable() {
							@Override
							public void run() {
								if (deletedLocation != null) {
									locationList.remove(intent.getIntExtra(
											"position", -1));
									locationAdapter.notifyDataSetChanged();
									progressDialog.dismiss();
									Toast.makeText(ManageLocation.this,
											"Location Deleted",
											Toast.LENGTH_SHORT).show();

								} else {
									Thread.currentThread().interrupt();
								}// end of if (object != null)
							}
						}); // end of post
					}
				}; // end of thread
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
							if (thread.getState() != Thread.State.TERMINATED) {
								if (!internetAlert()) {
									j = 1;
									alertDialogChecker();
									handler.post(new Runnable() {
										@Override
										public void run() {
											Toast.makeText(ManageLocation.this,
													"Unable to add location",
													Toast.LENGTH_SHORT).show();

										}
									});// post
								}
							}
						}
					}
				});
				thread2.start();

			} // end of if (resultCode == 10)

		} // end of if (requestCode == UPDATE_LOCATION)
	} // end of function

	@Override
	protected void onResume() {
		super.onResume();
		// overridePendingTransition(R.anim.slide_right, R.layout.fade_out);
	}

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
						// finish();
					}
				});

		handler.post(new Runnable() {
			@Override
			public void run() {
				dismissPreviousProgressBar();
				alertDialog = alertDialogBuilder.create();
				alertDialog.show();

			}
		});
	}

	public void alertDialogChecker() {
		while (alertDialog == null) {
			try {
				Thread.currentThread();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while (alertDialog.isShowing()) {
			try {
				Thread.currentThread();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}