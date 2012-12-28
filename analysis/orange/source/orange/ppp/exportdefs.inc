
/* from examplegen.hpp */
class EXPORT_DLL TExampleGenerator;

/* from random.hpp */
class EXPORT_DLL TRandomGenerator;

/* from minimal_error.hpp */
class EXPORT_DLL TColumnAssessor;
class EXPORT_DLL TColumnAssessor_m;
class EXPORT_DLL TColumnAssessor_Laplace;
class EXPORT_DLL TColumnAssessor_N;
class EXPORT_DLL TColumnAssessor_Relief;
class EXPORT_DLL TColumnAssessor_Kramer;
class EXPORT_DLL TColumnAssessor_Measure;
class EXPORT_DLL TColumnAssessor_mf;
class EXPORT_DLL TStopIMClusteringByAssessor;
class EXPORT_DLL TStopIMClusteringByAssessor_noProfit;
class EXPORT_DLL TStopIMClusteringByAssessor_noBigChange;
class EXPORT_DLL TStopIMClusteringByAssessor_binary;
class EXPORT_DLL TStopIMClusteringByAssessor_n;
class EXPORT_DLL TAssessIMQuality;
class EXPORT_DLL TIMClustering;
class EXPORT_DLL TClustersFromIM;
class EXPORT_DLL TClustersFromIMByAssessor;
class EXPORT_DLL TFeatureByIM;
class EXPORT_DLL TMeasureAttribute_IM;

/* from filter.hpp */
class EXPORT_DLL TFilter;
class EXPORT_DLL TFilter_random;
class EXPORT_DLL TFilter_hasSpecial;
class EXPORT_DLL TFilter_isDefined;
class EXPORT_DLL TFilter_hasMeta;
class EXPORT_DLL TFilter_hasClassValue;
class EXPORT_DLL TFilter_sameValue;
class EXPORT_DLL TValueFilter;
class EXPORT_DLL TValueFilter_continuous;
class EXPORT_DLL TValueFilter_discrete;
class EXPORT_DLL TValueFilter_string;
class EXPORT_DLL TValueFilter_stringList;
class EXPORT_DLL TFilter_values;
class EXPORT_DLL TFilter_sameExample;
class EXPORT_DLL TFilter_compatibleExample;
class EXPORT_DLL TFilter_conjunction;
class EXPORT_DLL TFilter_disjunction;

/* from exampleclustering.hpp */
class EXPORT_DLL TExampleCluster;
class EXPORT_DLL TGeneralExampleClustering;
class EXPORT_DLL TExampleClusters;

/* from pnn.hpp */
class EXPORT_DLL TPNN;
class EXPORT_DLL TP2NN;

/* from multi_learner.hpp */
class EXPORT_DLL TMultiLearner;

/* from bayes.hpp */
class EXPORT_DLL TBayesLearner;
class EXPORT_DLL TBayesClassifier;

/* from clas_gen.hpp */
class EXPORT_DLL TClassifierGenerator;
class EXPORT_DLL TClassifierRandomGenerator;

/* from tabdelim.hpp */
class EXPORT_DLL TTabDelimExampleGenerator;

/* from symmatrix.hpp */
class EXPORT_DLL TSymMatrix;

/* from tdidt_simple.hpp */
class EXPORT_DLL TSimpleTreeLearner;
class EXPORT_DLL TSimpleTreeClassifier;

/* from classfromvar.hpp */
class EXPORT_DLL TClassifierFromVar;
class EXPORT_DLL TClassifierFromVarFD;

/* from cost.hpp */
class EXPORT_DLL TCostMatrix;

/* from logistic.hpp */
class EXPORT_DLL TLogRegLearner;
class EXPORT_DLL TLogRegClassifier;

/* from subsets.hpp */
class EXPORT_DLL TSubsetsGenerator;
class EXPORT_DLL TSubsetsGenerator_iterator;
class EXPORT_DLL TSubsetsGenerator_constSize;
class EXPORT_DLL TSubsetsGenerator_constSize_iterator;
class EXPORT_DLL TSubsetsGenerator_minMaxSize;
class EXPORT_DLL TSubsetsGenerator_minMaxSize_iterator;
class EXPORT_DLL TSubsetsGenerator_constant;
class EXPORT_DLL TSubsetsGenerator_constant_iterator;
class EXPORT_DLL TSubsetsGenerator_withRestrictions;
class EXPORT_DLL TSubsetsGenerator_withRestrictions_iterator;

