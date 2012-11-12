package com.num.activities;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.num.Values;
import com.num.helpers.ServiceHelper;
import com.num.helpers.ThreadPoolHelper;
import com.num.listeners.BaseResponseListener;
import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.LastMile;
import com.num.models.Link;
import com.num.models.MainModel;
import com.num.models.Measurement;
import com.num.models.Model;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.Traceroute;
import com.num.models.TracerouteEntry;
import com.num.models.Usage;
import com.num.models.Wifi;
import com.num.tasks.MeasurementTask;
import com.num.ui.adapter.ListAdapter;
import com.num.R;


public class RunActivity extends BaseActivityGroup
{
	//private LinearLayout table;

	private ThreadPoolHelper serverhelper;
	private Values session = null;
	private Activity activity;
	private boolean firstPing=true;
	private static final int SWIPE_MIN_DISTANCE = 180; 
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;
	static int timeCount = 70;


	public ArrayList<Model> items;
	public ListView listview;
	public ListAdapter listadapter;
	public HorizontalScrollView scroll;
	public Button nextButton;
	public ProgressBar progress;

	Resources res;
	TabHost tabHost;
	TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	Intent intent;  // Reusable Intent for each tab
	public Button load;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manual_test);

		activity = this;

		serverhelper = new ThreadPoolHelper(5,10);


		ServiceHelper.processStopService(this);
		session = (Values) this.getApplicationContext();
		session.initDataStore();
		items = new ArrayList<Model>();
		nextButton = (Button) findViewById(R.id.next);
		progress = (ProgressBar) findViewById(R.id.progress);
		//listadapter = new ListAdapter(activity,noteButton,R.layout.item_view,items);
		serverhelper.execute(new MeasurementTask(activity,true,true,true, new MeasurementListener()));
		//listview.setAdapter(listadapter);
		scroll = (HorizontalScrollView) findViewById(R.id.scroller);
		load = (Button) findViewById(R.id.load);

		res = getResources(); // Resource object to get Drawables
		tabHost =  (TabHost) findViewById(R.id.tabhost);
		tabHost.setup(this.getLocalActivityManager());
		
		

		nextButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	

				int id = tabHost.getCurrentTab();
				tabHost.setCurrentTab(id+1);


				View v1 = tabHost.getCurrentTabView();
				int diff = v1.getLeft() - scroll.getScrollX() - 154;
				moveScrollBy(diff);
				LastChosen =v1;

				toggleVisibility();
			}
		});

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				timeCount--;		
				Message msg=Message.obtain(LoadMessageHandler, 0, new Integer(timeCount));
				LoadMessageHandler.sendMessage(msg);
			}

		}, 0, 1000);

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




	public void toggleVisibility(){

		if(tabHost.getCurrentTab()==tabHost.getTabWidget().getTabCount()-1){
			nextButton.setVisibility(View.INVISIBLE);
			progress.setVisibility(View.VISIBLE);
		}
		else{
			nextButton.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
		}
	}

	public class MeasurementListener extends BaseResponseListener{

		public void onCompletePing(Ping response) {

			//onCompleteOutput(response);
		}

		public void onCompleteDevice(Device response) {

			onCompleteOutput(response);
		}

		public void onCompleteMeasurement(Measurement response) {
			LoadBarHandler.sendEmptyMessage(0);
			onCompleteOutput(response);
		}

		public void onCompleteOutput(MainModel model){

			Message msg2=Message.obtain(UIHandler, 0, model);
			UIHandler.sendMessage(msg2);
		}

		public void onComplete(String response) {

		}

		public void onUpdateProgress(int val){
			Message msg=Message.obtain(progressBarHandler, 0, val);
			progressBarHandler.sendMessage(msg);
		}

		public void onCompleteGPS(GPS response) {
			onCompleteOutput(response);

		}

		public void makeToast(String text) {
			Message msg=Message.obtain(toastHandler, 0, text);
			toastHandler.sendMessage(msg);

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

		public void onUpdateUpLink(Link link) {
			// TODO Auto-generated method stub
			
		}

		public void onUpdateDownLink(Link link) {
			// TODO Auto-generated method stub
			
		}

		public void onUpdateThroughput(Throughput throughput) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteTraceroute(Traceroute traceroute) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteTracerouteHop(TracerouteEntry traceroute) {
			// TODO Auto-generated method stub
			
		}
	}

	private Handler toastHandler = new Handler() {
		public void  handleMessage(Message msg) {
			try {
				String text = (String)msg.obj;
				//Toast.makeText(activity, text, 1000);
				Toast.makeText(RunActivity.this, text, 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};



	private Handler UIHandler = new Handler(){
		public void  handleMessage(Message msg) {



			MainModel item = (MainModel)msg.obj;
			items.add(item);
			/*listadapter.add(item);			
			listadapter.notifyDataSetChanged();*/
			intent = new Intent().setClass(activity, DisplayActivity.class);

			session.storeModel(item);

			intent.putExtra("model_key",item.getTitle());

			
			View tabview = createTabView(tabHost.getContext(),item);					
			tabview.setTag(item.getTitle());			
			spec = tabHost.newTabSpec(item.getTitle()).setIndicator(tabview)
					.setContent(intent);

			
			tabHost.addTab(spec);

			if(LastChosen==null){
				LastChosen = tabview;
			}else{
				int diff = LastChosen.getLeft() - scroll.getScrollX() - 154;
				moveScrollBy(diff);
			}
			tabHost.setCurrentTabByTag(tabHost.getCurrentTabTag());

			toggleVisibility();
			tabview.setOnClickListener(new OnClickListener()  {
				public void onClick(View v) {	
					tabHost.setCurrentTabByTag((String) v.getTag());

					int diff = v.getLeft() - scroll.getScrollX() - 154;
					moveScrollBy(diff);
					LastChosen =v;
					toggleVisibility();
				}
			});


		}
	};
	

	private void moveScrollBy(int diff){
		final long time = diff;
		System.out.println("Difference is " + diff);
		final int scrollIni = scroll.getScrollX();
		if(time>0){
			new CountDownTimer(time, 10) { 

				public void onTick(long millisUntilFinished) { 

					scroll.scrollTo((int) (scrollIni+((time-millisUntilFinished))), 0); 
				} 

				public void onFinish() { 

				} 
			}.start();
		}
		else{

			new CountDownTimer(-time, 10) { 

				public void onTick(long millisUntilFinished) { 

					scroll.scrollTo((int) (scrollIni-((-time-millisUntilFinished))), 0); 
				} 

				public void onFinish() { 

				} 
			}.start();

		}
	}

	private View LastChosen;

	private Handler LoadBarHandler = new Handler(){
		public void  handleMessage(Message msg) {
			load.setVisibility(View.GONE);
			ServiceHelper.processStopService(activity);
			ServiceHelper.processStartService(activity);
		}
	};

	private Handler progressBarHandler = new Handler() {

		public void  handleMessage(Message msg) {
			try {
				int value=(Integer)msg.obj;
				//progressBar.setProgress(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private static View createTabView(final Context context, Model item) {

		View view = LayoutInflater.from(context).inflate(R.layout.tabs_hg, null);
		view.setPadding(0,0,0,0);


		//ImageView tv = (ImageView) view.findViewById(R.id.icon);
		TextView tv =  (TextView) view.findViewById(R.id.text);

		tv.setText(item.getTitle().toUpperCase());

		return view;

	}


}