package com.ping.ui.viewgenerator;

import com.ping.R;
import com.ping.models.Model;
import com.ping.models.Row;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class KeyValueViewGenerator extends ViewGenerator{

	ViewHolder holder;

	public KeyValueViewGenerator(int resource) {
		super(resource);
		holder = new ViewHolder();
	}

	@Override
	public ViewHolder fillViewHolder(View view,LayoutInflater inflater) {
		
		
		holder.first =  (TextView) view.findViewById(R.id.key);
		holder.second =  (TextView) view.findViewById(R.id.value);
		
		return holder;
	}
	@Override
	public void populateView(Row item) {
		holder.first.setText(item.first);
		holder.second.setText(item.second);
		
	}
	

}
