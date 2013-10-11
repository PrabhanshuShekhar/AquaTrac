package com.example.parameters;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class AddParameterValueActivity extends Activity {
	TextView slider,slider_value;
	SeekBar seekbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_parameter_value);
		slider_value = (TextView) findViewById(R.id.slider_value);
		 slider = (TextView) findViewById(R.id.slider);
		slider.setText(getIntent().getStringExtra("param_name"));
		 seekbar = (SeekBar) findViewById(R.id.seekbar);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser)
			{
				slider_value.setText(""+progress);
			}
			
			public void onStartTrackingTouch(SeekBar seekbar)
			{
				
			}
			
			public void onStopTrackingTouch(SeekBar seekbar)
			{
				
			}
		});
	}
	
	public void submit(View v)
	{
		Toast.makeText(this,"save Clicked", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_parameter_value, menu);
		return true;
	}

}
