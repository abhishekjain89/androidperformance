import sys
sys.path.append('../')
import database
from queryconstructor import QueryConstructor
from plotconstructor import *

filename_prefix = "ping_over_battery_att"
networkcountry="us"
 
filename=filename_prefix

qc = QueryConstructor()
qc.setGroupOrderSelectBy("battery","5*(level/5)")
qc.addSelectMedian("ping","avg")
qc.addSelectPercentile("ping","avg",25)
qc.addSelectPercentile("ping","avg",75)
qc.addSelectField("count(*)")
qc.addWhereEqualsString("network","connectiontype","Mobile: 3G")
qc.addWhereEqualsString("device","networkcountry",networkcountry)
qc.addWhereEqualsString("ping","dstip","www.google.com")
#qc.addWhereEquals("battery","plugged",0)
qc.applyMobileClauses()
qc.applyLatencyClauses("www.google.com")
qc.addWhereEqualsString("device","networkname",'AT&T')

result = database.query(qc.toString())

plot = SimpleBarPlot()
plot.addMapping(MappingWithBounds(result,0,1,2,3,"AT&T"))
plot.setYLabel("ms")
plot.setXLabel("battery (in %)")
plot.setTitle("Roundtrip latency over Battery level, for AT&T users in US") 
plot.setLegend("ms")

plot.construct()

plot.save(filename)
plot.show()
