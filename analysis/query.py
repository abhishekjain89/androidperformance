import queryconstructor

def usage_over_hour_of_day():
	return "select date_part('hour', \"localtime\"),(sum(total_till_now)-sum(mobile_till_now)),sum(mobile_till_now) from usage,measurement where measurement.measurementid = usage.measurementid and deviceid!='7a64da14bf60b976e7f3048400b0f4daeb0b2d33' group by date_part('hour', \"localtime\")"
	
def usage_over_hour_of_day_finegrain():
	return "select date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int),(sum(total_till_now)-sum(mobile_till_now)),sum(mobile_till_now) from usage,measurement where measurement.measurementid = usage.measurementid and deviceid!='7a64da14bf60b976e7f3048400b0f4daeb0b2d33' group by date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int)"

def package_profile_over_time_of_day(package):
	return "select date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int),sum(total_diff),max(total_diff),count(*) from measurement,application_use where measurement.measurementid = application_use.measurementid and package='" + str(package) + "' and total_diff>0 group by date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int) order by date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int)"

def app_by_total_data():
	return "select application.package,application.name,sum(total_diff) from application_use,application where application_use.package = application.package and total_diff > 0 and application.package!='com.num' and application.package!='com.ping' group by application.package order by sum(total_diff) desc limit 10"
	
def app_by_popularity():
	return "select count(distinct(deviceid)),count(*),application.name from application,application_use,measurement where application_use.measurementid = measurement.measurementid and application_use.package = application.package group by application.name order by count(distinct(deviceid)) desc limit 10"


########
	
def signalstrength_over_hour_of_day():
	return "select networktype,date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int),avg(cast(signalstrength as int)),stddev(cast(signalstrength as int)) from measurement,network where measurement.measurementid = network.measurementid and signalstrength!=''and networktype!='' and networktype!='GPRS' and networktype!='EVDO_0' and networktype!='HSPA' and networktype!='UNKNOWN'  and networktype!='EVDO_A' group by networktype,date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int)"

def connectiontype_over_time_of_day():
	return "select connectiontype='Wifi',date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int),count(*) from measurement,network where measurement.measurementid = network.measurementid  group by connectiontype='Wifi',date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int)"

def signalstrength_count():
	return "select networktype,signalstrength,count(*) from network where  networktype!='UNKNOWN' and signalstrength!='' and networktype!='' and connectiontype!='' and connectiontype!='Wifi' group by networktype,signalstrength;"

def signalstrength_scatter_over_day():
	return "select networktype, date_part('hour', \"localtime\"),date_part('minute', \"localtime\"),cast(signalstrength as int)\
			from measurement,network \
			where measurement.measurementid = network.measurementid and signalstrength!=''and networktype!='' and networktype!='GPRS' and networktype!='EVDO_0' and networktype!='HSUPA' and networktype!='HSPA' and networktype!='UNKNOWN'  and networktype!='EVDO_A';"
########

def ping_over_time_finegrain():
	return "select dstip,networktype,date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int) \
	,avg(\"avg\"),avg(stdev) 	from measurement,ping,network where avg<1000 and dstip!='localhost' and networktype!='UNKNOWN' \
	and networktype!='' and networktype!='GPRS' and networktype!='HSPA' and connectiontype!='' and connectiontype!='Wifi' and \
	measurement.measurementid = network.measurementid and measurement.measurementid = ping.measurementid and avg>0	group by \
	networktype,dstip,date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int)	order by \
	date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int);"
	
def ping_over_time_finegrain_dst_nt(dstip, networktype):
	return "select dstip,networktype,date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int) \
	,avg(\"avg\"),avg(stdev) 	from measurement,ping,network where avg<1000 and avg>0 and  \
	dstip='" + str(dstip) + "' and networktype='" + str(networktype) + "' \
	and networktype!='HSPA' and connectiontype!='' and connectiontype!='Wifi' and \
	measurement.measurementid = network.measurementid and measurement.measurementid = ping.measurementid and avg>0	group by \
	networktype,dstip,date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int)	order by \
	date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int);"	

