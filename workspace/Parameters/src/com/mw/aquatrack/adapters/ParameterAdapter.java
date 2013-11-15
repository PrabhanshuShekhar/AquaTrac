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
import com.example.parameters.SelectParameterActivity;
import com.parse.ParseObject;

public class ParameterAdapter extends BaseAdapter {
	private final Context context;
	private final List<ParseObject> parameterValues;
	private int radioButtonDecider;

	public ParameterAdapter(Context context, List<ParseObject> values,
			int radioButtonDecider) {
		this.context = context;
		this.parameterValues = values;
		this.radioButtonDecider = radioButtonDecider;
	}

	static class ViewHolder {
		protected TextView textView;
		protected TextView textView2;
		protected RadioButton radioButton;
	}

	LayoutInflater inflater;

	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("getView");
		ViewHolder viewHolder;
		if (convertView == null) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(
					R.layout.manage_parameter_list_element, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.parameter_textView);

			viewHolder.textView2 = (TextView) convertView
					.findViewById(R.id.parameter_range_textView);
			
			viewHolder.radioButton = (RadioButton)convertView
					.findViewById(R.id.parameter_radioButton);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.textView.setText(parameterValues.get(position).getString(
				"parameterName"));
		viewHolder.textView2.setText("Critical Range ("
				+ (parameterValues.get(position)
						.getNumber("criticalStartRange")) + " - "
				+ (parameterValues.get(position).getNumber("criticalEndRange"))
				+ ")");
		viewHolder.radioButton.setChecked(false);
		if (radioButtonDecider == 0) {
			((RadioButton) convertView.findViewById(R.id.parameter_radioButton))
					.setVisibility(View.INVISIBLE);

		}
		if (position == SelectParameterActivity.staticListIndex) {
			((RadioButton) convertView.findViewById(R.id.parameter_radioButton))
					.setChecked(true);
		}

		return convertView;
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