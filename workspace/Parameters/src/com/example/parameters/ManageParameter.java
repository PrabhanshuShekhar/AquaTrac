package com.example.parameters;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.mw.aquatrack.DAO.ParameterDAO;
import com.mw.aquatrack.adapters.ParameterAdapter;
import com.parse.ParseObject;

public class ManageParameter extends Activity {

	ParameterDAO dao;
	ParameterAdapter parameterAdapter;
	ListView listView;
	List<ParseObject> parameterList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_parameter_list);

		dao = new ParameterDAO(this);
		parameterList = dao.getAllParameters();

		listView = (ListView) findViewById(R.id.parameters_list);
		parameterAdapter = new ParameterAdapter(this, parameterList);
		listView.setAdapter(parameterAdapter);

	}
	
	public void onSingleEdit(View view)
	{
		final int postion = listView.getPositionForView(view);
		System.out.println("postion selected is : " + postion);
	}

}