package com.example.parameters;

import com.mw.aquatrack.adapters.ParameterAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ManageParameter extends Activity{

	ListView listView;
	ParameterAdapter parameterAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_parameter_list);
		
		listView = (ListView)findViewById(R.id.parameters_list);
		parameterAdapter = new ParameterAdapter(this);
		if(parameterAdapter!=null)
		System.out.println("not null");
//		listView.setAdapter(parameterAdapter);
		System.out.println("manage parameters");
	}

	
	
}
