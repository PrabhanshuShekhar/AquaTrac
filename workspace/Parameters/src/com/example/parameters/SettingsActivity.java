package com.example.parameters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class SettingsActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_settings);
	}

	public void onManageLocation(View view)
	{
		startActivity(new Intent(SettingsActivity.this, ManageLocation.class));
	}
	
	public void onManageParameter(View view)
	{
		startActivity(new Intent(SettingsActivity.this, ManageParameter.class));
	}

}