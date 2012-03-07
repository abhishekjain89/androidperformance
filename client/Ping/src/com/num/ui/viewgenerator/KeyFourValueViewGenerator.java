package com.num.ui.viewgenerator;

import com.num.models.Model;
import com.num.models.Row;
import com.num.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class KeyFourValueViewGenerator extends ViewGenerator{

	ViewHolder holder;

	public KeyFourValueViewGenerator(int resource) {
		super(resource);
		holder = new ViewHolder();
	}

	@Override
	public ViewHolder fillViewHolder(View view,LayoutInflater inflater) {
		
		
		holder.first =  (TextView) view.findViewById(R.id.key);
		holder.second =  (TextView) view.findViewById(R.id.value1);
		holder.third =  (TextView) view.findViewById(R.id.value2);
		holder.fourth =  (TextView) view.findViewById(R.id.value3);
		holder.fifth =  (TextView) view.findViewById(R.id.value4);
		return holder;
	}
	@Override
	public void populateView(Row item) {
		holder.first.setText(item.first);
		holder.second.setText(item.seconds.get(0));
		holder.third.setText(item.seconds.get(1));
		holder.fourth.setText(item.seconds.get(2));
		holder.fifth.setText(item.seconds.get(3));
		
	}
	

}