/* from basket.hpp */
class EXPORT_DLL TBasketFeeder;
class EXPORT_DLL TBasketExampleGenerator;

/* from basstat.hpp */
class EXPORT_DLL TBasicAttrStat;
class EXPORT_DLL TDomainBasicAttrStat;
class EXPORT_DLL TPearsonCorrelation;

/* from estimateprob.hpp */
class EXPORT_DLL TProbabilityEstimator;
class EXPORT_DLL TProbabilityEstimatorConstructor;
class EXPORT_DLL TConditionalProbabilityEstimator;
class EXPORT_DLL TConditionalProbabilityEstimatorConstructor;
class EXPORT_DLL TProbabilityEstimator_FromDistribution;
class EXPORT_DLL TProbabilityEstimatorConstructor_relative;
class EXPORT_DLL TProbabilityEstimatorConstructor_Laplace;
class EXPORT_DLL TProbabilityEstimatorConstructor_m;
class EXPORT_DLL TProbabilityEstimatorConstructor_kernel;
class EXPORT_DLL TProbabilityEstimatorConstructor_loess;
class EXPORT_DLL TConditionalProbabilityEstimator_FromDistribution;
class EXPORT_DLL TConditionalProbabilityEstimator_ByRows;
class EXPORT_DLL TConditionalProbabilityEstimatorConstructor_ByRows;
class EXPORT_DLL TConditionalProbabilityEstimatorConstructor_loess;

/* from distance.hpp */
class EXPORT_DLL TExamplesDistance;
class EXPORT_DLL TExamplesDistanceConstructor;
class EXPORT_DLL TExamplesDistance_Hamming;
class EXPORT_DLL TExamplesDistanceConstructor_Hamming;
class EXPORT_DLL TExamplesDistance_Normalized;
class EXPORT_DLL TExamplesDistanceConstructor_Normalized;
class EXPORT_DLL TExamplesDistance_Maximal;
class EXPORT_DLL TExamplesDistanceConstructor_Maximal;
class EXPORT_DLL TExamplesDistance_Manhattan;
class EXPORT_DLL TExamplesDistanceConstructor_Manhattan;
class EXPORT_DLL TExamplesDistance_Euclidean;
class EXPORT_DLL TExamplesDistanceConstructor_Euclidean;
class EXPORT_DLL TExamplesDistance_Lp;
class EXPORT_DLL TExamplesDistanceConstructor_Lp;
class EXPORT_DLL TExamplesDistance_Relief;
class EXPORT_DLL TExamplesDistanceConstructor_Relief;

/* from distance_dtw.hpp */
class EXPORT_DLL TExamplesDistance_DTW;
class EXPORT_DLL TExamplesDistanceConstructor_DTW;

/* from hclust.hpp */
class EXPORT_DLL THierarchicalCluster;
class EXPORT_DLL THierarchicalClustering;
class EXPORT_DLL THierarchicalClusterOrdering;

/* from learn.hpp */
class EXPORT_DLL TLearner;
class EXPORT_DLL TLearnerFD;

/* from values.hpp */
class EXPORT_DLL TSomeValue;

/* from logfit.hpp */
class EXPORT_DLL TLogRegFitter;
class EXPORT_DLL TLogRegFitter_Cholesky;

/* from spec_contingency.hpp */
class EXPORT_DLL TComputeDomainContingency_DomainTransformation;
class EXPORT_DLL TComputeDomainContingency_ImputeWithClassifier;
class EXPORT_DLL TComputeDomainContingency_Preprocessor;

/* from filegen.hpp */
class EXPORT_DLL TFileExampleGenerator;

/* from decomposition.hpp */
class EXPORT_DLL TIM;
class EXPORT_DLL TIMConstructor;
class EXPORT_DLL TIMBySorting;
class EXPORT_DLL TIMFromExamples;
class EXPORT_DLL TPreprocessIM;
class EXPORT_DLL TIMByRows;
class EXPORT_DLL TIMByRowsConstructor;
class EXPORT_DLL TIMByRowsBySorting;
class EXPORT_DLL TIMByRowsByRelief;
class EXPORT_DLL TIMByIMByRows;
class EXPORT_DLL TIMByRelief;
class EXPORT_DLL TIMByRowsPreprocessor;
class EXPORT_DLL TIMBlurer;

/* from induce.hpp */
class EXPORT_DLL TFeatureInducer;

