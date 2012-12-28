import sys
sys.path.append('../')
import database
import pprint
from queryconstructor import QueryConstructor
from orangeconstructor import OrangeConstructor,Column
from plotconstructor import LinePlot,Mapping
def constructQuery(qc):
    qc.addSelect("device","networkcountry")
    #qc.addSelect("battery","level")
    #qc.addSelect("battery","plugged")
    #qc.addSelect("battery","50*(temperature/50)")
    qc.addSelect("network","connectiontype")
    #qc.addSelect("network","networktype")
    qc.addSelect("device","networkname")
    qc.addSelect("device","phonetype")
    qc.addSelect("device","phonemodel")
    qc.addSelect("device","devicedesign")
    #qc.addSelect("network","cellid")
    qc.addSelect("device","androidversion")
    qc.addSelect("network","signalstrength")
    qc.addSelect("network","datastate")
    qc.safeAddTable("warmup_experiment")
    
    #qc.addSelect("measurement","date_part('hour',\"localtime\")/4")
    qc.addSelectField("highest>1000")
    qc.addWhereNotEqualsString("device","networkname","")
    #qc.addWhereEqualsString("device","networkcountry","us")
    qc.applyMobileClauses()
    #qc.addWhereEqualsString("ping","dstip","www.google.com")
    
qc=QueryConstructor()
constructQuery(qc)
qc.addWhereRaw("measurement.time<'2012-12-08 00:00:00'")

learner_result = database.query(qc.toString())

qc=QueryConstructor()
constructQuery(qc)
qc.addWhereRaw("measurement.time>'2012-12-08 00:00:00'")

eval_result = database.query(qc.toString())


orange = OrangeConstructor()
i=0
orange.add(Column("country",i,"discrete",False))
i+=1
#orange.add(Column("battery",i,"continuous",False))
#i+=1
#orange.add(Column("plugged",i,"discrete",False))
#i+=1
#orange.add(Column("temperature",i,"continuous",False))
#i+=1
orange.add(Column("connectiontype",i,"discrete",False))
i+=1
#orange.add(Column("networktype",i,"discrete",False))
#i+=1
orange.add(Column("networkname",i,"discrete",False))
i+=1

orange.add(Column("phonetype",i,"discrete",False))
i+=1
orange.add(Column("phonemodel",i,"discrete",False))
i+=1
orange.add(Column("devicedesign",i,"discrete",False))
i+=1
#orange.add(Column("cellid",i,"discrete",False))
#i+=1
orange.add(Column("androidversion",i,"discrete",False))
i+=1
orange.add(Column("signal",i,"continuous",False))
i+=1
orange.add(Column("datastate",i,"discrete",False))
i+=1
#orange.add(Column("hour",i,"continuous",False))
#i+=1
orange.add(Column("ping",i,"discrete",True))
i+=1

orange.saveToFile(learner_result,"learner_data.tab")
orange.saveToFile(eval_result,"eval_data.tab")

print "data creation done"