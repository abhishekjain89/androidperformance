package com.num.ui.viewgenerator;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.num.models.GraphData;
import com.num.models.GraphPoint;
import com.num.models.Model;
import com.num.models.Row;
import com.num.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GraphViewGenerator extends ViewGenerator{

	ViewHolder holder;
	private XYMultipleSeriesDataset dataset;
	private XYMultipleSeriesRenderer renderer;
	private XYSeries mCurrentSeries;
	private XYSeriesRenderer mCurrentRenderer;
	private GraphicalView mChartView;
	private int index = 0;
	private XYSeries xy; 
	
	private GraphData data;


	public GraphViewGenerator(GraphData data) {
		super(R.layout.cell_view_graph);
		holder = new ViewHolder();
		this.data = data;
	}

	@Override
	public ViewHolder fillViewHolder(View view, LayoutInflater inflater) {
				
		holder.linear = (LinearLayout) view.findViewById(R.id.chart);
		
		return holder;
	}
	@Override
	public void populateView(Row item,Context context) {
		
		createGraph(context);
		
		xy.add(0, 5);
		xy.add(1, 23);
		xy.add(2, 53);
		xy.add(3, 51);
		
		updateGraph(context);
	}
	
	private void updateGraph(Context context){
		
		
		renderer.setXAxisMax(data.getPoints().size());
		renderer.setXAxisMin(0);
		int count = 0;
		for(GraphPoint point : data.getPoints()) {
			xy.add(count++, point.y);
		}
		
		mChartView.repaint();
		
	}
	
	private void createGraph(Context context){
		
		
		renderer = new XYMultipleSeriesRenderer();
		xy = new XYSeries("title");
		
		//renderer.setMargins(new int[] {20,20,20,20});
		
		renderer.setYAxisMax(data.getyMax());
		renderer.setXAxisMin(0.0);
		renderer.setYAxisMin(0.0);		
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(context.getResources().getColor(R.color.black));
		renderer.setMarginsColor(context.getResources().getColor(R.color.black));				
		renderer.setLabelsTextSize(0);
		renderer.setXTitle(data.getxAxisTitle());
		renderer.setMargins(new int[] { 0,0,0, 0 });		
		renderer.setPointSize(0);		
		dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(xy);
		mCurrentSeries = xy;
		XYSeriesRenderer seriesrenderer = new XYSeriesRenderer();
		renderer.addSeriesRenderer(seriesrenderer);
		seriesrenderer.setPointStyle(PointStyle.CIRCLE);
		seriesrenderer.setFillPoints(true);
		seriesrenderer.setChartValuesSpacing(4); 
		
		seriesrenderer.setColor(context.getResources().getColor(R.color.mid_blue));
		seriesrenderer.setLineWidth(4);
		
		//mChartView = ChartFactory.getBarChartView(context, dataset, renderer, org.achartengine.chart.BarChart.Type.DEFAULT);
		mChartView = ChartFactory.getLineChartView(context, dataset, renderer);
				
		renderer.setClickEnabled(true);
		renderer.setSelectableBuffer(100);
		holder.linear.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
				100));
		
	}
	

}
