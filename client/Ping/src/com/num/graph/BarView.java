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

//import sun.awt.RepaintArea;

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

public class BarView extends ChartView{


	XYSeries xyseries;

	public BarView(Context _context, AttributeSet _attrs) {
		super(_context, _attrs);		
	}
	
	public void constructGraph() {

		SimpleSeriesRenderer simpleSeriesRenderer = new SimpleSeriesRenderer();
		simpleSeriesRenderer.setColor(getResources().getColor(R.color.mid_blue));
		
	    renderer.addSeriesRenderer(simpleSeriesRenderer);
	 	
		xyseries = new XYSeries("");		
		renderer.setYLabels(4);
		renderer.setXLabels(0);
		renderer.setBarSpacing(0.15);
		
		renderer.setAxisTitleTextSize(16);
	    renderer.setChartTitleTextSize(20);
	    renderer.setLabelsTextSize(20);
	    
	    dataset.addSeries(xyseries);
	    
		mChartView = ChartFactory.getBarChartView(context, dataset, renderer,Type.DEFAULT);		
		super.constructGraph();

	}

	public void updateGraph() {
		super.updateGraph();								
		renderer.clearXTextLabels();
		xyseries.clear();
		
		for(GraphPoint point : data.getPoints()) {
			xyseries.add(point.x, point.y);
			renderer.addXTextLabel(point.x, point.string);
		}
		
		xyseries.add(data.getPoints().size(),0);
		xyseries.add(-1, 0);
		repaint();
	}

}
