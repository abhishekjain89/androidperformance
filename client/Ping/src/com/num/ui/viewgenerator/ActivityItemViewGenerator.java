package com.num.ui.viewgenerator;

import com.num.activities.FullDisplayActivity;
import com.num.activities.GraphActivity;
import com.num.database.DatabasePicker;
import com.num.database.datasource.ApplicationDataSource;
import com.num.database.datasource.LatencyDataSource;
import com.num.database.mapping.ApplicationMapping;
import com.num.database.mapping.LatencyMapping;
import com.num.models.ActivityItem;
import com.num.models.Application;
import com.num.models.Model;
import com.num.models.Row;
import com.num.utils.DeviceUtil;
import com.num.R;
import com.num.Values;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;

public class ActivityItemViewGenerator extends ViewGenerator{

	ViewHolder holder;
	ActivityItem activityItem;

	public ActivityItemViewGenerator(ActivityItem item) {
		super(R.layout.cell_view_activityitem);
		this.activityItem = item;
		holder = new ViewHolder();
	}

	@Override
	public ViewHolder fillViewHolder(View view, LayoutInflater inflater) {
		
		holder.first =  (TextView) view.findViewById(R.id.title);
		holder.second =  (TextView) view.findViewById(R.id.description);		
		holder.imageview = (ImageView) view.findViewById(R.id.icon);		
		holder.linear = (LinearLayout) view.findViewById(R.id.view);
		return holder;
	}
	@Override
	public void populateView(Row item,final Context context,View view) {
				
		holder.first.setText(activityItem.getTitle());		
		holder.second.setText(activityItem.getDescription());				
		holder.imageview.setImageResource(activityItem.getImageResource());
		
		holder.linear.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				activityItem.getHandle().sendEmptyMessage(0);				
			}
		});
		
	}
	
}
