package com.num.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import com.num.*;
import com.num.models.Measurement;
import com.num.models.Screen;
import com.num.utils.HTTPUtil;

import org.json.JSONObject;
import android.content.Context;

public class MeasurementHelper {

	public static void attemptSendingUnsentMeasurements(Context context){
		Values session = (Values) context.getApplicationContext();
		HTTPUtil http = new HTTPUtil();

		while(!session.unsentMeasurements.isEmpty()){

			String input = session.unsentMeasurements.getLastMeasurement();
			

			try {
				String output = http.request(new HashMap<String,String>(), "POST", "measurement", "", input);
				session.unsentMeasurements.removeLastMeasurement();
			} catch (Exception e) {
				return;
			}

		}	
	}

	public static String sendMeasurement(Context context,Measurement measurement){
		Values session = (Values) context.getApplicationContext();
		HTTPUtil http = new HTTPUtil();
		JSONObject object = new JSONObject();

		try {
			object = measurement.toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			GAnalytics.log(GAnalytics.MEASUREMENT, "Send Fail New",e.getMessage());
			return "Failure";
			
		}
		GAnalytics.log(GAnalytics.MEASUREMENT, "Send Success New","");
		
		
		try {

			String output = http.request(new HashMap<String,String>(), "POST", "measurement", "", object.toString());
			System.out.println(object.toString());
			System.out.println(output);
			session.screenBuffer = new ArrayList<Screen>();
		

			MeasurementHelper.attemptSendingUnsentMeasurements(context);
			GAnalytics.log(GAnalytics.MEASUREMENT, "Send Success Old","");
		} catch (Exception e) {
			e.printStackTrace();
	
			GAnalytics.log(GAnalytics.MEASUREMENT, "Send Fail Old",e.getMessage());
			return "Failure";
			
		}
		
		return "Success";
	}

}
