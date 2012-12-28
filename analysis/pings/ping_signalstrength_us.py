import sys
sys.path.append('../')
import database
from queryconstructor import QueryConstructor
from plotconstructor import SimpleBarPlot

filename_prefix = "ping_signalstrength"
networktype=sys.argv[1]
networkname=sys.argv[2]
networkcountry="us"
 
filename=filename_prefix+"-"
filename+=networktype+"_"+networkcountry+"_"+networkname

qc = QueryConstructor()
qc.setGroupOrderSelectBy("network", "cast(signalstrength as int)")
qc.addSelectMedian("ping","avg")
qc.addWhereEqualsString("network","networktype",networktype)
qc.addWhereEqualsString("device","networkcountry",networkcountry)
qc.addWhereEqualsString("device","networkname",networkname)
qc.addSelectField("count(*)")
qc.applyMobileClauses()
qc.applyLatencyClauses("www.google.com")

result = database.query(qc.toString())

plot = SimpleBarPlot(result)
plot.setXticks(0)
plot.setYVals(1)
plot.setYLabel("ms")
plot.setTitle("RTT Latency over signalStrength for US devices only")
plot.setLegend("RTT to Google")

filename=filename_prefix+"-"
filename+=networktype+"_"+networkcountry+"_"+networkname
plot.construct()
plot.save(filename)
plot.show()
