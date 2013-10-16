package com.mw.aquatrack.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.parameters.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ParameterAdapter extends BaseAdapter {

	List<ParseObject> parameters = new ArrayList<ParseObject>();
	Context context;

	public ParameterAdapter(Context context) {
		super();
		this.context = context;
		Parse.initialize(context, "hjYMRHgjBNK6fzcltOMtnmglaDYIQIU3PJfdCMF3",
				"xgBSMsHThQK5kzLvqSwDznSrpH9Gq8bW7ZYl6YoA");
		ParseQuery<ParseObject> parameterQquery = ParseQuery
				.getQuery("ParameterDetails");
		parameterQquery.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> parameterObject, ParseException e) {
				System.out.println("no of parameters are"
						+ parameterObject.size());
				if (e == null)
					parameters = parameterObject;
				else
					System.out.println("exception while fetching parameters");
			}
		});
	}

	@Override
	public int getCount() {
		System.out.println("getCountT()_para");
		return parameters.size();
	}

	@Override
	public Object getItem(int position) {
		System.out.println("getItem");
		return getItem(position);
		
	}

	@Override
	public long getItemId(int position) {
		System.out.println("getItemId");
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("getView()_para");
		if(convertView==null)
			{
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				convertView = inflater.inflate(R.layout.manage_parameter_list_element, parent, false);
			}
			System.out.println("position   " + position);
					
			return convertView;
	}

}
