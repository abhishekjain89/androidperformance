from django.http import HttpResponse, HttpResponseRedirect
import urllib2, urllib, json
from django.shortcuts import render_to_response
from backend.summary.models import *

import random
from datetime import datetime, timedelta
from time import time,mktime,strftime,gmtime
import hashlib
import ast
import error_message_helper,insertJSON,getJSON

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
        m_screens = request_object['screens']
        count+=1
        m_wifi = request_object['wifi']
        count+=1
        m_state = request_object['state']
        count+=1
        m_ismanual = request_object['isManual']

    except Exception as inst:
       message.append(error_message_helper.insert_entry_fail("measurement-extract",inst))           
    print "measurement insertion started..."
    
    measurement = Measurement()
    measurement.time = m_time
    try:
        if m_ismanual == 1:
            measurement.ismanual = True
        else:
            measurement.ismanual = False
    except:
        pass
    
    
    
    measurement.localtime = m_localtime;
    
    message = []
    try:
        details=Device.objects.filter(deviceid=m_deviceid)[0]
        measurement.deviceid = details
        try:
            exist = Measurement.objects.filter(deviceid=details,time=m_time)[0]
            #return HttpResponse(error_message_helper.duplicate_entry())
        except Exception as inst:
            pass
    except:
        pass
        
    try:
        details=insertJSON.device(m_device,m_deviceid,m_sim)
        measurement.deviceid = details
    except Exception as inst:
       message.append(error_message_helper.insert_entry_fail("device",inst))
        
    measurement.save();
    
    try:
        network=insertJSON.network(m_network,measurement)
    except Exception as inst:
       message.append(error_message_helper.insert_entry_fail("network",inst))    
    
    try:
        insertJSON.screen(m_screens,m_deviceid)
    except Exception as inst:
       message.append(error_message_helper.insert_entry_fail("screens",inst))
    
    try:
        throughput=insertJSON.throughput(m_throughput,measurement)
    except Exception as inst:
        message.append(error_message_helper.insert_entry_fail("throughput",inst))
         
    try:
        gps=insertJSON.gps(m_gps,measurement)        
    except Exception as inst:
       message.append(error_message_helper.insert_entry_fail("gps",inst))
    try:
        wifi=insertJSON.wifi(m_wifi,measurement)
    except Exception as inst:
       message.append(error_message_helper.insert_entry_fail("wifi",inst))
    
    try:
        battery=insertJSON.battery(m_battery,measurement)
    except Exception as inst:
        message.append(error_message_helper.insert_entry_fail("battery",inst))
    
    try:
        usage=insertJSON.usage(m_usage,measurement)
    except Exception as inst:
        message.append(error_message_helper.insert_entry_fail("usage",inst))
   
    try:
       if not throughput.measurementid == None:
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
                   
               states = State(cellid=s_cellid,deviceid=s_deviceid,timeslice=s_timeslice,weekday=s_weekday,networktype=s_type,measurementid=measurement.measurementid)
                   
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
        message.append(error_message_helper.insert_entry_fail("ping",inst))            
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
    data['values'] = getJSON.values()
    return HttpResponse(json.dumps(data))

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
        killdevice = KillList.objects.filter(deviceid=s_deviceid)[0]
        response['go_ahead']=0
        return HttpResponse(str(response))
    except:
        pass
    
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
    
    
    current_time= datetime.utcnow()
    ranged = timedelta(hours=float(range))
    range_time = current_time - ranged
    
    measurements = Measurement.objects.filter(deviceid = device,time__gte=str(range_time)).order_by('time')
    last_measurement = measurements[len(measurements)-1]
    
    
    last_usage =  ApplicationUse.objects.filter(usageid=last_measurement.usageid)
    
    
    appDataMB={}
    lastVal={}
    firstVal={}
    
    for app_row in last_usage:
         
         appDataMB[app_row.package.package]=0
   
    result = {}
    result['app-data']=[]
    result['range']="1 day"
        
    if len(measurements) > 0:
        oldest = current_time - measurements[0].time
        if oldest.days == 0:
            result['range'] = str(oldest.seconds/3600 + 1) + " hrs"
        else:
            result['range'] = str(oldest.days) + " days"
        
        result['range-abs'] = str(oldest)
    
    for measurement in measurements:
         related_apps = ApplicationUse.objects.filter(usageid=measurement.usageid)
        
         for app in related_apps:
               pkg = app.package.package
               current = app.total_sent + app.total_recv
                
               if appDataMB.has_key(pkg):
                     if firstVal.has_key(pkg):
                           if lastVal.has_key(pkg):
                                 if current > lastVal[pkg]:
                                       a=1
                                 elif (100*lastVal[pkg]-100*current)/(current) > 10:
                                       appDataMB[pkg] = appDataMB[pkg] + lastVal[pkg]
                        
                     else:
                           firstVal[pkg]=current
                        
                     lastVal[pkg]=current  
                    
                    
    result['app-data']=[]               
    
    for key in appDataMB:
        appDataMB[key]+=lastVal[key] -firstVal[key]       
        print key
        print appDataMB[key]/1000000
        res={}
        res['app']=key
        res['total']=appDataMB[key]/1000000
        
        result['app-data'].append(res)
    
    print result
    
    return HttpResponse(str(result))


