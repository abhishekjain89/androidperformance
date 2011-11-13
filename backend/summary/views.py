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
    return render_to_response('test_request.html')

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

def measurement(request):

    response = {}


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
	for p in pings:
		d_srcip = p['scr_ip']
		d_dstip = p['dst_ip']
		d_time = p['time']
		measure = p['measure']
		d_average = measure['average']
		d_std = measure['stddev']
		d_minimum = measure['minimum']
		d_maximum = measure['maximum']
		


    except:
	return HttpResponse(error_message_helper.missing_attributes())    	

    try:
	details=Devices.objects.filter(deviceid=d_deviceid)
    except:	
	return HttpResponse(error_message_helper.retrieve_entry_fail())	        

    if len(details)<1:
	return HttpResponse(error_message_helper.device_not_found())

    try:
    	Device.objects.filter(deviceid=d_deviceid).update(last_update=datetime.now())
	if (d_measurement == 'rtt'):
    		details = MRtt(deviceid = d_deviceid, srcip=d_srcip,dstip=d_dstip, average=d_average, std=d_std, minimum=d_minimum, maximum=d_maximum, type=d_type, eventstamp= datetime.now())
		details.save()

	elif (d_measurement == 'throughput'):
		details = MThroughput(deviceid = d_deviceid, srcip=d_srcip,dstip=d_dstip, average=d_average, std=d_std, minimum=d_minimum, maximum=d_maximum, type=d_type, eventstamp= datetime.now())
		details.save()

    except:
	return HttpResponse(error_message_helper.insert_entry_fail())	        
        
    response['message'] = 'measurement inserted'
    response['status'] = 'OK'

    return HttpResponse(str(response))
