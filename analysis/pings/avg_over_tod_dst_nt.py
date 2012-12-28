import sys
sys.path.append('../')
import matplotlib.pyplot as plt
import database, query, tuple_gen
import pprint
import numpy as np
import matplotlib.pyplot as plt
from queryconstructor import QueryConstructor
from plotconstructor import *


# EXAMPLE: python avg_over_tod_dst_nt.py www.google.com EVDO_A
dstip = sys.argv[1]
networktype = sys.argv[2]
filename = "ping_over_tod_" + networktype

plot = LinePlot()
times=[{'s':0,'e':6},{'s':6,'e':12},{'s':12,'e':18},{'s':18,'e':24}]

for time in times:
    qc=QueryConstructor()
    qc.setGroupOrderSelectBy("ping", "5*cast((avg/5) as int)")
    qc.addSelectField("count(*)")
    qc.addWhereLessThan("ping","avg",2000)
    qc.applyMobileClauses()
    qc.applyLatencyClauses(dstip)
    qc.addWhereEqualsString("network","networktype",networktype)
    qc.safeAddTable("measurement")
    qc.addWhereRaw("date_part('hour',\"localtime\")<"+str(time['e']))
    qc.addWhereRaw("date_part('hour',\"localtime\")>"+str(time['s']))
    result = database.query(qc.toString())
    plot.addMapping(Mapping(result,0,1,str(time['s'])+"to"+str(time['e'])))
    
plot.setYLabel("F(x)")
plot.setXLabel("ms")
plot.setTitle("Roundtrip latency CDF Graph, for "+ networktype +" network in US, for different time of day") 
plot.setLegend("count")

plot.transformToCDF()
plot.construct()
plot.save(filename)
plot.show()