/* from majority.hpp */
class EXPORT_DLL TMajorityLearner;
class EXPORT_DLL TCostLearner;
class EXPORT_DLL TRandomLearner;

/* from examples.hpp */
class EXPORT_DLL TExample;

/* from discretize.hpp */
class EXPORT_DLL TDiscretization;
class EXPORT_DLL TDiscretizer;
class EXPORT_DLL TDomainDiscretization;
class EXPORT_DLL TEquiDistDiscretizer;
class EXPORT_DLL TThresholdDiscretizer;
class EXPORT_DLL TIntervalDiscretizer;
class EXPORT_DLL TBiModalDiscretizer;
class EXPORT_DLL TEquiDistDiscretization;
class EXPORT_DLL TFixedDiscretization;
class EXPORT_DLL TEquiNDiscretization;
class EXPORT_DLL TEntropyDiscretization;
class EXPORT_DLL TBiModalDiscretization;

/* from stringvars.hpp */
class EXPORT_DLL TStringValue;
class EXPORT_DLL TStringVariable;

/* from minimal_complexity.hpp */
class EXPORT_DLL TIG;
class EXPORT_DLL TIGConstructor;
class EXPORT_DLL TIGByIM;
class EXPORT_DLL TIGBySorting;
class EXPORT_DLL TColoredIG;
class EXPORT_DLL TColorIG;
class EXPORT_DLL TColorIG_MCF;
class EXPORT_DLL TFeatureByMinComplexity;

/* from preprocessors.hpp */
class EXPORT_DLL TPreprocessor;
class EXPORT_DLL TPreprocessor_ignore;
class EXPORT_DLL TPreprocessor_select;
class EXPORT_DLL TPreprocessor_drop;
class EXPORT_DLL TPreprocessor_take;
class EXPORT_DLL TPreprocessor_removeDuplicates;
class EXPORT_DLL TPreprocessor_dropMissing;
class EXPORT_DLL TPreprocessor_takeMissing;
class EXPORT_DLL TPreprocessor_dropMissingClasses;
class EXPORT_DLL TPreprocessor_takeMissingClasses;
class EXPORT_DLL TPreprocessor_shuffle;
class EXPORT_DLL TPreprocessor_addNoise;
class EXPORT_DLL TPreprocessor_addGaussianNoise;
class EXPORT_DLL TPreprocessor_addMissing;
class EXPORT_DLL TPreprocessor_addMissingClasses;
class EXPORT_DLL TPreprocessor_addClassNoise;
class EXPORT_DLL TPreprocessor_addGaussianClassNoise;
class EXPORT_DLL TPreprocessor_addClassWeight;
class EXPORT_DLL TPreprocessor_addCensorWeight;
class EXPORT_DLL TPreprocessor_discretize;
class EXPORT_DLL TImputeClassifier;
class EXPORT_DLL TPreprocessor_imputeByLearner;
class EXPORT_DLL TPreprocessor_filter;
class EXPORT_DLL TTableAverager;

/* from distvars.hpp */
class EXPORT_DLL TDistribution;
class EXPORT_DLL TDiscDistribution;
class EXPORT_DLL TContDistribution;
class EXPORT_DLL TGaussianDistribution;
class EXPORT_DLL TDomainDistributions;

/* from multi_classifier.hpp */
class EXPORT_DLL TMultiClassifier;

/* from classify.hpp */
class EXPORT_DLL TClassifier;
class EXPORT_DLL TClassifierFD;
class EXPORT_DLL TDefaultClassifier;
class EXPORT_DLL TRandomClassifier;
class EXPORT_DLL TEFMDataDescription;
class EXPORT_DLL TExampleForMissing;

/* from tdidt_stop.hpp */
class EXPORT_DLL TTreeStopCriteria;
class EXPORT_DLL TTreeStopCriteria_common;

/* from trindex.hpp */
class EXPORT_DLL TMakeRandomIndices;
class EXPORT_DLL TMakeRandomIndices2;
class EXPORT_DLL TMakeRandomIndicesN;
class EXPORT_DLL TMakeRandomIndicesCV;
class EXPORT_DLL TMakeRandomIndicesMultiple;

/* from liblinear_interface.hpp */
class EXPORT_DLL TLinearLearner;
class EXPORT_DLL TLinearClassifier;

