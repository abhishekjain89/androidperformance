package com.ping.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import com.ping.*;
import com.ping.models.Measurement;
import com.ping.models.Screen;
import com.ping.utils.HTTPUtil;
import org.json.JSONObject;
import android.content.Context;

public class MeasurementHelper {

	public static void attemptSendingUnsentMeasurements(Context context){
		Values session = (Values) context.getApplicationContext();
		HTTPUtil http = new HTTPUtil();

		while(!session.unsentMeasurements.isEmpty()){

			Measurement measurement = session.unsentMeasurements.getLastMeasurement();
			measurement.setScreens(new ArrayList<Screen>());
			JSONObject object = measurement.toJSON();

			try {
				String output = http.request(new HashMap<String,String>(), "POST", "measurement", "", object.toString());
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
			return "Failure";
		}

		
		String isSuccess = "Failure";
		try {

			String output = http.request(new HashMap<String,String>(), "POST", "measurement", "", object.toString());
			System.out.println(object.toString());
			System.out.println(output);
			session.screenBuffer = new ArrayList<Screen>();
			isSuccess = "Success";

			MeasurementHelper.attemptSendingUnsentMeasurements(context);

		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = "Failure";

			session.unsentMeasurements.addMeasurment(measurement);
		}
		
		return isSuccess;
	}

}
