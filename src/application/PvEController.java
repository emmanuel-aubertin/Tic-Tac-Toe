package application;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ai.ConfigFileLoader;
import ai.MultiLayerPerceptron;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ai.Config;

public class PvEController {
	
	@FXML private AnchorPane parent;
	
	@FXML private Pane pane0;
	@FXML private ImageView sign0;
	@FXML private Pane pane1;
	@FXML private ImageView sign1;
	@FXML private Pane pane2;
	@FXML private ImageView sign2;
	@FXML private Pane pane3;
	@FXML private ImageView sign3;
	@FXML private Pane pane4;
	@FXML private ImageView sign4;
	@FXML private Pane pane5;
	@FXML private ImageView sign5;
	@FXML private Pane pane6;
	@FXML private ImageView sign6;
	@FXML private Pane pane7;
	@FXML private ImageView sign7;
	@FXML private Pane pane8;
	@FXML private ImageView sign8;
	
	private Game game;
	private MultiLayerPerceptron iaNet;
	private String level;
	private static String playerSign = "file:src/style/x.png";
	private static String iaSign = "file:src/style/Opiece.png";
	private static boolean canPlay = true;
	@FXML 
	private Text playersSwitch;
	@FXML
	GridPane gridPane;
	@FXML
	Button btnNewGame;
	List<FillTransition> transitionList = new ArrayList<>();
	List<ScaleTransition> scaleTransitionList = new ArrayList<>();
	
	
	@FXML 
	public void initialize() {
		System.out.println("|----------------------------|");
		System.out.println("|-- NEW GAME                 |");
		System.out.println("|----------------------------|");
		game = new Game();
		if(level != null) {
			ConfigFileLoader confFromFile = new ConfigFileLoader();
			confFromFile.loadConfigFile("./resources/config.txt");
			Config lvlConf = confFromFile.get(level);
			System.out.println("Level : " + level);
			String modelFile = "./resources/train/"+ "model_" + 
								lvlConf.numberOfhiddenLayers + "_" + lvlConf.hiddenLayerSize + "_" + lvlConf.learningRate + ".srl";
			iaNet = MultiLayerPerceptron.load(modelFile);
		}

	}
	
	private boolean gameResult() {
		double resultGame = game.checkIfWin();
		System.out.println("RESUTL ===> " + resultGame);
		if(resultGame == -1) 
		{
			playersSwitch.setText("Human wins!");
			 startWinTransition();
			return true;
		} 
		if(resultGame == 1)
		{
			playersSwitch.setText("IA wins!");
			 startWinTransition();
			return true;
		}
		if(resultGame == 2) {
			playersSwitch.setText("It's a tie!");
	        btnNewGame.setVisible(true);
	        return true;
		}
		return false;
	}

	/**
	 * When the game is over, clicking on this button allows you to start a new game.
	 * The field with the players' moves is cleared, the result is reset
	 * 
	 * @author Svitlana Temkaieva (lanebx)
	 */
	@FXML 
	public void handleNewGame(ActionEvent event) {
	    // Очистить все поля Text внутри Pane
		// Clear all Text fields inside the Pane
	    for (Node node : gridPane.getChildren()) {
	        if (node instanceof Pane) {
	            ((Text) ((Pane) node).getChildren().get(0)).setText("");
	            ((Text) ((Pane) node).getChildren().get(0)).setDisable(false);
	            node.setDisable(false);
	        }
	    }
	    
	    for (FillTransition transition : transitionList) {
	        transition.stop();
	        Text text = (Text) transition.getShape();
	        text.setFill(Color.WHITE);
	    }
	    transitionList.clear();
	    
	    for (ScaleTransition transition : scaleTransitionList) {
	        transition.stop();
	    }
	    scaleTransitionList.clear();
	    
	    playersSwitch.setText("Player : X");
	    btnNewGame.setVisible(false);
	    game =  new Game();
	    canPlay = true;
	}
	
