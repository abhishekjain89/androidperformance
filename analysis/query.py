
def usage_over_hour_of_day():
	return "select date_part('hour', devicetime),(sum(total_till_now)-sum(mobile_till_now))/count(*),sum(mobile_till_now)/count(*) from usage,measurement where measurement.measurementid = usage.measurementid and deviceid!='7a64da14bf60b976e7f3048400b0f4daeb0b2d33' group by date_part('hour', devicetime)"

def app_by_total_data():
	return "select application.package,application.name,sum(total_diff) from application_use,application where application_use.package = application.package and total_diff > 0 and application.package!='com.num' and application.package!='com.ping' group by application.package order by sum(total_diff) desc limit 20"