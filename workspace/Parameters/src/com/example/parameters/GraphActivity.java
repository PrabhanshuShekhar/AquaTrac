package com.example.parameters;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
	String param;
	String[] horizontalLabel;
	ParseProxyObject selectedParameter;
	Date date;

	Intent intent;

	GraphView graphView;
	GraphViewSeriesStyle[] graphViewSeriesStyles = new GraphViewSeriesStyle[] {
			new GraphViewSeriesStyle(Color.rgb(139, 71, 38), 3),
			new GraphViewSeriesStyle(Color.rgb(142, 56, 142), 3),
			new GraphViewSeriesStyle(Color.rgb(0, 100, 0), 3),
			new GraphViewSeriesStyle(Color.rgb(205, 173, 0), 3),
			new GraphViewSeriesStyle(Color.rgb(0, 205, 205), 3),
			new GraphViewSeriesStyle(Color.rgb(25, 25, 112), 3),
			new GraphViewSeriesStyle(Color.rgb(128, 0, 0), 5) };

	Date getDate(int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, ((-1) * days));
		return cal.getTime();
	}

	public void setCriticalValuesOnGraph() {
		int start = selectedParameter.getInt("criticalStartRange");
		int end = selectedParameter.getInt("criticalEndRange");
		GraphViewData[] dataBox = new GraphViewData[DAYS+1];
		GraphViewData[] dataBox2 = new GraphViewData[DAYS+1];

		for (int j = 0; j < dataBox.length; j++) {
			dataBox[j] = new GraphViewData(j, start);
			dataBox2[j] = new GraphViewData(j, end);
		}
		GraphViewSeries exampleSeries = new GraphViewSeries(
				"Crtical Start Range", graphViewSeriesStyles[6], dataBox);
		graphView.addSeries(exampleSeries);
		exampleSeries = new GraphViewSeries("Crtical End Range",
				graphViewSeriesStyles[6], dataBox2);
		graphView.addSeries(exampleSeries);
	}

	public void setEndRangeValuesOnGraph() {
		int start = selectedParameter.getInt("startingRange");
		int end = selectedParameter.getInt("endRange");
		graphView.addSeries( new GraphViewSeries(
				new GraphViewData[] { new GraphViewData(0, start) }) );
		
		graphView.addSeries( new GraphViewSeries(
				new GraphViewData[] { new GraphViewData(0, end) }) );
	}

	public String[] setVerticalAxisLabel() {
		String[] asdf = new String[7];
		int start = selectedParameter.getInt("startingRange");
		int end = selectedParameter.getInt("endRange");
		float jump = (end-start)/6;
		System.out.println("jump is :  " + jump);
		for (int i = 0; i < 7; i++) {
			asdf[6-i] = Float.valueOf(start + (jump * i)).toString();
		}
		return asdf;
	}

	public void setGraphViewStyle(int days) {
		graphView.getGraphViewStyle().setNumHorizontalLabels(days + 2);
		graphView.getGraphViewStyle().setNumVerticalLabels(7);
		graphView.setVerticalLabels(setVerticalAxisLabel());
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView.setShowLegend(true);
		graphView.setLegendAlign(LegendAlign.TOP);
		graphView.setLegendWidth(290);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("starting");
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.graph);
		intent = getIntent();
		String[] locationIdArray = intent.getStringArrayExtra("locationId");
		String[] locationNameArray = intent.getStringArrayExtra("locationName");
		System.out.println(locationIdArray[0] + "    " + locationNameArray[0]);
		selectedParameter = (ParseProxyObject) intent
				.getSerializableExtra("selected_parameter");
		String parameterDescription = selectedParameter
				.getString("parameterDescription");
		graphView = new LineGraphView(this,
				selectedParameter.getString("parameterName"));

		if (intent.getIntExtra("minus_days", 0) == 1)
			DAYS = 7;
		else
			DAYS = 30;

		date = getDate(DAYS);
		System.out.println("date is : " + date);
		setGraphViewStyle(DAYS);

		for (int i = 0; i < locationIdArray.length; i++) {
			List<ParseObject> values = new ValuesDAO(this).getDataForReports(
					locationIdArray[i], date);
			System.out.println("number  :  " + values.size());
			GraphViewData[] dataBox = new GraphViewData[values.size()];
			for (int j = 0; j < dataBox.length; j++) {
				dataBox[j] = new GraphViewData(j + 1, values.get(j).getInt(
						parameterDescription));
			} // innner for

			GraphViewSeries exampleSeries = new GraphViewSeries(
					locationNameArray[i], graphViewSeriesStyles[i], dataBox);
			graphView.addSeries(exampleSeries);
		} // outer for
		setCriticalValuesOnGraph();
		setEndRangeValuesOnGraph();
		((LinearLayout) findViewById(R.id.graphLinearLayout))
				.addView(graphView);
	}

}