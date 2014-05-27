package com.dx.task;

import rcaller.RCaller;
import rcaller.RCode;

public class TaskWithRCaller {
	public static void main(String args[]) {
		String path = LoadProperties.getInstance().getProperty("dataset");
		String rscript = LoadProperties.getInstance().getProperty("rscriptpath");
		String labelColumn = LoadProperties.getInstance().getProperty(
				"labelcolumn");
		
		// The connection with R using RCaller
		RCaller caller = new RCaller();
		caller.setRscriptExecutable(rscript);
		
			
		RCode code = new RCode();
		code.clear();
		
		//Load the functions code into R
		loadFunctions(code);
		// Loading the dataset
		System.out.println("Loading the dataset at: " + path);
		code.addRCode("breast <-read.csv('" + path + "',header=FALSE)");
		System.out.println("breast <-read.csv('" + path + "',header=FALSE)");
		System.out.println("");
		System.out.println("");

		// Now, we are going to generate two subsets as training set and
		// test set.
		// We will divide the original dataset in 30% and 70%
		System.out.println("Generating the testset and trainingset...");
		code.addRCode("index <- 1:nrow(breast)");
		System.out.println("index <- 1:nrow(breast)");
		code.addRCode("testindex <- sample(index, trunc(length(index)*30/100))");
		System.out
				.println("testindex <- sample(index, trunc(length(index)*30/100))");
		System.out.println("");

		// Finally, we obtain the two new dataset to perform the task
		code.addRCode("trainset <- breast[-testindex,]");
		System.out.println("trainset <- breast[-testindex,]");
		System.out.println("");
		code.addRCode("testset <-breast[testindex,]");
		System.out.println("testset <-breast[testindex,]");
		System.out.println("");
		System.out.println("");

		// We call the first function to train the naiveBayes classifier
		System.out.println("Executing the trainning to the classifier...");
		System.out.println("cla<-trainer(trainset,trainset[," + labelColumn
				+ "])");
		code.addRCode("cla<-trainer(trainset,trainset[," + labelColumn + "])");
		System.out.println("");
		System.out.println("");

		// Now we call to the second function to obtain the predicted labels
		System.out.println("Executing the test to the classifier...");
		System.out.println("lab <- labels(cla,testset[,-" + labelColumn + "])");
		code.addRCode("labels(cla,testset[,-" + labelColumn + "])");

		
		System.out.println("");
		System.out.println("");

		// Finally, we print these predicted labels
		System.out.println("Predicted labels for the testset:");
		System.out.println("---------------------------------");
		
		caller.setRCode(code);
		caller.redirectROutputToConsole();
		caller.runOnly();				
		


	}
	
	private static void loadFunctions(RCode code){
		code.addRCode("library(e1071)");
		//code.addRCode("library(class)");
		code.addRCode("trainer<- function(trainset,label){\n"
		+	"classifier<-naiveBayes(trainset, label)\n" 
		+	"return(classifier)\n" 
		+    "}");
		code.addRCode("labels<-function(classifier,testset){\n"
				+	"x<-predict(classifier,testset)\n" 
				+	"return(x)\n" 
				+    "}");
	}
}
