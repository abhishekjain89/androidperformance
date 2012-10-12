package com.num.ui.viewgenerator;

import com.num.models.Model;
import com.num.models.Row;
import com.num.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class DumbGenerator extends ViewGenerator{

	ViewHolder holder;

	public DumbGenerator(int resource) {
		super(resource);
		holder = new ViewHolder();
	}

	@Override
	public ViewHolder fillViewHolder(View view,LayoutInflater inflater) {
		
		
		return holder;
	}
	@Override
	public void populateView(Row item,Context context,View view) {
		
		
		
	}
	

}
