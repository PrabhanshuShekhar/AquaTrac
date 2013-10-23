package com.mw.aquatrack.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.parameters.R;
import com.parse.ParseObject;

public class LocationAdapter extends BaseAdapter {
	
	public static boolean[] asdf;
	private static int checkBoxDecider;
	private static int imageDecider;
	private final Context context;
	private final List<ParseObject> values;
	CheckBox box;
	

	public LocationAdapter(Context context, List<ParseObject> values,
			int checkBoxDecider, int imageDecider) {
		this.context = context;
		this.values = values;
		LocationAdapter.checkBoxDecider = checkBoxDecider;
		LocationAdapter.imageDecider = imageDecider;
		LocationAdapter.asdf=new boolean[values.size()];
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
		
		
		box = (CheckBox) rowView
				.findViewById(R.id.location_checkBox);
//		box.setTag(0);

		if (checkBoxDecider == 0) {
			box.setVisibility(View.GONE);
		}

		OnCheckedChangeListener myCheckBoxChange = new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				View rowElement = ((View) buttonView.getParent());
				int i=((ViewGroup) rowElement.getParent()).indexOfChild(rowElement);
				System.out.println("index of child is :  " + i );
				System.out.println("tick-untick"  + isChecked);

				if(isChecked)
				{System.out.println("tag1");	box.setTag(1); asdf[i]=true;}
				else
				{System.out.println("tag0");	box.setTag(0); asdf[i]=false;}
			}
		};

		if (imageDecider == 0) {
			
			// new thing
			box.setOnCheckedChangeListener(myCheckBoxChange);
		}
		
		return rowView;
	
	}		//	end of getView

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