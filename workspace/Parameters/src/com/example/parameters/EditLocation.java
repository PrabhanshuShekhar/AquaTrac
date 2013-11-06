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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EditLocation extends Activity {
	public static int DELETE = 10;
	EditText editText1;
	EditText editText2;
	LinearLayout buttonLinearLayout;
	Intent intent;
	ImageButton clearLocNameButton;
	ImageButton clearLocDescButton;
	String currentLocation;
	String currentDescription;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_location);
		((RelativeLayout) findViewById(R.id.add_location_main_layout))
				.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(getCurrentFocus()
								.getWindowToken(), 0);
						return false;
					}
				});

		clearLocNameButton = (ImageButton) findViewById(R.id.clear_location_name_imageButton);
		clearLocDescButton = (ImageButton) findViewById(R.id.clear_location_description_imageButton);
		clearLocNameButton.setVisibility(View.INVISIBLE);
		clearLocDescButton.setVisibility(View.INVISIBLE);

		buttonLinearLayout = (LinearLayout) findViewById(R.id.linearLayoutButtons);
		buttonLinearLayout.setVisibility(View.GONE);
		((TextView) findViewById(R.id.location_heading_textView))
				.setText("EDIT LOCATION");
		intent = getIntent();
		if (intent.hasExtra("current_location_name")) {
			currentLocation = intent.getStringExtra("current_location_name");
			editText1 = (EditText) findViewById(R.id.location_name_editText);
			editText1.setText(currentLocation);
			editText1.setSelection(currentLocation.length());
		}
		editText1.setEnabled(false);

		if (intent.hasExtra("current_location_description")) {
			currentDescription = intent
					.getStringExtra("current_location_description");
			editText2 = (EditText) findViewById(R.id.location_description_editText);
			editText2.setText(currentDescription);
			if (currentDescription != null)
				editText2.setSelection(currentDescription.length());
		}
		editText2.setEnabled(false);

		editText1.addTextChangedListener(new TextWatcher() {
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
				if (editText1.getText().toString().length() > 0)
					clearLocNameButton.setVisibility(View.VISIBLE);
				else
					clearLocNameButton.setVisibility(View.INVISIBLE);

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
				if (editText2.getText().toString().length() > 0)
					clearLocDescButton.setVisibility(View.VISIBLE);
				else
					clearLocDescButton.setVisibility(View.INVISIBLE);
			}
		});
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
				// Do nothing
				break;
			}
		}
	};

	DialogInterface.OnClickListener backDialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				finish();
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				actuallySave();
				break;
			}
		}
	};
	
	public boolean validate() {
		if (editText1.getText().toString().trim().length() == 0) {
			editText1.requestFocus();
			editText1.setError("You must enter location name.");
			return false;
		}
		return true;
	}

	/* on UI this is shown as Update button */
	private void actuallySave()
	{
		if (!validate())
			return;
		intent.putExtra("name", editText1.getText().toString().trim());
		intent.putExtra("description", editText2.getText().toString().trim());
		this.setResult(RESULT_OK, intent);
		finish();
	}
	
	public void onSave(View view) {
		actuallySave();
	}

	public void onClearScreen(View view) {
		int crossButtonPressedID = ((ImageButton) view).getId();
		int clearLocNameButtonId = clearLocNameButton.getId();
		if (crossButtonPressedID == clearLocNameButtonId) {
			editText1.setText("");
		} else {
			editText2.setText("");
		}
		view.setVisibility(View.INVISIBLE);
	}

	public void onDelete(View view) {
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setMessage("Are you sure you want to delete?").setCancelable(false)
				.setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();
	}

	public void onEdit(View view) {
		editText1.setEnabled(true);
		editText2.setEnabled(true);
		editText1.setHint("Location Name");
		editText2.setHint("Location Description");
		clearLocNameButton.setVisibility(View.VISIBLE);
		if (editText2.getText().toString().length() > 0)
			clearLocDescButton.setVisibility(View.VISIBLE);
		buttonLinearLayout.setVisibility(View.VISIBLE);
		((Button) buttonLinearLayout.findViewById(R.id.location_save))
				.setText("Update");
	}

	public void onCancel(View view) {
		finish();
	}

	@Override
	public void onBackPressed() {
		if (editText1.getText().toString().trim().equals(currentLocation)
				&& editText2.getText().toString().trim()
						.equals(currentDescription)) {
			super.onBackPressed();
		} else {
			AlertDialog.Builder ab = new AlertDialog.Builder(this);
			ab.setMessage("Save Changes?")
					.setPositiveButton("Discard", backDialogClickListener)
					.setNegativeButton("Save", backDialogClickListener).show();
		}
	}

	public void onExperiment(View view) {
		System.out.println("experiment");
	}
}