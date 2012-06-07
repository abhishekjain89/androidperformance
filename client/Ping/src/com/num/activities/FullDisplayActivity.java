package com.num.activities;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.num.Values;
import com.num.activities.RunActivity.MeasurementListener;
import com.num.helpers.TaskHelper;
import com.num.helpers.ThreadPoolHelper;
import com.num.listeners.BaseResponseListener;
import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.LastMile;
import com.num.models.MainModel;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Row;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.Usage;
import com.num.models.Wifi;
import com.num.tasks.AllPingTask;
import com.num.tasks.MeasurementTask;
import com.num.ui.UIUtil;
import com.num.ui.adapter.ItemAdapter;
import com.num.R;

public class FullDisplayActivity extends Activity {

	Values session;
	TextView title;
	ListView listview;
	Button load;
	//ImageView imageview;
	TextView description;
	Activity activity;
	ProgressBar progress;
	private ThreadPoolHelper serverhelper;
	static int timeCount = 70;
	Timer timer = new Timer();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.item_view_full);
		session = (Values) this.getApplicationContext();
		Bundle extras = getIntent().getExtras();
		String key = extras.getString("model_key");


		serverhelper = new ThreadPoolHelper(5,10);
		serverhelper.execute(TaskHelper.getTask(key, activity, new MeasurementListener()));

		title =  (TextView) findViewById(R.id.title);
		listview = (ListView) findViewById(R.id.listview);
		load = (Button) findViewById(R.id.load);
		description = (TextView) findViewById(R.id.description);
		progress = (ProgressBar) findViewById(R.id.progress);
		try{
			String secs = extras.getString("time");
			timeCount = Integer.parseInt(secs);
			if(secs!=null)
				load.setText("Loading ...   will take about " + secs + " seconds");
		}
		catch (Exception e){
			timeCount = 2;
		}
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				timeCount--;		
				Message msg=Message.obtain(LoadMessageHandler, 0, new Integer(timeCount));
				LoadMessageHandler.sendMessage(msg);
			}

		}, 0, 1000);

	}

	@Override
	public void finish(){
		super.finish();
		timer.cancel();
	}

	private Handler LoadMessageHandler = new Handler(){
		public void  handleMessage(Message msg) {
			Integer time= (Integer)msg.obj;

			if(time>0)
				load.setText("Loading ...   will take about " + (time) + " seconds");
			else
				load.setText("Loading ...   Very soon!");
		}	
	};






	public class MeasurementListener extends BaseResponseListener{

		public void onCompletePing(Ping response) {

			//onCompleteOutput(response);
		}

		public void onCompleteDevice(Device response) {

			onCompleteOutput(response);
		}

		public void onCompleteMeasurement(Measurement response) {
			//LoadBarHandler.sendEmptyMessage(0);
			onCompleteOutput(response);
		}

		public void onCompleteOutput(MainModel model){

			Message msg2=Message.obtain(UIHandler, 0, model);
			UIHandler.sendMessage(msg2);
		}

		public void onComplete(String response) {

		}

		public void onUpdateProgress(int val){
			//Message msg=Message.obtain(progressBarHandler, 0, val);
			//progressBarHandler.sendMessage(msg);
		}

		public void onCompleteGPS(GPS response) {
			onCompleteOutput(response);

		}

		public void makeToast(String text) {
			//Message msg=Message.obtain(toastHandler, 0, text);
			//toastHandler.sendMessage(msg);

		}

		public void onCompleteSignal(String signalStrength) {

		}
		public void onCompleteUsage(Usage response) {
			onCompleteOutput(response);

		}

		public void onCompleteThroughput(Throughput response) {
			onCompleteOutput(response);

		}

		public void onCompleteWifi(Wifi response) {
			onCompleteOutput(response);

		}

		public void onCompleteBattery(Battery response) {
			onCompleteOutput(response);

		}

		public void onCompleteNetwork(Network response) {
			onCompleteOutput(response);

		}

		public void onCompleteSIM(Sim response) {
			//onCompleteOutput(response);

		}

		public void onCompleteSummary(JSONObject Object) {
			// TODO Auto-generated method stub

		}

		public void onFail(String response) {
			// TODO Auto-generated method stub

		}

		public void onCompleteLastMile(LastMile lastMile) {
			// TODO Auto-generated method stub

		}
	}


	private Handler UIHandler = new Handler(){
		public void  handleMessage(Message msg) {

			MainModel item = (MainModel)msg.obj;
			load.setVisibility(View.GONE);
			progress.setVisibility(View.GONE);

			title.setText(item.getTitle().toUpperCase());
			description.setText(item.getDescription());

			//imageview.setImageResource(item.getIcon());

			ArrayList<Row> cells = item.getDisplayData();

			if(cells.size()!=0){
				ItemAdapter itemadapter = new ItemAdapter(activity,cells);
				for(Row cell: cells)
					itemadapter.add(cell);
				listview.setAdapter(itemadapter);


				itemadapter.notifyDataSetChanged();
				UIUtil.setListViewHeightBasedOnChildren(listview,itemadapter);
			}


		}
	};
}