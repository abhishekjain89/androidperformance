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

public class TimelineView extends ChartView{


	private TimeSeries timeseries;

	public TimelineView(Context _context, AttributeSet _attrs) {
		super(_context, _attrs);		
	}
	
	public void constructGraph() {

		timeseries = new TimeSeries("");
		timeseries.add(new Date().getDate(),0);

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
		renderer.setPointSize(0);
		mChartView = ChartFactory.getTimeChartView(context, dataset, renderer,"MM/dd HH:00");		
		super.constructGraph();

	}

	public void updateGraph() {
		super.updateGraph();								
		timeseries.clear();

		for(GraphPoint point : data.getPoints()) {
			timeseries.add(point.datetime, point.getValue());
		}
		repaint();
	}

}
