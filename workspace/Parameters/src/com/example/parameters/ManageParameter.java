package com.example.parameters;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.mw.aquatrack.DAO.ParameterDAO;
import com.mw.aquatrack.adapters.ParameterAdapter;
import com.parse.ParseObject;

public class ManageParameter extends Activity {
	public static final int UPDATE_PARAMETER = 1;
	ParameterDAO dao;
	ParameterAdapter parameterAdapter;
	ListView listView;
	List<ParseObject> parameterList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.manage_parameter_list);

		dao = new ParameterDAO(this);
		parameterList = dao.getAllParameters();
		((Button) findViewById(R.id.parameter_done_button))
				.setVisibility(View.GONE);
		listView = (ListView) findViewById(R.id.parameters_list);
		parameterAdapter = new ParameterAdapter(this, parameterList, 0, 1);
		listView.setAdapter(parameterAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				System.out.println("item being clicked");
				final int position = listView.getPositionForView(arg1);
				// listView.setItemChecked(position, true);
				// System.out.println("postion selected is : " + position);
				System.out.println("postion selected is : " + position);

				// listView.setItemChecked(position, true);
				ParseObject selectedParameter = parameterList.get(position);

				Intent intent = new Intent(ManageParameter.this,
						EditParameterActivity.class);

				intent.putExtra("current_critical_start_value",
						selectedParameter.getNumber("criticalStartRange"));
				intent.putExtra("current_critical_end_value",
						selectedParameter.getNumber("criticalEndRange"));

				intent.putExtra("start_value",
						selectedParameter.getNumber("startingRange"));
				intent.putExtra("end_value",
						selectedParameter.getNumber("endRange"));

				intent.putExtra("objectId", selectedParameter.getObjectId());
				intent.putExtra("parameterName",
						selectedParameter.getString("parameterName"));

				intent.putExtra("position", position);

				startActivityForResult(intent, UPDATE_PARAMETER);
			}
		});

	}

	// public void onSingleEdit(View view) {
	// final int position = listView.getPositionForView(view);
	//
	// System.out.println("postion selected is : " + position);
	//
	// listView.setItemChecked(position, true);
	// ParseObject selectedParameter = parameterList.get(position);
	//
	// Intent intent = new Intent(this, EditParameterActivity.class);
	//
	// intent.putExtra("current_critical_start_value",
	// selectedParameter.getNumber("criticalStartRange"));
	// intent.putExtra("current_critical_end_value",
	// selectedParameter.getNumber("criticalEndRange"));
	//
	// intent.putExtra("start_value",
	// selectedParameter.getNumber("startingRange"));
	// intent.putExtra("end_value", selectedParameter.getNumber("endRange"));
	//
	// intent.putExtra("objectId", selectedParameter.getObjectId());
	// intent.putExtra("parameterName",
	// selectedParameter.getString("parameterName"));
	//
	// intent.putExtra("position", position);
	//
	// startActivityForResult(intent, UPDATE_PARAMETER);
	// }

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == UPDATE_PARAMETER)
			if (resultCode == RESULT_OK) {
				dao = new ParameterDAO(this);
				ParseObject object = dao.updateParameter(
						intent.getStringExtra("objectId"),
						intent.getIntExtra("updated_critical_start_value", 0),
						intent.getIntExtra("updated_critical_end_value", 0));
				parameterList.set(intent.getIntExtra("position", -1), object);
				parameterAdapter.notifyDataSetChanged();
				super.onActivityResult(requestCode, resultCode, intent);
			}
	}

}