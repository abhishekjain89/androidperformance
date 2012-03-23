
def base_str(result,column):
	tup = ()
	for row in result:
		tup = tup + (str(row[column]),)
	
	return tup
	
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

def MB(result,column):
	tup = ()
	for row in result:
		tup = tup + (float(row[column])/1024/1024,)
		
	
	return tup
	