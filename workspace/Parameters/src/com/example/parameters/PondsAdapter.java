package com.example.parameters;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PondsAdapter extends BaseAdapter {
	
	private Context context;
	
	
	public PondsAdapter(Context c)
	{
		this.context = c;
	}
	
	public int getCount()
	{
		return location.length;
	}
	
	public Object getItem(int position)
	{
		return null;
	}
	public long getItemId(int podition)
	{
		return 0;
	}
	
	public View getView(int position, View view , ViewGroup parent)
	{
		LinearLayout linear_layout;
		TextView textview;
		if(view == null)
		{
			linear_layout = new LinearLayout(context);
			linear_layout.setOrientation(LinearLayout.HORIZONTAL);
			if(getCount()-1 == position || getCount()-2 == position)
			{
				
			}
			else
			{
//				if(getCount()-3 == position && location[position] == "")
//				{
//					linear_layout.setLayoutParams(new GridView.LayoutParams(200,200));
//				}
//				else
//				{
			     linear_layout.setLayoutParams(new GridView.LayoutParams(200,200));
//			linear_layout.setPadding(10, 50, 40, 10);
			     linear_layout.setBackgroundColor(Color.parseColor("#2E64FE"));
			
			    textview = new TextView(context);
			    textview.setTextColor(Color.parseColor("#F2F2F2"));
			    textview.setTextSize(25);
			    textview.setText("Location"+(position+1));
			 // Application ID : qqZAyhUs4EAmoDAcfZZrSAvqK5EbCvNvv8Vb90yc
			// Rest Api key :ADxrJ4Aqx0EZslhw8z3nHteg8BNqPoibVrwdSpGt
			textview.setPadding(25,75, 10,10);
			Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica-Condensed.otf");
			textview.setTypeface(tf);
		    linear_layout.addView(textview);
//				}
			}
			
		}
		else
		{
			linear_layout = (LinearLayout)view;
		}
		return linear_layout;
	}
	
	private String [] location = {"Location1" , "Location2" , "Location3" , "Location4" , "Location5", "Location6","",""};
}
