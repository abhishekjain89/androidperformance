package com.ping.activities;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class BaseActivityGroup extends ActivityGroup {
	
	protected static final int MENU_PERIODIC = Menu.FIRST;

	protected static final int MENU_ABOUT = Menu.FIRST +4;

	protected static final int PAST_RECORD = Menu.FIRST +5;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_PERIODIC, 0, "Settings");
		menu.add(0, PAST_RECORD, 0, "View past record");
		menu.add(0, MENU_ABOUT, 0, "About us");
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		Log.v("menu","onOptionsItemSelected "+item.getItemId());
		switch (item.getItemId()) {
		case MENU_PERIODIC:
			Log.v("menu", "DIALOG_PERIODIC");
			Intent in1 = new Intent(this, UserFormActivity.class);
			in1.putExtra("force",true);
			startActivityForResult(in1, 0);
			break;
			
		case PAST_RECORD:
			break;		    

		case MENU_ABOUT:
			try
			{
			//	Intent in1 = new Intent(this, com.mobiperf.ui.About.class);
			//	startActivityForResult(in1, 0);
			}
			catch(ActivityNotFoundException e)
			{
				Toast.makeText(getApplicationContext(), "Please send us an email at MobiPerf@umich.edu", Toast.LENGTH_SHORT).show();
			}
			break;
		}


		return true;

	}
	
}
