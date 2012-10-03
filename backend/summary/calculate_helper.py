from django.http import HttpResponse, HttpResponseRedirect
import urllib2, urllib, json
from django.shortcuts import render_to_response
from backend.summary.models import *

import random
from datetime import datetime, timedelta
from time import time, mktime, strftime, gmtime
import hashlib
import ast
import error_message_helper, insertJSON, getJSON

def traffic(device_list,filter_list):
    for device in device_list:
        
        measurement_list = filter_list.filter(deviceid=device).order_by('time')
        
        print "new DEVICE: " + str(device.deviceid)
        last = None
        
        for measure in measurement_list:
            try:
                current = Usage.objects.filter(measurementid=measure.measurementid)[0]
            except:
                continue
          
            if last == None:
                try:
                    last = Usage.objects.filter(measurementid=measure.measurementid)[0]
                    last.mobile_till_now = 0
                    last.total_till_now = 0
                    last.save()
                    continue
                except:
                    continue
            
            
            last=usage(current,last)

def appTraffic(device_list,filter_list):
    for device in device_list:
        
        measurement_list = filter_list.filter(deviceid=device).order_by('time')
        
        print "new DEVICE(app): " + str(device.deviceid)
        last_list = {}
        
        for measure in measurement_list:
            
            appuse_list = ApplicationUse.objects.filter(measurementid=measure.measurementid)
            
            for appuse in appuse_list:
                
                pkgName = appuse.package.package
                
                try:
                    last = last_list[pkgName]
                except:
                    last = appuse
                    last.total_diff = 0
                    last.save()
                    last_list[pkgName]=last
                    continue
                
                
                
                last=app_usage(appuse,last)
                
                last_list[pkgName] = last

def usage(current,last):

    current_total = current.total_recv + current.total_sent
            
    last_total = last.total_recv + last.total_sent
            
    if current_total >= last_total:
        current.total_till_now = current_total - last_total
               
    else:
        if current_total < (last_total / 2):
            current.total_till_now = current_total
               
        else:
            current.total_till_now = 0
                    
            
    current_mobile_total = current.mobile_recv + current.mobile_sent          
    last_mobile_total = last.mobile_recv + last.mobile_sent
            
    if current_mobile_total >= last_mobile_total:
        current.mobile_till_now = current_mobile_total - last_mobile_total
                
    else:
        if current_mobile_total < (last_mobile_total / 2):
            current.mobile_till_now = current_mobile_total
                    
        else:
            current.mobile_till_now = 0
                    
    last = current
    current.save()
    return last

def app_usage(current,last):
        
    current_total = current.total_recv + current.total_sent
            
    last_total = last.total_recv + last.total_sent
            
    if current_total >= last_total:
        current.total_diff = current_total - last_total
               
    else:
        if current_total < (last_total / 2):
            current.total_diff = current_total
               
        else:
            current.total_diff = 0
            
    last = current
    current.save()
    return last