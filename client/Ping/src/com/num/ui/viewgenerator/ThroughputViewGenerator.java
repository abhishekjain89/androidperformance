package com.num.ui.viewgenerator;
import com.num.activities.GraphActivity;
import com.num.database.DatabasePicker;
import com.num.database.datasource.LatencyDataSource;
import com.num.database.datasource.ThroughputDataSource;
import com.num.database.mapping.LatencyMapping;
import com.num.database.mapping.ThroughputMapping;
import com.num.graph.LinkGraph;
import com.num.graph.PingGraph;
import com.num.models.Model;
import com.num.models.Ping;
import com.num.models.Row;
import com.num.models.Throughput;
import com.num.utils.DeviceUtil;
import com.num.R;
import com.num.Values;

import android.bluetooth.BluetoothClass.Device;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ThroughputViewGenerator extends ViewGenerator{

	ViewHolder holder;
	LinkGraph link;

	public ThroughputViewGenerator(LinkGraph link) {
		super(R.layout.cell_view_keyprogress);
		this.link = link;
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
		
		holder.first.setText(link.getName());		
		holder.progress.setProgress(link.getProgressValue());		
		holder.second.setText(link.getLink().showData());
		
		holder.linear.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				DatabasePicker picker = values.createPicker(new ThroughputDataSource(context));
				picker.setTitle("Throughput Graph");
				picker.filterBy(ThroughputMapping.COLUMN_TYPE,link.getType(),"Type");				
				picker.filterBy(ThroughputMapping.COLUMN_CONNECTION,DeviceUtil.getNetworkInfo(context),"Connection");
				Intent myIntent = new Intent(context, GraphActivity.class);				
                context.startActivity(myIntent);
				
			}
		});
	}
	

}
