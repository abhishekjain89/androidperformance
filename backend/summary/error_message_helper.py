from django.http import HttpResponse, HttpResponseRedirect
import urllib2, urllib, json
from django.shortcuts import render_to_response
from androidperformance.summary.models import *
from pyofc2 import *
import random
from datetime import datetime, timedelta
from time import time,mktime,strftime
from mx.DateTime.ISO import ParseDateTimeUTC
import hashlib
import ast

def invalid_format():
	response={}
	response['message'] = 'invalid format'
	response['status'] = 'error'
	return str(response)

def missing_attributes():
	response={}
	response['message'] = 'missing attributes'
	response['status'] = 'error'
	return str(response)

def insert_entry_fail():
	response={}
	response['message'] = 'unable to insert entry in table'
	response['status'] = 'error'
	return str(response)

def retrieve_entry_fail():
	response={}
	response['message'] = 'unable to retrieve entry in table'
	response['status'] = 'error'
	return str(response)

def device_not_found():
	response={}
	response['message'] = 'device not found'
	response['status'] = 'NOTFOUND'
	return str(response)

