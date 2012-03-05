package com.ping.activities;

import java.util.ArrayList;

import com.ping.R;
import com.ping.Values;
import com.ping.models.Model;
import com.ping.models.Row;
import com.ping.ui.UIUtil;
import com.ping.ui.adapter.ItemAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayActivity extends Activity {
	
	Values session;
	TextView title;
	ListView listview;
	ImageView imageview;
	Button note;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_view);
		session = (Values) this.getApplicationContext();
		Bundle extras = getIntent().getExtras();
		String key = extras.getString("model_key");
		
		Model item = session.getModel(key);
		
		
		title =  (TextView) findViewById(R.id.title);
		listview = (ListView) findViewById(R.id.listview);
		imageview = (ImageView) findViewById(R.id.image);
		note = (Button) findViewById(R.id.note);
		//note.setVisibility(View.GONE);
		title.setText(item.getTitle());
		
		imageview.setImageResource(item.getIcon());
		
		ArrayList<Row> cells = item.getDisplayData();

		if(cells.size()!=0){
			ItemAdapter itemadapter = new ItemAdapter(this,note,cells);
			for(Row cell: cells)
				itemadapter.add(cell);
			listview.setAdapter(itemadapter);


			itemadapter.notifyDataSetChanged();
			UIUtil.setListViewHeightBasedOnChildren(listview,itemadapter);
		}

	}
}