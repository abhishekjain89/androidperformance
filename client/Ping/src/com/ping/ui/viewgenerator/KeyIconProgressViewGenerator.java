package com.ping.ui.viewgenerator;

import com.ping.R;
import com.ping.models.Model;
import com.ping.models.Row;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class KeyIconProgressViewGenerator extends ViewGenerator{

	ViewHolder holder;

	public KeyIconProgressViewGenerator(int resource) {
		super(resource);
		holder = new ViewHolder();
	}

	@Override
	public ViewHolder fillViewHolder(View view, LayoutInflater inflater) {
		
		
		holder.first =  (TextView) view.findViewById(R.id.key);
		holder.second =  (TextView) view.findViewById(R.id.message);
		holder.progress =  (ProgressBar) view.findViewById(R.id.value);
		holder.imageview = (ImageView) view.findViewById(R.id.icon); 
		
		return holder;
	}
	@Override
	public void populateView(Row item) {
		holder.first.setText(item.first);
		holder.progress.setProgress(item.value);
		holder.second.setText(item.second);
		holder.imageview.setImageResource(item.imageResourceID);
	}
	

}
