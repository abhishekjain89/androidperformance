select cellid,dstip,count(*),avg("avg"),avg("stdev"),networktype

from network,measurement,ping
where network.measurementid = measurement.measurementid and ping.measurementid = measurement.measurementid and dstip='www.google.com' and connectiontype='Mobile: 3G' and "avg">2 and "avg"<1000
group by cellid,dstip,networktype
order by count(*)/10 desc
limit 30;