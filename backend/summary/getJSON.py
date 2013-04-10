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


def values():
    all = Values.objects.all()
    print len(all)
    data = all[0]
    val = {}
    #val['frequency_secs'] = data.frequency_secs
    #val['throughput_freq'] = data.throughput_freq
    #val['uplink_port'] = data.uplink_port
    #val['uplink_duration'] = data.uplink_duration
    #val['downlink_port'] = data.downlink_port
    #val['downlink_duration'] = data.downlink_duration
    #val['tcp_headersize'] = data.tcp_headersize
    #val['tcp_packetsize'] = data.tcp_packetsize
    #val['throughput_server_address'] =data.throughput_server_address
    #val['api_server_address'] =data.api_server_address
    #val['signalstrength_timeout'] =data.signalstrength_timeout
    #val['wifi_timeout'] =data.wifi_timeout
    #val['unavailable_cellid'] =data.unavailable_cellid
    #val['unavailable_celllac'] =data.unavailable_celllac
    #val['threadpool_max_size'] =data.threadpool_max_size
    #val['threadpool_keepalive_sec'] =data.threadpool_keepalive_sec
    val['countries'] = get_countries()
    val['ping_servers'] = ping_servers()
    
    return val

def ping_servers():
    #data = PingServers.objects.all()
    f = open('/srv/www/androidperformance/backend/summary/PingServers.txt', 'r')
    servers = {}
    servers['servers'] = {}
    for line in f:
	row = line.split('|')	
	country = row[2].rstrip('\n')
	if country not in servers['servers']:
	    servers['servers'][country] = []
	serve = {}
	serve['ipaddress'] = row[0]
	serve['tag'] = row[1]
	servers['servers'][country].append(serve)

    f.close()
    return servers

def get_countries():
    f = open('/srv/www/androidperformance/backend/summary/Countries.txt', 'r')   
    countries = []
    for line in f:
	line = line.rstrip('\n')
	country = {}
	country['code'] = line	
	countries.append(country)
    
    f.close()
    return countries
     
    
    
        
