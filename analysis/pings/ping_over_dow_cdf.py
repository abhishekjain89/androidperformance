import sys
sys.path.append('../')
import database
import pprint
from queryconstructor import QueryConstructor
from plotconstructor import *

filename_prefix = "ping_over_dow_att_cdf"
networkcountry="us"
networkname="AT&T"
 
filename=filename_prefix
plot = LinePlot()


qc = QueryConstructor()
qc.setGroupOrderSelectBy("ping", "5*cast((avg/5) as int)")
qc.addSelectField("count(*)")
qc.addWhereLessThan("ping","avg",500)
qc.addWhereEqualsString("network","connectiontype","Mobile: 3G")
qc.addWhereEqualsString("device","networkcountry",networkcountry)
qc.addWhereEqualsString("ping","dstip","www.google.com")
qc.addWhereDayOfWeekMoreThan("measurement","\"localtime\"",0)
qc.addWhereDayOfWeekLessThan("measurement","\"localtime\"",6)
qc.applyMobileClauses()
qc.applyLatencyClauses("www.google.com")
qc.addWhereEqualsString("device","networkname",networkname)

result = database.query(qc.toString())

plot.addMapping(Mapping(result,0,1,"weekday"))

pprint.pprint(result)

qc = QueryConstructor()
qc.setGroupOrderSelectBy("ping", "5*cast((avg/5) as int)")
qc.addSelectField("count(*)")
qc.addWhereLessThan("ping","avg",500)
qc.addWhereEqualsString("network","connectiontype","Mobile: 3G")
qc.addWhereEqualsString("device","networkcountry",networkcountry)
qc.addWhereEqualsString("ping","dstip","www.google.com")
qc.addWhereDayOfWeekEquals("measurement","\"localtime\"",0)
qc.applyMobileClauses()
qc.applyLatencyClauses("www.google.com")
qc.addWhereEqualsString("device","networkname",networkname)

result = database.query(qc.toString())

plot.addMapping(Mapping(result,0,1,"Sunday"))

qc = QueryConstructor()
qc.setGroupOrderSelectBy("ping", "5*cast((avg/5) as int)")
qc.addSelectField("count(*)")
qc.addWhereLessThan("ping","avg",500)
qc.addWhereEqualsString("network","connectiontype","Mobile: 3G")
qc.addWhereEqualsString("device","networkcountry",networkcountry)
qc.addWhereEqualsString("ping","dstip","www.google.com")
qc.addWhereDayOfWeekEquals("measurement","\"localtime\"",6)
qc.applyMobileClauses()
qc.applyLatencyClauses("www.google.com")
qc.addWhereEqualsString("device","networkname",networkname)

result = database.query(qc.toString())

plot.addMapping(Mapping(result,0,1,"Saturday"))

plot.setYLabel("F(x)")
plot.setXLabel("ms")
plot.setTitle("Roundtrip latency CDF for weekend/weekday") 
plot.transformToCDF()
plot.construct()
plot.save(filename)
plot.show()
