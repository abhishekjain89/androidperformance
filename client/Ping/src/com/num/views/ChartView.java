package com.num.views;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
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

public class ChartView extends LinearLayout implements OnClickListener{
	
	Values values;
	DatabasePicker picker;
	LinearLayout chart;
	Context context;
	
	private LayoutInflater mInflater;
	private LinearLayout mBarView;
	
	private XYMultipleSeriesDataset dataset;
	private XYMultipleSeriesRenderer renderer;
	
	private XYSeriesRenderer mCurrentRenderer;
	private GraphicalView mChartView;
	private int index = 0;
	private GraphData data;
	 
	private TimeSeries timeseries;
	
	public ChartView(Context _context, AttributeSet _attrs) {
		super(_context, _attrs);
		context = _context;
		mInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mBarView = (LinearLayout) mInflater.inflate(R.layout.cell_view_graph, null);
		addView(mBarView);
		
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void setPicker(DatabasePicker picker) {
		this.picker = picker;
	}
	
	public void constructGraph() {
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
		 mChartView = ChartFactory.getTimeChartView(context, dataset, renderer,"MM/dd HH:00");

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
	
	public void updateGraph() {
		data = picker.getGraphDataWithoutOutliers();
		
		//renderer.setXAxisMax(data.getPoints().size()-1);
		renderer.setYAxisMax(data.getyMax()*1.2);
		renderer.setChartTitle(data.getxAxisTitle());
		timeseries.clear();
		
		for(GraphPoint point : data.getPoints()) {
			timeseries.add(point.datetime, point.y);
		}

		mChartView.repaint();
	}
	

}
