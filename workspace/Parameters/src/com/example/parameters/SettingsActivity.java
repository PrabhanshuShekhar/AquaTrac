package com.example.parameters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
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
