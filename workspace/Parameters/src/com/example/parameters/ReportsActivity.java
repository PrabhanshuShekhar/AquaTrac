package com.example.parameters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ReportsActivity extends Activity {
	private static int SELECT_PARAMETER = 1;
	private static int SELECT_POND = 10;
	ParseProxyObject selectedParameter;
	String[] locationIdArray;
	String[] locationNameArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reports_activity);
	}

	public void onSelectParameter(View view) {
		Intent intent = new Intent(this, SelectParameterActivity.class);
		startActivityForResult(intent, SELECT_PARAMETER);
	}

	public void onSelectPond(View view) {
		Intent intent = new Intent(this, SelectLocationActivity.class);
		startActivityForResult(intent, SELECT_POND);
	}

	public void onGenerateReports(View view) {
		if (!validate())
			return;
		RadioGroup group = (RadioGroup) findViewById(R.id.radio_group_duration);
		int y = group.getCheckedRadioButtonId();
		RadioButton button = (RadioButton) findViewById(y);

		Intent intent = new Intent(this, GraphActivity.class);
		intent.putExtra("selected_parameter", selectedParameter);
		intent.putExtra("locationId", locationIdArray);
		intent.putExtra("locationName", locationNameArray);

		if (button.getText().equals("Weekly Report"))
			intent.putExtra("minus_days", 1);
		startActivity(intent);
	}

	public boolean validate() {
		AlertDialog alertDialog;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Oops").setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		
		if (selectedParameter == null) {
			alertDialogBuilder.setMessage("Choose a parameter.");
			alertDialog = alertDialogBuilder.create();
			alertDialog.show();
			return false;
		}
		if (locationIdArray == null) {
			alertDialogBuilder.setMessage("Choose atleast 1 location.");
			alertDialog = alertDialogBuilder.create();
			alertDialog.show();
			return false;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == SELECT_PARAMETER)
			if (resultCode == RESULT_OK) {
				selectedParameter = (ParseProxyObject) intent
						.getSerializableExtra("selected_parameter");
				super.onActivityResult(requestCode, resultCode, intent);
			}
		if (requestCode == SELECT_POND)
			if (resultCode == RESULT_OK) {
				// if (locationIdArray == null)
				// System.out.println("right now null");
				locationIdArray = intent
						.getStringArrayExtra("location_id_array");
				locationNameArray = intent
						.getStringArrayExtra("location_name_array");
				super.onActivityResult(requestCode, resultCode, intent);
			}
	}

}