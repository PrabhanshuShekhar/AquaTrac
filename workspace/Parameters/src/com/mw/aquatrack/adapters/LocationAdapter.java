package com.mw.aquatrack.adapters;

import com.example.parameters.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationAdapter extends BaseAdapter{

	private final Context context;
	private final String[] values;
	
	public LocationAdapter(Context context, String[] values) {
		this.context = context;
		this.values = values;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
	System.out.println("getView");
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.manage_location_list_element, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.location_textView);
		textView.setText(values[position]);
 
		
		return rowView;
	}
	
	
	
	@Override
	public int getCount() {
		System.out.println("getCount");
		return values.length;
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
