package com.example.parameters;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mw.aquatrack.email.Mail;

public class SettingsActivity extends Activity {
	TextView manageLocationTextView;
	TextView manageParameterTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		System.out.println("create");
		setContentView(R.layout.activity_settings);
		manageLocationTextView = (TextView) findViewById(R.id.manage_location_textView);
		manageParameterTextView = (TextView) findViewById(R.id.manage_parameter_textView);
	}

	public void onManageLocation(View view) {
		manageLocationTextView.setClickable(false);
		startActivity(new Intent(SettingsActivity.this, ManageLocation.class));
	}

	public void onManageParameter(View view) {
		manageParameterTextView.setClickable(false);
		startActivity(new Intent(SettingsActivity.this, ManageParameter.class));
	}

	public void onEmail(View view) {
		AsyncExample asyncExample = new AsyncExample();
		asyncExample.execute(new String[] { "hello wrold" });

	}

	private class AsyncExample extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			Mail mail = new Mail();
			try {
				mail.send();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	@Override
	protected void onResume() {
		manageLocationTextView.setClickable(true);
		manageParameterTextView.setClickable(true);
		super.onResume();
	}

}