def lastmile_over_time_finegrain_dst_nt(dstip, networktype):
	return "select dstip,networktype,date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/30) as int) \
	,stddev(\"avg\"),count(*) 	from measurement,lastmile,network where avg<2000 and avg>10 and  \
	dstip='" + str(dstip) + "' and networktype='" + str(networktype) + "' \
	and connectiontype!='' and connectiontype!='Wifi' and \
	measurement.measurementid = network.measurementid and measurement.measurementid = lastmile.measurementid group by \
	networktype,dstip,date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/30) as int)	order by \
	date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/30) as int);"	

def ping_over_mobile_signal(dstip, networktype):
	return "select signalstrength,avg(\"avg\"),count(*) from measurement,network,lastmile \
		where avg<1000 and signalstrength!='' and \
		networktype='" + str(networktype) + "' and \
		dstip='" + str(dstip) + "' and \
		connectiontype!='' and connectiontype!='Wifi' and measurement.measurementid = network.measurementid \
		and measurement.measurementid = lastmile.measurementid and avg>10	\
		group by signalstrength	\
		order by cast(signalstrength as int)"
def ping_scatter_over_mobile_signal():
	"select dstip,networktype,signalstrength,\"avg\",stdev,count(*)	from measurement,network,ping	where avg<600 and dstip!='localhost' and networktype!='UNKNOWN' and signalstrength!='' and networktype!='' and networktype!='GPRS' and networktype!='HSPA' and connectiontype!='' and connectiontype!='Wifi' and measurement.measurementid = network.measurementid and measurement.measurementid = ping.measurementid and avg>0;"	
#########

def warmup_avg_over_sequence(networktype):
	return "select sequence_count,avg(\"value\") \
	from measurement,warmup_experiment,warmup_ping,network where networktype!='UNKNOWN' and\
	networktype='" + str(networktype) + "' and connectiontype!='' and connectiontype!='Wifi' and \
	highest-lowest>500 and \
	measurement.measurementid = network.measurementid and measurement.measurementid = warmup_experiment.measurementid \
	and measurement.measurementid = warmup_ping.measurementid and value>0 group by \
	sequence_count order by sequence_count;"
	
def warmup_avg_over_sequence_device(deviceid):
	return "select sequence_count,avg(\"value\") \
	from measurement,warmup_experiment,warmup_ping,network where networktype!='UNKNOWN' and\
	connectiontype!='' and connectiontype!='Wifi' and deviceid='" + str(deviceid) + "' and \
	measurement.measurementid = network.measurementid and measurement.measurementid = warmup_experiment.measurementid \
	and measurement.measurementid = warmup_ping.measurementid and value>0 group by \
	sequence_count order by sequence_count;"


#########

def throughput_over_time():
	return "select networktype,port,date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int),avg(speed),count(*) from throughput,measurement,network,link where networktype!='UNKNOWN' and networktype!='' and connectiontype!='Wifi' and (link.linkid = throughput.downlinkid or link.linkid = throughput.uplinkid) and throughput.measurementid = measurement.measurementid and network.measurementid = measurement.measurementid group by networktype,date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int),port order by networktype,port,date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int)";

#########

def wifi_strength_over_time():
	return "select date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int),avg(cast(signalstrength as int)),avg(rssi),count(*) from measurement,wifi where wifi.measurementid = measurement.measurementid and cast(ipaddress as int)>0 group by date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int) order by date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int)"
	
def wifi_favorite_count_over_time(hour_str, hour_end):
	return "select date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int),count(*) from measurement,wifi,device where wifi.measurementid = measurement.measurementid and cast(ipaddress as int)>0 and connection!='' and measurement.deviceid = device.deviceid and connection=(select connection from measurement,wifi where wifi.measurementid = measurement.measurementid and cast(ipaddress as int)>0 and connection!='' and measurement.deviceid = device.deviceid and date_part('hour', \"localtime\") > " + str(hour_str) + " and date_part('hour', \"localtime\") < " + str(hour_end) + " group by connection order by count(*) desc limit 1) group by date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int) order by date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int)"
	
##########

def battery_level_over_time():
	return "select date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int),avg(cast(level as float)/cast(scale as float)) from battery,measurement where battery.measurementid = measurement.measurementid and level>0 group by date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int) order by date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int)"

##########
def screen_open_over_time():
	return "select date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int),count(*) from screen group by date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int) order by date_part('hour', \"localtime\"),cast(floor(date_part('minute', \"localtime\")/10) as int);"
	

##########

def measurement_stats():
	return "select max(time),date_part('day', time),count(distinct(deviceid)),count(*) from measurement group by date_part('day', time)"
