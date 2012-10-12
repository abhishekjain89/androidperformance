package com.num.ui.viewgenerator;
import com.num.activities.GraphActivity;
import com.num.database.DatabasePicker;
import com.num.database.datasource.LatencyDataSource;
import com.num.database.mapping.LatencyMapping;
import com.num.graph.PingGraph;
import com.num.models.Model;
import com.num.models.Ping;
import com.num.models.Row;
import com.num.R;
import com.num.Values;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LatencyViewGenerator extends ViewGenerator{

	ViewHolder holder;
	PingGraph ping;

	public LatencyViewGenerator(PingGraph ping) {
		super(R.layout.cell_view_keyprogress);
		this.ping = ping;
		holder = new ViewHolder();
	}

	@Override
	public ViewHolder fillViewHolder(View view, LayoutInflater inflater) {
		
		
		holder.first =  (TextView) view.findViewById(R.id.key);
		holder.second =  (TextView) view.findViewById(R.id.message);
		holder.progress =  (ProgressBar) view.findViewById(R.id.value);
		holder.linear = (LinearLayout) view.findViewById(R.id.main);
		
		return holder;
	}
	@Override
	public void populateView(Row item,final Context context,View view) {
		
		final Values values = (Values) context.getApplicationContext();
		
		holder.first.setText(ping.getPing().getDst().getTagname());		
		holder.progress.setProgress(ping.getProgressValue());		
		holder.second.setText(ping.getPing().getMeasure().showText());
		
		holder.linear.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				DatabasePicker picker = values.createPicker(new LatencyDataSource(context));
				picker.setTitle("Latency Graph");
				picker.filterBy(LatencyMapping.COLUMN_TYPE,ping.getPing().getDst().getType());
				picker.filterBy(LatencyMapping.COLUMN_DSTIP,ping.getPing().getDst().getTagname());
				Intent myIntent = new Intent(context, GraphActivity.class);				
                context.startActivity(myIntent);
				
			}
		});
	}
	

}
