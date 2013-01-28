from django.http import HttpResponse, HttpResponseRedirect
import urllib2, urllib, json
from django.shortcuts import render_to_response
from backend.summary.models import *

import random
from datetime import datetime, timedelta
from time import time, mktime, strftime
import hashlib
import ast
import error_message_helper


def device(dev, m_deviceid, m_sim):
    
    count = 0
    
    d = Device()
    try:
        d = Device.objects.filter(deviceid=m_deviceid)[0]
    except:
        d = Device()
        try:
            d.deviceid = m_deviceid
        except Exception as inst:
            print error_message_helper.insert_entry_fail("device", inst)
    
    try:
        
        try:
            d.phonenumber = dev['phoneNumber']
        except Exception as inst:
            print error_message_helper.insert_entry_fail("device", inst)
        try:
            d.phonetype = dev['phoneType']
        except Exception as inst:
            print error_message_helper.insert_entry_fail("device", inst)
        try:                         
            d.softwareversion = dev['softwareVersion']
        except Exception as inst:
            print error_message_helper.insert_entry_fail("device", inst)
        try:
            d.phonemodel = dev['phoneModel']
        except Exception as inst:
            print error_message_helper.insert_entry_fail("device", inst)
        try:
            d.androidversion = dev['androidVersion']
        except Exception as inst:
            print error_message_helper.insert_entry_fail("device", inst)
        try:
            d.phonebrand = dev['phoneBrand']
        except Exception as inst:
            print error_message_helper.insert_entry_fail("device", inst)
        try:
            d.devicedesign = dev['deviceDesign']
        except Exception as inst:
            error_message_helper.insert_entry_fail("device", inst)
        try:
            d.manufacturer = dev['manufacturer']
        except Exception as inst:
            error_message_helper.insert_entry_fail("device", inst)
        try:
            d.productname = dev['productName']
        except Exception as inst:
            error_message_helper.insert_entry_fail("device", inst)
        try:
            d.radioversion = dev['radioVersion']
        except Exception as inst:
            error_message_helper.insert_entry_fail("device", inst)
        try:
            d.boardname = dev['boardName']
        except Exception as inst:
            error_message_helper.insert_entry_fail("device", inst)
        try:
            d.datacap = dev['datacap']
        except Exception as inst:
            error_message_helper.insert_entry_fail("device", inst)
        try:
            d.billingcycle = dev['billingcycle']
        except Exception as inst:
            error_message_helper.insert_entry_fail("device", inst)
        try:
            d.networkcountry = dev["networkCountry"]
        except Exception as inst:
            error_message_helper.insert_entry_fail("device", inst)
        try:
            d.networkname = dev["networkName"]
        except Exception as inst:
            error_message_helper.insert_entry_fail("device", inst)
        
        try:           
            if not m_sim == None:
		s = sim(m_sim);
            	d.serialnumber = s
        except Exception as inst:
            print error_message_helper.insert_entry_fail("sim", inst) 
        
        
        d.save()
	
        print "Device inserted"
    except Exception as inst:
        print error_message_helper.insert_entry_fail("device+", inst) 
       
    return d


def network(dev, m):
    
    print "trying network"
    n = Network()
    try:
        n.measurementid = m.measurementid
    except:
        pass
    try:                
        n.networktype = dev["networkType"]
    except:
        n.networktype = "null"
    try:    
        n.connectiontype = dev["connectionType"]
    except:
        n.connectiontype = "null"
    
    try:
        n.cellid = parse(dev['cellId'])
    except:
        n.cellid = "null"
            
    try:
        n.celllac = parse(dev["cellLac"])
    except:
        n.celllac = "null"
    
    try:
        n.celltype = parse(dev["cellType"])
    except:
        n.celltype = "null"
    try:
        n.longitude = parseFloat(dev["basestationLong"], -99)
    except:
        n.longitude = -99
    try:
        n.latitude = parseFloat(dev["basestationLat"], -99)
    except:
        n.latitude = -99
    try:
        n.networkid = parseInt(dev["networkid"], -1)
    except:
        n.networkid = -1            
    try:
        n.systemid = parseInt(dev["systemid"], -1)
    except:
        n.systemid = -1
            
    try:     
        n.datastate = dev["dataState"]
    except:
        n.datastate="null"
    try:    
        n.dataactivity = dev["dataActivity"]
    except:
        n.dataactivity = "null"
    try:    
        n.signalstrength = dev["signalStrength"]
    except:
        n.signalstrength = "-1"
    print "saving"
    n.save()
    print n.systemid
    print "Network inserted"
       
    return n

