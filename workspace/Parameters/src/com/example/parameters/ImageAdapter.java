package com.example.parameters;

import com.example.parameters.R.drawable;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
	private Context context;
	
	public ImageAdapter(Context c)
	{
		this.context = c;
	}
	
	public int getCount()
	{
		return thumbIds.length;
	}
	
	public Object getItem(int position)
	{
		return null;
	}
	
	public long getItemId(int position)
	{
		return 0;
	}
	
	public View getView(int position , View view , ViewGroup parent)
	{
		LinearLayout linear_layout;
		ImageView imageview ;
		TextView textview;
		
		if(view == null)
		{
			linear_layout = new LinearLayout(context);
			linear_layout.setOrientation(LinearLayout.VERTICAL);
			imageview = new ImageView(context);
			imageview.setLayoutParams(new GridView.LayoutParams(250,250));
			imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
//			imageview.setPadding(30, 30, 10, 10);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imageview.getLayoutParams());
//			lp.setMargins(60, 100,0, 0);
//			lp.setMargins(left, top, right, bottom);
			lp.setMargins(50, 40, 5, 5);
			imageview.setLayoutParams(lp);
			imageview.setImageResource(thumbIds[position]);
//			imageview.setBackgroundResource(R.drawable.rounded);
			textview = new  TextView(context);
			textview.setText(text[position]);
			textview.setTextColor(Color.parseColor("#000066"));
			textview.setTextSize(25);
			Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica-Condensed.otf");
			textview.setTypeface(tf);
		    textview.setGravity(Gravity.CENTER);
			linear_layout.addView(imageview);
			linear_layout.addView(textview);
		}
		else
		{
//			imageview = (ImageView) view;
			linear_layout = (LinearLayout) view ;
		}
//		imageview.setImageResource(thumbIds[position]);
//		return imageview;
		
		return linear_layout;
	}
	
	private Integer [] thumbIds  = {
			R.drawable.add_blue_rounded,
			R.drawable.view_blue_rounded,
			R.drawable.settings_blue_rounded,
			R.drawable.users_blue_rounded
	};
	private String [] text = {"Add" , "View" , "Settings", "Report"};

}
