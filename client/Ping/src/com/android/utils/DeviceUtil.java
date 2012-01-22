package com.android.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

import com.android.models.Measurement;

public class DeviceUtil {
	
	public Measurement getFullDetail(Context context)
	{
		Measurement dev = new Measurement();
		String srvnName = Context.TELEPHONY_SERVICE;
		String service = Context.CONNECTIVITY_SERVICE;
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(srvnName);
		ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(service);
		NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
		boolean isWIFI = false;		

		int phoneType = telephonyManager.getPhoneType();
		switch (phoneType) {
		case (TelephonyManager.PHONE_TYPE_CDMA) : 
			dev.setPhoneType("CDMA");
			break;
		case (TelephonyManager.PHONE_TYPE_GSM) :
			dev.setPhoneType("GSM");
			break;
		case (TelephonyManager.PHONE_TYPE_NONE) :
			dev.setPhoneType("NONE");
			break;
		default:
			break;
		}

		// Read the IMEI for GSM or MEID for CDMA
		String deviceId = telephonyManager.getDeviceId();
		dev.setDeviceId(deviceId);
		
		// Read the software version on the phone
		String softwareVersion = telephonyManager.getDeviceSoftwareVersion();	
		dev.setSoftwareVersion(softwareVersion);
		
		// Get the phone's number
		String phoneNumber = telephonyManager.getLine1Number();
		dev.setPhoneNumber(phoneNumber);
		
		// Get connected network country ISO code
		String networkCountry = telephonyManager.getNetworkCountryIso();
		dev.setNetworkCountryISO(networkCountry);
		
		// Get the connected network operator ID (MCC + MNC)
		String networkOperatorId = telephonyManager.getNetworkOperator();
		dev.setNetworkOperatorId(networkOperatorId);
		
		// Get the connected network operator name // Carrier
		String networkName = telephonyManager.getNetworkOperatorName();
		dev.setNetworkName(networkName);
		
		// Get the type of network you are connected to
		int networkType = telephonyManager.getNetworkType();
		switch (networkType) {
		  case (TelephonyManager.NETWORK_TYPE_1xRTT)   :    
			  dev.setNetworkType("1xRTT");
			  break;
		  case (TelephonyManager.NETWORK_TYPE_CDMA)    :   
			  dev.setNetworkType("CDMA");
			  break;
		  case (TelephonyManager.NETWORK_TYPE_EDGE)    : 
			  dev.setNetworkType("EDGE");
			  break;
		  case (TelephonyManager.NETWORK_TYPE_EVDO_0)  : 
			  dev.setNetworkType("EVDO_0");
			  break;
		  case (TelephonyManager.NETWORK_TYPE_EVDO_A)  : 
			  dev.setNetworkType("EVDO_A");
			  break;
		  case (TelephonyManager.NETWORK_TYPE_GPRS)    : 
			  dev.setNetworkType("GPRS");
			  break;
		  case (TelephonyManager.NETWORK_TYPE_HSDPA)   : 
			  dev.setNetworkType("HSDPA");
			  break;
		  case (TelephonyManager.NETWORK_TYPE_HSPA)    : 
			  dev.setNetworkType("HSPA");
			  break;
		  case (TelephonyManager.NETWORK_TYPE_HSUPA)   : 
			  dev.setNetworkType("HSUPA");
			  break;
		  case (TelephonyManager.NETWORK_TYPE_UMTS)    : 
			  dev.setNetworkType("UMTS");
			  break;
		  case (TelephonyManager.NETWORK_TYPE_UNKNOWN) : 
			  dev.setNetworkType("UNKNOWN");
			  break;
		  default: 
			  break;
		}
		

		String simDetail = "";
		simDetail += "SIM State: ";
		
		int simState = telephonyManager.getSimState();
		switch (simState) {
		  case (TelephonyManager.SIM_STATE_ABSENT):
			  dev.setSimState("ABSENT");
			  break;
		  case (TelephonyManager.SIM_STATE_NETWORK_LOCKED):
			  dev.setSimState("NETWORK_LOCKED");
			  break;
		  case (TelephonyManager.SIM_STATE_PIN_REQUIRED): 
			  dev.setSimState("PIN_REQUIRED");
			  break;
		  case (TelephonyManager.SIM_STATE_PUK_REQUIRED): 
			  dev.setSimState("PUK_REQUIRED");
			  break;
		  case (TelephonyManager.SIM_STATE_UNKNOWN):
			  dev.setSimState("UNKNOWN");
			  break;
		  case (TelephonyManager.SIM_STATE_READY): {
			  dev.setSimState("READT");
			  
			  // Get the SIM country ISO code
			  String simCountry = telephonyManager.getSimCountryIso();
			  dev.setSimNetworkCountry(simCountry);
		    
			  // Get the operator code of the active SIM (MCC + MNC)
			  String simOperatorCode = telephonyManager.getSimOperator(); 
			  dev.setSimOperatorCode(simOperatorCode);
		    
			  // Get ther name of the SIM operator
			  String simOperatorName = telephonyManager.getSimOperatorName();
			  dev.setSimOperatorName(simOperatorName);
		    
			  // Get the SIM's serial number
			  String simSerial = telephonyManager.getSimSerialNumber();
			  dev.setSimSerialNumber(simSerial);

			  break;
		  }
		}

		
		int connectionType = activeNetwork.getType();
		switch (connectionType) {
			case (ConnectivityManager.TYPE_MOBILE) : 
				dev.setConnectionType("Mobile");
				isWIFI = false;
				break;
		  	case (ConnectivityManager.TYPE_WIFI) : 
		  		dev.setConnectionType("Wifi");
		  		isWIFI = true;
		  		break;
		  	default: 
		  		break;
		}

		if (!isWIFI) {		
			// Get the mobile network information.
			int network = ConnectivityManager.TYPE_MOBILE;
			NetworkInfo mobileNetwork = connectivity.getNetworkInfo(network);
			String networkInfo = mobileNetwork.toString();
			dev.setMobileNetworkInfo(networkInfo);
			
			//NetworkInfo.State state = mobileNetwork.getState();
			//dev.setMobileNetworkState(state.toString());
			//NetworkInfo.DetailedState detailedState = mobileNetwork.getDetailedState();
			//dev.setMobileNetworkDetailedState(detailedState.toString());
			
		}
		else {
			String srvc = Context.WIFI_SERVICE;
			WifiManager wifi = (WifiManager)context.getSystemService(srvc);
			WifiInfo info = wifi.getConnectionInfo();
			if (info.getBSSID() != null) {
				int strength = WifiManager.calculateSignalLevel(info.getRssi(), 11);
				int speed = info.getLinkSpeed();
				String units = WifiInfo.LINK_SPEED_UNITS;
				String ssid = info.getSSID();
				String cSummary = String.format("Connected to %s at %s%s. Strength %s",
			                                   ssid, speed, units, strength);
				dev.setWifiState(cSummary);
			} 
		}

		// Getting current time
	    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	    String utcTime = sdf.format(new Date());
	    dev.setTime(utcTime);
		
	    // Cell Id and Cell lac
	    try {
	    	TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			GsmCellLocation loc = (GsmCellLocation) tm.getCellLocation();
			int cellId = loc.getCid();
			int lac = loc.getLac();
			
			dev.setCellId("" + cellId);
			dev.setCellLac("" + lac);
	    } catch (Exception e) {
	    	System.out.print(e.getMessage());
	    	System.out.print(e.getCause());
	    }
	    
		/*
		List<NeighboringCellInfo> cellinfo = telephonyManager.getNeighboringCellInfo();
        String deviceinfo = "";
        if(null != cellinfo){
                for(NeighboringCellInfo info: cellinfo){
                        deviceinfo += ("\tCellID: " + info.getCid() + ", RSSI: " + info.getRssi() + "\n");
                }
        }
        */
		 
		
		
		return dev;
	}
	
