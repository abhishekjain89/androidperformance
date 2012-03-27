select max(time),date_part('day', time),count(distinct(deviceid)),count(*),count(*)/count(distinct(deviceid))
from measurement,throughput where measurement.measurementid = throughput.measurementid
group by date_part('day', time);


select deviceid, sum(total_diff)/1024/1024,count(*),ismanual,count(distinct(date_part('day', time)))
from measurement,application_use where package='com.num' and measurement.measurementid = application_use.measurementid
group by deviceid,ismanual
order by sum(total_diff) desc;

