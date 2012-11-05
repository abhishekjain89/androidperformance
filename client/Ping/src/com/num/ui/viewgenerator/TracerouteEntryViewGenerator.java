package com.num.ui.viewgenerator;

import com.num.models.Row;
import com.num.models.TracerouteEntry;
import com.num.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class TracerouteEntryViewGenerator extends ViewGenerator{

	ViewHolder holder;
	TracerouteEntry entry;

	public TracerouteEntryViewGenerator(TracerouteEntry entry) {
		super(R.layout.cell_traceroute_entry);		
		this.entry = entry;
		holder = new ViewHolder();
	}

	@Override
	public ViewHolder fillViewHolder(View view,LayoutInflater inflater) {
		
		
		holder.first =  (TextView) view.findViewById(R.id.index);
		holder.second =  (TextView) view.findViewById(R.id.ipaddress);
		holder.third =  (TextView) view.findViewById(R.id.hostname);
		holder.fourth =  (TextView) view.findViewById(R.id.rtt);
		
		return holder;
	}
	@Override
	public void populateView(Row item,Context context,View view) {
		
		holder.first.setText(""+entry.getHopnumber());
		holder.second.setText(""+entry.getHostname());
		holder.third.setText(""+entry.getIpAddr());
		holder.fourth.setText(""+entry.getRtt());
		
		
	}
	

}