def sim(dev):
    
    s = Sim()
    try:
        try:
            s.serialnumber = dev["serialNumber"]
        except:
            pass
        try: 
            s.state = dev["state"]
        except:
            pass
        try: 
            s.operatorcode = dev["operatorCode"]
        except:
            pass
        try:        
            s.operatorname = dev["operatorName"]
        except:
            pass
        try:
            s.networkcountry = dev["networkCountry"]
        except:
            pass
    
        s.save()
        print "Sim inserted"
    except Exception as inst:
        print error_message_helper.insert_entry_fail("sim", inst)
       
    return s

def gps(dev, m):
    
    g = Gps() 
    try:
        g.measurementid = m       
        g.latitude = dev['latitude']
        g.longitude = dev['longitude']    
        g.altitude = dev['altitude']
        g.save()
        print "GPS inserted"
    except:
        pass
       
    return g

def battery(dev, m):
    
    g = Battery()
    g.measurementid = m.measurementid
    
    try:        
        g.ispresent = dev['isPresent']
    except:
        pass
    try:
        g.technology = dev['technology']
    except:
        pass
    try:
        g.plugged = dev['plugged']
    except:
        pass
    try:
        g.scale = dev['scale']
    except:
        pass
    try:
        g.health = dev['health']
    except:
        pass
    try:
        g.voltage = dev['voltage']
    except:
        pass
    try:
        g.level = dev['level']
    except:
        pass
    try:
        g.temperature = dev['temperature']
    except:
        pass
    try:
        g.status = dev['status']
    except:
        pass
    
    g.save()
    print "Battery inserted"
       
    return g

def warmup(warm, m):
    
    w = WarmupExperiment()
    w.measurementid = m.measurementid
    
    try:        
        w.lowest = warm['lowest']
    except:
        pass
    try:        
        w.highest = warm['highest']
    except:
        pass
    
    try:
        w.version = warm['version']
    except:
        pass
    try:
        w.total_count = warm['total_count']
    except:
        pass
    try:
        w.gap = warm['time_gap']
    except:
        pass
    try:
        w.dstip = warm['dstip']
    except:
        pass
    
    w.save()
    
    for entry in warm['sequence']:
        e = WarmupPing()
        e.value = entry['value']
        e.sequence_count = entry['sequence']
        e.period = entry['period']
        e.measurementid = m
        e.save()
    
    print "Warmup inserted"
       
    return w


def link(dev):
    
    l = Link()
    try:
        l.count = dev['count']          
        l.message_size = dev['message_size']    
        l.duration = dev['time']    
        l.speed = dev['speedInBits']    
        l.port = dev['dstPort']
        l.ip_address = dev['dstIp']
        l.save()
        print "Link inserted"
    except:
        pass
    
    return l


def screen(arrdev, device):
    
    for dev in arrdev:
        s = Screen()
        try:
            s.time = dev['time']          
            s.localtime = dev['localtime']
            s.deviceid = device
            if dev['isOn'] == 1:
                s.turnedon = True
            else:
                s.turnedon = False
                
            s.save()
        except:
            pass
        
    print "Screen inserted"
    

def throughput(dev, m):
    
    t = Throughput()
    
    try:
        t.measurementid = m.measurementid
        t.uplinkid = link(dev['upLink'])
        t.downlinkid = link(dev['downLink'])
        t.save()
        print "Throughput inserted"
    except:
        pass
     
    
    return t


def usage(dev, m):
    
    u = Usage()
    u.measurementid = m.measurementid
    
    last = None
    
    try:
        print m.deviceid.deviceid
        last_measurement = Measurement.objects.filter(deviceid=m.deviceid.deviceid).order_by('time')[0]
        print last_measurement.measurementid
        last = Usage.objects.filter(measurementid=last_measurement.measurementid)[0]
    except:
        pass
    
    try:
        u.total_sent = dev['total_sent']
    except:
        pass
    
    try:
        u.total_recv = dev['total_recv']
    except:
        pass
    try:
        u.mobile_sent = dev['mobile_sent']
    except:
        pass
    try:
        u.mobile_recv = dev['mobile_recv']
    except:
        pass
    u.total_till_now = 0
    u.mobile_till_now = 0
    u.save()
    
    for app in dev['applications']:
        
        try:
            result = Application.objects.filter(package=app['packageName'][:50])[0]
        except:
            result = Application(package=app['packageName'][:50], name=app['name'][:20])
            result.save()
        
        appUse = ApplicationUse()
        
        try:
            appUse.package = result
        except:
            pass
        try:
            appUse.total_sent = app['total_sent']
        except:
            pass
        try:
            if app['isRunning'] == 1:
                appUse.isrunning = True
            else:
                appUse.isrunning = False
        except:
            pass
        try:
            appUse.total_recv = app['total_recv']
        except:
            pass
        try:
            appUse.measurementid = m
        except:
            pass
        
     
        appUse.save()
   

    print "Usage inserted"
    
        
    
    return u
    

