package com.mw.aquatrack.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.parameters.R;
import com.parse.ParseObject;

public class LocationAdapter extends BaseAdapter {

	private final Context context;
	private final List<ParseObject> values;

	public LocationAdapter(Context context, List<ParseObject> values) {
		this.context = context;
		this.values = values;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("getView");
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.manage_location_list_element,
				parent, false);
		TextView textView = (TextView) rowView
				.findViewById(R.id.location_textView);
		textView.setText(values.get(position).getString("location_name"));

		return rowView;
	}

	@Override
	public int getCount() {
		System.out.println("getCount");
		return values.size();
	}

	@Override
	public Object getItem(int position) {
		System.out.println("getItem");
		return null;
	}

	@Override
	public long getItemId(int position) {
		System.out.println("getItemId");
		return 0;
	}

}