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

import com.mw.aquatrack.DAO.ParameterDAO;
import com.mw.aquatrack.adapters.ParameterAdapter;
import com.parse.ParseObject;

public class ManageParameter extends Activity {
	public static final int UPDATE_PARAMETER = 1;
	ParameterDAO dao;
	ParameterAdapter parameterAdapter;
	ListView listView;
	List<ParseObject> parameterList;
	Handler handler;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.manage_parameter_list);
		handler = new Handler();
		dao = new ParameterDAO(this);
		((Button) findViewById(R.id.parameter_done_button))
				.setVisibility(View.GONE);
		listView = (ListView) findViewById(R.id.parameters_list);

		dialog = ProgressDialog.show(this, "Loading",
				"Please wait for a while.");
		Thread thread = new Thread() {
			public void run() {
				System.out.println("thread");
				parameterList = dao.getAllParameters();
				parameterAdapter = new ParameterAdapter(ManageParameter.this,
						parameterList, 0);
				handler.post(new Runnable() {
					@Override
					public void run() {
						listView.setAdapter(parameterAdapter);
						System.out.println("handler");
						dialog.dismiss();

					}
				});// post
			}
		};// thread
		thread.start();

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				System.out.println("item being clicked");
				final int position = listView.getPositionForView(arg1);
				// listView.setItemChecked(position, true);
				// System.out.println("postion selected is : " + position);
				System.out.println("postion selected is : " + position);

				// listView.setItemChecked(position, true);
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
				dialog = ProgressDialog.show(this, "Updating Parameter",
						"Please wait for a while.");
				Thread thread = new Thread() {
					public void run() {
						System.out.println("thread");
						final ParseObject object = dao.updateParameter(intent
								.getStringExtra("objectId"), intent
								.getDoubleExtra("updated_critical_start_value",
										0), intent.getDoubleExtra(
								"updated_critical_end_value", 0));

						handler.post(new Runnable() {
							@Override
							public void run() {
								parameterList.set(
										intent.getIntExtra("position", -1),
										object);
								parameterAdapter.notifyDataSetChanged();
								dialog.dismiss();
								Toast.makeText(ManageParameter.this,
										"Parameter Updated", Toast.LENGTH_SHORT)
										.show();
							}
						});// post
					}
				};// thread
				thread.start();
				super.onActivityResult(requestCode, resultCode, intent);
			}
	}

}