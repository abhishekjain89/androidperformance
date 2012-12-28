import sys
sys.path.append('../')
import database
import pprint
from queryconstructor import QueryConstructor
from plotconstructor import *

filename_prefix = "ping_over_carrier"
networkcountry="us"
 
filename=filename_prefix

qc = QueryConstructor()
qc.setGroupOrderSelectBy("device","networkname")
qc.addSelectMedian("ping","avg")
qc.addSelectPercentile("ping","avg",25)
qc.addSelectPercentile("ping","avg",75)
qc.addSelectField("count(*)")
qc.addWhereEqualsString("network","connectiontype","Mobile: 3G")
qc.addWhereNotEqualsString("device","networkname","sprint")
qc.addWhereNotEqualsString("device","networkname","cricket")
qc.addWhereNotEqualsString("device","networkname","US Cellular")
qc.addWhereNotEqualsString("device","networkname","T - Mobile")
qc.addWhereNotEqualsString("device","networkname","Simple Mobile")
qc.addWhereNotEqualsString("device","networkname","My Network")
qc.addWhereNotEqualsString("device","networkname","MetroPCS")
qc.addWhereNotEqualsString("device","networkname","HOME")
qc.addWhereNotEqualsString("device","networkname","")
qc.addWhereEqualsString("device","networkcountry",networkcountry)
qc.addWhereEqualsString("ping","dstip","www.google.com")
qc.applyMobileClauses()
qc.applyLatencyClauses("www.google.com")
#qc.addWhereEqualsString("device","networkname",'AT&T')

result = database.query(qc.toString())

pprint.pprint(result)

plot = SimpleBarPlot()
plot.addMapping(MappingWithBounds(result,0,1,2,3,"AT&T"))
plot.setYLabel("ms")
plot.setXLabel("Carrier")
plot.setTitle("3G RTT latency for different carriers in US") 
#plot.setLegend("ms")

plot.construct()

#plot.save(filename)
plot.show()
