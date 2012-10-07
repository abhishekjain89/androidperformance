package com.num.models;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.num.helpers.UserDataHelper;
import com.num.utils.SHA1Util;
import com.num.R;


public class Device implements MainModel {
	String phoneType = "";
	String phoneNumber = "";
	String softwareVersion = "";

	String androidVersion = "";
	String phoneBrand = ""; 
	String manufacturer = ""; 
	String productName = ""; 
	String radioVersion = "";
	String boardName = "";//
	String deviceDesign = "";// 
	String phoneModel = "";//
	
	Context context;

	String networkCountry = "";
	String networkName ="";

	private static String DESCRIPTION = "Device specifications";

	public String getDescription() {
		return DESCRIPTION;
	}
	public String getNetworkCountry() {
		return networkCountry;
	}
	public String getNetworkName() {
		return networkName;
	}
	public void setNetworkCountry(String networkCountry) {
		this.networkCountry = networkCountry;
	}
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getPhoneBrand() {
		return phoneBrand;
	}

	public void setPhoneBrand(String phoneBrand) {
		this.phoneBrand = phoneBrand;
	}

	public String getDeviceDesign() {
		return deviceDesign;
	}

	public void setDeviceDesign(String deviceDesign) {
		this.deviceDesign = deviceDesign;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getRadioVersion() {
		return radioVersion;
	}

	public void setRadioVersion(String radioVersion) {
		this.radioVersion = radioVersion;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public String getAndroidVersion() {
		return androidVersion;
	}

	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public JSONObject toJSON(){

		JSONObject obj = new JSONObject();
		try {
			obj.putOpt("phoneType", phoneType);
			obj.putOpt("phoneNumber", SHA1Util.SHA1(phoneNumber));
			obj.putOpt("softwareVersion", softwareVersion);
			obj.putOpt("phoneModel", phoneModel);
			obj.putOpt("androidVersion",androidVersion);
			obj.putOpt("phoneBrand",phoneBrand);
			obj.putOpt("deviceDesign",deviceDesign);
			obj.putOpt("manufacturer",manufacturer);
			obj.putOpt("productName",productName);
			obj.putOpt("radioVersion",radioVersion);
			obj.putOpt("boardName",boardName);
			obj.putOpt("networkCountry", networkCountry);
			obj.putOpt("networkName", networkName);
			try{
			if(getContext()!=null){
				
				UserDataHelper userhelp = new UserDataHelper(getContext());

				obj.putOpt("datacap",userhelp.getDataCap());
				obj.putOpt("billingcycle",userhelp.getBillingCycle());
			}
			}
			catch (Exception e){
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}		
		return obj;
	}

	public String getTitle() {

		return "Device";
	}

	public ArrayList<Row> getDisplayData(Context context){
		ArrayList<Row> data = new ArrayList<Row>();
		data.add(new Row("Brand",getPhoneBrand()));
		data.add(new Row("Model",getPhoneModel()));
		data.add(new Row("Manufacturer",getManufacturer()));
		data.add(new Row("Phone Number",getPhoneNumber()));
		data.add(new Row("Phone Type",getPhoneType()));
		return data;
	}

	public int getIcon() {

		return R.drawable.device;
	}




}
