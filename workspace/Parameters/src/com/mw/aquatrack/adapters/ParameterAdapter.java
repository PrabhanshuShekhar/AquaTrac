package com.mw.aquatrack.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.parameters.R;
import com.parse.ParseObject;

public class ParameterAdapter extends BaseAdapter {
	private final Context context;
	private final List<ParseObject> parameterValues;
	private static int radioButtonDecider;

	// private static int imageDecider;

	public ParameterAdapter(Context context, List<ParseObject> values,
			int radioButtonDecider, int imageDecider) {
		this.context = context;
		this.parameterValues = values;
		ParameterAdapter.radioButtonDecider = radioButtonDecider;
		// ParameterAdapter.imageDecider = imageDecider;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("getView");
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.manage_parameter_list_element,
				parent, false);
		TextView textView = (TextView) rowView
				.findViewById(R.id.parameter_textView);
		textView.setText(parameterValues.get(position).getString(
				"parameterName"));

		TextView textView2 = (TextView) rowView
				.findViewById(R.id.parameter_range_textView);
		textView2.setText("Critical Range ("
				+ (parameterValues.get(position)
						.getNumber("criticalStartRange")) + " - "
				+ (parameterValues.get(position).getNumber("criticalEndRange"))
				+ ")");
//		if (position == 0)
//			((RadioButton) rowView.findViewById(R.id.parameter_radioButton))
//					.setChecked(true);
		if (radioButtonDecider == 0) {
			((RadioButton) rowView.findViewById(R.id.parameter_radioButton))
					.setVisibility(View.INVISIBLE);
		
		}

		return rowView;
	}

	@Override
	public int getCount() {
		System.out.println("getCount");
		return parameterValues.size();
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