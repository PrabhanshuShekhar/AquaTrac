package com.example.parameters;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;
import com.mw.aquatrack.DAO.ValuesDAO;
import com.parse.ParseObject;

public class GraphActivity extends Activity {

	static int DAYS;
	String[] locationIdArray;
	String[] locationNameArray;
	String parameterDescription;
	ParseProxyObject selectedParameter;
	Date date;
	Handler handler;
	Intent intent;
	ProgressDialog dialog;
	GraphView graphView;
	GraphViewSeriesStyle[] graphViewSeriesStyles = new GraphViewSeriesStyle[] {
			new GraphViewSeriesStyle(Color.rgb(127, 255, 0), 4),
			new GraphViewSeriesStyle(Color.rgb(0, 191, 255), 4),
			new GraphViewSeriesStyle(Color.rgb(41, 36, 33), 4),
			new GraphViewSeriesStyle(Color.rgb(255, 255, 0), 4),
			new GraphViewSeriesStyle(Color.rgb(238, 0, 238), 4),
			new GraphViewSeriesStyle(Color.rgb(255, 153, 18), 4),
			new GraphViewSeriesStyle(Color.rgb(255, 0, 0), 6),// critical
			new GraphViewSeriesStyle(Color.rgb(145, 145, 145), 1) };

	Date getDate(int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, ((-1) * days));
		return cal.getTime();
	}

	public void setCriticalValuesOnGraph() {
		int start = selectedParameter.getInt("criticalStartRange");
		int end = selectedParameter.getInt("criticalEndRange");
		System.out.println("Critical Values are" + start + "      " + end);
		GraphViewData[] dataBox = new GraphViewData[DAYS + 1];
		GraphViewData[] dataBox2 = new GraphViewData[DAYS + 1];

		for (int j = 0; j < dataBox.length; j++) {
			dataBox[j] = new GraphViewData(j, start);
			dataBox2[j] = new GraphViewData(j, end);
		}
		GraphViewSeries exampleSeries = new GraphViewSeries(
				"Critical Start Range", graphViewSeriesStyles[6], dataBox);
		graphView.addSeries(exampleSeries);
		exampleSeries = new GraphViewSeries("Critical End Range",
				graphViewSeriesStyles[6], dataBox2);
		graphView.addSeries(exampleSeries);
	}

	public void setEndRangeValuesOnGraph(int i) {
		if (i == 1) {
			int start = selectedParameter.getInt("startingRange");

			graphView.addSeries(new GraphViewSeries("",
					graphViewSeriesStyles[7],
					new GraphViewData[] { new GraphViewData(0, start) }));
		}
		if (i == 2) {
			int end = selectedParameter.getInt("endRange");

			graphView.addSeries(new GraphViewSeries("",
					graphViewSeriesStyles[7],
					new GraphViewData[] { new GraphViewData(0, end) }));
		}
	}

	public String[] setVerticalAxisLabel() {
		String[] verticalLabels = new String[7];
		int start = selectedParameter.getInt("startingRange");
		int end = selectedParameter.getInt("endRange");
		float jump = (end - start) / 6;
		System.out.println("jump is :  " + jump);
		for (int i = 0; i < 7; i++) {
			verticalLabels[6 - i] = Float.valueOf(start + (jump * i))
					.toString();
		}
		return verticalLabels;
	}

	@SuppressLint("SimpleDateFormat")
	public String[] setHorizontalAxisLabel(int days) {
		SimpleDateFormat ft = new SimpleDateFormat("dd/MM");
		String[] horizontalLabels = new String[days + 1];
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		for (int i = 0; i < horizontalLabels.length; i++) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			horizontalLabels[i] = ft.format(cal.getTime());
			// System.out.println("date is :  " + ft.format(cal.getTime()));
		}
		horizontalLabels[days] = " ";
		System.out.println("horizontalLabels   : " + horizontalLabels.length);
		return horizontalLabels;
	}

	public void setGraphViewStyle(int days) {
		System.out.println("setNumHorizontalLabels   :  " + (days + 1));
		graphView.getGraphViewStyle().setNumHorizontalLabels(days + 1);
		graphView.getGraphViewStyle().setNumVerticalLabels(7);
		graphView.setVerticalLabels(setVerticalAxisLabel());
		graphView.setHorizontalLabels(setHorizontalAxisLabel(days));
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLUE);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLUE);
		graphView.setShowLegend(true);
		graphView.setLegendAlign(LegendAlign.TOP);
		graphView.setLegendWidth(290);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("starting");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.graph);
		handler = new Handler();

		dialog = ProgressDialog.show(this, "Loading",
				"Please wait for a while.");
		Thread thread = new Thread() {
			public void run() {
				intent = getIntent();
				locationIdArray = intent.getStringArrayExtra("locationId");
				locationNameArray = intent.getStringArrayExtra("locationName");
				selectedParameter = (ParseProxyObject) intent
						.getSerializableExtra("selected_parameter");
				parameterDescription = selectedParameter
						.getString("parameterDescription");
				graphView = new LineGraphView(GraphActivity.this,
						selectedParameter.getString("parameterName"));

				if (intent.getIntExtra("minus_days", 0) == 1)
					DAYS = 7;
				else
					DAYS = 30;

				date = getDate(DAYS);
				System.out.println("date is : " + date);
				setGraphViewStyle(DAYS);
				setEndRangeValuesOnGraph(1);
				for (int i = 0; i < locationIdArray.length; i++) {
					List<ParseObject> values = new ValuesDAO(GraphActivity.this)
							.getDataForReports(locationIdArray[i], date);
					System.out.println("number  :  " + values.size());
					GraphViewData[] dataBox = new GraphViewData[values.size()];
					for (int j = 0; j < dataBox.length; j++) {
						dataBox[j] = new GraphViewData(j, values.get(j).getInt(
								parameterDescription));
					} // inner for

					GraphViewSeries exampleSeries = new GraphViewSeries(
							locationNameArray[i], graphViewSeriesStyles[i],
							dataBox);
					graphView.addSeries(exampleSeries);
				} // outer for
				setCriticalValuesOnGraph();
				setEndRangeValuesOnGraph(2);
				handler.post(new Runnable() {
					@Override
					public void run() {
						((LinearLayout) findViewById(R.id.graphLinearLayout))
								.addView(graphView);
						dialog.dismiss();

					}
				});// post
			}
		};// thread
		thread.start();

	}

}