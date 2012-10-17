package com.num.graph;

import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.num.R;
import com.num.Values;
import com.num.database.DatabasePicker;
import com.num.models.GraphData;
import com.num.models.GraphPoint;

public abstract class CopyOfChartView extends LinearLayout implements OnClickListener{

	Values values;
	DatabasePicker picker;

	Context context;

	private LayoutInflater mInflater;
	private LinearLayout mBarView;

	private XYMultipleSeriesDataset dataset;
	private XYMultipleSeriesRenderer renderer;

	private XYSeriesRenderer mCurrentRenderer;
	private GraphicalView mChartView;
	private int index = 0;
	private GraphData data;

	private String chartType="area";

	private TimeSeries timeseries;
	
	private SimpleSeriesRenderer simpleSeriesRenderer;
	private CategorySeries categorySeries;
	XYSeries xyseries;

	public CopyOfChartView(Context _context, AttributeSet _attrs) {
		super(_context, _attrs);
		context = _context;
		mInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mBarView = (LinearLayout) mInflater.inflate(R.layout.cell_view_graph, null);
		addView(mBarView);

	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	public void setChartType(String type) {
		this.chartType = type;
	}

	public void setPicker(DatabasePicker picker) {
		this.picker = picker;
		setChartType(picker.getChartType());
	}

	private void constructAreaChart() {
		
		renderer = new XYMultipleSeriesRenderer();
		
		timeseries = new TimeSeries("");
		timeseries.add(new Date().getDate(),0);

		dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(timeseries);

		XYSeriesRenderer seriesrenderer = new XYSeriesRenderer();
		renderer.addSeriesRenderer(seriesrenderer);
		setColors();
		seriesrenderer.setPointStyle(PointStyle.CIRCLE);
		seriesrenderer.setFillPoints(true);
		seriesrenderer.setChartValuesSpacing(2); 
		seriesrenderer.setFillBelowLine(true);
		seriesrenderer.setColor(getResources().getColor(R.color.light_blue));
		seriesrenderer.setFillBelowLineColor(getResources().getColor(R.color.mid_blue));
		seriesrenderer.setLineWidth(2);
		//mChartView = ChartFactory.getCubeLineChartView(context, dataset, renderer, 0);
		mChartView = ChartFactory.getTimeChartView(context, dataset, renderer,"MM/dd HH:00");
	}

	private void constructBarChart() {
		
		renderer = new XYMultipleSeriesRenderer();
		setColors();
		
		SimpleSeriesRenderer simpleSeriesRenderer = new SimpleSeriesRenderer();
		simpleSeriesRenderer.setColor(getResources().getColor(R.color.mid_blue));
		
	    renderer.addSeriesRenderer(simpleSeriesRenderer);
	    
		dataset = new XYMultipleSeriesDataset();		
		
		xyseries = new XYSeries("");		
		renderer.setYLabels(4);
		renderer.setXLabels(0);
		renderer.setBarSpacing(0.15);
		
		//renderer.setShowLabels(false);
		renderer.setAxisTitleTextSize(16);
	    renderer.setChartTitleTextSize(20);
	    renderer.setLabelsTextSize(20);
	    
	    dataset.addSeries(xyseries);
	    //simpleSeriesRenderer.setDisplayChartValues(true);
		mChartView = ChartFactory.getBarChartView(context, dataset, renderer,Type.DEFAULT);
	}

	public void constructGraph() {

		if(chartType.equals("area")) {
			constructAreaChart();
		} else if(chartType.equals("bar")) {
			constructBarChart();
		}
		
		makeUnClickable();
		

		mBarView.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
				250));
		
		mChartView.repaint();
	}

	public void updateGraph() {
		data = picker.getGraphDataWithoutOutliers();
		renderer.setYAxisMax(data.getyMax()*1.2);
		renderer.setChartTitle(data.getxAxisTitle());
		if(chartType.equals("area")) {
						
			timeseries.clear();

			for(GraphPoint point : data.getPoints()) {
				timeseries.add(point.datetime, point.y);
			}
			
		} else if(chartType.equals("bar")) {					
			renderer.clearXTextLabels();
			xyseries.clear();
			
			for(GraphPoint point : data.getPoints()) {
				xyseries.add(point.x, point.y);
				renderer.addXTextLabel(point.x, point.string);
			}
			
			xyseries.add(data.getPoints().size(),0);
			xyseries.add(-1, 0);
			
			
		}
		mChartView.repaint();
	}

	private void makeUnClickable() {
		renderer.setPanEnabled(false,false);
		renderer.setZoomEnabled(false, false);		
		renderer.setClickEnabled(false);
		renderer.setShowGridX(true);
		renderer.setInScroll(true);
		renderer.setShowLegend(false);
	}

	private void setColors() {
		renderer.setMargins(new int[] {0, 25, -10, 0});    
		renderer.setYAxisMax(0);
		renderer.setYAxisMin(0.0);
		renderer.setLabelsTextSize(14);
		renderer.setPointSize(0);		
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(getResources().getColor(R.color.black));
		renderer.setMarginsColor(getResources().getColor(R.color.black));
		renderer.setGridColor(getResources().getColor(R.color.dark_blue));
		renderer.setAxesColor(getResources().getColor(R.color.dark_blue));
		renderer.setChartTitleTextSize(20);
		renderer.setTextTypeface("Bold", Typeface.NORMAL);	
		renderer.setSelectableBuffer(100);
	}


}
