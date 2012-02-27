from django.http import HttpResponse, HttpResponseRedirect
import urllib2, urllib, json
from django.shortcuts import render_to_response
from backend.summary.models import *

import random
from datetime import datetime, timedelta
from time import time,mktime,strftime
import hashlib
import ast

def invalid_format():
	response={}
	response['message'] = 'invalid format'
	response['status'] = 'error'
	return str(response)

def duplicate_entry():
	response={}
	response['message'] = 'duplicate_entry'
	response['status'] = 'duplicate'
	return str(response)

def missing_attributes(attr):
	response={}
	response['message'] = 'missing attributes: ' + attr
	response['status'] = 'error'
	return str(response)

def insert_entry_fail(tablename):
	print "ffs"
	response = {}
	response['message'] = 'unable to insert entry in table:' + tablename
	
	response['traceback'] = traceback.print_exc()
	response['status'] = 'error'
	return str(response)

def insert_entry_fail(tablename,instr):
	response={}
	response['message'] = 'unable to insert entry in table:' + tablename
	response['error_type'] = type(instr)
	response['error_text'] = instr
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

