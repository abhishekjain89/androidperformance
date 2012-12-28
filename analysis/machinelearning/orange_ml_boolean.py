import sys
sys.path.append('../')
import database
import warnings
warnings.simplefilter("ignore")
import math
from queryconstructor import QueryConstructor
from plotconstructor import *
import orange
warnings.filterwarnings("ignore", "", orange.AttributeWarning)

learner_data = orange.ExampleTable("learner_data")
print "starting learning"
treeLearner = orange.TreeLearner(learner_data)
majorityLearner = orange.MajorityLearner(learner_data)
print "done with learning"
eval_data = orange.ExampleTable("eval_data")
total=0.0
total_lame=0.0
total_true=0.0
count=0.0

print "starting evaluation"    

for i in eval_data:
    
    actual = i.getclass()
    if (treeLearner(i)==actual):
        total+=1
    if (majorityLearner(i)==actual):
        total_lame+=1
    if actual==True:
        total_true+=1
    
    count+=1
print "Learner rows: ",len(learner_data)
print "Evaluation rows: ",len(eval_data)    
print "Decision Tree Diff: ",total/count
print "Majority Diff:",total_lame/count
print "True Diff:",total_true/count

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
    


