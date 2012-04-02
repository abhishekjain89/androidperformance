package com.num.tasks;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.num.helpers.ThreadPoolHelper;
import com.num.listeners.ResponseListener;
import com.num.utils.HTTPUtil;

public class UrlTask extends ServerTask{

	String url;

	public UrlTask(Context context, Map<String, String> reqParams, String url, ResponseListener listener) {
		super(context, reqParams, listener);
		this.url = url;			
	}

	public void runTask() {

		HTTPUtil http = new HTTPUtil();
		String data = "";
		
		try {
			data = http.request(new HashMap<String,String>(), "GET", "", "", url, "");
			getResponseListener().onComplete(data);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public String toString() {
		return "Url Task";
	}
}