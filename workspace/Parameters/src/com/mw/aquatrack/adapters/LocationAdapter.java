package com.mw.aquatrack.adapters;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.parameters.R;
import com.parse.ParseObject;

public class LocationAdapter extends BaseAdapter {

	int count = 0;
	public static boolean[] selectedLocations;
	private int checkBoxDecider;

	private final Context context;
	private final List<ParseObject> values;
	
	public LocationAdapter(Context context, List<ParseObject> values,
			int checkBoxDecider) {
		this.context = context;
		this.values = values;
		this.checkBoxDecider = checkBoxDecider;
		LocationAdapter.selectedLocations = new boolean[values.size()];
	}

	static class ViewHolder {
		protected boolean b;
		protected TextView textView;
		protected CheckBox box;
	}

	LayoutInflater inflater;
	View rowView;
	TextView textView;
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("getView position" + position);
		final ViewHolder holder;
		if (convertView == null) {
			System.out.println("convertView == null");
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.manage_location_list_element,
					parent, false);
			holder = new ViewHolder();
			holder.textView = (TextView) rowView
					.findViewById(R.id.location_textView);
			holder.box = (CheckBox) rowView.findViewById(R.id.location_checkBox);
			rowView.setTag(holder);
		} else {
			System.out.println("convertView != null");
			rowView = convertView;
			holder = (ViewHolder) convertView.getTag();
			if(holder==null)
				System.out.println("holder null");
		}
		holder.textView.setText(values.get(position).getString("location_name"));

		if (holder.b)
			holder.box.setChecked(true);
		else
			System.out.println("2222");
		

		OnCheckedChangeListener myCheckBoxChange = new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				View rowElement = ((View) buttonView.getParent());

				// int i = ((ViewGroup) rowElement.getParent())
				// .indexOfChild(rowElement);
				int i = ((ListView) rowElement.getParent())
						.getPositionForView(rowElement);
				System.out.println("index of child is :  " + i + isChecked);

				if (isChecked) {
					if (count < 6) {
						selectedLocations[i] = true;
						holder.b = true;
						count++;
					} else {
						buttonView.setChecked(false);
						AlertDialog alertDialog;
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								context);
						alertDialogBuilder
								.setTitle("Oops")
								.setMessage(
										"Maximum of 6 ponds can be selected")
								.setCancelable(false)
								.setPositiveButton("OK",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
											}
										});
						alertDialog = alertDialogBuilder.create();
						alertDialog.show();
					}
				} else {
					selectedLocations[i] = false;
					holder.b = false;
					count--;
				}

			}
		};

		if (checkBoxDecider == 0) {
			holder.box.setVisibility(View.GONE);
		} else {
			holder.box.setOnCheckedChangeListener(myCheckBoxChange);
		}
		return rowView;
	} // end of getView

	@Override
	public int getCount() {
		// System.out.println("getCount");
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