/* from measures.hpp */
class EXPORT_DLL TMeasureAttribute;
class EXPORT_DLL TMeasureAttributeFromProbabilities;
class EXPORT_DLL TMeasureAttribute_info;
class EXPORT_DLL TMeasureAttribute_gainRatio;
class EXPORT_DLL TMeasureAttribute_gainRatioA;
class EXPORT_DLL TMeasureAttribute_gini;
class EXPORT_DLL TMeasureAttribute_logOddsRatio;
class EXPORT_DLL TMeasureAttribute_relevance;
class EXPORT_DLL TMeasureAttribute_chiSquare;
class EXPORT_DLL TMeasureAttribute_cost;
class EXPORT_DLL TMeasureAttribute_MSE;

/* from transdomain.hpp */
class EXPORT_DLL TDomainTransformerConstructor;

/* from spec_gen.hpp */
class EXPORT_DLL TAdapterGenerator;
class EXPORT_DLL TFilteredGenerator;
class EXPORT_DLL TChangeExampleGenerator;
class EXPORT_DLL TMissValuesGenerator;
class EXPORT_DLL TNoiseValuesGenerator;
class EXPORT_DLL TGaussianNoiseGenerator;

/* from root.hpp */
class EXPORT_DLL TOrange;

/* from tdidt.hpp */
class EXPORT_DLL TTreeNode;
class EXPORT_DLL TTreeLearner;
class EXPORT_DLL TTreeDescender;
class EXPORT_DLL TTreeDescender_UnknownToNode;
class EXPORT_DLL TTreeDescender_UnknownToBranch;
class EXPORT_DLL TTreeDescender_UnknownToCommonBranch;
class EXPORT_DLL TTreeDescender_UnknownToCommonSelector;
class EXPORT_DLL TTreeDescender_UnknownMergeAsBranchSizes;
class EXPORT_DLL TTreeDescender_UnknownMergeAsSelector;
class EXPORT_DLL TTreeClassifier;
class EXPORT_DLL TTreePruner;
class EXPORT_DLL TTreePruner_SameMajority;
class EXPORT_DLL TTreePruner_m;

/* from distancemap.hpp */
class EXPORT_DLL TDistanceMap;
class EXPORT_DLL TDistanceMapConstructor;

/* from vars.hpp */
class EXPORT_DLL TVariable;
class EXPORT_DLL TEnumVariable;
class EXPORT_DLL TFloatVariable;
class EXPORT_DLL TValueList;

/* from tdidt_split.hpp */
class EXPORT_DLL TTreeSplitConstructor;
class EXPORT_DLL TTreeSplitConstructor_Measure;
class EXPORT_DLL TTreeSplitConstructor_Combined;
class EXPORT_DLL TTreeSplitConstructor_Attribute;
class EXPORT_DLL TTreeSplitConstructor_ExhaustiveBinary;
class EXPORT_DLL TTreeSplitConstructor_OneAgainstOthers;
class EXPORT_DLL TTreeSplitConstructor_Threshold;
class EXPORT_DLL TTreeExampleSplitter;
class EXPORT_DLL TTreeExampleSplitter_IgnoreUnknowns;
class EXPORT_DLL TTreeExampleSplitter_UnknownsToCommon;
class EXPORT_DLL TTreeExampleSplitter_UnknownsToAll;
class EXPORT_DLL TTreeExampleSplitter_UnknownsToRandom;
class EXPORT_DLL TTreeExampleSplitter_UnknownsToBranch;
class EXPORT_DLL TTreeExampleSplitter_UnknownsAsBranchSizes;
class EXPORT_DLL TTreeExampleSplitter_UnknownsAsSelector;

/* from transval.hpp */
class EXPORT_DLL TTransformValue;
class EXPORT_DLL TMapIntValue;
class EXPORT_DLL TDiscrete2Continuous;
class EXPORT_DLL TOrdinal2Continuous;
class EXPORT_DLL TNormalizeContinuous;
class EXPORT_DLL TDomainContinuizer;

/* from calibrate.hpp */
class EXPORT_DLL TThresholdCA;

/* from cartesian.hpp */
class EXPORT_DLL TCartesianClassifier;

/* from nearest.hpp */
class EXPORT_DLL TFindNearest;
class EXPORT_DLL TFindNearest_BruteForce;
class EXPORT_DLL TFindNearestConstructor;
class EXPORT_DLL TFindNearestConstructor_BruteForce;

/* from costwrapper.hpp */
class EXPORT_DLL TCostWrapperLearner;
class EXPORT_DLL TCostWrapperClassifier;

