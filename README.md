SUMMARY:
-------
Files contained:
	* file task.R - a script with the code of the two requested functions (trainer, labels).
	* folder DXTask - this folder contains the eclipse JAVA project that access to the R functions.
	* wdbc.data - the dataset used for the task, this dataset is described bellow.

	
	
CONSIDERATIONS:
--------------
To test the JAVA program you should install Rserve and load "task.R" script in R. The easiest way to install Rserve is to install it from CRAN, simply use

install.packages("Rserve")

To start Rserve is from within R, just type:

library(Rserve)
Rserve()



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

NOTE : Please, set the working directory of R (using setwd function) to the directory where is stored your dataset.
