package com.android.activities;


import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.R;
import com.android.Session;
import com.android.helpers.PingHelper;
import com.android.helpers.ServerHelper;
import com.android.listeners.BaseResponseListener;
import com.android.models.Ping;
import com.android.tasks.PingTask;
import com.android.utils.ParseUtil;


public class AnalysisActivity extends Activity 
{
	
	private Button testButton;
	private TextView tv;
	private Activity activity;
	private ServerHelper serverhelper;
	private Session session = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		activity = this;
		session =  (Session) getApplicationContext();
		serverhelper = new ServerHelper(session);
		testButton=(Button)findViewById(R.id.test);
		tv = (TextView)findViewById(R.id.textView1);
		
		
		testButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {		
				serverhelper.execute(new PingTask(activity,new HashMap<String,String>(), new PingListener()));
			}
		});

	}
	
	private class PingListener extends BaseResponseListener{

		public void onCompletePing(Ping response) {
			handler.sendEmptyMessage(0);		
		}

		public void onComplete(String response) {
		
		}
	}

	private Handler handler = new Handler() {
		public void  handleMessage(Message msg) {
			try {
				String output = PingHelper.getPingOutput();
				tv.setText(output);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}