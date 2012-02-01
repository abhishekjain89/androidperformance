package com.android.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

import com.android.models.Device;
import com.android.models.Measurement;
import com.android.models.Network;
import com.android.models.Sim;

public class DeviceUtil {
	
	public Device getDeviceDetail(Context context) {
		Device dev = new Device();
		String srvnName = Context.TELEPHONY_SERVICE;
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(srvnName);
	
		// Get the phone's type
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
		
		// Read the software version on the phone
		String softwareVersion = telephonyManager.getDeviceSoftwareVersion();	
		dev.setSoftwareVersion(softwareVersion);
		
		// Get the phone's number
		String phoneNumber = telephonyManager.getLine1Number();
		dev.setPhoneNumber(phoneNumber);
		dev.setBattery(getBattery()+"");
		
		return dev;
	}
	
	public Sim getSimDetail(Context context) {
		Sim dev = new Sim();
		String srvnName = Context.TELEPHONY_SERVICE;
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(srvnName);
		
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
		return dev;
	}
	
	public Network getNetworkDetail(Context context) {
		Network dev = new Network();
		String srvnName = Context.TELEPHONY_SERVICE;
		String service = Context.CONNECTIVITY_SERVICE;
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(srvnName);
		ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(service);
		NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
		boolean isWIFI = false;		

		// Get connected network country ISO code
		String networkCountry = telephonyManager.getNetworkCountryIso();
		dev.setNetworkCountry(networkCountry);
		
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
		

		// Finds the current data connection state and transfer activity
		int dataActivity = telephonyManager.getDataActivity();
		int dataState = telephonyManager.getDataState();
		
		switch (dataActivity) {
			case TelephonyManager.DATA_ACTIVITY_IN :
				dev.setDataActivity("DATA_ACTIVITY_IN");
				break;
			case TelephonyManager.DATA_ACTIVITY_OUT :
				dev.setDataActivity("DATA_ACTIVITY_OUT");
				break;
			case TelephonyManager.DATA_ACTIVITY_INOUT :
				dev.setDataActivity("DATA_ACTIVITY_INOUT");
				break;
			case TelephonyManager.DATA_ACTIVITY_NONE :
				dev.setDataActivity("DATA_ACTIVITY_NONE");
				break;
			case TelephonyManager.DATA_ACTIVITY_DORMANT :
				dev.setDataActivity("DATA_ACTIVITY_DORMANT");
				break;
		}

		switch (dataState) {
			case TelephonyManager.DATA_CONNECTED :
				dev.setDataState("DATA_CONNECTED");
				break;
			case TelephonyManager.DATA_CONNECTING :
				dev.setDataState("DATA_CONNECTING");
				break;
			case TelephonyManager.DATA_DISCONNECTED :
				dev.setDataState("DATA_DISCONNECTED");
				break;
			case TelephonyManager.DATA_SUSPENDED :
				dev.setDataState("DATA_SUSPENDED");
				break;
		}
	    
	    // Cell Id and Cell lac
	    try {
	    	TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			CellLocation cellLocation = tm.getCellLocation();
	    	//GsmCellLocation loc = (GsmCellLocation) tm.getCellLocation();
			
			if(cellLocation instanceof GsmCellLocation){
				GsmCellLocation gsmCellLocation = (GsmCellLocation)cellLocation;
				int cid = gsmCellLocation.getCid();
				int lac = gsmCellLocation.getLac();
				cid = cid & 0xffff;
				lac = lac & 0xffff;
				dev.setCellId("" + cid);
				dev.setCellLac("" + lac);
			}
			else {
				dev.setCellId("65535");
				dev.setCellLac("65535");
			}
			
	    } catch (Exception e) {
	    	System.out.print(e.getMessage());
	    	System.out.print(e.getCause());
	    }
		
		return dev;
	}
	
	public String getDeviceId(Context context) {
		String srvnName = Context.TELEPHONY_SERVICE;
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(srvnName);
		
		// Read the IMEI for GSM or MEID for CDMA
		String deviceId = telephonyManager.getDeviceId();
		
		return deviceId;
	}
	
	public String getTime() {
		// Getting current time
	    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	    String utcTime = sdf.format(new Date());
	    
	    return utcTime;
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
	
	static public Long getBattery() {

        File f = null;      
        
        // htc desire hd / desire z / inspire?
        if (Build.MODEL.toLowerCase().contains("desire hd") ||
                Build.MODEL.toLowerCase().contains("desire z") ||
                Build.MODEL.toLowerCase().contains("inspire")) {

            f = new File("/sys/class/power_supply/battery/batt_current");
            if (f.exists()) {
                return FileReaderUtil.getValue_OneLine(f, false);
            }
        }

        // nexus one cyangoenmod
        f = new File("/sys/devices/platform/ds2784-battery/getcurrent");
        if (f.exists()) {
            return FileReaderUtil.getValue_OneLine(f, true);
        }

        // sony ericsson xperia x1
        f = new File("/sys/devices/platform/i2c-adapter/i2c-0/0-0036/power_supply/ds2746-battery/current_now");
        if (f.exists()) {
            return FileReaderUtil.getValue_OneLine(f, false);
        }

        // xdandroid
        /*if (Build.MODEL.equalsIgnoreCase("MSM")) {*/
            f = new File("/sys/devices/platform/i2c-adapter/i2c-0/0-0036/power_supply/battery/current_now");
            if (f.exists()) {
                return FileReaderUtil.getValue_OneLine(f, false);
            }
        /*}*/

        // droid eris
        f = new File("/sys/class/power_supply/battery/smem_text");      
        if (f.exists()) {
            Long value = FileReaderUtil.getValue_SMS();
            if (value != null)
                return value;
        }

        // htc sensation / evo 3d
        f = new File("/sys/class/power_supply/battery/batt_attr_text");
        if (f.exists())
        {
            Long value = FileReaderUtil.getValue_BattAtr(f,"");
            if (value != null)
                return value;
        }

        // some htc devices
        f = new File("/sys/class/power_supply/battery/batt_current");
        if (f.exists())
            return FileReaderUtil.getValue_OneLine(f, false);

        // nexus one
        f = new File("/sys/class/power_supply/battery/current_now");
        if (f.exists())
            return FileReaderUtil.getValue_OneLine(f, true);

        // samsung galaxy vibrant       
        f = new File("/sys/class/power_supply/battery/batt_chg_current");
        if (f.exists())
            return FileReaderUtil.getValue_OneLine(f, false);

        // sony ericsson x10
        f = new File("/sys/class/power_supply/battery/charger_current");
        if (f.exists())
            return FileReaderUtil.getValue_OneLine(f, false);

        // Nook Color
        f = new File("/sys/class/power_supply/max17042-0/current_now");
        if (f.exists())
            return FileReaderUtil.getValue_OneLine(f, false);

        return null;
    }

}
