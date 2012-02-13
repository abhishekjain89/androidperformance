package com.android.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.android.R;
import com.android.models.Item;
import com.android.models.Row;

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
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListAdapter  extends ArrayAdapter<Item>{

	public ArrayList<Row> rows;
	private Activity activity;
	private LayoutInflater inflater=null;
	private int ResourceId;

	public ListAdapter(Activity activity,
			int ResourceId,ArrayList<Row> rows) {
		super(activity.getApplicationContext(), ResourceId);

		this.rows = rows;
		
		
		
		this.activity = activity;
		this.ResourceId = ResourceId;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	private class ViewHolder{
		public TextView title;
		public TextView text;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;

		Row row =rows.get(position);
		System.out.println("getView: " + position);
		if (v == null) {

			v = inflater.inflate(ResourceId, null);
			holder = new ViewHolder();
			holder.title =  (TextView) v.findViewById(R.id.title);
			holder.text =  (TextView) v.findViewById(R.id.text);
			
			v.setTag(holder);
		}
		else
		{			
			holder = (ViewHolder)v.getTag();
		}


		/*if (item!=null) {	
			try{
				holder.title.setText(item.title);
				holder.text.setText(item.obj.toJSON().toString());

			} catch(Exception e) {
				e.printStackTrace();

			}
		}*/
		return v;
	}

	class imageViewClickListener implements OnClickListener {
		Item item;
		
		public imageViewClickListener( Item item)
		{
			this.item = item;
			
		}

		public void onClick(View v)
		{

		}

	}


}




