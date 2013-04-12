package com.num.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

import com.num.Values;
import com.num.models.Device;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Sim;

public class DeviceUtil {

	
	public Device getDeviceDetail(Context context,Measurement measurement) {
		Device dev = new Device();
		dev.setContext(context);
		Values session = (Values) context.getApplicationContext();
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
	
		// Device model
		String phoneModel = android.os.Build.MODEL;
		dev.setPhoneModel(phoneModel);

		// Android version
		String androidVersion = android.os.Build.VERSION.RELEASE;
		dev.setAndroidVersion(androidVersion);
		
		// Phone Brand Name
		String phoneBrand = android.os.Build.BRAND;
		dev.setPhoneBrand(phoneBrand);
		
		// Name of the industrial design
		String deviceDesign = android.os.Build.DEVICE;
		dev.setDeviceDesign(deviceDesign);
		
		// Manufacturere of the product
		String manufacturer = android.os.Build.MANUFACTURER;
		dev.setManufacturer(manufacturer);
		
		// Name of overall product
		String productName = android.os.Build.PRODUCT;
		dev.setProductName(productName);
		
		// Name of the radio firmware version
		String radioVersion = android.os.Build.RADIO;
		dev.setRadioVersion(radioVersion);
		
		// Name of underlying board
		String boardName = android.os.Build.BOARD;
		dev.setBoardName(boardName);
		
		// Get connected network country ISO code
		String networkCountry = telephonyManager.getNetworkCountryIso();
		dev.setNetworkCountry(networkCountry);

		// Get the connected network operator name // Carrier
		String networkName = telephonyManager.getNetworkOperatorName();
		dev.setNetworkName(networkName);
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
	
	public static String getNetworkInfo(Context context) {
		
		Values session = (Values) context.getApplicationContext();
		String srvnName = Context.TELEPHONY_SERVICE;
		String service = Context.CONNECTIVITY_SERVICE;

		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(srvnName);
		ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(service);
		NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo(); // Null if 3G/Wifi off
		boolean isWIFI = false;		
		String networkLevelType = "";
		
		if (activeNetwork != null) {
			int networkType2 = activeNetwork.getType();
			if(networkType2 == ConnectivityManager.TYPE_WIMAX) {
				networkLevelType = "4G";
			}
			else {
			// Get the type of network you are connected to
			int networkType = telephonyManager.getNetworkType();
			switch (networkType) {
			  case (TelephonyManager.NETWORK_TYPE_1xRTT)   :    
			  	  networkLevelType = "3G";
				  break;
			  case (TelephonyManager.NETWORK_TYPE_CDMA)    :   	  	  	  
			  	  networkLevelType = "2G/3G";
				  break;
			  case (TelephonyManager.NETWORK_TYPE_EDGE)    : 
			  	  networkLevelType = "2G";
				  break;
			  case (TelephonyManager.NETWORK_TYPE_EVDO_0)  : 
			  	  networkLevelType = "3G";
				  break;
			  case (TelephonyManager.NETWORK_TYPE_EVDO_A)  : 
		  	  	  networkLevelType = "3G";
		  	  	  break;
			  case (TelephonyManager.NETWORK_TYPE_EVDO_B)   :    
			  	  networkLevelType = "3G";
				  break;
			  case (TelephonyManager.NETWORK_TYPE_IDEN)   :    
			  	  networkLevelType = "PDT"; 
				  break;
			  case (TelephonyManager.NETWORK_TYPE_GPRS)    : 
			  	  networkLevelType = "2G";
				  break;
			  case (TelephonyManager.NETWORK_TYPE_HSDPA)   : 
		  	  	  networkLevelType = "3G";
				  break;
			  case (TelephonyManager.NETWORK_TYPE_HSPA)    : 
			  	  networkLevelType = "3G";
				  break;
			  case (TelephonyManager.NETWORK_TYPE_HSUPA)   : 
	  	  	  	  networkLevelType = "3G";
	  	  	  	  break;
			  case (TelephonyManager.NETWORK_TYPE_UMTS)    : 
			  	  networkLevelType = "3G";
				  break;
			  case (TelephonyManager.NETWORK_TYPE_UNKNOWN) : 
			  	  networkLevelType = "2G";	
				  break;
			  case (13) : 
			  	  networkLevelType = "4G";
				  break;
			  default: 
				  break;
			}
			}
			try {
				int connectionType = activeNetwork.getType();
				switch (connectionType) {
					case (ConnectivityManager.TYPE_MOBILE) : 
						isWIFI = false;
						break;
				  	case (ConnectivityManager.TYPE_WIFI) : 
						networkLevelType = "Wifi";
				  		isWIFI = true;
				  		break;
				  	default: 
				  		break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			networkLevelType = "No Network";
		}
		return networkLevelType;
	}
	
	public Network getNetworkDetail(Context context) {
		Network dev = new Network();
		Values session = (Values) context.getApplicationContext();
		String srvnName = Context.TELEPHONY_SERVICE;
		String service = Context.CONNECTIVITY_SERVICE;


		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(srvnName);
		ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(service);
		NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo(); // Null if 3G/Wifi off
		boolean isWIFI = false;		
		
		// Get the connected network operator ID (MCC + MNC)
		String networkOperatorId = telephonyManager.getNetworkOperator();
		dev.setNetworkOperatorId(networkOperatorId);
		
		if (activeNetwork != null) {
			int networkType2 = activeNetwork.getType();
			String networkLevelType = "";
			if(networkType2 == ConnectivityManager.TYPE_WIMAX) {
				dev.setNetworkType("WIMAX");
				networkLevelType = "4G";
			}
			else {
			// Get the type of network you are connected to
			int networkType = telephonyManager.getNetworkType();
			switch (networkType) {
			  case (TelephonyManager.NETWORK_TYPE_1xRTT)   :    
				  dev.setNetworkType("1xRTT");	  	  	  
			  	  networkLevelType = "3G";
				  break;
			  case (TelephonyManager.NETWORK_TYPE_CDMA)    :   
				  dev.setNetworkType("CDMA");	  	  	  
			  	  networkLevelType = "2G/3G";
				  break;
			  case (TelephonyManager.NETWORK_TYPE_EDGE)    : 
				  dev.setNetworkType("EDGE");
			  	  networkLevelType = "2G";
				  break;
			  case (TelephonyManager.NETWORK_TYPE_EVDO_0)  : 
				  dev.setNetworkType("EVDO_0");
			  	  networkLevelType = "3G";
				  break;
			  case (TelephonyManager.NETWORK_TYPE_EVDO_A)  : 
				  dev.setNetworkType("EVDO_A");
		  	  	  networkLevelType = "3G";
		  	  	  break;
			  case (TelephonyManager.NETWORK_TYPE_EVDO_B)   :    
				  dev.setNetworkType("EVDO_B");		  	  
			  	  networkLevelType = "3G";
				  break;
			  case (TelephonyManager.NETWORK_TYPE_IDEN)   :    
				  dev.setNetworkType("IDEN");		  	  
			  	  networkLevelType = "PDT"; 
				  break;
			  case (TelephonyManager.NETWORK_TYPE_GPRS)    : 
				  dev.setNetworkType("GPRS");
			  	  networkLevelType = "2G";
				  break;
			  case (TelephonyManager.NETWORK_TYPE_HSDPA)   : 
				  dev.setNetworkType("HSDPA");
		  	  	  networkLevelType = "3G";
				  break;
			  case (TelephonyManager.NETWORK_TYPE_HSPA)    : 
				  dev.setNetworkType("HSPA");
			  	  networkLevelType = "3G";
				  break;
			  case (TelephonyManager.NETWORK_TYPE_HSUPA)   : 
				  dev.setNetworkType("HSUPA");
	  	  	  	  networkLevelType = "3G";
	  	  	  	  break;
			  case (TelephonyManager.NETWORK_TYPE_UMTS)    : 
				  dev.setNetworkType("UMTS");		  	  
			  	  networkLevelType = "3G";
				  break;
			  case (TelephonyManager.NETWORK_TYPE_UNKNOWN) : 
				  dev.setNetworkType("UNKNOWN");
			  	  networkLevelType = "2G";	
				  break;
			  case (13) : 
				  dev.setNetworkType("LTE");
			  	  networkLevelType = "4G";
				  break;
			  default: 
				  break;
			}
			}
			try {
				int connectionType = activeNetwork.getType();
				switch (connectionType) {
					case (ConnectivityManager.TYPE_MOBILE) : 
						dev.setConnectionType("Mobile: " + networkLevelType);
						isWIFI = false;
						break;
				  	case (ConnectivityManager.TYPE_WIFI) : 
				  		dev.setConnectionType("Wifi");
				  		isWIFI = true;
				  		break;
				  	default: 
				  		break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!isWIFI) {		
				// Get the mobile network information.
				int network = ConnectivityManager.TYPE_MOBILE;
				NetworkInfo mobileNetwork = connectivity.getNetworkInfo(network);
				String networkInfo = mobileNetwork.toString();
				dev.setMobileNetworkInfo(networkInfo);
			}

		}
		else {
			dev.setMobileNetworkInfo("No Network");
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
			List<NeighboringCellInfo> nci = tm.getNeighboringCellInfo();
			
	    	CellLocation cellLocation = tm.getCellLocation();
	    	//GsmCellLocation loc = (GsmCellLocation) tm.getCellLocation();
	    	
	    	List<NeighboringCellInfo> neighCell = tm.getNeighboringCellInfo();  
	    	 for (int i = 0; i < neighCell.size(); i++) {  
	    	 try {  
	    	   NeighboringCellInfo thisCell = neighCell.get(i);  
	    	   int thisNeighCID = thisCell.getCid();  
	    	   int thisNeighRSSI = thisCell.getRssi();  
	    	     
	    	 } catch (NumberFormatException e) {  
	    	   e.printStackTrace();
	    	   NeighboringCellInfo thisCell = neighCell.get(i);  
	    	     
	    	 }  
	    	}  
			if(cellLocation instanceof CdmaCellLocation){
				CdmaCellLocation cdmaCellLocation = (CdmaCellLocation)cellLocation;
				int basestationId = cdmaCellLocation.getBaseStationId();
				int basestationgLat = cdmaCellLocation.getBaseStationLatitude();
				int basestationLong = cdmaCellLocation.getBaseStationLongitude();
				int networkid = cdmaCellLocation.getNetworkId();
				int systemid = cdmaCellLocation.getSystemId();
				String cellType = "CDMA";
				dev.setBasestationLat("" + basestationgLat);
				dev.setBasestationLong("" + basestationLong);
				dev.setCellId("" + basestationId);
				dev.setCellType("CDMA");
				dev.setNetworkid("" + networkid);
				dev.setSystemid("" + systemid);
				
			}
			else if(cellLocation instanceof GsmCellLocation){
				GsmCellLocation gsmCellLocation = (GsmCellLocation)cellLocation;
				int cid = gsmCellLocation.getCid();
				int lac = gsmCellLocation.getLac();
				
				cid = cid & 0xffff;
				lac = lac & 0xffff;
				dev.setCellId("" + cid);
				dev.setCellLac("" + lac);
				dev.setCellType("GSM");
				
			}
			else {
				dev.setCellId(session.UNAVAILABLE_CELLID);
				dev.setCellLac(session.UNAVAILABLE_CELLLAC);
			}
			
	    } catch (Exception e) {
	    	System.out.print(e.getMessage());
	    	System.out.print(e.getCause());
	    }
	    
	    try {
		    List<NeighboringCellInfo> n = telephonyManager.getNeighboringCellInfo();

	        //Construct the string
	        String s = "";
	        int rss = 0;
	        int cid = 0;
	        for (NeighboringCellInfo nci : n)
	        {
	                cid = nci.getCid();
	            rss = -113 + 2*nci.getRssi();
	            s += "Cell ID: " + Integer.toString(cid) + "     Signal Power (dBm): " + Integer.toString(rss) + "\n";
	        }
	    } catch (Exception e) {
	    	System.out.print(e.getMessage());
	    }
	    
		return dev;
	}
	
	public String getDeviceId(Context context) {
		String srvnName = Context.TELEPHONY_SERVICE;
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(srvnName);
		
		String deviceId = null;
		if (telephonyManager.getDeviceId() != null) {
			deviceId = telephonyManager.getDeviceId(); //*** use for mobiles
		} else {
			deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID); //** use for tablet
		}
		
		return deviceId;
	}
	
	public String getUTCTime() {
		// Getting current time
	    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	    String utcTime = sdf.format(new Date());
	    
	    return utcTime;
	}
	
	public String getLocalTime() {
		// Getting current time
	    final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String localTime = df.format(new Date());
	    
	    return localTime;
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
	
	public String getNetworkCountry(Context context)
	{
		String srvnName = Context.TELEPHONY_SERVICE;
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(srvnName);
		String networkCountry = telephonyManager.getNetworkCountryIso();
		//String networkcountry = null;
		return networkCountry;
	}
     
}
