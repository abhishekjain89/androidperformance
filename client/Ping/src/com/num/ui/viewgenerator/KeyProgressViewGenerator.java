package com.num.ui.viewgenerator;
import com.num.models.Model;
import com.num.models.Row;
import com.num.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class KeyProgressViewGenerator extends ViewGenerator{

	ViewHolder holder;

	public KeyProgressViewGenerator(int resource) {
		super(resource);
		holder = new ViewHolder();
	}

	@Override
	public ViewHolder fillViewHolder(View view, LayoutInflater inflater) {
		
		
		holder.first =  (TextView) view.findViewById(R.id.key);
		holder.second =  (TextView) view.findViewById(R.id.message);
		holder.progress =  (ProgressBar) view.findViewById(R.id.value);
		
		return holder;
	}
	@Override
	public void populateView(Row item) {
		holder.first.setText(item.first);
		
		holder.progress.setProgress(item.value);
		
		//System.out.println(holder.progress.getLayoutParams().width);
		//holder.progress.getLayoutParams().width = 140;
		//holder.progress.getLayoutParams().width = (int) ((float)(140) * ((float)item.value/(float)100));
		holder.second.setText(item.second);
	}
	

}
