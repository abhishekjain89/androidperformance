import sys
sys.path.append('../')
import database
import warnings
warnings.simplefilter("ignore")
import math
from queryconstructor import QueryConstructor
from plotconstructor import *
import orange,orngTree
warnings.filterwarnings("ignore", "", orange.AttributeWarning)

learner_data = orange.ExampleTable("learner_data")
print "starting learning"
treeLearner = orange.TreeLearner(learner_data)

#orngTree.printTree(treeLearner)

majorityLearner = orange.MajorityLearner(learner_data)
print "done with learning"
eval_data = orange.ExampleTable("eval_data")
total=0
total_lame=0
count=0
found_total=0

print "starting evaluation"    

for i in eval_data:
    
    actual = i.getclass()
    total+=math.fabs(treeLearner(i) - actual)        
    total_lame+=math.fabs(majorityLearner(i) -actual)
    found_total+=actual
    count+=1
print "Learner rows: ",len(learner_data)
print "Evaluation rows: ",len(eval_data)    
print "Decision Tree Diff: ",total/count
print "Majority Diff:",total_lame/count
average=found_total/count
total_avg=0
for i in eval_data:    
    actual = i.getclass()
    total_avg+=math.fabs(average - actual)
    
print "Average Diff:",total_avg/count
print "ActualAverage:",found_total/count
    
#===============================================================================
# 
# correct = 0.0
# total = 0.0
# positive = 0.0
# negative = 0.0
# 
# for i in eval_data:
#    #p = tree(i, orange.GetProbabilities)
#    
#    if tree(i)==i.getclass():
#        correct+=1
#    
#    total+=1
#    if i.getclass()=='True':
#        positive+=1
#    else:
#        negative+=1
#    
# print "Accuracy:",correct/total
# print "Total:",total
# print "Positives:",positive/total
# print "Negatives:",negative/total
#===============================================================================
    


