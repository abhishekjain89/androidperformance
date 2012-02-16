package com.android.ui.viewgenerator;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.android.R;
import com.android.models.Model;
import com.android.models.Row;

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
	public void populateView(Row item) {
		MapController controller = holder.map.getController();
		
		GeoPoint point = new GeoPoint((int)(item.valueOne*1E6),(int)(item.valueTwo*1E6));
		controller.setCenter(point);
		
	}
	

}
