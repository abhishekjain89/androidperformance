package com.num.ui.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.num.models.Row;
import com.num.R;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AlphaAnimation;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ItemAdapter  extends ArrayAdapter<Row>{

	public ArrayList<Row> items;
	private Activity activity;
	private LayoutInflater inflater=null;


	public ItemAdapter(Activity activity,ArrayList<Row> items) {
		super(activity.getApplicationContext(), 0);

		this.items = items;
		this.activity = activity;

		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;				
		final Row item =items.get(position);

		if (v == null) {			
			v=item.getViewGenerator().generateViewHolder(inflater);			
		}
		try{
			item.getViewGenerator().populateView(item,activity,v);
		} catch(Exception e) {
			e.printStackTrace();

		}


		return v;
	}

}




