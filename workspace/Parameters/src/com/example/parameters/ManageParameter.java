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

import com.mw.aquatrack.DAO.ParameterDAO;
import com.mw.aquatrack.adapters.ParameterAdapter;
import com.mw.aquatrack.services.CreateDialog;
import com.mw.aquatrack.services.MyApplication;
import com.parse.ParseObject;

public class ManageParameter extends Activity {
	public static final int UPDATE_PARAMETER = 1;
	ParameterDAO dao;
	ParameterAdapter parameterAdapter;
	ListView listView;
	List<ParseObject> parameterList;
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
		setContentView(R.layout.manage_parameter_list);
		application = (MyApplication) getApplication();
		createDialog = new CreateDialog(this);
		handler = new Handler();
		dao = new ParameterDAO(this);
		((Button) findViewById(R.id.parameter_done_button))
				.setVisibility(View.GONE);
		listView = (ListView) findViewById(R.id.parameters_list);

		progressDialog = new ProgressDialog(this);
		progressDialog.setIndeterminate(true);
		progressDialog.setIndeterminateDrawable(getResources().getDrawable(
				R.anim.progress_dialog_animation));
		progressDialog.setTitle("Loading");
		progressDialog.setMessage("Please wait a while.");
		progressDialog.show();
		final Thread thread = new Thread() {
			public void run() {
				System.out.println("thread");
				parameterList = dao.getAllParameters();
				if (parameterList == null) {
					finish();
					Thread.currentThread().interrupt();
				} else {
					parameterAdapter = new ParameterAdapter(
							ManageParameter.this, parameterList, 0);
					handler.post(new Runnable() {
						@Override
						public void run() {
							listView.setAdapter(parameterAdapter);
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

				ParseObject selectedParameter = parameterList.get(position);

				Intent intent = new Intent(ManageParameter.this,
						EditParameterActivity.class);

				intent.putExtra("current_critical_start_value",
						selectedParameter.getDouble("criticalStartRange"));
				intent.putExtra("current_critical_end_value",
						selectedParameter.getDouble("criticalEndRange"));

				intent.putExtra("start_value",
						selectedParameter.getDouble("startingRange"));
				intent.putExtra("end_value",
						selectedParameter.getDouble("endRange"));

				intent.putExtra("objectId", selectedParameter.getObjectId());
				intent.putExtra("parameterName",
						selectedParameter.getString("parameterName"));

				intent.putExtra("position", position);

				startActivityForResult(intent, UPDATE_PARAMETER);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			final Intent intent) {
		if (requestCode == UPDATE_PARAMETER)
			if (resultCode == RESULT_OK) {
				dao = new ParameterDAO(this);
				progressDialog = new ProgressDialog(this);
				progressDialog.setIndeterminate(true);
				progressDialog.setIndeterminateDrawable(getResources()
						.getDrawable(R.anim.progress_dialog_animation));
				progressDialog.setTitle("Updating Parameter");
				progressDialog.setMessage("Please wait a while.");
				progressDialog.show();
				final Thread thread = new Thread() {
					public void run() {
						System.out.println("thread");
						final ParseObject updatedParameter = dao
								.updateParameter(intent
										.getStringExtra("objectId"), intent
										.getDoubleExtra(
												"updated_critical_start_value",
												0), intent.getDoubleExtra(
										"updated_critical_end_value", 0));
						if (updatedParameter == null) {
							// finish();
							Thread.currentThread().interrupt();
						} else {
							handler.post(new Runnable() {
								@Override
								public void run() {
									parameterList.set(
											intent.getIntExtra("position", -1),
											updatedParameter);
									parameterAdapter.notifyDataSetChanged();
									progressDialog.dismiss();
									Toast.makeText(ManageParameter.this,
											"Parameter Updated",
											Toast.LENGTH_SHORT).show();
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
							if (thread.getState() != Thread.State.TERMINATED)
								if (!internetAlert())
									j = 1;
						}
					}
				});
				thread2.start();

				super.onActivityResult(requestCode, resultCode, intent);
			}
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
					public void onClick(DialogInterface alertDialog, int id) {
						alertDialog.dismiss();
						// finish();
					}
				});

		handler.post(new Runnable() {
			@Override
			public void run() {
				dismissPreviousProgressBar();
				alertDialog = alertDialogBuilder.create();
				alertDialog.show();
				// dialog.is
			}
		});
	}
}