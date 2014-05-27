package com.dx.task;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class TaskWithRserve {

	public static void main(String args[]) {
		// The connection with R
		RConnection c;
		try {
			c = new RConnection();

			if (!c.isConnected()) {
				System.out.println("The connection with R is closed...");
				return;
			}

			// We load from the properties file the path to the dataset and the
			// label column
			String path = LoadProperties.getInstance().getProperty("dataset");
			String labelColumn = LoadProperties.getInstance().getProperty(
					"labelcolumn");

			//Load the functions code into R
			loadFunctions(c);
			// Loading the dataset
			System.out.println("Loading the dataset at: " + path);
			c.parseAndEval("breast <-read.csv('" + path + "',header=FALSE)");
			System.out
					.println("breast <-read.csv('" + path + "',header=FALSE)");
			System.out.println("");
			System.out.println("");

			// Now, we are going to generate two subsets as training set and
			// test set.
			// We will divide the original dataset in 30% and 70%
			System.out.println("Generating the testset and trainingset...");
			c.parseAndEval("index <- 1:nrow(breast)");
			System.out.println("index <- 1:nrow(breast)");
			c.parseAndEval("testindex <- sample(index, trunc(length(index)*30/100))");
			System.out
					.println("testindex <- sample(index, trunc(length(index)*30/100))");
			System.out.println("");

			// Finally, we obtain the two new dataset to perform the task
			c.parseAndEval("trainset <- breast[-testindex,]");
			System.out.println("trainset <- breast[-testindex,]");
			System.out.println("");
			c.parseAndEval("testset <-breast[testindex,]");
			System.out.println("testset <-breast[testindex,]");
			System.out.println("");
			System.out.println("");

			// We call the first function to train the naiveBayes classifier
			System.out.println("Executing the trainning to the classifier...");
			System.out.println("cla<-trainer(trainset,trainset[," + labelColumn
					+ "])");
			c.parseAndEval("cla<-trainer(trainset,trainset[," + labelColumn
					+ "])");
			System.out.println("");
			System.out.println("");

			// Now we call to the second function to obtain the predicted labels
			System.out.println("Executing the test to the classifier...");
			System.out.println("lab <- labels(cla,testset[,-" + labelColumn
					+ "])");
			c.parseAndEval("lab <- labels(cla,testset[,-" + labelColumn + "])");

			REXP predictions = c.parseAndEval("lab");
			System.out.println("");
			System.out.println("");

			// Finally, we print these predicted labels
			System.out.println("Predicted labels for the testset:");
			System.out.println("---------------------------------");
			String[] list = predictions.asStrings();
			for (int i = 0; i < list.length; i++)
				System.out.println(list[i]);

			// The connection with R is closed
			c.close();
		} catch (RserveException e) {
			System.out.println("Error during the execution:"+e.getMessage());
		} catch (REngineException e) {
			System.out.println("Error during the execution:"+e.getMessage());
		} catch (REXPMismatchException e) {
			System.out.println("Error during the execution:"+e.getMessage());
		}
	}
	/**
	 * This method contains the code for the functions to execute the naiveBayes classifier
	 * @param c
	 * @throws RserveException
	 */
	private static void loadFunctions(RConnection c) throws RserveException{
		c.eval("library(e1071)");
		c.eval("trainer<- function(trainset,label){\n"
				+	"classifier<-naiveBayes(trainset, label)\n" 
				+	"return(classifier)\n" 
				+    "}");
		c.eval("labels<-function(classifier,testset){\n"
				+	"x<-predict(classifier,testset)\n" 
				+	"return(x)\n" 
				+    "}");
	}

}
