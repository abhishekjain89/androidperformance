package com.num.models;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.num.R;
import com.num.Values;
import com.num.utils.DeviceUtil;
import com.num.utils.ThroughputDataSource;
import com.num.utils.ThroughputDataSource.ThroughputOutput;

public class Throughput implements MainModel{

	public Link downLink;
	public Link upLink;
	public boolean isComplete = false;


	private static String DESCRIPTION = "Upload and Download speeds";

	public String getDescription() {
		return DESCRIPTION;
	}
	public Link getDownLink() {
		return downLink;
	}
	public void setDownLink(Link downLink) {
		this.downLink = downLink;
	}
	public Link getUpLink() {
		return upLink;
	}
	public void setUpLink(Link upLink) {
		this.upLink = upLink;
	}

	public Throughput() {
		super();
		Link downLink = new Link();
		Link upLink = new Link();

	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {			

			obj.put("downLink", downLink.toJSON());
			obj.put("upLink", upLink.toJSON());

		} catch (Exception e) {
			obj = new JSONObject();
		}

		return obj;
	}

	public String getTitle() {

		return "Throughput";
	}

	public ArrayList<Row> getDisplayData(Context context){
		ArrayList<Row> data = new ArrayList<Row>();
		int downL = -1;
		int upL = -1;
		if (downLink == null) {
			downL = -1;
		} else {
			downL = (int)downLink.speedInBits();
		}
		if (upLink == null) {
			upL = -1;
		} else {
			upL = (int)upLink.speedInBits();
		}

		if (upL>0) {
			data.add(new Row("Upload",(int)upLink.getTime()/(Values.UPLINK_DURATION/100),upL+ " Kbps"));
		}

		if (downL>0) {
			data.add(new Row("Download",(int)downLink.getTime()/(Values.DOWNLINK_DURATION/100),downL + " Kbps"));
		}

		if(isComplete) {
			
			ThroughputDataSource dataSource = new ThroughputDataSource(context);
			dataSource.open();
			ThroughputOutput output = dataSource.getOutput();
			HashMap<String,ArrayList<GraphPoint>> graphPoints = dataSource.getGraphData();
			if (output.averageDownload>0) {
				data.add(new Row("DOWNLOAD GRAPH"));
				data.add(new Row("Connection",DeviceUtil.getNetworkInfo(context)));
				data.add(new Row("Avg Download",output.averageDownload + " Kbps"));
				data.add(new Row(new GraphData(graphPoints.get("downlink"))));
				if (output.averageUpload>0) {
					data.add(new Row("UPLOAD GRAPH"));
					data.add(new Row("Avg Upload",output.averageUpload + " Kbps"));
					data.add(new Row(new GraphData(graphPoints.get("uplink"))));
				}
			}
			
			dataSource.close();
		} else {
			data.add(new Row("In progress ..."));
		}


		return data;
	}

	public int getIcon() {

		return R.drawable.throughput;
	}



}