	/**
	 * Find phone type, unique ID (IMEI or MEID) and software version
	 * @param telephonyManager
	 * @return
	 */
	public String getPhoneDetail(TelephonyManager telephonyManager) {
		
		String phoneDetail = "";
		
		phoneDetail += "Phone type: ";
		int phoneType = telephonyManager.getPhoneType();
		switch (phoneType) {
		case (TelephonyManager.PHONE_TYPE_CDMA) : 
			phoneDetail += "CDMA";
			break;
		case (TelephonyManager.PHONE_TYPE_GSM) :
			phoneDetail += "GSM";
			break;
		case (TelephonyManager.PHONE_TYPE_NONE) :
			phoneDetail += "NONE";
			break;
		default:
			break;
		}

		// Read the IMEI for GSM or MEID for CDMA
		String deviceId = telephonyManager.getDeviceId();
		
		// Read the software version on the phone
		String softwareVersion = telephonyManager.getDeviceSoftwareVersion();	
		
		// Get the phone's number
		String phoneNumber = telephonyManager.getLine1Number();
		
		phoneDetail += "\nDevice ID: " + deviceId;
		phoneDetail += "\nSoftware Version: " + softwareVersion;
		phoneDetail += "\nPhone Number: " + phoneNumber;
		
		return phoneDetail;
	}