/* from redundancy.hpp */
class EXPORT_DLL TRemoveRedundant;
class EXPORT_DLL TRemoveRedundantByInduction;
class EXPORT_DLL TRemoveRedundantByQuality;
class EXPORT_DLL TRemoveRedundantOneValue;
class EXPORT_DLL TRemoveUnusedValues;

/* from dist_clustering.hpp */
class EXPORT_DLL TDistributionAssessor;
class EXPORT_DLL TStopDistributionClustering;
class EXPORT_DLL TExampleDistVector;
class EXPORT_DLL TExampleDistConstructor;
class EXPORT_DLL TExampleDistBySorting;
class EXPORT_DLL TClustersFromDistributions;
class EXPORT_DLL TClustersFromDistributionsByAssessor;
class EXPORT_DLL TFeatureByDistributions;
class EXPORT_DLL TDistributionAssessor_m;
class EXPORT_DLL TDistributionAssessor_Laplace;
class EXPORT_DLL TDistributionAssessor_Relief;
class EXPORT_DLL TDistributionAssessor_Kramer;
class EXPORT_DLL TDistributionAssessor_Measure;
class EXPORT_DLL TDistributionAssessor_mf;
class EXPORT_DLL TStopDistributionClustering_noProfit;
class EXPORT_DLL TStopDistributionClustering_noBigChange;
class EXPORT_DLL TStopDistributionClustering_binary;
class EXPORT_DLL TStopDistributionClustering_n;

/* from assoc.hpp */
class EXPORT_DLL TAssociationRule;
class EXPORT_DLL TAssociationRulesInducer;
class EXPORT_DLL TAssociationRulesSparseInducer;
class EXPORT_DLL TItemsetsSparseInducer;
class EXPORT_DLL TAssociationLearner;
class EXPORT_DLL TAssociationClassifier;

/* from earth.hpp */
class EXPORT_DLL TEarthLearner;
class EXPORT_DLL TEarthClassifier;

/* from pythonvars.hpp */
class EXPORT_DLL TPythonValue;
class EXPORT_DLL TPythonVariable;
class EXPORT_DLL TPythonValueSpecial;

/* from imputation.hpp */
class EXPORT_DLL TTransformValue_IsDefined;
class EXPORT_DLL TImputer;
class EXPORT_DLL TImputer_defaults;
class EXPORT_DLL TImputer_asValue;
class EXPORT_DLL TImputer_model;
class EXPORT_DLL TImputer_random;
class EXPORT_DLL TImputerConstructor;
class EXPORT_DLL TImputerConstructor_defaults;
class EXPORT_DLL TImputerConstructor_average;
class EXPORT_DLL TImputerConstructor_minimal;
class EXPORT_DLL TImputerConstructor_maximal;
class EXPORT_DLL TImputerConstructor_asValue;
class EXPORT_DLL TImputerConstructor_model;
class EXPORT_DLL TImputerConstructor_random;

/* from knn.hpp */
class EXPORT_DLL TkNNLearner;
class EXPORT_DLL TkNNClassifier;

/* from progress.hpp */
class EXPORT_DLL TProgressCallback;

/* from table.hpp */
class EXPORT_DLL TExampleTable;

/* from c4.5.hpp */
class EXPORT_DLL TC45Learner;
class EXPORT_DLL TC45TreeNode;
class EXPORT_DLL TC45Classifier;

/* from lookup.hpp */
class EXPORT_DLL TClassifierByLookupTable;
class EXPORT_DLL TClassifierByLookupTable1;
class EXPORT_DLL TClassifierByLookupTable2;
class EXPORT_DLL TClassifierByLookupTable3;
class EXPORT_DLL TClassifierByLookupTableN;
class EXPORT_DLL TLookupLearner;
class EXPORT_DLL TClassifierByExampleTable;

/* from graph.hpp */
class EXPORT_DLL TGraph;
class EXPORT_DLL TGraphAsMatrix;
class EXPORT_DLL TGraphAsList;
class EXPORT_DLL TGraphAsTree;

/* from c45inter.hpp */
class EXPORT_DLL TC45ExampleGenerator;

