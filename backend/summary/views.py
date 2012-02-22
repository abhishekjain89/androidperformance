from django.http import HttpResponse, HttpResponseRedirect
import urllib2, urllib, json
from django.shortcuts import render_to_response
from backend.summary.models import *

import random
from datetime import datetime, timedelta
from time import time,mktime,strftime,gmtime
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
        m_localtime = request_object['localtime']
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
        m_wifi = request_object['wifi']
        count+=1
        m_state = request_object['state']
        count+=1

    except:
        return HttpResponse(error_message_helper.missing_attributes('measurement(' +count+ ')'))        
    print "measurement insertion started..."
    
    measurement = Measurement()
    measurement.time = m_time
    measurement.localtime = m_localtime;
    message = []
    try:
        details=Device.objects.filter(deviceid=m_deviceid)[0]
        measurement.deviceid = details
    except Exception as inst:
        details=insertJSON.device(m_device,m_deviceid)
        measurement.deviceid = details
        
    try:
        network=insertJSON.network(m_network)
        measurement.networkid = network
    except Exception as inst:
       message.append(error_message_helper.insert_entry_fail("network",inst))
    
    try:
        sim=insertJSON.sim(m_sim)
        measurement.serialnumber = sim
    except Exception as inst:
        message.append(error_message_helper.insert_entry_fail("sim",inst))
    
    try:
        throughput=insertJSON.throughput(m_throughput)
        measurement.throughputid = throughput
    except Exception as inst:
        message.append(error_message_helper.insert_entry_fail("throughput",inst))
    
    try:
        gps=insertJSON.gps(m_gps)
        measurement.gpsid = gps
    except Exception as inst:
       message.append(error_message_helper.insert_entry_fail("gps",inst))
       
    try:
        wifi=insertJSON.wifi(m_wifi)
        measurement.wifiid = wifi
    except Exception as inst:
       message.append(error_message_helper.insert_entry_fail("wifi",inst))
    
    try:
        battery=insertJSON.battery(m_battery)
        measurement.batteryid=battery
    except Exception as inst:
        message.append(error_message_helper.insert_entry_fail("battery",inst))
    
    try:
        usage=insertJSON.usage(m_usage)
        measurement.usageid=usage
    except Exception as inst:
        
        message.append(error_message_helper.insert_entry_fail("usage",inst))
        
        
    measurement.save()
    print measurement.throughputid.throughputid
    try:
       if not measurement.throughputid.throughputid == None:
           s_cellid = m_state['cellId']
           s_localtime = m_state['localtime']
           s_time = m_state['time']
           s_deviceid = m_state['deviceid']
           s_type = m_state['networkType']
           localtime_object = datetime.strptime(s_localtime, '%Y-%m-%d %H:%M:%S')
           s_timeslice = (int(localtime_object.hour)/6)*6
           day_of_week = int(localtime_object.strftime('%w'))
           
           if day_of_week>0 and day_of_week<6:
               s_weekday = 1
           else:
               s_weekday = 0
           try:    
               current_states =State.objects.filter(cellid=s_cellid,deviceid=s_deviceid,timeslice=s_timeslice,weekday=s_weekday,networktype=s_type)[0]
           except:
               states = State(cellid=s_cellid,deviceid=s_deviceid,timeslice=s_timeslice,weekday=s_weekday,networktype=s_type,measurementid=measurement)
               states.save()
       
    except Exception as inst:
       message.append(error_message_helper.insert_entry_fail("state",inst))   
    
    
    m_id = measurement.measurementid
        
    #except Exception as inst:     
    #    return HttpResponse(error_message_helper.insert_entry_fail("measurement",inst))
    count = 0    
    try:
        insertJSON.pings(pings,measurement)
    except Exception as inst:
        message.append(error_message_helper.insert_entry_fail("ping(" + count+")",inst))            
    print "measurement insertion ended"
    response['message'] = 'measurement inserted: ' + str(message)
    response['status'] = 'OK'

    return HttpResponse(str(response))

def summary(request):
	
    data={}
    data['status']='Working Fine'
    data['total-apps'] = len(Application.objects.all())
    data['total-devices'] = len(Device.objects.all())
    data['total-cells'] = len(Cell.objects.all())
    data['total-wifis'] = len(WifiHotspot.objects.all())
    return HttpResponse(str(data))

def parameterCheck(request):
    response = {}

    print "start"
    try:
        request_object = ast.literal_eval(request.read())
    except:
        return HttpResponse(error_message_helper.invalid_format())
    
    
    s_type = request_object['networkType']
    s_cellid = request_object['cellId']
    s_localtime = request_object['localtime']
    s_time = request_object['time']
    s_deviceid = request_object['deviceid']
    
    localtime_object = datetime.strptime(s_localtime, '%Y-%m-%d %H:%M:%S')
    s_timeslice = (int(localtime_object.hour)/6)*6
    day_of_week = int(localtime_object.strftime('%w'))
    s_weekday=0
    if day_of_week>0 and day_of_week<6:
        s_weekday = 1
    
    
    try:
        states = State.objects.filter(cellid=s_cellid,deviceid=s_deviceid,timeslice=s_timeslice,weekday=s_weekday,networktype=s_type)[0]
        response['go_ahead']=0
    except:
        response['go_ahead']=1
        pass
    
    
    
    return HttpResponse(str(response))
    

def getTraffic(request):
    
    device =  request.GET.get("device")
    range = request.GET.get('hours')
    
    
    current_time= datetime.now()
    ranged = timedelta(hours=float(range))
    range_time = current_time - ranged
    
    measurements = Measurement.objects.filter(deviceid = device,time__gte=str(range_time)).order_by('time')
    last_measurement = measurements[len(measurements)-1]
    
    last_usage =  ApplicationUse.objects.filter(usageid=last_measurement.usageid)
    
    result = {}
    result['app-data']=[]
    result['range']="1 day"
        
    if len(measurements) > 0:
        oldest = current_time - measurements[0].time
        result['range'] = str(oldest.days+1) + " days"
    
    print last_measurement.usageid.usageid
    print len(last_usage)
    
    for app_row in last_usage: 
        print app_row.package.package
        app_related = ApplicationUse.objects.filter(package = app_row.package)
        
        total = 0
        first = app_related[0].total_sent + app_related[0].total_recv
        last = 0
        
        for row in app_related:
            
            try:
                
                now = row.total_sent + row.total_recv
                
                if last>now:
                    total+=last
                
                last=now
                
            except:
                continue
            
            
        if(total>0):
            total+=last-first
        else:
            total=last
        res={}
        res['app']=app_row.package.package
        res['total']=(total/1000000)
        result['app-data'].append(res)
    
    
    return HttpResponse(str(result))


