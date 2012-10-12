package com.num.ui.viewgenerator;

import com.num.activities.FullDisplayActivity;
import com.num.activities.GraphActivity;
import com.num.models.Model;
import com.num.models.Row;
import com.num.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;

public class IconKeyProgressViewGenerator extends ViewGenerator{

	ViewHolder holder;

	public IconKeyProgressViewGenerator(int resource) {
		super(resource);
		holder = new ViewHolder();
	}

	@Override
	public ViewHolder fillViewHolder(View view, LayoutInflater inflater) {
		
		
		holder.first =  (TextView) view.findViewById(R.id.key);
		holder.second =  (TextView) view.findViewById(R.id.message);
		holder.progress =  (ProgressBar) view.findViewById(R.id.value);
		holder.imageview = (ImageView) view.findViewById(R.id.icon);
		holder.second2 =  (TextView) view.findViewById(R.id.message2);
		holder.progress2 =  (ProgressBar) view.findViewById(R.id.value2);
		holder.linear = (LinearLayout) view.findViewById(R.id.main);
		return holder;
	}
	@Override
	public void populateView(Row item,final Context context,View view) {
		holder.first.setText(item.key);
		holder.progress.setProgress(item.valueOne);
		holder.second.setText("Sent: " + item.first);
		holder.progress2.setProgress(item.valueTwo);
		holder.second2.setText("Recv: " + item.second);
		holder.imageview.setImageDrawable(item.image);
		
		holder.linear.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent myIntent = new Intent(context, GraphActivity.class);				
                context.startActivity(myIntent);
				
			}
		});
		
	}
	

}
