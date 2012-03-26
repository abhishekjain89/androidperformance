select date_part('hour', devicetime),cast(floor(date_part('minute', devicetime)/10) as int),avg(cast(level as float)/cast(scale as float))
from battery,measurement
where battery.measurementid = measurement.measurementid and level>0
group by date_part('hour', devicetime),cast(floor(date_part('minute', devicetime)/10) as int)
order by date_part('hour', devicetime),cast(floor(date_part('minute', devicetime)/10) as int);
