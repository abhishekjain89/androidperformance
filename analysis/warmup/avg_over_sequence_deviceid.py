import sys
sys.path.append('../')
import database, query, tuple_gen
import pprint
from queryconstructor import QueryConstructor
from plotconstructor import *



# EXAMPLE: warmup_avg_over_sequence  7a64da14bf60b976e7f3048400b0f4daeb0b2d33
deviceid=sys.argv[1]
filename_prefix = "warmup_over_sequence_"+deviceid
filename=filename_prefix

qc = QueryConstructor()
qc.setGroupOrderSelectBy("warmup_ping","sequence_count")
qc.addSelectMedian("warmup_ping","value")
qc.addSelectPercentile("warmup_ping","value",25)
qc.addSelectPercentile("warmup_ping","value",75)

qc.addSelectField("count(*)")
qc.applyMobileClauses()
qc.applyWarmupSequenceClauses()
qc.addWhereEqualsString("device","deviceid",deviceid)
#qc.addWhereGreaterThan("warmup_ping","sequence_count",10)

result = database.query(qc.toString())

pprint.pprint(result)

plot = SimpleBarPlot()
plot.addMapping(MappingWithBounds(result,0,1,2,3,"Device"))
plot.setYLabel("ms")
plot.setXLabel("raw signalstrength")
plot.setTitle("RTT over warmup sequence, for device: " + deviceid) 
plot.setLegend("ms")

plot.construct()

plot.save(filename)
#plot.show()