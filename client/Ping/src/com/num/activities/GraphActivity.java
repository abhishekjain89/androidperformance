package com.num.activities;


import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
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
	private XYSeries mCurrentSeries;
	private XYSeriesRenderer mCurrentRenderer;
	private GraphicalView mChartView;
	private int index = 0;
	private GraphData data;
	private XYSeries xy; 


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);				
		setContentView(R.layout.graph_view);

		values = (Values) this.getApplicationContext();
		picker = values.getPicker();

		TextView title = (TextView) this.findViewById(R.id.title);
		chart =  (LinearLayout) this.findViewById(R.id.chart);

		title.setText(picker.getTitle());

		data = picker.getGraphData();
		
		createGraph(this);
		updateGraph(this);


	}

	private void updateGraph(Context context){


		renderer.setXAxisMax(data.getPoints().size()-1);

		int count = 0;
		for(GraphPoint point : data.getPoints()) {
			xy.add(count++, point.y);
		}

		mChartView.repaint();

	}

	private void createGraph(Context context){


		renderer = new XYMultipleSeriesRenderer();
		xy = new XYSeries("");

		renderer.setMargins(new int[] {0, 20, -30, 0});    

		renderer.setYAxisMax(data.getyMax()*1.2);
		renderer.setXAxisMin(0.0);
		renderer.setYAxisMin(0.0);
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(context.getResources().getColor(R.color.black));
		renderer.setMarginsColor(context.getResources().getColor(R.color.black));
		renderer.setGridColor(context.getResources().getColor(R.color.dark_blue));

		renderer.setLabelsTextSize(14);


		renderer.setPointSize(0);		
		dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(xy);
		mCurrentSeries = xy;
		XYSeriesRenderer seriesrenderer = new XYSeriesRenderer();
		renderer.addSeriesRenderer(seriesrenderer);
		seriesrenderer.setPointStyle(PointStyle.CIRCLE);
		seriesrenderer.setFillPoints(true);
		seriesrenderer.setChartValuesSpacing(2); 
		seriesrenderer.setFillBelowLine(true);
		seriesrenderer.setColor(context.getResources().getColor(R.color.light_blue));
		seriesrenderer.setFillBelowLineColor(context.getResources().getColor(R.color.mid_blue));
		seriesrenderer.setLineWidth(2);
		mChartView = ChartFactory.getCubeLineChartView(context, dataset, renderer, 0);

		renderer.setAxesColor(context.getResources().getColor(R.color.dark_blue));		
		renderer.setPanEnabled(false,false);
		renderer.setZoomEnabled(false, false);
		renderer.setChartTitle(data.getxAxisTitle());
		renderer.setChartTitleTextSize(20);
		renderer.setTextTypeface("Bold", Typeface.NORMAL);
		//		renderer.setChartValuesTextSize(//arg0)
		renderer.setClickEnabled(false);
		renderer.setShowGridX(true);
		renderer.setInScroll(true);
		renderer.setSelectableBuffer(100);
		chart.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
				250));

	}



}