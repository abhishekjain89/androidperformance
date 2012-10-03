
def base_str(result,column):
	tup = ()
	
	for row in result:
		
		tup = tup + (str(row[column]),)
	
	return tup


def base_int(result,column):
		tup = ()
		for row in result:
			tup = tup + (int(row[column]),)

		return tup

def base_float(result,column):
		tup = ()
		for row in result:
			tup = tup + (float(row[column]),)

		return tup



def toarray(tup):
	
	ret = []
	
	for t in tup:
		ret.append(t)
		
	return ret

def normalize(tup):
	
	maxi = max(tup)
	ret = ()
	for t in tup:

		ret = ret + (float(t)/float(maxi),)
	return ret
	
def hour(result,column):
	tup = ()
	for row in result:
		time = int(row[column])
		if time < 12:
			string = str(time) + "A"
		else:
			string = str(time-12) + "P"

		tup = tup + (string,)
	
	return tup

def hour_minute_period(result,column1,column2,period):
	tup = ()
	for row in result:
		time_hour = float(row[column1])
		time_min = float(row[column2])*period
		time = time_hour + float(time_min/60)
		
		tup = tup + (time,)
		
	
	return tup

def hour_minute_in_mins(result,column1,column2):
	tup = ()
	for row in result:
		time_hour = float(row[column1])
		time_min = float(row[column2])
		time = time_hour*60 + float(time_min)
		
		tup = tup + (time,)
		
	
	return tup

def MB(result,column):
	tup = ()
	for row in result:
		tup = tup + (float(row[column])/1024/1024,)
		
	
	return tup

def group_by(result,column):
	data = {}
	for row in result:
		if not data.has_key(str(row[column])):
			data[str(row[column])] = []

		data[str(row[column])].append(row)
		
	return data