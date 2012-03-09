package com.num.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.num.Values;
import com.num.models.MainModel;
import com.num.models.Row;
import com.num.ui.UIUtil;
import com.num.ui.adapter.ItemAdapter;
import com.num.R;

public class DisplayActivity extends Activity {
	
	Values session;
	TextView title;
	ListView listview;
	//ImageView imageview;
	TextView description;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_view);
		session = (Values) this.getApplicationContext();
		Bundle extras = getIntent().getExtras();
		String key = extras.getString("model_key");
		
		MainModel item = session.getModel(key);
		
		
		title =  (TextView) findViewById(R.id.title);
		listview = (ListView) findViewById(R.id.listview);
		//imageview = (ImageView) findViewById(R.id.image);
		description = (TextView) findViewById(R.id.description);
		
		//note.setVisibility(View.GONE);
		title.setText(item.getTitle());
		description.setText(item.getDescription());
		
		//imageview.setImageResource(item.getIcon());
		
		ArrayList<Row> cells = item.getDisplayData();

		if(cells.size()!=0){
			ItemAdapter itemadapter = new ItemAdapter(this,cells);
			for(Row cell: cells)
				itemadapter.add(cell);
			listview.setAdapter(itemadapter);


			itemadapter.notifyDataSetChanged();
			UIUtil.setListViewHeightBasedOnChildren(listview,itemadapter);
		}

	}
}