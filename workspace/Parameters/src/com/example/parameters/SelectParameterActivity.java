package com.example.parameters;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;

import com.mw.aquatrack.DAO.ParameterDAO;
import com.mw.aquatrack.adapters.ParameterAdapter;
import com.parse.ParseObject;


public class SelectParameterActivity extends Activity {

	ParameterDAO dao;
	ParameterAdapter parameterAdapter;
	ListView listView;
	List<ParseObject> parameterList;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_parameter_list);

		dao = new ParameterDAO(this);
		parameterList = dao.getAllParameters();

		listView = (ListView) findViewById(R.id.parameters_list);
		parameterAdapter = new ParameterAdapter(this, parameterList,1,0);
		listView.setAdapter(parameterAdapter);
		
	}
	
	private RadioButton listRadioButton = null;
//	private RadioButton listRadioButton = (RadioButton)((View)listView.getChildAt(0)).findViewById(R.id.parameter_radioButton);
	int listIndex = -1;

	   public void onClickRadioButton(View view) {
		   final int position = listView.getPositionForView(view);
			System.out.println("postion selected is : " + position);
	        View rowElement = ((View) view.getParent());
	        // getParent() must be added 'n' times, 
	        // where 'n' is the number of RadioButtons' nested parents
	        // in your case is one.

	        // uncheck previous checked button. 
	        if (listRadioButton != null) 
	        	listRadioButton.setChecked(false);
	        // assign to the variable the new one
	        listRadioButton = (RadioButton) view;
	        // find if the new one is checked or not, and set "listIndex"
	        if (listRadioButton.isChecked()) {
	            listIndex = ((ViewGroup) rowElement.getParent()).indexOfChild(rowElement); 
	        } 
//	        if (!(listRadioButton.isChecked()))    
	        else 
	            {
	            listRadioButton = null;
	            listIndex = -1;
	        }
	        
	        System.out.println("list index  :  " + listIndex);
	    }
	   
	   public void onDone(View view)
	   {
		   ParseProxyObject selectedParameter = new ParseProxyObject(parameterList.get(listIndex));
		   intent = getIntent();
		   intent.putExtra("selected_parameter", selectedParameter);
		   setResult(RESULT_OK, intent);
		   finish();
	   }
	
}