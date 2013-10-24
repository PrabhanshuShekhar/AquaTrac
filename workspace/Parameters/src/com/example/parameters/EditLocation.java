package com.example.parameters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditLocation extends Activity {
	public static int DELETE = 10;
	EditText editText1;
	EditText editText2;
	LinearLayout buttonLinearLayout;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_location);
		buttonLinearLayout = (LinearLayout) findViewById(R.id.linearLayoutButtons);
		buttonLinearLayout.setVisibility(View.GONE);
		((TextView) findViewById(R.id.location_heading_textView))
				.setText("EDIT LOCATION");
		intent = getIntent();
		if (intent.hasExtra("current_location_name")) {
			String currentLocation = intent
					.getStringExtra("current_location_name");
			editText1 = (EditText) findViewById(R.id.location_name_editText);
			editText1.setText(currentLocation);
			editText1.setSelection(currentLocation.length());
		}
		if (intent.hasExtra("current_location_description")) {
			String currentDescription = intent
					.getStringExtra("current_location_description");
			editText2 = (EditText) findViewById(R.id.location_description_editText);
			editText2.setText(currentDescription);
			editText2.setSelection(currentDescription.length());
		}
		editText1.setEnabled(false);
		editText2.setEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				EditLocation.this.setResult(DELETE, intent);
				finish();
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				// Do your No progress
				break;
			}
		}
	};
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_edit:
			editText1.setEnabled(true);
			editText2.setEnabled(true);
			buttonLinearLayout.setVisibility(View.VISIBLE);
			((Button) buttonLinearLayout.findViewById(R.id.location_save))
					.setText("Update");
			return true;
		case R.id.action_delete:

			AlertDialog.Builder ab = new AlertDialog.Builder(this);
			ab.setMessage("Are you sure you want to delete?")
					.setPositiveButton("Yes", dialogClickListener)
					.setNegativeButton("No", dialogClickListener).show();

			

			
			return true;
		default:
			return false;
		}
	}
/*	on UI this is shown as Update button  */
	public void onSave(View view) {			
		intent.putExtra("name",
				((EditText) findViewById(R.id.location_name_editText))
						.getText().toString());
		intent.putExtra("description",
				((EditText) findViewById(R.id.location_description_editText))
						.getText().toString());
		this.setResult(RESULT_OK, intent);
		finish();
	}

	public void onCancel(View view) {
		finish();
	}

}