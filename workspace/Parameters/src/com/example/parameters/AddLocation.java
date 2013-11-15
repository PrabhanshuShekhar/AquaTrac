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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AddLocation extends Activity {
	EditText editText1;
	EditText editText2;
	ImageButton clearLocNameButton;
	ImageButton clearLocDescButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_location);
		((TextView) findViewById(R.id.location_name_textView))
				.setVisibility(View.GONE);
		((TextView) findViewById(R.id.location_description_textView))
				.setVisibility(View.GONE);
		((ImageButton)findViewById(R.id.edit_location)).setVisibility(View.INVISIBLE);
		((ImageButton)findViewById(R.id.delete_location)).setVisibility(View.INVISIBLE);
		editText1 = (EditText) findViewById(R.id.location_name_editText);
		editText2 = (EditText) findViewById(R.id.location_description_editText);
		editText1.setHint("Location Name");
		editText2.setHint("Location Description");
		clearLocNameButton = (ImageButton) findViewById(R.id.clear_location_name_imageButton);
		clearLocDescButton = (ImageButton) findViewById(R.id.clear_location_description_imageButton);
		clearLocNameButton.setVisibility(View.INVISIBLE);
		clearLocDescButton.setVisibility(View.INVISIBLE);

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
				System.out.println(editText2.getId());
				if (editText2.getText().toString().length() > 0)
					clearLocDescButton.setVisibility(View.VISIBLE);
				else
					clearLocDescButton.setVisibility(View.INVISIBLE);
			}
		});

	}// on Create

	public boolean validate() {
		if (editText1.getText().toString().trim().length() == 0) {
			editText1.requestFocus();
			editText1.setError("You must enter location name.");
			return false;
		}
		return true;
	}

	public void onSave(View view) {
		actuallySave();
	}

	public void onCancel(View view) {
		finish();
		overridePendingTransition(R.anim.slide_left, R.anim.slide_left);
	}

	public void onClearScreen(View view) {
		int temp1 = ((ImageButton) view).getId();
		int clearLocNameButtonId = clearLocNameButton.getId();
		System.out.println("pressing");
		if (temp1 == clearLocNameButtonId) {
			editText1.setText("");
		} else {
			editText2.setText("");
		}
		view.setVisibility(View.INVISIBLE);
	}

	DialogInterface.OnClickListener backDialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				finish();
				overridePendingTransition(R.anim.slide_left, R.anim.slide_left);
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				actuallySave();
				break;
			}
		}
	};
	
	private void actuallySave() {
		if (!validate())
			return;
		Intent intent = getIntent();
		intent.putExtra("name", editText1.getText().toString());
		intent.putExtra("description", editText2.getText().toString());
		this.setResult(RESULT_OK, intent);
		finish();
		overridePendingTransition(R.anim.slide_left, 0);
	}
	
	@Override
	public void onBackPressed() {
		if(editText1.getText().toString().trim().length()>0 || editText2.getText().toString().trim().length()>0)
		{AlertDialog.Builder ab = new AlertDialog.Builder(this);
			ab.setMessage("Save Changes?")
					.setPositiveButton("Discard", backDialogClickListener)
					.setNegativeButton("Save", backDialogClickListener).show();}
		else
//			super.onBackPressed();
		{	finish();
		overridePendingTransition(R.anim.slide_left, 0);}
	}

	
}