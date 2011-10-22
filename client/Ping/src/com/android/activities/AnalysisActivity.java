package com.android.activities;


import java.util.HashMap;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.R;
import com.android.Session;
import com.android.helpers.PingHelper;
import com.android.helpers.ServerHelper;
import com.android.listeners.BaseResponseListener;
import com.android.models.Ping;
import com.android.tasks.MeasurementTask;
import com.android.tasks.PingTask;
import com.android.utils.ParseUtil;


public class AnalysisActivity extends Activity 
{
	
	private Button testButton;
	private TextView tv;
	private Activity activity;
	private ServerHelper serverhelper;
	private Session session = null;
	private int rows=0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		activity = this;
		session =  (Session) getApplicationContext();
		serverhelper = new ServerHelper(session);
		testButton=(Button)findViewById(R.id.test);
		tv = (TextView)findViewById(R.id.textView1);
		
		
		//****
		TableLayout tl = (TableLayout)findViewById(R.id.tableLayout1);
		TableRow tr = new TableRow(this);
		tr.setId(100+rows);
        tr.setLayoutParams(new LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.WRAP_CONTENT));
        
        TextView cell1 = new TextView(this);
        cell1.setId(200+rows);
        cell1.setText("Measurement");
        cell1.setTextColor(Color.BLACK);
        cell1.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        tr.addView(cell1);
        
        TextView cell2 = new TextView(this);
        cell2.setId(300+rows);
        cell2.setText("Maximum");
        cell2.setTextColor(Color.BLACK);
        cell2.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        tr.addView(cell2);
        
        TextView cell3 = new TextView(this);
        cell3.setId(400+rows);
        cell3.setText("Minimum");
        cell3.setTextColor(Color.BLACK);
        cell3.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        tr.addView(cell3);
        
        TextView cell4 = new TextView(this);
        cell4.setId(500+rows);
        cell4.setText("Std deviation");
        cell4.setTextColor(Color.BLACK);
        cell4.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        tr.addView(cell4);
        
        TextView cell5 = new TextView(this);
        cell5.setId(600+rows);
        cell5.setText("Average");
        cell5.setTextColor(Color.BLACK);
        cell5.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        tr.addView(cell5);
        
        
        tl.addView(tr,new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        rows++;
        //****
		
		
		testButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {		
				serverhelper.execute(new MeasurementTask(activity,new HashMap<String,String>(), new MeasurementListener()));
			}
		});

	}
	
	private void updateTable(Ping p){
		//****
		TableLayout tl = (TableLayout)findViewById(R.id.tableLayout1);
		TableRow tr = new TableRow(this);
        tr.setLayoutParams(new LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.WRAP_CONTENT));
        
        TextView cell1 = new TextView(this);
        cell1.setId(100+rows);
        cell1.setText(String.valueOf(rows));
        cell1.setTextColor(Color.BLACK);
        cell1.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        tr.addView(cell1);
        
        TextView cell2 = new TextView(this);
        cell2.setId(200+rows);
        cell2.setText(String.valueOf(p.getMeasure().getMax()));
        cell2.setTextColor(Color.BLACK);
        cell2.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        tr.addView(cell2);
        
        TextView cell3 = new TextView(this);
        cell3.setId(300+rows);
        cell3.setText(String.valueOf(p.getMeasure().getMin()));
        cell3.setTextColor(Color.BLACK);
        cell3.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        tr.addView(cell3);
        
        TextView cell4 = new TextView(this);
        cell4.setId(400+rows);
        cell4.setText(String.valueOf(p.getMeasure().getStddev()));
        cell4.setTextColor(Color.BLACK);
        cell4.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        tr.addView(cell4);
        
        TextView cell5 = new TextView(this);
        cell5.setId(500+rows);
        cell5.setText(String.valueOf(p.getMeasure().getAverage()));
        cell5.setTextColor(Color.BLACK);
        cell5.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        tr.addView(cell5);
        
        
        tl.addView(tr,new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        rows++;
        //****
	}
	
	private class MeasurementListener extends BaseResponseListener{

		public void onCompletePing(Ping response) {
			Message msg=Message.obtain(handler, 0, response);
			handler.sendMessage(msg);		
		}

		public void onComplete(String response) {
		
		}
	}

	private Handler handler = new Handler() {
		public void  handleMessage(Message msg) {
			try {
				Ping p=(Ping)msg.obj;
				tv.setText(""+p.getMeasure().getAverage()+" "+p.getMeasure().getMax()+" "+p.getMeasure().getMin()+" "+p.getMeasure().getStddev());
				updateTable(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}