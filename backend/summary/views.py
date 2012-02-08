from django.http import HttpResponse, HttpResponseRedirect
import urllib2, urllib, json
from django.shortcuts import render_to_response
from backend.summary.models import *

import random
from datetime import datetime, timedelta
from time import time,mktime,strftime
import hashlib
import ast
import error_message_helper,insertJSON

def index(request):
    return render_to_response('index.html')

def test_request(request):
    return render_to_response('test_request.html')

def showdata(request,deviceid):
    did = deviceid
    print(did);
    try:
        all_pings = Ping.objects.filter(measurementid__deviceid = did)
        print len(all_pings)
        return render_to_response('showdata.html',{'pings':all_pings,'deviceid':did})
    except:
        return render_to_response('error.html',{'deviceid':did})

def measurementdetails(request,measurementid):
    
    mid = measurementid
    try:  
        measurements = Measurement.objects.filter(measurementid = mid)
        details = measurements[0]
        pings = Ping.objects.filter(measurementid__measurementid = mid)
        return render_to_response('measurement.html',{'pings':pings,'details':details})
    except:
        return render_to_response('error.html')

def devicesummary(request):
    phone_number = str(request.POST.get("code")) + str(request.POST.get("num"))
    try:
        device = Device.objects.filter(phonenumber=phone_number)
        device_id = device[0].deviceid
        if len(device)<1:
            return render_to_response('error.html', {'deviceid': phone_number})
    except:
            return render_to_response('error.html', {'deviceid': phone_number})

    try:
        measurements = Measurement.objects.filter(deviceid=device_id)
        if (len(measurements)<1):
            return render_to_response('error.html', {'deviceid': device_id})
        else:
            return render_to_response('device.html', {'deviceid': device_id, 'measurements': measurements})
            
    except:
            return render_to_response('error.html', {'deviceid': device_id})
        
    

def measurement(request):

    response = {}

    print "start"
    try:
        request_object = ast.literal_eval(request.read())
    except:
        return HttpResponse(error_message_helper.invalid_format())
    
    count = 0
    try:

        m_deviceid = request_object['deviceid']
        count+=1
        m_time = request_object['time']
        count+=1
        pings = request_object['pings']
        count+=1   
        m_device = request_object['device']
        count+=1
        m_network = request_object['network']
        count+=1
        m_sim = request_object['sim']
        count+=1
        m_throughput = request_object['throughput']
        count+=1
        m_gps = request_object['gps']
        count+=1
        m_usage = request_object['usage']
        count+=1
        m_battery = request_object['battery']
        count+=1

    except:
        return HttpResponse(error_message_helper.missing_attributes('measurement(' +count+ ')'))        
    print "measurement insertion started..."
    
    try:
        details=Device.objects.filter(deviceid=m_deviceid)[0]
    except Exception as inst:
        details=insertJSON.device(m_device,m_deviceid)
        
    try:
        network=insertJSON.network(m_network)
    except Exception as inst:
        return HttpResponse(error_message_helper.insert_entry_fail("network",inst))
    
    try:
        sim=insertJSON.sim(m_sim)
    except Exception as inst:
        return HttpResponse(error_message_helper.insert_entry_fail("sim",inst))
    
    try:
        throughput=insertJSON.throughput(m_throughput)
    except Exception as inst:
        return HttpResponse(error_message_helper.insert_entry_fail("throughput",inst))
    
    try:
        gps=insertJSON.gps(m_gps)
    except Exception as inst:
        return HttpResponse(error_message_helper.insert_entry_fail("gps",inst))
    
    try:
        battery=insertJSON.battery(m_battery)
    except Exception as inst:
        return HttpResponse(error_message_helper.insert_entry_fail("battery",inst))
    
    try:
        usage=insertJSON.usage(m_usage)
    except Exception as inst:
        return HttpResponse(error_message_helper.insert_entry_fail("usage",inst))
    
    try:  
        measurement = Measurement(deviceid = details,time=m_time,networkid=network,serialnumber=sim,throughputid=throughput,gpsid=gps,batteryid=battery,usageid=usage)
        measurement.save()
        m_id = measurement.measurementid
        
    except Exception as inst:     
        return HttpResponse(error_message_helper.insert_entry_fail("measurement",inst))
    count = 0    
    try:
        insertJSON.pings(pings,measurement)
    except Exception as inst:
        return HttpResponse(error_message_helper.insert_entry_fail("ping(" + count+")",inst))            
    print "measurement insertion ended"
    response['message'] = 'measurement inserted'
    response['status'] = 'OK'

    return HttpResponse(str(response))