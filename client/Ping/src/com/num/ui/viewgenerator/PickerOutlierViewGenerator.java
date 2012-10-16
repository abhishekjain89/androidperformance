package com.num.ui.viewgenerator;
import java.util.ArrayList;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PickerOutlierViewGenerator extends ViewGenerator{

	ViewHolder holder;
	final DatabasePicker picker;
	ArrayList<String> items;
	ArrayList<String> displayitems;

	public PickerOutlierViewGenerator(DatabasePicker picker) {
		super(R.layout.cell_view_spinner);
		this.picker = picker;
		holder = new ViewHolder();
	}

	@Override
	public ViewHolder fillViewHolder(View view, LayoutInflater inflater) {

		holder.spinner = (Spinner) view.findViewById(R.id.choose);
		holder.first = (TextView) view.findViewById(R.id.key);
		holder.second = (TextView) view.findViewById(R.id.value);
		holder.imageview = (ImageView) view.findViewById(R.id.edit);

		return holder;
	}
	@Override
	public void populateView(Row item,final Context context,View view) {


		holder.first.setText("Outliers");
		holder.second.setText((int)(picker.getOutlierFraction()*100)+"%");
		displayitems = new ArrayList<String>();
		
		int initialSelection = 0;
		
		int count = 0;
		for(float option : picker.getOutlierOptions()) {
			
			if(option==picker.getOutlierFraction()) {
				initialSelection = count;
			}
			
			displayitems.add((int)(option*100)+"%");
			count++;
		}

		ArrayAdapter<String> adapter = 
				new ArrayAdapter<String> (context, 
						R.layout.spinner_text,displayitems);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		holder.spinner.setAdapter(adapter);		
		
		holder.spinner.setSelection(initialSelection);
		
		holder.imageview.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				 holder.spinner.performClick();				
			}
		});
		
		holder.spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				int pos = holder.spinner.getSelectedItemPosition();
				picker.setOutlierFraction(picker.getOutlierOptions()[pos]);
				holder.second.setText((int)(picker.getOutlierFraction()*100)+"%");
				picker.updateGraph();
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

	}

}
