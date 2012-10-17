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

public abstract class ChartView extends LinearLayout{

	Values values;
	DatabasePicker picker;
	Context context;
	
	private LayoutInflater mInflater;
	private LinearLayout mBarView;

	protected XYMultipleSeriesDataset dataset;
	protected XYMultipleSeriesRenderer renderer;

	protected GraphicalView mChartView;
	protected GraphData data;

	public ChartView(Context _context, AttributeSet _attrs) {
		super(_context, _attrs);
		context = _context;
		mInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mBarView = (LinearLayout) mInflater.inflate(R.layout.cell_view_graph, null);
		addView(mBarView);
		renderer = new XYMultipleSeriesRenderer();
		dataset = new XYMultipleSeriesDataset();
	}

	public void setPicker(DatabasePicker picker) {
		this.picker = picker;	
	}

	public void constructGraph() {
		
		makeUnClickable();		
		customizeUI();
		mBarView.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
				250));		
		repaint();
	}

	public void updateGraph() {
		data = picker.getGraphDataWithoutOutliers();
		renderer.setYAxisMax(data.getyMax()*1.2);
		renderer.setChartTitle(data.getxAxisTitle());			
	}
	
	protected void repaint() {
		mChartView.repaint();
	}

	protected void makeUnClickable() {
		renderer.setPanEnabled(false,false);
		renderer.setZoomEnabled(false, false);		
		renderer.setClickEnabled(false);
		renderer.setShowGridX(true);
		renderer.setInScroll(true);
		renderer.setShowLegend(false);
	}

	protected void customizeUI() {
		renderer.setMargins(new int[] {0, 35, -10, 0});    
		renderer.setYAxisMax(0);		
		renderer.setYTitle(picker.getYAxisLabel());
		renderer.setYAxisMin(0.0);
		renderer.setLabelsTextSize(14);
		renderer.setPointSize(0);		
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(getResources().getColor(R.color.black));
		renderer.setLabelsColor(getResources().getColor(R.color.text_primary));
		renderer.setMarginsColor(getResources().getColor(R.color.black));
		renderer.setGridColor(getResources().getColor(R.color.dark_blue));
		renderer.setAxesColor(getResources().getColor(R.color.dark_blue));
		renderer.setChartTitleTextSize(20);
		renderer.setTextTypeface("Bold", Typeface.NORMAL);	
		renderer.setSelectableBuffer(100);
	}


}
