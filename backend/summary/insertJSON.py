from django.http import HttpResponse, HttpResponseRedirect
import urllib2, urllib, json
from django.shortcuts import render_to_response
from backend.summary.models import *

import random
from datetime import datetime, timedelta
from time import time,mktime,strftime
import hashlib
import ast
import error_message_helper


def device(dev,m_deviceid):
    
    count=0
    d = Device(deviceid = m_deviceid,phonenumber=dev['phoneNumber'])
    
    try:
        d.phonetype = dev['phoneType']
        count+=1            
        d.softwareversion = dev['softwareVersion']
        count+=1
        d.phonemodel = dev['phoneModel']
        count+=1
        d.androidversion = dev['androidVersion']
        count+=1
        d.phonebrand = dev['phoneBrand']
        count+=1
        d.devicedesign = dev['deviceDesign']
        count+=1
        d.manufacturer = dev['manufacturer']
        count+=1
        d.productname = dev['productName']
        count+=1
        d.radioversion = dev['radioVersion']
        count+=1
        d.boardname = dev['boardName']
        count+=1
        d.save()
	print d.deviceid
        print "Device inserted"
    except Exception as inst:
        print type(inst)     # the exception instance
        print inst    
        print error_message_helper.insert_entry_fail("device("+str(count)+")") 
       
    return d


def network(dev):
    
    count=0
    n = Network()
    
    n.networkcountry = dev["networkCountry"]
    count+=1
    n.networkname = dev["networkName"]
    count+=1            
    n.networktype = dev["networkType"]
    count+=1
    n.connectiontype = dev["connectionType"]
    count+=1
    n.mobilenetworkinfo = dev["mobileNetworkInfo"]
    count+=1
    #n.wifistate = dev["wifiState"]
    #count+=1
    n.cellid = dev["cellId"]
    count+=1
    n.celllac = dev["cellLac"]
    count+=1
    n.datastate = dev["dataState"]
    count+=1
    n.dataactivity = dev["dataActivity"]
    count+=1
    #n.signalstrength = dev["signalStrength"]
    #count+=1
    
    n.save()
    print "Network inserted"
       
    return n

def sim(dev):
    
    count=0
    s = Sim()
    
    s.serialnumber = dev["serialNumber"]
    s.state = dev["state"]
    s.operatorcode = dev["operatorCode"]
    s.operatorname = dev["operatorName"]
    s.networkcountry = dev["networkCountry"]
    count+=1
    
    s.save()
    print "Sim inserted"
       
    return s

def gps(dev):
    
    g = Gps()    
    g.latitude = dev['latitude']
    g.longitude = dev['longitude']
    g.altitude = dev['altitude']
    g.save()
    print "GPS inserted"
       
    return g

def battery(dev):
    
    g = Battery()    
    g.ispresent = dev['isPresent']
    g.technology = dev['technology']
    g.plugged = dev['plugged']
    g.scale = dev['scale']
    g.health = dev['health']
    g.voltage = dev['voltage']
    g.level = dev['level']
    g.temperature = dev['temperature']
    g.status = dev['status']
    g.save()
    print "Battery inserted"
       
    return g


def link(dev):
    
    l = Link()    
    l.count = dev['count']
    l.message_size = dev['message_size']
    l.duration = dev['time']
    l.speed = dev['speedInBits']
    l.port = dev['dstPort']
    l.ip_address = dev['dstIp']
    l.save()
    print "Link inserted"
    return l

def throughput(dev):
    
    t = Throughput()
    t.uplinkid=link(dev['upLink'])
    t.downlinkid=link(dev['downLink']) 
    t.save()
    print "Throughput inserted"
    return t


def usage(dev):
    
    u = Usage()
    u.total_sent=dev['total_sent']
    u.total_recv=dev['total_recv']
    u.mobile_sent=dev['mobile_sent']
    u.mobile_recv=dev['mobile_recv']
    u.save()
    
    for app in dev['applications']:
        
        try:
            result = Application.objects.filter(package=app['packageName'][:50])[0]
        except:
            result = Application(package=app['packageName'][:50],name=app['name'][:20])
            result.save()
        
        appUse = ApplicationUse()
        appUse.package = result
        appUse.total_sent=app['total_sent']
        appUse.total_recv=app['total_recv']
        appUse.usageid= u
        appUse.save()
    
    print "Usage inserted"
        
    
    return u
    

def pings(pings,measurement):
    for p in pings:
        count = 0
        d_srcip = p['src_ip']
        count+=1
        d_dstip = p['dst_ip']
        count+=1
        d_time = p['time']
        count+=1
        measure = p['measure']           
        d_average = measure['average']
        count+=1
        d_std = measure['stddev']
        count+=1
        d_min = measure['min']
        count+=1
        d_max = measure['max']
        count+=1
            
        ping = Ping(measurementid = measurement,scrip=d_srcip,dstip=d_dstip,time=d_time,avg=d_average,stdev=d_std,min=d_min,max=d_max)
            
        ping.save()