	/**
	 * Get network information such as mobile country and type of network
	 * @param telephonyManager
	 * @return
	 */
	public String getNetworkDetail(TelephonyManager telephonyManager) {

		String networkDetail = ""; 
		
		// Get connected network country ISO code
		String networkCountry = telephonyManager.getNetworkCountryIso();
		
		// Get the connected network operator ID (MCC + MNC)
		String networkOperatorId = telephonyManager.getNetworkOperator();
		
		// Get the connected network operator name
		String networkName = telephonyManager.getNetworkOperatorName();
		
		networkDetail += "Network Country ISO code: " + networkCountry;
		networkDetail += "\nNetwork Operator: " + networkOperatorId;
		networkDetail += "\nNetwork Name: " + networkName;
		networkDetail += "\nNetwork Type: ";
		
		// Get the type of network you are connected to
		int networkType = telephonyManager.getNetworkType();
		switch (networkType) {
		  case (TelephonyManager.NETWORK_TYPE_1xRTT)   :    
			  networkDetail += "NETWORK_TYPE_1xRTT";
			  break;
		  case (TelephonyManager.NETWORK_TYPE_CDMA)    :   
			  networkDetail += "NETWORK_TYPE_CDMA";  
			  break;
		  case (TelephonyManager.NETWORK_TYPE_EDGE)    : 
			  networkDetail += "NETWORK_TYPE_EDGE";  
			  break;
		  case (TelephonyManager.NETWORK_TYPE_EVDO_0)  : 
			  networkDetail += "NETWORK_TYPE_EVDO_0";  
			  break;
		  case (TelephonyManager.NETWORK_TYPE_EVDO_A)  : 
			  networkDetail += "NETWORK_TYPE_EVDO_A";  
			  break;
		  case (TelephonyManager.NETWORK_TYPE_GPRS)    : 
			  networkDetail += "NETWORK_TYPE_GPRS";  
			  break;
		  case (TelephonyManager.NETWORK_TYPE_HSDPA)   : 
			  networkDetail += "NETWORK_TYPE_HSDPA";  
			  break;
		  case (TelephonyManager.NETWORK_TYPE_HSPA)    : 
			  networkDetail += "NETWORK_TYPE_HSPA";  
			  break;
		  case (TelephonyManager.NETWORK_TYPE_HSUPA)   : 
			  networkDetail += "NETWORK_TYPE_HSUPA";  
			  break;
		  case (TelephonyManager.NETWORK_TYPE_UMTS)    : 
			  networkDetail += "NETWORK_TYPE_UMTS";  
			  break;
		  case (TelephonyManager.NETWORK_TYPE_UNKNOWN) : 
			  networkDetail += "UNKNOWN";  
			  break;
		  default: 
			  break;
		}
		return networkDetail;
	}

	/**
	 * Get SIM detail
	 * @param telephonyManager
	 * @return
	 */
	public String getSimDetail(TelephonyManager telephonyManager) {
		
		String simDetail = "";
		simDetail += "SIM State: ";
		
		int simState = telephonyManager.getSimState();
		switch (simState) {
		  case (TelephonyManager.SIM_STATE_ABSENT):
			  simDetail += "SIM_STATE_ABSENT";
			  break;
		  case (TelephonyManager.SIM_STATE_NETWORK_LOCKED):
			  simDetail += "SIM_STATE_NETWORK_LOCKED"; 
			  break;
		  case (TelephonyManager.SIM_STATE_PIN_REQUIRED): 
			  simDetail += "SIM_STATE_PIN_REQUIRED";
			  break;
		  case (TelephonyManager.SIM_STATE_PUK_REQUIRED): 
			  simDetail += "SIM_STATE_PUK_REQUIRED";
			  break;
		  case (TelephonyManager.SIM_STATE_UNKNOWN):
			  simDetail += "SIM_STATE_UNKNOWN";
			  break;
		  case (TelephonyManager.SIM_STATE_READY): {
			  simDetail += "SIM_STATE_READY";
			  
			  // Get the SIM country ISO code
			  String simCountry = telephonyManager.getSimCountryIso();
		    
			  // Get the operator code of the active SIM (MCC + MNC)
			  String simOperatorCode = telephonyManager.getSimOperator(); 
		    
			  // Get ther name of the SIM operator
			  String simOperatorName = telephonyManager.getSimOperatorName();
		    
			  // Get the SIM's serial number
			  String simSerial = telephonyManager.getSimSerialNumber();

			  simDetail += "\nSIM Country Code: " + simCountry;;
			  simDetail += "\nSIM Operator Code: " + simOperatorCode;;
			  simDetail += "\nSIM Operator Name: " + simOperatorName;;
			  simDetail += "\nSIM Serial number: " + simSerial;;
			  
			  break;
		  }
		  default: 
			  break;
		}
		return simDetail;
	}
	
