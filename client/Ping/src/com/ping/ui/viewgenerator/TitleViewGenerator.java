package com.ping.ui.viewgenerator;

import com.ping.R;
import com.ping.models.Model;
import com.ping.models.Row;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class TitleViewGenerator extends ViewGenerator{

	ViewHolder holder;

	public TitleViewGenerator(int resource) {
		super(resource);
		holder = new ViewHolder();
	}

	@Override
	public ViewHolder fillViewHolder(View view,LayoutInflater inflater) {
		
		holder.first =  (TextView) view.findViewById(R.id.key);
		return holder;
	}
	@Override
	public void populateView(Row item) {
		holder.first.setText(item.first);
		
		
	}
	

}
