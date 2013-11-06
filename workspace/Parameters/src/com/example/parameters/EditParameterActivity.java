package com.example.parameters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditParameterActivity extends Activity {
	Intent intent;
	EditText editText;
	EditText editText2;
	double startValue;
	double endValue;
	Double currentCriticalStartValue;
	Double currentCriticalEndValue;
	ImageButton clearStartCriticValueButton;
	ImageButton clearEndCriticValueButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit_parameter);

		((LinearLayout) findViewById(R.id.edit_parameter_main_layout))
				.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(getCurrentFocus()
								.getWindowToken(), 0);
						return false;
					}
				});

		intent = getIntent();
		editText = (EditText) findViewById(R.id.parameter_critical_start_editText);
		editText2 = (EditText) findViewById(R.id.parameter_critical_end_editText);

		if (intent.hasExtra("parameterName")) {
			TextView textView = (TextView) findViewById(R.id.parameter_name_textView);
			textView.setText(intent.getStringExtra("parameterName"));
		}

		if (intent.hasExtra("current_critical_start_value")) {
			currentCriticalStartValue = intent.getDoubleExtra(
					"current_critical_start_value", 0);
			System.out.println("x is  :  " + currentCriticalStartValue
					+ Double.toString(currentCriticalStartValue));
			if (currentCriticalStartValue != 0)
				editText.setText(Double.toString(currentCriticalStartValue));
			else
				editText.setText("");
			// editText.setSelection(Float.toString(x).length());
		}
//		editText.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
		if (intent.hasExtra("current_critical_end_value")) {
			currentCriticalEndValue = intent.getDoubleExtra(
					"current_critical_end_value", 0);
			if (currentCriticalEndValue != 0)
				editText2.setText(Double.toString(currentCriticalEndValue));
			else
				editText2.setText("");
		}

		startValue = intent.getDoubleExtra("start_value", 0);
		endValue = intent.getDoubleExtra("end_value", 0);
		
		clearStartCriticValueButton = (ImageButton) findViewById(R.id.clear_start_critic_value);
		clearEndCriticValueButton = (ImageButton) findViewById(R.id.clear_end_critic_value);
		
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (editText.getText().toString().length() > 0)
					clearStartCriticValueButton.setVisibility(View.VISIBLE);
				else
					clearStartCriticValueButton.setVisibility(View.INVISIBLE);
			}
		});

		editText2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				System.out.println(editText2.getId());
				if (editText2.getText().toString().length() > 0)
					clearEndCriticValueButton.setVisibility(View.VISIBLE);
				else
					clearEndCriticValueButton.setVisibility(View.INVISIBLE);
			}
		});
		
//		clearStartCriticValue.setVisibility(View.INVISIBLE);
//		clearEndCriticValue.setVisibility(View.INVISIBLE);

	}

	public boolean validate() {
		String startRangeString = editText.getText().toString();
		String endRangeString = editText2.getText().toString();
		if (startRangeString.length() == 0) {
			editText.setError("Please enter start critical value.");
			editText.requestFocus();
			return false;
		}
		if (endRangeString.length() == 0) {
			editText2.setError("Please enter end critical value.");
			editText2.requestFocus();
			return false;
		}
		double temp1 = Double.parseDouble(startRangeString);
		double temp2 = Double.parseDouble(endRangeString);

		if (temp1 >= temp2) {
			editText.setError("Starting Critical Value should be less than End Critical Value.");
			editText.requestFocus();
			return false;
		}
		if (temp1 <= startValue) {
			editText.setError("Value should be greater than starting range("
					+ startValue + ")");
			editText.requestFocus();
			return false;
		}
		if (temp2 >= endValue) {
			editText2.setError("Value should be less than ending range("
					+ endValue + ")");
			editText2.requestFocus();
			return false;
		}
		return true;
	}

	public void onUpdate(View view) {
		actuallyUpdate();
	}

	public void onCancel(View view) {
		finish();
	}

	public void actuallyUpdate() {
		if (!validate())
			return;
		intent.putExtra("updated_critical_start_value",
				Double.parseDouble(editText.getText().toString()));
		intent.putExtra("updated_critical_end_value",
				Double.parseDouble(editText2.getText().toString()));

		this.setResult(RESULT_OK, intent);
		finish();
	}

	DialogInterface.OnClickListener backDialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				finish();
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				actuallyUpdate();
				break;
			}
		}
	};

	@Override
	public void onBackPressed() {
		String temp = editText.getText().toString();
		String temp2 = editText2.getText().toString();
		if (temp.length() == 0 || temp2.length() == 0)
		{
			System.out.println("one of em is zero");
			super.onBackPressed();
		}
		else if (currentCriticalStartValue.equals(Double.parseDouble(editText
				.getText().toString()))
				&& currentCriticalEndValue.equals(Double.parseDouble(editText2
						.getText().toString()))) {
			super.onBackPressed();
		} else {
			AlertDialog.Builder ab = new AlertDialog.Builder(this);
			ab.setMessage("Save Changes?")
					.setPositiveButton("Discard", backDialogClickListener)
					.setNegativeButton("Save", backDialogClickListener).show();
		}
	}
	
	public void onClear(View view)
	{
		int temp1 = ((ImageButton) view).getId();
		int clearStartCriticValueId = clearStartCriticValueButton.getId();
		if (temp1 == clearStartCriticValueId) {
			editText.setText("");
		} else {
			editText2.setText("");
		}
		view.setVisibility(View.INVISIBLE);
	}
}