package com.num.activities;

import java.util.ArrayList;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.TimeChart;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.apps.analytics.easytracking.TrackedActivity;
import com.num.Values;
import com.num.database.DatabasePicker;
import com.num.graph.ChartView;
import com.num.graph.TimelineView;
import com.num.helpers.ServiceHelper;
import com.num.helpers.ThreadPoolHelper;
import com.num.listeners.BaseResponseListener;
import com.num.listeners.FakeListener;
import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.GraphData;
import com.num.models.GraphPoint;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Row;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.Usage;
import com.num.models.Wifi;
import com.num.tasks.SummaryTask;
import com.num.tasks.ValuesTask;
import com.num.ui.UIUtil;
import com.num.ui.adapter.ItemAdapter;
import com.num.R;

public class GraphActivity extends TrackedActivity {
	Values values;
	DatabasePicker picker;

	private ListView listview;
	private ChartView chart;
	private ProgressBar load;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph_view);

		values = (Values) this.getApplicationContext();
		picker = values.getPicker();
		picker.setGraphUpdateHandler(updateGraphHandler);

		TextView title = (TextView) this.findViewById(R.id.title);
		
		if (picker.getChartType().equals("area"))
			chart = (TimelineView) this.findViewById(R.id.timeline);
		else if (picker.getChartType().equals("bar")){
			chart = (ChartView) this.findViewById(R.id.barchart);
		}
		load = (ProgressBar) this.findViewById(R.id.load);
		listview = (ListView) findViewById(R.id.listview);
		chart.setPicker(picker);
		title.setText(picker.getTitle());

		chart.constructGraph();
		//updateGraphHandler.sendEmptyMessage(0);

		populatePicker();

	}

	public Handler updateGraphHandler = new Handler() {

		public void handleMessage(Message msg) {
			load.setVisibility(View.VISIBLE);
			chart.updateGraph();
			load.setVisibility(View.GONE);

		}

	};

	private void populatePicker() {

		ArrayList<Row> cells = picker.getRows();

		if (cells.size() != 0) {
			ItemAdapter itemadapter = new ItemAdapter(this, cells);
			for (Row cell : cells)
				itemadapter.add(cell);
			listview.setAdapter(itemadapter);

			itemadapter.notifyDataSetChanged();
			UIUtil.setListViewHeightBasedOnChildren(listview, itemadapter);
		}

	}

}