package application;

import java.util.HashMap;

import ai.Config;
import ai.ConfigFileLoader;
import ai.Coup;
import ai.MultiLayerPerceptron;
import ai.SigmoidalTransferFunction;
import ai.Test;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;


public class TrainingController {
	
	@FXML
	private ProgressBar progressBarLearn;
	
	@FXML
	private Label textLearn;
	
	@FXML
	private Button buttonLearn;
	
	private MultiLayerPerceptron net;
	
	
	@FXML
	public void onButtonLearnClicked(ActionEvent event) {
		buttonLearn = (Button) event.getSource();
		buttonLearn.setVisible(false);
		
		HashMap<Integer, Coup> coups = Test.loadGames("./resources/dataset/Tic_tac_initial_results.csv");
		Test.saveGames(coups, "./resources/train_dev_test/", 0.7);
		//
		// LOAD CONFIG ...
		//
		ConfigFileLoader cfl = new ConfigFileLoader();
		cfl.loadConfigFile("./resources/config.txt");
		// Choose the write configuration
		Config config = cfl.get("F");
		System.out.println("Test.main() : " + config);
		//
		//TRAIN THE MODEL ...
		//
		// Number of learning iterration
		double epochs = 100000 ; 
		HashMap<Integer, Coup> mapTrain = Test.loadCoupsFromFile("./resources/train_dev_test/train.txt");
		
		if ( true ) {
			System.out.println();
			System.out.println("START TRAINING ...");
			System.out.println();
		}

		//			int[] layers = new int[]{ size, 128, 128, size };
		int[] layers = new int[config.numberOfhiddenLayers + 2];
		layers[0] = 9 ;
		for (int i = 0; i < config.numberOfhiddenLayers; i++) {
			layers[i+1] = config.hiddenLayerSize;
		}
		layers[layers.length-1] = 9 ;
		//
		net = new MultiLayerPerceptron(layers, config.learningRate, new SigmoidalTransferFunction());

		if ( true ) {
			System.out.println("---");
			System.out.println("Load data ...");
			System.out.println("---");
		}
	    
	    Task<Integer> task = new Task<Integer>() {
			double error = 0.0 ;
	         @Override protected Integer call() throws Exception {
	        	for(int i = 1; i <= epochs; i++) {
					Coup c = null ;
					
					while ( c == null )
						c = mapTrain.get((int)(Math.round(Math.random() * mapTrain.size())));
					
					error += net.backPropagate(c.in, c.out);
					
					if ( i % 1000 == 0 && true) {
						System.out.println("Error at step "+i+" is "+ (error/(double)i));
						System.out.println("Percentage : " + (i/epochs*100));
						updateProgress(i, epochs);
						updateMessage("Progres: " + (int)(i/epochs*100) + "%");
					}
				}
	        	 
	 			if ( true ) {
	 				System.out.println("Learning completed!");
	 				updateMessage("Learning completed!");
	 			}
	 			
	 			return null;
	 		} 
	     };
		
	     
	     progressBarLearn.progressProperty().bind(task.progressProperty());
	     textLearn.textProperty().bind(task.messageProperty());

	     new Thread(task).start();
	    		 
	}
}
