package com.num.ui.viewgenerator;

import com.num.models.Model;
import com.num.models.Row;
import com.num.R;

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
	public void populateView(Row item,Context context,View view) {
		holder.first.setText(item.first);
		
		
	}
	

}
