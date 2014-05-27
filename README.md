SUMMARY:
-------

Selected Classifier:

I have used the naives Bayes classifier[1] since it is a simple probabilistic classifier based on applying Bayes' theorem (from Bayesian statistics) with strong (naive) independence assumptions. A more descriptive term for the underlying probability model would be "independent feature model".
An advantage of the naive Bayes classifier is that it requires a small amount of training data to estimate the parameters (means and variances of the variables) necessary for classification [2].Because independent variables are assumed, only the variances of the variables for each class need to be determined and not the entire covariance matrix.

Solutions:
I have worked before with the RCaller library to connect R with JAVA. Therefore, I have developed two different solutions in the same JAVA project:
	1. Rserve-based as it was described in the instructions: TaskWithRserve.java
	2. And a RCaller-based to complement the solution for the task: TaskWithRCaller.java


Files contained:

	* task.R - a script with the code of the two requested functions (trainer, labels).
	* folder "java executable" - this folder contains a .jar file to execute the task using Rserve ("java -jar DXtask.jar").
	* DXTask.zip - this file contains the eclipse JAVA project that access to the R functions with two different JAVA-based libraries Rserve and RCaller.
	* wdbc.data - the dataset used for the task, this dataset is described below.
	

	
CONSIDERATIONS FOR THE JAVA PROGRAMS:
-----------------------------------


Rserve Solution:
	To test the Rserve-based JAVA program you should install Rserve in R. The easiest way to install Rserve is to install it from CRAN, simply use
	

	install.packages("Rserve")


To start Rserve is from within R, just type:

		library(Rserve)
		Rserve()


RCaller Solution:
	To test the RCaller-based solution you should install the Runiversal package.
	
	install.packages("Runiversal")


DATASET USED INFORMATION:
------------------------
* url: http://archive.ics.uci.edu/ml/datasets/Breast+Cancer+Wisconsin+%28Diagnostic%29
* Breast Cancer Wisconsin (Diagnostic) Data Set.
* Number of Instances: 569
* Number of Attributes: 32
* Without missing values

Attribute Information:

	1) ID number
	2) Diagnosis (M = malignant, B = benign)---> Label

	Ten real-valued features are computed for each cell nucleus:

	a) radius (mean of distances from center to points on the perimeter)
	b) texture (standard deviation of gray-scale values)
	c) perimeter
	d) area
	e) smoothness (local variation in radius lengths)
	f) compactness (perimeter^2 / area - 1.0)
	g) concavity (severity of concave portions of the contour)
	h) concave points (number of concave portions of the contour)
	i) symmetry
	j) fractal dimension ("coastline approximation" - 1)




R CODE TO TEST THE R FUNCTIONS:
------------------------------



		breast<-read.csv('wdbc.data', header=FALSE)

		index <- 1:nrow(breast)
		testindex <- sample(index, trunc(length(index)*30/100))

		trainset <- breast[-testindex,]
		testset <-breast[testindex,]

		cla<-trainer(trainset,trainset[,2])

		labels(cla,testset[,-2])

NOTE : This code is included in task.R file. Please, set the working directory of R (using setwd function) to the directory where is stored your dataset.


REFERENCES:
-----------
		[1] Malik Yousef,Segun Jung, Andrew V. Kossenkov,Louise C. Showe,and Michael K. Showe.
			Naïve Bayes for microRNA target predictions—machine learning for microRNA targets.
			Bioinformatics (2007) 23 (22): 2987-2992 first published online October 8, 2007 	
		[2] Caruana, R.; Niculescu-Mizil, A.
			An empirical comparison of supervised learning algorithms. 
			Proceedings of the 23rd international conference on Machine learning (2006)	
