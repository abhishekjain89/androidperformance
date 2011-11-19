from django.http import HttpResponse, HttpResponseRedirect
import urllib2, urllib, json
from django.shortcuts import render_to_response
from backend.summary.models import *

import random
from datetime import datetime, timedelta
from time import time,mktime,strftime
from mx.DateTime.ISO import ParseDateTimeUTC
import hashlib
import ast
import error_message_helper

def index(request):
    return render_to_response('index.html')

def register(request):
    
    response = {}
    try:
    	request_object = ast.literal_eval(request.read())
    except:
	return HttpResponse(error_message_helper.invalid_format())
    
    try:
	d_deviceid = request_object['deviceid']
	d_username = request_object['username']
	d_carrier = request_object['carrier']
	d_plantype = request_object['plantype']
    except:
	return HttpResponse(error_message_helper.missing_attributes())	

    try:

	details = Devices(deviceid = d_deviceid, username=d_username, carrier = d_carrier, plantype = d_plantype, register = datetime.now(), last_update = datetime.now())
        details.save()
    except:	
	return HttpResponse(error_message_helper.insert_entry_fail())	
        
    response['message'] = 'device registered'
    response['status'] = 'OK'
    return HttpResponse(str(response))

def check_register(request):

    response = {}
    
    try:
    	request_object = ast.literal_eval(request.read())
    except:
	return HttpResponse(error_message_helper.invalid_format())    

    try:
	d_deviceid = request_object['deviceid']
    except:
	return HttpResponse(error_message_helper.missing_attributes())		

    try:
	details=Devices.objects.filter(deviceid=d_deviceid)
    except:	
	return HttpResponse(error_message_helper.retrieve_entry_fail())	    

    if len(details)<1:
	return HttpResponse(error_message_helper.device_not_found())

    else:    
    	response['message'] = 'device found'
    	response['status'] = 'OK'

    return HttpResponse(str(response))

def devicesummary(request):
    device_id = str(request.POST.get("device"))
    try:
        device = Device.objects.filter(deviceid=device_id)
        if len(device)<1:
            return render_to_response('error.html', {'deviceid': device_id})
    except:
            return render_to_response('error.html', {'deviceid': device_id})

    try:
        measurements = Measurement.objects.filter(deviceid=device_id)
        if len(measurements)<1:
            return render_to_response('error.html', {'deviceid': device_id})
        else:
            return render_to_response('device.html', {'deviceid': device_id, 'measurements': measurements})
    except:
        measurements = Measurement.objects.filter(deviceid=device_id)
    

def measurement(request):

    response = {}

    print "start"
    try:
    	request_object = ast.literal_eval(request.raw_post_data)

	print request_object
	
    except:
	return HttpResponse(error_message_helper.invalid_format())

    try:

	m_deviceid = request_object['deviceid']
	m_simoperatorcode = request_object['simOperatorCode']
	m_networktype = request_object['networkType']
	m_simserialnumber = request_object['simSerialNumber']
	m_phonenumber = request_object['phoneNumber']
	m_altitude = request_object['altitude']
	m_networkcountry = request_object['networkCountry']
	m_connectiontype = request_object['connectionType']
	m_simnetworkcountry = request_object['simNetworkCountry']
	m_networkoperatorid = request_object['networkOperatorId']
	m_mobilenetworkdetailedstate = request_object['mobileNetworkDetailedState']
	m_simstate = request_object['simState']
	m_time = request_object['time']
	m_mobilenetworkstate = request_object['mobileNetworkState']
	m_longitude = request_object['longitude']
	m_latitude = request_object['latitude']
	m_simoperatorname = request_object['simOperatorName']
	m_networkname = request_object['networkName']

	pings = request_object['pings']	

    except:
	return HttpResponse(error_message_helper.missing_attributes())    	

    try:
	details=Device.objects.filter(deviceid=m_deviceid)[0]

	if len(details)<1:
		details = Device(deviceid = m_deviceid)
		details.save()
    except:	
	details = Device(deviceid = m_deviceid)
	details.save()

  
    try:
    
    	measurement = Measurement(deviceid = details,simoperatorcode = m_simoperatorcode,networktype = m_networktype,simserialnumber = m_simserialnumber,phonetype = m_phonenumber,altitude = m_altitude,networkcountry = m_networkcountry,connectiontype = m_connectiontype,simnetworkcountry = m_simnetworkcountry,networkoperatorid = m_networkoperatorid,mobilenetworkdetailedstate = m_mobilenetworkdetailedstate,simstate = m_simstate,time = m_time,mobilenetworkstate = m_mobilenetworkstate,longitude = m_longitude,latitude = m_latitude,simoperatorname = m_simoperatorname,networkname = m_networkname)
    	measurement.save()

	m_id = measurement.measurementid
	

    except:
	return HttpResponse(error_message_helper.insert_entry_fail("measurement"))	
    try:
	for p in pings:
		d_srcip = p['src_ip']
		d_dstip = p['dst_ip']
		d_time = p['time']
		measure = p['measure']
		d_average = measure['average']
		d_std = measure['stddev']
		d_min = measure['min']
		d_max = measure['max']
		
		ping = Ping(measurementid = measurement,scrip=d_srcip,dstip=d_dstip,time=d_time,avg=d_average,stdev=d_std,min=d_min,max=d_max)
		
		ping.save()

    except:
	return HttpResponse(error_message_helper.insert_entry_fail("ping"))			
        
    response['message'] = 'measurement inserted'
    response['status'] = 'OK'

    return HttpResponse(str(response))
