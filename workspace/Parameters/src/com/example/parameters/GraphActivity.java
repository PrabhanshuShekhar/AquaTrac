package com.example.parameters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.mw.aquatrack.services.CreateDialog;
import com.mw.aquatrack.services.MyApplication;
import com.parse.ParseObject;

@SuppressLint("SimpleDateFormat")
public class GraphActivity extends Activity {

	static int DAYS;
	String[] locationIdArray;
	String[] locationNameArray;
	String parameterDescription;
	double criticalStartRange;
	double criticalEndRange;
	ParseProxyObject selectedParameter;
	Date dateAfterMinus;
	Handler handler;
	MyApplication application;
	AlertDialog.Builder alertDialogBuilder;
	CreateDialog createDialog;

	Intent intent;
	ProgressDialog progressDialog;
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
		criticalStartRange = intent.getDoubleExtra("critical_start_value", 0);
		criticalEndRange = intent.getDoubleExtra("critical_end_value", 0);

		GraphViewData[] dataBox = new GraphViewData[DAYS + 1];
		GraphViewData[] dataBox2 = new GraphViewData[DAYS + 1];

		for (int j = 0; j < dataBox.length; j++) {
			dataBox[j] = new GraphViewData(j, criticalStartRange);
			dataBox2[j] = new GraphViewData(j, criticalEndRange);
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
			System.out.println("setting starting range  : " + start);

			graphView.addSeries(new GraphViewSeries("",
					graphViewSeriesStyles[7],
					new GraphViewData[] { new GraphViewData(0, start) }));
		}
		if (i == 2) {
			int end = selectedParameter.getInt("endRange");
			System.out.println("setting end range   :   " + end);

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

	public String[] setHorizontalAxisLabel(int days) {
		SimpleDateFormat ft = new SimpleDateFormat("dd/MM");
		String[] horizontalLabels = new String[days + 1];
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateAfterMinus);
		if (days == 7)
			for (int i = 0; i < horizontalLabels.length; i++) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
				horizontalLabels[i] = ft.format(cal.getTime());
			}
		else
			for (int i = 0; i < horizontalLabels.length; i++) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
				if ((i % 5) == 0)
					horizontalLabels[i] = ft.format(cal.getTime());
				else
					horizontalLabels[i] = " ";
			}
		horizontalLabels[days] = " ";
		System.out.println("horizontalLabels   : " + horizontalLabels.length);
		return horizontalLabels;
	}

	public void setGraphViewStyle(int days) {
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.graph);
		handler = new Handler();
		application = (MyApplication) getApplication();
		createDialog = new CreateDialog(this);

		progressDialog = createDialog.createProgressDialog("Loading",
				"Please wait for a while.", true,
				getResources().getDrawable(R.anim.progress_dialog_animation));
		progressDialog.show();
		final Thread thread = new Thread() {
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

				dateAfterMinus = getDate(DAYS);
				System.out.println("date is : " + dateAfterMinus);
				setGraphViewStyle(DAYS);
				setEndRangeValuesOnGraph(1);
				for (int i = 0; i < locationIdArray.length; i++) {
					int previousValueHolder = -1;
					Calendar cal = Calendar.getInstance();
					cal.setTime(dateAfterMinus);
					SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
					List<ParseObject> values = new ValuesDAO(GraphActivity.this)
							.getDataForReports(locationIdArray[i],
									dateAfterMinus);
					System.out.println("records fetched  :  " + values.size());
					int size = values.size();
					int counter = 0;
					GraphViewData[] dataBox = new GraphViewData[DAYS];
					for (int j = 0; j < dataBox.length; j++) {
						cal.add(Calendar.DAY_OF_MONTH, 1);
						try {
							if (values.size() > 0) {
								Date databaseDate = ft.parse(ft.format(values
										.get(counter).getDate("date")));
								Date currentDate = ft.parse(ft.format(cal
										.getTime()));
								System.out.println("database date  : "
										+ databaseDate + " currentDate  "
										+ currentDate);
								System.out.println(" comparison  :"
										+ databaseDate.compareTo(currentDate));
								if (databaseDate.compareTo(currentDate) == 0) {
									System.out.println("j is : == " + j);
									dataBox[j] = new GraphViewData(j, values
											.get(counter).getInt(
													parameterDescription));
									previousValueHolder = counter;
									if (counter < (size - 1)) {
										counter++;
									}
								} else {
									if (previousValueHolder != -1) {
										System.out.println("j is !=  !=-1:  "
												+ j);
										dataBox[j] = new GraphViewData(
												j,
												values.get(previousValueHolder)
														.getInt(parameterDescription));
									} else {
										System.out.println("j is :  !=  ==-1"
												+ j);
										dataBox[j] = new GraphViewData(j, 0);
									}
								}
							} else {
								// System.out.println("j is :  " + j);
								dataBox[j] = new GraphViewData(j, 0);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
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
						progressDialog.dismiss();

					}
				});// post
			}
		};// thread
		thread.start();

		Thread thread2 = new Thread(new Runnable() {

			@Override
			public void run() {
				int j = 0;
				while (j == 0) {
					try {
						Thread.currentThread();
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (thread.getState() != Thread.State.TERMINATED)
						if (!internetAlert())
							j = 1;
				}
			}
		});
		thread2.start();

	} // end of onCreate

	public boolean internetAlert() {
		int internetScore = application.combinedInternetTest();
		if (internetScore != 0) {
			System.out.println("problem");
			if (internetScore == 1) {
				alertDialogBuilder = createDialog.createAlertDialog(
						"CONNECTION ERROR", "Connect to WiFi or Data Service.",
						false);
				singleOKButton(alertDialogBuilder);
				return false;
			}
			if (internetScore == 2) {
				alertDialogBuilder = createDialog.createAlertDialog(
						"CONNECTION ERROR", "Internet services required.",
						false);
				singleOKButton(alertDialogBuilder);
				return false;
			}
		}
		return true;
	}

	public void singleOKButton(final AlertDialog.Builder alertDialogBuilder) {
		alertDialogBuilder.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
						finish();
					}
				});

		handler.post(new Runnable() {
			@Override
			public void run() {
				dismissPreviousProgressBar();
				AlertDialog dialog = alertDialogBuilder.create();
				dialog.show();

			}
		});
	}

	public void dismissPreviousProgressBar() {
		handler.post(new Runnable() {

			@Override
			public void run() {
				progressDialog.dismiss();
			}
		});
	}
}