package com.example.parameters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddLocation extends Activity {
	EditText editText1;
	EditText editText2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_location);
		editText1 = (EditText) findViewById(R.id.location_name_editText);
		editText2 = (EditText) findViewById(R.id.location_description_editText);
	}

	public boolean validate() {
		if (editText1.getText().toString().trim().length() == 0) {
			editText1.requestFocus();
			editText1.setError("You must enter location name.");
			return false;
		}
		return true;
	}

	public void onSave(View view) {
		if (!validate())
			return;
		Intent intent = getIntent();
		intent.putExtra("name", editText1.getText().toString());
		intent.putExtra("description", editText2.getText().toString());
		this.setResult(RESULT_OK, intent);
		finish();
	}

	public void onCancel(View view) {
		finish();
	}

}