/* from callback.hpp */
class EXPORT_DLL TFilter_Python;
class EXPORT_DLL TTransformValue_Python;
class EXPORT_DLL TMeasureAttribute_Python;
class EXPORT_DLL TLearner_Python;
class EXPORT_DLL TLogRegFitter_Python;
class EXPORT_DLL TClassifier_Python;
class EXPORT_DLL TTreeSplitConstructor_Python;
class EXPORT_DLL TTreeStopCriteria_Python;
class EXPORT_DLL TTreeDescender_Python;
class EXPORT_DLL TTreeExampleSplitter_Python;
class EXPORT_DLL TProgressCallback_Python;
class EXPORT_DLL TImputerConstruct_Python;
class EXPORT_DLL TImputer_Python;
class EXPORT_DLL TRuleEvaluator_Python;
class EXPORT_DLL TEVDistGetter_Python;
class EXPORT_DLL TRuleValidator_Python;
class EXPORT_DLL TRuleCovererAndRemover_Python;
class EXPORT_DLL TRuleStoppingCriteria_Python;
class EXPORT_DLL TRuleDataStoppingCriteria_Python;
class EXPORT_DLL TRuleFinder_Python;
class EXPORT_DLL TRuleBeamRefiner_Python;
class EXPORT_DLL TRuleBeamInitializer_Python;
class EXPORT_DLL TRuleBeamCandidateSelector_Python;
class EXPORT_DLL TRuleBeamFilter_Python;
class EXPORT_DLL TRuleClassifierConstructor_Python;
class EXPORT_DLL TKernelFunc_Python;
class EXPORT_DLL TExamplesDistanceConstructor_Python;
class EXPORT_DLL TExamplesDistance_Python;

/* from domain.hpp */
class EXPORT_DLL TDomain;

/* from libsvm_interface.hpp */
class EXPORT_DLL TKernelFunc;
class EXPORT_DLL TSVMLearner;
class EXPORT_DLL TSVMLearnerSparse;
class EXPORT_DLL TSVMClassifier;
class EXPORT_DLL TSVMClassifierSparse;

/* from contingency.hpp */
class EXPORT_DLL TContingency;
class EXPORT_DLL TContingencyClass;
class EXPORT_DLL TContingencyClassAttr;
class EXPORT_DLL TContingencyAttrClass;
class EXPORT_DLL TContingencyAttrAttr;
class EXPORT_DLL TDomainContingency;
class EXPORT_DLL TComputeDomainContingency;

/* from rulelearner.hpp */
class EXPORT_DLL TRule;
class EXPORT_DLL TRuleValidator;
class EXPORT_DLL TRuleValidator_LRS;
class EXPORT_DLL TRuleEvaluator;
class EXPORT_DLL TRuleEvaluator_Entropy;
class EXPORT_DLL TRuleEvaluator_Laplace;
class EXPORT_DLL TEVDist;
class EXPORT_DLL TEVDistGetter;
class EXPORT_DLL TEVDistGetter_Standard;
class EXPORT_DLL TRuleEvaluator_mEVC;
class EXPORT_DLL TRuleEvaluator_LRS;
class EXPORT_DLL TRuleFinder;
class EXPORT_DLL TRuleBeamInitializer;
class EXPORT_DLL TRuleBeamInitializer_Default;
class EXPORT_DLL TRuleBeamRefiner;
class EXPORT_DLL TRuleBeamRefiner_Selector;
class EXPORT_DLL TRuleBeamCandidateSelector;
class EXPORT_DLL TRuleBeamCandidateSelector_TakeAll;
class EXPORT_DLL TRuleBeamFilter;
class EXPORT_DLL TRuleBeamFilter_Width;
class EXPORT_DLL TRuleBeamFinder;
class EXPORT_DLL TRuleDataStoppingCriteria;
class EXPORT_DLL TRuleDataStoppingCriteria_NoPositives;
class EXPORT_DLL TRuleStoppingCriteria;
class EXPORT_DLL TRuleStoppingCriteria_NegativeDistribution;
class EXPORT_DLL TRuleCovererAndRemover;
class EXPORT_DLL TRuleCovererAndRemover_Default;
class EXPORT_DLL TRuleClassifierConstructor;
class EXPORT_DLL TRuleClassifierConstructor_firstRule;
class EXPORT_DLL TRuleLearner;
class EXPORT_DLL TRuleClassifier;
class EXPORT_DLL TRuleClassifier_firstRule;
class EXPORT_DLL TLogitClassifierState;
class EXPORT_DLL TRuleClassifier_logit;

/* from tdidt_clustering.hpp */
class EXPORT_DLL TClusteringTreeLearner;
class EXPORT_DLL TClusteringTreeClassifier;

/* from relief.hpp */
class EXPORT_DLL TMeasureAttribute_relief;