	private void startWinTransition() {
		btnNewGame.setVisible(true);
		int[] posWin = game.getWinPos();
	    if(posWin != null) {
	        Duration duration = Duration.seconds(2);
	        Color startColor = Color.WHITE;
	        Color endColor = Color.GREEN;
	        int cycleCount = FillTransition.INDEFINITE;
	        
		    for(int e : posWin) {
		    	System.out.println("pos : " + e);
		    	ImageView winner_field = (ImageView) ((Pane) gridPane.getChildren().get(e)).getChildren().get(0);
		    	//winner_field.

		        
		        
		        ScaleTransition scaleTransition = new ScaleTransition(duration, winner_field);
		        scaleTransition.setFromX(1);
		        scaleTransition.setFromY(1);
		        scaleTransition.setToX(1.2);
		        scaleTransition.setToY(1.2);
		        scaleTransition.setCycleCount(cycleCount);
		        scaleTransition.setAutoReverse(true);
		        scaleTransition.play();
		        scaleTransitionList.add(scaleTransition);
		    }
	    }
	}
	
	@FXML 
	public void handlePlay(MouseEvent event) {
		playersSwitch.setText("Player : O");
		System.out.println(canPlay);
		if(!canPlay || gameResult()) {
			return;
		}
		System.out.println("Get pane clicked");
		ImageView imgPlayed;
		if(event.getTarget() instanceof Pane) {
			imgPlayed = (ImageView)((Pane) event.getTarget()).getChildren().get(0);
		} else {
			imgPlayed = ((ImageView) event.getTarget());
		}
		
		int posPlayed = Character.getNumericValue( imgPlayed.getId().charAt(imgPlayed.getId().length() - 1));
		if(!game.play(posPlayed, -1))
		{
			return;
		}
		System.out.print(game);
		imgPlayed.setImage(new Image(playerSign));
		//textPlayed.setText(playerSign);
		
		// if someone win
		if(gameResult()) {
			return;
		}
		
		
		playIA();
		
		if(gameResult()) {
			return;
		}
		
		System.out.println("You clicked ON " + posPlayed);
	}
	
	public void setVariable(String var) {
		level = var;
		ConfigFileLoader confFromFile = new ConfigFileLoader();
		confFromFile.loadConfigFile("./resources/config.txt");
		Config lvlConf = confFromFile.get(level);
		System.out.println("Level : " + level);
		String modelFile = "./resources/train/"+ "model_" + 
							lvlConf.numberOfhiddenLayers + "_" + lvlConf.hiddenLayerSize + "_" + lvlConf.learningRate + ".srl";
		iaNet = MultiLayerPerceptron.load(modelFile);
	}
	
	private void playIA() {
		
		double[] gameBoard = game.getGame();
		double[] newGame = iaNet.forwardPropagation(gameBoard);
		for(int i = 0; i < 3; i++) {
			System.out.println(newGame[i*3] + " | " + newGame[i*3+1] +" | " + newGame[i*3+2] + "\n-------------");
		}
		int posPlayed = 0;
		double maxProbs = 99;
		for(int i=0; i < 9; i++) {
			if(maxProbs > newGame[i] && game.isPlayable(i)) {
				System.out.println("IA play : " + i);
				posPlayed = i;
				maxProbs =  newGame[i];
			}
		}
		game.play(posPlayed, 1);
		canPlay = false;
		ImageView imgPlayed = (ImageView) parent.lookup("#sign" + posPlayed);
		Timeline timeline  = new Timeline();
		Duration delayBetweenMessages = Duration.millis(350);
		timeline.getKeyFrames().add(new KeyFrame(delayBetweenMessages, e -> imgPlayed.setImage(new Image(iaSign))));
		timeline.getKeyFrames().add(new KeyFrame(delayBetweenMessages, e -> {
		    if(gameResult()) {
		    	return;
		    }
    		playersSwitch.setText("Player: X");
    		canPlay = true;
		}));
		timeline.play();
		}
}