def pings(pings, measurement):
    
    for p in pings:
        ping = Ping()
       
        
        ping.measurementid = measurement
        
        try:
            ping.scrip = p['src_ip']
        except:
            pass
        try:        
            ping.dstip = p['dst_ip']
        except:
            pass
        try:
            ping.time = p['time']
        except:
            pass
        
        measure = p['measure']
        
        
        try:           
            ping.avg = measure['average']
        except:
            pass
        try:
            ping.stdev = measure['stddev']
        except:
            pass
        try:
            ping.min = measure['min']
        except:
            pass
        try:
            ping.max = measure['max']
        except:
            pass
        
        ping.save()
        
def lastmiles(lastmiles, measurement):
    
    for p in lastmiles:
        lastmile = Lastmile()
       
        
        lastmile.measurementid = measurement
        
        try:
            lastmile.scrip = p['src_ip']
        except:
            pass
        try:        
            lastmile.dstip = p['dst_ip']
        except:
            pass
        try:
            lastmile.time = p['time']
        except:
            pass
        try:
            lastmile.hopcount = p['hopcount']
        except:
            pass
        try:
            lastmile.firstip = p['firstip']
        except:
            pass
        
        measure = p['measure']
        
        
        try:           
            lastmile.avg = measure['average']
        except:
            pass
        try:
            lastmile.stdev = measure['stddev']
        except:
            pass
        try:
            lastmile.min = measure['min']
        except:
            pass
        try:
            lastmile.max = measure['max']
        except:
            pass
        
        lastmile.save()

def calculate_log(range):
    print "im in"
    current_time = datetime.utcnow()
    ranged = timedelta(hours=float(range))
    l_time = current_time - ranged
    
    print str(range) + " hours" 
    print current_time
    print l_time
    log = CalculateLog(log_time=current_time, time=l_time)
    log.save()
        
def error_log(message, device, request):
    
    error_log = ErrorLog()
    error_log.log_time = datetime.utcnow()
    error_log.deviceid = device
    error_log.error_text = str(message)
    error_log.user_agent = str(request.META['HTTP_USER_AGENT'])
    error_log.remote_addr = str(request.META['REMOTE_ADDR'])
    
    error_log.save()
        
def wifi(dev, m):
    
    w = Wifi()
    w.measurementid = m.measurementid
    w.ipaddress = dev['ipAddress']
    w.detailedinfo = dev['detailedInfo']
    w.rssi = dev['rssi']
    w.signalstrength = dev['strength']
    w.speed = dev['speed']
    w.units = dev['units']
  
    for spot in dev['wifiNeighbors']:
       
        try:
            result = WifiHotspot.objects.filter(macaddress=spot['macAddress'])[0]
        except:
            result = WifiHotspot()
            result.macaddress = spot['macAddress']
            result.ssid = spot['ssid']
            result.frequency = spot['frequency']
            result.capability = spot['capability'][:20]
            result.save()
        
        if spot['isConnected'] == 'true':            
            w.connection = result            
            w.save()
            
    
    for spot in dev['wifiNeighbors']:
        result = WifiHotspot.objects.filter(macaddress=spot['macAddress'])[0]
        
        wn = WifiNeighbor()     
        wn.macaddress = result
        wn.measurementid = m
        if spot['isPreferred'] == 'true':
            wn.ispreferred = 1
        else:
            wn.ispreferred = 0
        wn.signallevel = spot['signalLevel']
        wn.save()
        
    print "Wifi inserted"
        
    w.save()
    return w

def delay_variation(delay_variation, m):
    
    for entry in delay_variation['ipdvlist']:
        e = Ipdv()
        try:
	    e.sequencenumber = entry['sequence']
	except:
	    pass	
	try:
  	    e.ipdv = entry['ipdv']
	except:
	    pass        
	try:
	    e.measurementid = m
	except:
	    pass 
        e.save()
    
    print "Ipdv inserted"
             
def loss(loss, m):
    
    l = Loss()
    try:    
	l.measurementid = m
    except:
	pass    
    try:
	l.total = loss['total']
    except:
	pass
    try:
	l.lost = loss['lost']
    except:
	pass
    try:
	l.losspercentage = loss['losspercentage']
    except:
	pass
    
    l.save()
    
    print "Loss inserted"
    return l

def parse(object):
   
    if object == None:
        return ''
    else:
        return object
    
def parseInt(object, backup):
    
    try:
        a = int(object)
        return object
    except:
        return backup
    
def parseFloat(object, backup):
    object = parse(object)
    try:
        a = float(object)
        return object
    except:
        return backup
        
