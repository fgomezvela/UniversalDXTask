install.packages('e1071', dependencies = TRUE)
library(class) 
library(e1071) 

trainer<- function(trainset,label){
	classifier<-naiveBayes(trainset, label) 
	return(classifier)
}

labels<-function(classifier,testset){
	x<-predict(classifier,testset)
	return(x)
}