import sys
sys.path.append('../')
import database
import pprint
from queryconstructor import QueryConstructor
from plotconstructor import LinePlot,Mapping

qc=QueryConstructor()
qc.addSelect("ping","avg")
qc.addWhereRaw("cast(signalstrength as int)>0")
qc.addWhereNotEqualsString("device","networkname","")
qc.applyMobileClauses()
qc.applyLatencyClauses("www.google.com")

result = database.query(qc.toString())
#pprint.pprint(result)
total=0
count=0

for row in result:
    ratio = 332.0/float(str(row[0]))
    
    if ratio>1:
        ratio=1/ratio
        
    accuracy=ratio
    total+=accuracy
    count+=1
    
print total/count
