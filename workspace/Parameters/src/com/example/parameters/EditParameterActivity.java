package com.example.parameters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class EditParameterActivity extends Activity {
	Intent intent;
	EditText editText2;
	EditText editText;
	int startValue;
	int endValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit_parameter);
		intent = getIntent();

		if (intent.hasExtra("parameterName")) {
			TextView textView = (TextView) findViewById(R.id.parameter_name_textView);
			textView.setText(intent.getStringExtra("parameterName"));
		}

		if (intent.hasExtra("current_critical_start_value")) {
			editText = (EditText) findViewById(R.id.parameter_critical_start_editText);
			Integer x = intent.getIntExtra("current_critical_start_value", 0);
			System.out.println("x is  :  " + x + Integer.toString(x));
			if (x != 0)
				editText.setText(Integer.toString(x));
			else
				editText.setText("");
			editText.setSelection(Integer.toString(x).length());
		}

		if (intent.hasExtra("current_critical_end_value")) {
			Integer x = intent.getIntExtra("current_critical_end_value", 0);
			editText2 = (EditText) findViewById(R.id.parameter_critical_end_editText);
			if (x != 0)
				editText2.setText(Integer.toString(x));
			else
				editText2.setText("");
		}

		startValue = intent.getIntExtra("start_value", 0);
		endValue = intent.getIntExtra("end_value", 0);

	}

	public boolean validate() {
		editText = (EditText) findViewById(R.id.parameter_critical_start_editText);
		editText2 = (EditText) findViewById(R.id.parameter_critical_end_editText);
		if (Integer.parseInt(editText.getText().toString()) >= Integer
				.parseInt(editText2.getText().toString())) {
			editText.setError("Starting Range should be less than end range.");
			editText.requestFocus();
			return false;
		}
		if (Integer.parseInt(editText.getText().toString()) <= startValue) {
			editText.setError("Value should be greater than starting range("
					+ startValue + ")");
			editText.requestFocus();
			return false;
		}
		if (Integer.parseInt(editText2.getText().toString()) >= endValue) {
			editText2.setError("Value should be less than ending range("
					+ endValue + ")");
			editText2.requestFocus();
			return false;
		}
		return true;
	}

	public void onUpdate(View view) {
		if (!validate())
			return;
		intent.putExtra(
				"updated_critical_start_value",
				Integer.parseInt(((EditText) findViewById(R.id.parameter_critical_start_editText))
						.getText().toString()));
		intent.putExtra(
				"updated_critical_end_value",
				Integer.parseInt(((EditText) findViewById(R.id.parameter_critical_end_editText))
						.getText().toString()));
		this.setResult(RESULT_OK, intent);
		finish();
	}

	public void onCancel(View view) {
		finish();
	}

}