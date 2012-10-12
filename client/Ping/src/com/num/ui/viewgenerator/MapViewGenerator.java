package com.num.ui.viewgenerator;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.num.models.Model;
import com.num.models.Row;
import com.num.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class MapViewGenerator extends ViewGenerator{

	ViewHolder holder;

	public MapViewGenerator(int resource) {
		super(resource);
		holder = new ViewHolder();
	}

	@Override
	public ViewHolder fillViewHolder(View view,LayoutInflater inflater) {
		
		holder.map =  (MapView) view.findViewById(R.id.map);
		
		return holder;
	}
	@Override
	public void populateView(Row item,Context context,View view) {
		MapController controller = holder.map.getController();
		
		GeoPoint point = new GeoPoint((int)(item.valueOne*1E6),(int)(item.valueTwo*1E6));
		controller.setCenter(point);
		
	}
	

}
