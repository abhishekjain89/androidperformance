import sys
sys.path.append('../')
import database, query, tuple_gen
import pprint
from queryconstructor import QueryConstructor
from plotconstructor import *



# EXAMPLE: warmup_avg_over_sequence  GPRS        
networktype=sys.argv[1]
filename_prefix = "warmup_over_sequence_network_"+networktype
filename=filename_prefix

qc = QueryConstructor()
qc.setGroupOrderSelectBy("warmup_ping","sequence_count")
qc.addSelectMedian("warmup_ping","value")
qc.addSelectPercentile("warmup_ping","value",25)
qc.addSelectPercentile("warmup_ping","value",75)

qc.addSelectField("count(*)")
qc.applyMobileClauses()
qc.applyWarmupSequenceClauses()
qc.addWhereEqualsString("network","networktype",networktype)
qc.addWhereEqualsString("device","networkcountry","us")
qc.addWhereEqualsString("device","networkname","AT&T")
#qc.addWhereGreaterThan("warmup_ping","sequence_count",10)

result = database.query(qc.toString())

pprint.pprint(result)

plot = SimpleBarPlot()
plot.addMapping(MappingWithBounds(result,0,1,2,3,"networktype"))
plot.setYLabel("ms")
plot.setXLabel("raw signalstrength")
plot.setTitle("RTT over warmup sequence, for " + networktype) 
plot.setLegend("ms")

plot.construct()

plot.save(filename)
#plot.show()