	/**
	 * Finds the current data connection state and transfer activity
	 * @param telephonyManager
	 * @return
	 */
	public String getTransferState(TelephonyManager telephonyManager) {
		
		String stateDetail = "";
		
		int dataActivity = telephonyManager.getDataActivity();
		int dataState = telephonyManager.getDataState();
		
		stateDetail += "Data Activity: ";
		switch (dataActivity) {
			case TelephonyManager.DATA_ACTIVITY_IN :
				stateDetail += "DATA_ACTIVITY_IN";
				break;
			case TelephonyManager.DATA_ACTIVITY_OUT :
				stateDetail += "DATA_ACTIVITY_OUT";
				break;
			case TelephonyManager.DATA_ACTIVITY_INOUT :
				stateDetail += "DATA_ACTIVITY_INOUT";
				break;
			case TelephonyManager.DATA_ACTIVITY_NONE :
				stateDetail += "DATA_ACTIVITY_NONE";
				break;
			case TelephonyManager.DATA_ACTIVITY_DORMANT :
				stateDetail += "DATA_ACTIVITY_DORMANT";
				break;
		}

		stateDetail += "\nData State: ";
		switch (dataState) {
		case TelephonyManager.DATA_CONNECTED :
			stateDetail += "DATA_CONNECTED";
			break;
		case TelephonyManager.DATA_CONNECTING :
			stateDetail += "DATA_CONNECTING";
			break;
		case TelephonyManager.DATA_DISCONNECTED :
			stateDetail += "DATA_DISCONNECTED";
			break;
		case TelephonyManager.DATA_SUSPENDED :
			stateDetail += "DATA_SUSPENDED";
			break;
		}
		return stateDetail;
	}

	/**
	 * Get Network Connectivity Detail (WIFI or MOBILE)
	 * @param context
	 * @return
	 */
	public String getConnectivityDetail(Context context) {
		
		String connectivityDetail = "";
		boolean isWIFI = false;
		
		String service = context.CONNECTIVITY_SERVICE;
		ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(service);
		NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
		
		connectivityDetail += "Network Type: ";
		int networkType = activeNetwork.getType();
		switch (networkType) {
			case (ConnectivityManager.TYPE_MOBILE) : 
				connectivityDetail += "TYPE_MOBILE";
				isWIFI = false;
				break;
		  	case (ConnectivityManager.TYPE_WIFI) : 
				connectivityDetail += "TYPE_WIFI";
		  		isWIFI = true;
		  		break;
		  	default: 
		  		break;
		}

		if (!isWIFI) {		
			// Get the mobile network information.
			int network = ConnectivityManager.TYPE_MOBILE;
			NetworkInfo mobileNetwork = connectivity.getNetworkInfo(network);
			NetworkInfo.State state = mobileNetwork.getState();
			NetworkInfo.DetailedState detailedState = mobileNetwork.getDetailedState();

			connectivityDetail += "\nMobile Network State" + state;
			connectivityDetail += "\nMobile Network Detailed State" + detailedState;
		}
		else {
			String srvc = context.WIFI_SERVICE;
			WifiManager wifi = (WifiManager)context.getSystemService(srvc);
			WifiInfo info = wifi.getConnectionInfo();
			if (info.getBSSID() != null) {
				int strength = WifiManager.calculateSignalLevel(info.getRssi(), 11);
				int speed = info.getLinkSpeed();
				String units = WifiInfo.LINK_SPEED_UNITS;
				String ssid = info.getSSID();
				String cSummary = String.format("Connected to %s at %s%s. Strength %s",
			                                   ssid, speed, units, strength);
				
				connectivityDetail += "\nWIFI Strength:" + strength;
				connectivityDetail += "\nWIFI Speed:" + speed;
				connectivityDetail += "\nWIFI Units:" + units;
				connectivityDetail += "\nWIFI SSID:" + ssid;
				connectivityDetail += "\nWIFI Summary:" + cSummary;
			} 
		}
		
		return connectivityDetail;
	}

}
