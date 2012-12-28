import sys
sys.path.append('../')
import database
import pprint
from queryconstructor import QueryConstructor
from plotconstructor import *

networkcountry="us"
networktype=sys.argv[1]
filename_prefix = "ping_over_signal_att_"+networktype


 
filename=filename_prefix

qc = QueryConstructor()
qc.setGroupOrderSelectBy("network","cast(signalstrength as int)")
qc.addSelectMedian("ping","avg")
qc.addSelectPercentile("ping","avg",25)
qc.addSelectPercentile("ping","avg",75)
qc.addSelectField("count(*)")
#qc.addWhereEqualsString("network","connectiontype","Mobile: 3G")
qc.addWhereEqualsString("network","networktype",networktype)
#qc.addWhereEqualsString("network","cellid","62095")
qc.addWhereEqualsString("device","networkcountry",networkcountry)
qc.addWhereEqualsString("ping","dstip","www.google.com")
qc.applyMobileClauses()
qc.applyLatencyClauses("www.google.com")
qc.addWhereEqualsString("device","networkname",'AT&T')


result = database.query(qc.toString())

pprint.pprint(result)

plot = SimpleBarPlot()
plot.addMapping(MappingWithBounds(result,0,1,2,3,"AT&T"))
plot.setYLabel("ms")
plot.setXLabel("raw signalstrength")
plot.setTitle("RTT latency over signal, for AT&T users in US ("+str(networktype)+")") 
plot.setLegend("ms")

plot.construct()

plot.save(filename)
#plot.show()
