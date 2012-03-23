
def usage_over_hour_of_day():
	return "select date_part('hour', devicetime),(sum(total_till_now)-sum(mobile_till_now))/count(*),sum(mobile_till_now)/count(*) from usage,measurement where measurement.measurementid = usage.measurementid and deviceid!='7a64da14bf60b976e7f3048400b0f4daeb0b2d33' group by date_part('hour', devicetime)"
	
def usage_over_hour_of_day_finegrain():
	return "select date_part('hour', devicetime),cast(floor(date_part('minute', devicetime)/10) as int),(sum(total_till_now)-sum(mobile_till_now))/count(*),sum(mobile_till_now)/count(*) from usage,measurement where measurement.measurementid = usage.measurementid and deviceid!='7a64da14bf60b976e7f3048400b0f4daeb0b2d33' group by date_part('hour', devicetime),cast(floor(date_part('minute', devicetime)/10) as int)"

def app_by_total_data():
	return "select application.package,application.name,sum(total_diff) from application_use,application where application_use.package = application.package and total_diff > 0 and application.package!='com.num' and application.package!='com.ping' group by application.package order by sum(total_diff) desc limit 20"
	
def signalstrength_over_hour_of_day():
	return "select networktype,date_part('hour', devicetime),cast(floor(date_part('minute', devicetime)/10) as int),avg(cast(signalstrength as int)),stddev(cast(signalstrength as int)) from measurement,network where measurement.measurementid = network.measurementid and signalstrength!=''and networktype!='' and networktype!='GPRS' and networktype!='EVDO_0' and networktype!='HSPA' and networktype!='UNKNOWN'  and networktype!='EVDO_A' group by networktype,date_part('hour', devicetime),cast(floor(date_part('minute', devicetime)/10) as int)"

def connectiontype_over_time_of_day():
	return "select connectiontype='Wifi',date_part('hour', devicetime),cast(floor(date_part('minute', devicetime)/10) as int),count(*) from measurement,network where measurement.measurementid = network.measurementid  group by connectiontype='Wifi',date_part('hour', devicetime),cast(floor(date_part('minute', devicetime)/10) as int)"