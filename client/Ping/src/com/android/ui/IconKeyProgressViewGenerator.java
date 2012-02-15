package com.android.ui;

import com.android.R;
import com.android.models.Model;
import com.android.models.Row;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;

public class IconKeyProgressViewGenerator extends ViewGenerator{

	ViewHolder holder;

	public IconKeyProgressViewGenerator(int resource) {
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
		holder.imageview.setImageDrawable(item.image);
		
	}
	

}
