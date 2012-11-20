package com.num.models;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.num.R;

public class Network implements MainModel{
	
	String networkOperatorId ="";
	String networkType = "";
	String connectionType = "";
	String mobileNetworkInfo = "";
	String wifiState;
	String cellId="";
	String cellLac="";	
	String dataState ="";
	String dataActivity = "";
	String signalStrength = "-1";
	String cellType = "";
	String basestationLat = "";
	String basestationLong = "";
	String networkid = "";
	String systemid = "";
	

	private static String DESCRIPTION = "Details of your device's current cellular network";

	public String getDescription() {
		return DESCRIPTION;
	}
	public String getCellType() {
		return cellType;
	}
	public void setCellType(String cellType) {
		this.cellType = cellType;
	}
	public String getBasestationLat() {
		return basestationLat;
	}
	public void setBasestationLat(String basestationLat) {
		this.basestationLat = basestationLat;
	}
	public String getBasestationLong() {
		return basestationLong;
	}
	public void setBasestationLong(String basestationLong) {
		this.basestationLong = basestationLong;
	}
	public String getNetworkid() {
		return networkid;
	}
	public void setNetworkid(String networkid) {
		this.networkid = networkid;
	}
	public String getSystemid() {
		return systemid;
	}
	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}
	public String getSignalStrength() {
		return signalStrength;
	}
	public void setSignalStrength(String signalStrength) {
		this.signalStrength = signalStrength;
	}
	public String getNetworkOperatorId() {
		return networkOperatorId;
	}
	public void setNetworkOperatorId(String networkOperatorId) {
		this.networkOperatorId = networkOperatorId;
	}
	public String getNetworkType() {
		return networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	public String getConnectionType() {
		return connectionType;
	}
	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}
	public String getMobileNetworkInfo() {
		return mobileNetworkInfo;
	}
	public void setMobileNetworkInfo(String mobileNetworkInfo) {
		this.mobileNetworkInfo = mobileNetworkInfo;
	}
	public String getWifiState() {
		return wifiState;
	}
	public void setWifiState(String wifiState) {
		this.wifiState = wifiState;
	}
	public String getCellId() {
		return cellId;
	}
	public void setCellId(String cellId) {
		this.cellId = cellId;
	}
	public String getCellLac() {
		return cellLac;
	}
	public void setCellLac(String cellLac) {
		this.cellLac = cellLac;
	}
	public String getDataState() {
		return dataState;
	}
	public void setDataState(String dataState) {
		this.dataState = dataState;
	}
	public String getDataActivity() {
		return dataActivity;
	}
	public void setDataActivity(String dataActivity) {
		this.dataActivity = dataActivity;
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {
			obj.putOpt("networkOperatorId", networkOperatorId);
			obj.putOpt("networkType", networkType);
			obj.putOpt("connectionType", connectionType);		
			obj.putOpt("wifiState", wifiState);
			obj.putOpt("cellType",cellType );
			obj.putOpt("cellId", cellId);
			obj.putOpt("cellLac", cellLac);
			obj.putOpt("basestationLat",basestationLat );
			obj.putOpt("basestationLong", basestationLong);
			obj.putOpt("networkid",networkid );
			obj.putOpt("systemid", systemid);
			obj.putOpt("dataState", dataState);
			obj.putOpt("dataActivity", dataActivity);
			obj.putOpt("signalStrength", signalStrength);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	public String getTitle() {
		
		return "Network";
	}
	
	public ArrayList<Row> getDisplayData(Context context){
		ArrayList<Row> data = new ArrayList<Row>();
		data.add(new Row("Type",getNetworkType()));
		data.add(new Row("CellID",getCellId()));
		data.add(new Row("CellLac",getCellLac()));
		data.add(new Row("Signal Strength", signalStrength + ""));
		return data;
	}
	
	public int getIcon() {

		return R.drawable.network;
	}

	
}
