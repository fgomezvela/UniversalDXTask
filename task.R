install.packages('e1071', dependencies = TRUE)

library(e1071) 

trainer<- function(trainset,label){
	classifier<-naiveBayes(trainset, label) 
	return(classifier)
}

labels<-function(classifier,testset){
	x<-predict(classifier,testset)
	return(x)
}

breast<-read.csv('wdbc.data', header=FALSE)

index <- 1:nrow(breast)
testindex <- sample(index, trunc(length(index)*30/100))

trainset <- breast[-testindex,]
testset <-breast[testindex,]

cla<-trainer(trainset,trainset[,2])

labels(cla,testset[,-2])

