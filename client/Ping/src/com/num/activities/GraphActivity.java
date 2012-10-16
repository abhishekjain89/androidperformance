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
import android.widget.TextView;
import android.widget.Toast;

import com.num.Values;
import com.num.activities.AboutUsActivity.Listener;
import com.num.database.DatabasePicker;
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



public class GraphActivity extends Activity 
{
	Values values;
	DatabasePicker picker;
	LinearLayout chart;
	private XYMultipleSeriesDataset dataset;
	private XYMultipleSeriesRenderer renderer;
	
	private XYSeriesRenderer mCurrentRenderer;
	private GraphicalView mChartView;
	private int index = 0;
	private GraphData data;
	 
	private TimeSeries timeseries;
	private ListView listview;


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);				
		setContentView(R.layout.graph_view);

		values = (Values) this.getApplicationContext();
		picker = values.getPicker();
		picker.setGraphUpdateHandler(updateGraphHandler);

		TextView title = (TextView) this.findViewById(R.id.title);
		chart =  (LinearLayout) this.findViewById(R.id.chart);
		listview = (ListView) findViewById(R.id.listview);

		title.setText(picker.getTitle());
		
		createGraph();
		updateGraph();
	
		populatePicker();


	}
	
	public Handler updateGraphHandler = new Handler(){
		
		public void  handleMessage(Message msg) {
			updateGraph();
			
		}
		
		
	};
	
	private void populatePicker() {
		
		ArrayList<Row> cells = picker.getRows();
		
		if(cells.size()!=0){
			ItemAdapter itemadapter = new ItemAdapter(this,cells);
			for(Row cell: cells)
				itemadapter.add(cell);
			listview.setAdapter(itemadapter);


			itemadapter.notifyDataSetChanged();
			UIUtil.setListViewHeightBasedOnChildren(listview,itemadapter);
		}

	}

	private void updateGraph(){
		data = picker.getGraphDataWithoutOutliers();
				
		//renderer.setXAxisMax(data.getPoints().size()-1);
		renderer.setYAxisMax(data.getyMax()*1.2);
		renderer.setChartTitle(data.getxAxisTitle());
		timeseries.clear();
		int count = 0;
		for(GraphPoint point : data.getPoints()) {
			timeseries.add(point.datetime, point.y);
		}

		mChartView.repaint();

	}

	private void createGraph(){


		renderer = new XYMultipleSeriesRenderer();
		timeseries = new TimeSeries("");

		renderer.setMargins(new int[] {0, 20, -30, 0});    
		
		renderer.setYAxisMax(0);
		
		renderer.setYAxisMin(0.0);
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(getResources().getColor(R.color.black));
		renderer.setMarginsColor(getResources().getColor(R.color.black));
		renderer.setGridColor(getResources().getColor(R.color.dark_blue));

		renderer.setLabelsTextSize(14);


		renderer.setPointSize(0);		
		dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(timeseries);
		
		XYSeriesRenderer seriesrenderer = new XYSeriesRenderer();
		renderer.addSeriesRenderer(seriesrenderer);
		seriesrenderer.setPointStyle(PointStyle.CIRCLE);
		seriesrenderer.setFillPoints(true);
		seriesrenderer.setChartValuesSpacing(2); 
		seriesrenderer.setFillBelowLine(true);
		seriesrenderer.setColor(getResources().getColor(R.color.light_blue));
		seriesrenderer.setFillBelowLineColor(getResources().getColor(R.color.mid_blue));
		seriesrenderer.setLineWidth(2);
		//mChartView = ChartFactory.getCubeLineChartView(context, dataset, renderer, 0);
		 mChartView = ChartFactory.getTimeChartView(this, dataset, renderer,"MM/dd HH:00");

		renderer.setAxesColor(getResources().getColor(R.color.dark_blue));		
		renderer.setPanEnabled(false,false);
		renderer.setZoomEnabled(false, false);
		
		renderer.setChartTitleTextSize(20);
		renderer.setTextTypeface("Bold", Typeface.NORMAL);

		renderer.setClickEnabled(false);
		renderer.setShowGridX(true);
		renderer.setInScroll(true);
		renderer.setSelectableBuffer(100);
		chart.removeAllViews();
		chart.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
				250));
		mChartView.repaint();

	}
	

}