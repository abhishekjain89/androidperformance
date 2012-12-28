import sys
sys.path.append('../')
import database
from queryconstructor import QueryConstructor
from plotconstructor import *
level = 95
filename_prefix = "ping_over_battery_att_cdf_"+str(level)
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
qc.addWhereLessThan("battery","level",level)
qc.addWhereEquals("battery","plugged",0)
qc.applyMobileClauses()
qc.applyLatencyClauses("www.google.com")
qc.addWhereEqualsString("device","networkname",'AT&T')

result = database.query(qc.toString())

plot.addMapping(Mapping(result,0,1,"lessThan"+str(level)))

qc = QueryConstructor()
qc.setGroupOrderSelectBy("ping", "5*cast((avg/5) as int)")
qc.addSelectField("count(*)")
qc.addWhereLessThan("ping","avg",1000)
qc.addWhereEqualsString("network","connectiontype","Mobile: 3G")
qc.addWhereEqualsString("device","networkcountry",networkcountry)
qc.addWhereEqualsString("ping","dstip","www.google.com")
qc.addWhereGreaterThan("battery","level",level)
qc.addWhereEquals("battery","plugged",0)
qc.applyMobileClauses()
qc.applyLatencyClauses("www.google.com")
qc.addWhereEqualsString("device","networkname",'AT&T')

result = database.query(qc.toString())

plot.addMapping(Mapping(result,0,1,"moreThan"+str(level)))

plot.setYLabel("F(x)")
plot.setXLabel("ms")
plot.setTitle("Roundtrip latency CDF for different battery levels") 
plot.transformToCDF()
plot.construct()
#plot.save(filename)
plot.show()
