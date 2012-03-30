select min(time),deviceid from measurement group by deviceid order by min(time);
psql androidperformance < summary.txt

