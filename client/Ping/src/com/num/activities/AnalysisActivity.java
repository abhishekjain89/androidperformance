package com.num.activities;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.num.Values;
import com.num.database.DatabasePicker;
import com.num.database.datasource.ApplicationDataSource;
import com.num.database.datasource.LatencyDataSource;
import com.num.database.mapping.ApplicationMapping;
import com.num.database.mapping.LatencyMapping;
import com.num.helpers.ServiceHelper;
import com.num.helpers.ThreadPoolHelper;
import com.num.listeners.BaseResponseListener;
import com.num.listeners.FakeListener;
import com.num.models.ActivityItem;
import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Row;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.Usage;
import com.num.models.Wifi;
import com.num.tasks.MeasurementTask;
import com.num.tasks.SummaryTask;
import com.num.tasks.ValuesTask;
import com.num.ui.UIUtil;
import com.num.ui.adapter.ItemAdapter;
import com.num.utils.DeviceUtil;
import com.num.R;

public class AnalysisActivity extends Activity {

	private ListView listview;
	private TextView apptext;
	private TextView devicetext;
	// private TextView tv;
	private Activity activity;
	private ThreadPoolHelper serverhelper;
	private Values session = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.measurement_screen);

		activity = this;
		session = (Values) getApplicationContext();
		session.loadValues();
		listview = (ListView) findViewById(R.id.listview);
		
		serverhelper = new ThreadPoolHelper(10, 30);

		serverhelper.execute(new ValuesTask(this, new FakeListener()));
		ServiceHelper.processRestartService(this);
		ArrayList<Row> cells = new ArrayList<Row>();

		cells.add(new Row(new ActivityItem("Application Usage", "Get data breakdown by app", new Handler() {

			public void handleMessage(Message msg) {
				Intent myIntent = new Intent(activity,
						FullDisplayActivity.class);
				myIntent.putExtra("model_key", "usage");
				startActivity(myIntent);
			}

		}, R.drawable.usage)));		

		cells.add(new Row(new ActivityItem("Speed Test", "Get Down/Up speed, 40 sec", new Handler() {
			public void handleMessage(Message msg) {
				Intent myIntent = new Intent(activity,
						FullDisplayActivity.class);
				myIntent.putExtra("model_key", "throughput");
				myIntent.putExtra("time", "45");
				startActivity(myIntent);}

		}, R.drawable.throughput)));


		cells.add(new Row(new ActivityItem("Latency", "Get time to reach server, 5 sec", new Handler() {
			public void handleMessage(Message msg) {
				Intent myIntent = new Intent(activity,
						FullDisplayActivity.class);
				myIntent.putExtra("model_key", "latency");
				myIntent.putExtra("time", "15");
				startActivity(myIntent);}

		}, R.drawable.stopwatch)));
		
		cells.add(new Row(new ActivityItem("Configure", "Change preference", new Handler() {
			public void handleMessage(Message msg) {
				Intent myIntent = new Intent(activity, UserFormActivity.class);
				myIntent.putExtra("force",true);
				startActivity(myIntent);
				}

		}, R.drawable.configure)));
		
		cells.add(new Row(new ActivityItem("About Us", "Read about this project", new Handler() {
			public void handleMessage(Message msg) {
				Intent myIntent = new Intent(activity, AboutUsActivity.class);				
				startActivity(myIntent);
				}

		}, R.drawable.team)));
		
		cells.add(new Row(new ActivityItem("Graphing", "Quick Graph", new Handler() {
			public void handleMessage(Message msg) {
				
				DatabasePicker picker = session.createPicker(new LatencyDataSource(activity));
				picker.setTitle("Latency Graph");
				picker.filterBy(LatencyMapping.COLUMN_TYPE,"ping","Type");
				picker.filterBy(LatencyMapping.COLUMN_DSTIP,"gsoogle","Destination");
				picker.filterBy(LatencyMapping.COLUMN_MEASUREMENT,"average","Metric");
				picker.filterBy(LatencyMapping.COLUMN_CONNECTION,DeviceUtil.getNetworkInfo(activity),"Connection");
				Intent myIntent = new Intent(activity, GraphActivity.class);				
				activity.startActivity(myIntent);
                                             
				}

		}, R.drawable.measure)));


		ItemAdapter itemadapter = new ItemAdapter(activity, cells);
		for(Row cell: cells)
			itemadapter.add(cell);
		listview.setAdapter(itemadapter);
		itemadapter.notifyDataSetChanged();
		UIUtil.setListViewHeightBasedOnChildren(listview,itemadapter);

	}

}