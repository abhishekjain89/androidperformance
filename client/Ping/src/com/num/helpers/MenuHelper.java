package com.num.helpers;

import com.num.activities.AboutUsActivity;
import com.num.activities.UserFormActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MenuHelper {
	
	protected static final int MENU_PERIODIC = Menu.FIRST;

	protected static final int MENU_ABOUT = Menu.FIRST +4;

	protected static final int PAST_RECORD = Menu.FIRST +5;
	
	public static Menu fillMenu(Menu menu){
		
		menu.add(0, MENU_PERIODIC, 0, "Settings");
		//menu.add(0, PAST_RECORD, 0, "View past record");
		menu.add(0, MENU_ABOUT, 0, "About Us");
		
		return menu;
	}
	
	public static void onMenuItemSelected(MenuItem item, Activity activity){
		
		switch (item.getItemId()) {
		case MENU_PERIODIC:
			Log.v("menu", "DIALOG_PERIODIC");
			Intent in1 = new Intent(activity, UserFormActivity.class);
			in1.putExtra("force",true);
			activity.startActivityForResult(in1, 0);
			break;
			
		case PAST_RECORD:
			break;		    

		case MENU_ABOUT:
			try
			{
			Intent in2 = new Intent(activity, AboutUsActivity.class);
			activity.startActivityForResult(in2, 0);
			}
			catch(ActivityNotFoundException e)
			{
				//Toast.makeText(context, "Please send us an email at MobiPerf@umich.edu", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

}
