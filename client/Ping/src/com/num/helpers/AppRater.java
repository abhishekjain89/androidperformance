package com.num.helpers;

import java.util.HashMap;

import com.num.Values;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AppRater {
      
    public static void startIntent(Activity activity){
    	Values values = (Values) activity.getApplicationContext();
    	activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + activity.getPackageName())));
    	SharedPreferences prefs = activity.getSharedPreferences("apprater", 0);
    	SharedPreferences.Editor editor = prefs.edit();
    	
        if (editor != null) {
            editor.putBoolean("dontshowagain", true);
            editor.commit();
        }
    }
}