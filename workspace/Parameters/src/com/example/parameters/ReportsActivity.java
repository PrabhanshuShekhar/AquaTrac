package com.example.parameters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ReportsActivity extends Activity {
	private static int SELECT_PARAMETER = 1;
	private static int SELECT_POND = 10;
	ParseProxyObject selectedParameter;
	String[] locationIdArray;
	String[] locationNameArray;
	ImageView imageView1;
	ImageView imageView2;
	double criticalStartRange;
	double criticalEndRange;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reports_activity);
		SelectParameterActivity.staticListIndex = -1;
		imageView1 = (ImageView) findViewById(R.id.tick_parameter_imageView1);
		imageView1.setVisibility(View.INVISIBLE);
		imageView2 = (ImageView) findViewById(R.id.tick_parameter_imageView2);
		imageView2.setVisibility(View.INVISIBLE);
	}

	public void onSelectParameter(View view) {
		System.out.println("start");
			Intent intent = new Intent(ReportsActivity.this,
					SelectParameterActivity.class);
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
		intent.putExtra("critical_start_value", criticalStartRange);
		intent.putExtra("critical_end_value", criticalEndRange);
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
				criticalStartRange = intent.getDoubleExtra(
						"critical_start_value", 0);
				criticalEndRange = intent.getDoubleExtra("critical_end_value",
						0);
				if (selectedParameter != null)
					imageView1.setVisibility(View.VISIBLE);
				else {
					// handle error condition later
				}
				super.onActivityResult(requestCode, resultCode, intent);
			}
		if (requestCode == SELECT_POND)
			if (resultCode == RESULT_OK) {
				locationIdArray = intent
						.getStringArrayExtra("location_id_array");
				locationNameArray = intent
						.getStringArrayExtra("location_name_array");
				if ((locationIdArray != null) && (locationNameArray != null))
					imageView2.setVisibility(View.VISIBLE);
				else {
					// handle error condition later
				}
				super.onActivityResult(requestCode, resultCode, intent);
			}
	} // end of function

//	public boolean func2() {
//		int temp = application.combinedInternetTest();
//		if (temp != 0) {
//			System.out.println("problem");
//			if (temp == 1) {
//				alertDialogBuilder = createDialog.createAlertDialog(
//						"CONNECTION ERROR", "Connect to WiFi or Data Service.",
//						false);
//				alertDialogBuilder.setPositiveButton("OK",
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog, int id) {
//								dialog.dismiss();
//							}
//						});
//				AlertDialog dialog = alertDialogBuilder.create();
//				dialog.show();
//				return false;
//			}
//			if (temp == 2) {
//				alertDialogBuilder = createDialog.createAlertDialog(
//						"CONNECTION ERROR", "Internet services required.",
//						false);
//				alertDialogBuilder.setPositiveButton("OK",
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog, int id) {
//								dialog.dismiss();
//							}
//						});
//				AlertDialog dialog = alertDialogBuilder.create();
//				dialog.show();
//				return false;
//			}
//		}
//		return true;
//	}

}