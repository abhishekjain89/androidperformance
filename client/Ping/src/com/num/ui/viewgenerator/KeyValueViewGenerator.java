package com.num.ui.viewgenerator;

import com.num.models.Row;
import com.num.R;

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
	public void populateView(Row item,Context context,View view) {
		holder.first.setText(item.first);
		String secondItem = item.second;
		if (secondItem.length() > 0) {
			secondItem = secondItem.substring(0, 1).toUpperCase() + secondItem.substring(1); 
		}
		holder.second.setText(secondItem);
		
	}
	

}
