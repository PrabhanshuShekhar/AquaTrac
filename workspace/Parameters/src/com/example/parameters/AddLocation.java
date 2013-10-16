package com.example.parameters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddLocation extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_location);
		Intent intent = getIntent();
		if(intent.hasExtra("current_name"))
			((EditText)findViewById(R.id.location_name_editText)).setText(intent.getStringExtra("current_time"));
		if(intent.hasExtra("current_description"))
			((EditText)findViewById(R.id.location_description_editText)).setText(intent.getStringExtra("current_note"));

	}

	public void onSave(View view) {
		Intent intent = getIntent();
		intent.putExtra("name", ( (EditText)findViewById(R.id.location_name_editText) ).getText().toString());
		intent.putExtra("description", ( (EditText)findViewById(R.id.location_description_editText) ).getText().toString());
		this.setResult(RESULT_OK, intent);
		finish();
	}

	public void onCancel(View view) {
		finish();
	}

}
