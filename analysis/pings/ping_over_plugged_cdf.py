import sys
sys.path.append('../')
import database
from queryconstructor import QueryConstructor
from plotconstructor import *

filename_prefix = "ping_over_plugged_att_cdf"
networkcountry="us"
 
filename=filename_prefix
plot = LinePlot()


qc = QueryConstructor()
qc.setGroupOrderSelectBy("ping", "5*cast((avg/5) as int)")
qc.addSelectField("count(*)")
qc.addWhereLessThan("ping","avg",1000)
qc.addWhereEqualsString("network","connectiontype","Mobile: 3G")
qc.addWhereEqualsString("device","networkcountry",networkcountry)
qc.addWhereEqualsString("ping","dstip","www.google.com")
qc.addWhereEquals("battery","plugged",0)
qc.applyMobileClauses()
qc.applyLatencyClauses("www.google.com")
qc.addWhereEqualsString("device","networkname",'AT&T')

result = database.query(qc.toString())

plot.addMapping(Mapping(result,0,1,"notPlugged"))

qc = QueryConstructor()
qc.setGroupOrderSelectBy("ping", "5*cast((avg/5) as int)")
qc.addSelectField("count(*)")
qc.addWhereLessThan("ping","avg",1000)
qc.addWhereEqualsString("network","connectiontype","Mobile: 3G")
qc.addWhereEqualsString("device","networkcountry",networkcountry)
qc.addWhereEqualsString("ping","dstip","www.google.com")
qc.addWhereEquals("battery","plugged",1)
qc.applyMobileClauses()
qc.applyLatencyClauses("www.google.com")
qc.addWhereEqualsString("device","networkname",'AT&T')

result = database.query(qc.toString())

plot.addMapping(Mapping(result,0,1,"Plugged"))

plot.setYLabel("F(x)")
plot.setXLabel("ms")
plot.setTitle("Roundtrip latency CDF for device plugged state") 
plot.transformToCDF()
plot.construct()
plot.save(filename)
plot.show()
