package com.num.ui.viewgenerator;

import com.num.activities.FullDisplayActivity;
import com.num.activities.GraphActivity;
import com.num.database.DatabasePicker;
import com.num.database.datasource.ApplicationDataSource;
import com.num.database.datasource.LatencyDataSource;
import com.num.database.mapping.ApplicationMapping;
import com.num.database.mapping.LatencyMapping;
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

public class ApplicationViewGenerator extends ViewGenerator{

	ViewHolder holder;
	Application app;
	long totalData;
	
	public ApplicationViewGenerator(Application app,long totalData) {
		super(R.layout.cell_view_application);
		this.app = app;
		this.totalData = totalData;
		holder = new ViewHolder();
	}

	@Override
	public ViewHolder fillViewHolder(View view, LayoutInflater inflater) {
		
		holder.first =  (TextView) view.findViewById(R.id.key);
		holder.second =  (TextView) view.findViewById(R.id.data);
		holder.progress =  (ProgressBar) view.findViewById(R.id.value);
		holder.imageview = (ImageView) view.findViewById(R.id.icon);
		holder.third =  (TextView) view.findViewById(R.id.percentage);
		holder.linear = (LinearLayout) view.findViewById(R.id.main);
		return holder;
	}
	@Override
	public void populateView(Row item,final Context context,View view) {
		
		final Values values = (Values) context.getApplicationContext();
		holder.first.setText(app.getName());
		holder.progress.setProgress(getValue(app.getTotal()));
		holder.second.setText(getOutput(app.getTotal()));		
		holder.imageview.setImageDrawable(app.getAppIcon());
		holder.third.setText(100*app.getTotal()/totalData + "%");
		
		holder.linear.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				DatabasePicker picker = values.createPicker(new ApplicationDataSource(context));
				picker.setTitle("Usage Graph");				
				picker.filterBy(ApplicationMapping.COLUMN_DIRECTION,"total","Type");
				picker.filterBy(ApplicationMapping.COLUMN_NAME,app.getName(),"Application");
				picker.setDisplayOutlier(false);
				Intent myIntent = new Intent(context, GraphActivity.class);				
                context.startActivity(myIntent);
				
			}
		});
		
	}
	
	private String getOutput(long val){
		int total = (int) (val/1024/1024);
		if(total>1)
			return (int)Math.max(total,5) + " MB";
		else
			return "< 1 MB";
	}
	
	private int getValue(long val){
		int total = (int) (val/1024/1024);
		return (int)Math.min(Math.max(total,5), 100);
	}

	

}
