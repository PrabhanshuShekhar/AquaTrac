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
import android.widget.ListView;
import android.widget.RadioButton;

import com.mw.aquatrack.DAO.ParameterDAO;
import com.mw.aquatrack.adapters.ParameterAdapter;
import com.mw.aquatrack.services.CreateDialog;
import com.mw.aquatrack.services.MyApplication;
import com.parse.ParseObject;

public class SelectParameterActivity extends Activity {

	ParameterDAO dao;
	ParameterAdapter parameterAdapter;
	ListView listView;
	List<ParseObject> parameterList;
	Intent intent;
	private RadioButton listRadioButton = null;
	int listIndex = -1;
	Handler handler;
	ProgressDialog progressDialog;
	public static int staticListIndex = -1;
	MyApplication application;
	AlertDialog.Builder alertDialogBuilder;
	CreateDialog createDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		final Dialog mainDialog = new Dialog(this);
		mainDialog.setContentView(R.layout.manage_parameter_list);
		mainDialog.setTitle("Select a Parameter");
		mainDialog.setCancelable(false);
		handler = new Handler();
		application = (MyApplication) getApplication();
		createDialog = new CreateDialog(this);
		progressDialog = createDialog.createProgressDialog("Loading",
				"Please wait for a while.", true,
				getResources().getDrawable(R.anim.progress_dialog_animation));
		progressDialog.show();
		final Thread thread = new Thread() {
			public void run() {

				System.out.println("threaddhu");
				dao = new ParameterDAO(SelectParameterActivity.this);
				parameterList = dao.getAllParameters();

				handler.post(new Runnable() {
					@Override
					public void run() {
						if (parameterList == null) {
							finish();
							Thread.currentThread().interrupt();
						} else {
							listView = (ListView) mainDialog
									.findViewById(R.id.parameters_list);
							parameterAdapter = new ParameterAdapter(
									SelectParameterActivity.this,
									parameterList, 1);
							listView.setAdapter(parameterAdapter);

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

		Button done = (Button) mainDialog
				.findViewById(R.id.parameter_done_button);
		done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!validate())
					return;
				ParseProxyObject selectedParameter = new ParseProxyObject(
						parameterList.get(listIndex));
				intent = getIntent();
				intent.putExtra("selected_parameter", selectedParameter);
				intent.putExtra(
						"critical_start_value",
						parameterList.get(listIndex).getDouble(
								"criticalStartRange"));
				intent.putExtra(
						"critical_end_value",
						parameterList.get(listIndex).getDouble(
								"criticalEndRange"));
				setResult(RESULT_OK, intent);
				mainDialog.dismiss();
				finish();
			}
		});

	}

	public void onClickRadioButton(View view) {
		final int position = listView.getPositionForView(view);
		System.out.println("postion selected is : " + position);
		View rowElement = ((View) view.getParent());

		// uncheck previous checked button.
		if (listRadioButton != null) {
			listRadioButton.setChecked(false);
			System.out.println("listRadioButton is not null");
		} else
			System.out.println("listRadioButton is null");
		// assign to the variable the new one
		listRadioButton = (RadioButton) view;
		// find if the new one is checked or not, and set "listIndex"

		// if (listRadioButton.isChecked()) {
		// listIndex = ((ViewGroup) rowElement.getParent())
		// .indexOfChild(rowElement);
		// }

		if (listRadioButton.isChecked()) {
			listIndex = ((ListView) rowElement.getParent())
					.getPositionForView(rowElement);
			staticListIndex = listIndex;
		} else {
			listRadioButton = null;
			listIndex = -1;
			staticListIndex = listIndex;
		}
		parameterAdapter.notifyDataSetChanged();
		System.out.println("list index  :  " + listIndex);
	}

	public boolean validate() {
		if (listIndex == -1) {
			AlertDialog alertDialog;
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder
					.setTitle("Oops")
					.setMessage("Choose a parameter.")
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
						// thread.interrupt();
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