package com.num.services;



import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import com.num.Values;
import com.num.helpers.ServiceHelper;
import com.num.helpers.ThreadPoolHelper;
import com.num.helpers.UserDataHelper;
import com.num.listeners.BaseResponseListener;
import com.num.listeners.FakeListener;
import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.LastMile;
import com.num.models.Link;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.Traceroute;
import com.num.models.TracerouteEntry;
import com.num.models.Usage;
import com.num.models.WarmupExperiment;
import com.num.models.Wifi;
import com.num.tasks.MeasurementTask;
import com.num.tasks.ParameterTask;
import com.num.utils.StateUtil;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;

public class MeasurementService extends IntentService {
	private PowerManager.WakeLock wakeLock;
	
	public static boolean isExecuting;
	private ThreadPoolHelper serverhelper;
	private boolean doThroughput;
	private Context context;

	public MeasurementService() {
		super(MeasurementService.class.getName());
	}

	public void onBegin() {
		isExecuting = true;
		context = this;
		// obtain wake lock, otherways our service ma stop executing
		PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
				MeasurementService.class.getName());
		wakeLock.acquire();
		System.out.println("wakelock acquired");
		ServiceHelper.recurringStartService(this);

	}

	private void onEnd() {
		wakeLock.release();
		System.out.println("wakelock released");
		isExecuting = false;
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		try {			
			onBegin();
			runTask();
			onEnd();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void runTask() {

		Values session = (Values) this.getApplicationContext();
		
		serverhelper = new ThreadPoolHelper(1,
				session.THREADPOOL_KEEPALIVE_SEC);

		doThroughput = session.doThroughput();

		session.incrementThroughput();

		if (doThroughput) {

			StateUtil stateutil = new StateUtil(this);
			boolean clear = stateutil.isNetworkClear();

			if (!clear) {
				session.decrementThroughput();
				doThroughput = false;
			}
		}
		UserDataHelper userhelp = new UserDataHelper(this);

		doThroughput = doThroughput && (userhelp.getDataEnable() == 1);

		if (doThroughput) {
			serverhelper.execute(new ParameterTask(this, (new Listener())));
		} else {
			serverhelper.execute(new MeasurementTask(this, false, false,
					false, new FakeListener()));
		}

		Log.i("MeasurementService", " Throughput:" + session.getThroughput());

		serverhelper.waitOnTasks(120);

	}
	
	

	public static void poke(Context ctx) {
		ctx.startService(new Intent(ctx, MeasurementService.class));
	}
	

	public class Listener extends BaseResponseListener {
		Values session = (Values) context.getApplicationContext();

		public void onComplete(String response) {
			System.out.println("throughput succeed");
			ThroughputHandler.sendEmptyMessage(0);

		}

		public void onFail(String response) {
			System.out.println("throughput failed");
			session.decrementThroughput();
			doThroughput = false;
			NoThroughputHandler.sendEmptyMessage(0);

		}

		public void onCompletePing(Ping response) {
			// TODO Auto-generated method stub

		}

		public void onCompleteMeasurement(Measurement response) {
			// TODO Auto-generated method stub

		}

		public void onCompleteDevice(Device response) {
			// TODO Auto-generated method stub

		}

		public void onCompleteBattery(Battery response) {
			// TODO Auto-generated method stub

		}

		public void onUpdateProgress(int val) {
			// TODO Auto-generated method stub

		}

		public void onCompleteGPS(GPS gps) {
			// TODO Auto-generated method stub

		}

		public void onCompleteUsage(Usage usage) {
			// TODO Auto-generated method stub

		}

		public void onCompleteThroughput(Throughput throughput) {
			// TODO Auto-generated method stub

		}

		public void makeToast(String text) {
			// TODO Auto-generated method stub

		}

		public void onCompleteSignal(String signalStrength) {
			// TODO Auto-generated method stub

		}

		public void onCompleteWifi(Wifi wifiList) {
			// TODO Auto-generated method stub

		}

		public void onCompleteNetwork(Network network) {
			// TODO Auto-generated method stub

		}

		public void onCompleteSIM(Sim sim) {
			// TODO Auto-generated method stub

		}

		public void onCompleteSummary(JSONObject Object) {

		}

		private Handler ThroughputHandler = new Handler() {
			public void handleMessage(Message msg) {
				try {
					serverhelper.execute(new MeasurementTask(context, false,
							true, false, new FakeListener()));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		private Handler NoThroughputHandler = new Handler() {
			public void handleMessage(Message msg) {
				try {
					serverhelper.execute(new MeasurementTask(context, false,
							false, false, new FakeListener()));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

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

		public void onCompleteWarmupExperiment(WarmupExperiment experiment) {
			// TODO Auto-generated method stub
			
		}

	}

	
}
