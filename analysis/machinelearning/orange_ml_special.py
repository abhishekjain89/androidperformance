import orange, orngTree, orngTest, orngStat

data = orange.ExampleTable("learner_data.tab")

maj = orange.MajorityLearner()
maj.name = "default"
rt = orngTree.TreeLearner(measure="retis", mForPruning=2, minExamples=20)
rt.name = "regression tree"
k = 5
knn = orange.kNNLearner(k=k)
knn.name = "k-NN (k=%i)" % k
learners = [maj, rt, knn]

data = orange.ExampleTable("learner_data.tab")
results = orngTest.crossValidation(learners, data, folds=10)
mse = orngStat.MSE(results)

print "Learner        MSE"
for i in range(len(learners)):
  print "%-15s %5.3f" % (learners[i].name, mse[i])