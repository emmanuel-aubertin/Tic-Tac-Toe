package application;


import java.util.ArrayList;

import ai.ConfigFileLoader;
import ai.MultiLayerPerceptron;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import application.Game;
import ai.Config;

public class PvEController {
	
	@FXML private AnchorPane parent;
	
	@FXML private Pane pane0;
	@FXML private Text sign0;
	@FXML private Pane pane1;
	@FXML private Text sign1;
	@FXML private Pane pane2;
	@FXML private Text sign2;
	@FXML private Pane pane3;
	@FXML private Text sign3;
	@FXML private Pane pane4;
	@FXML private Text sign4;
	@FXML private Pane pane5;
	@FXML private Text sign5;
	@FXML private Pane pane6;
	@FXML private Text sign6;
	@FXML private Pane pane7;
	@FXML private Text sign7;
	@FXML private Pane pane8;
	@FXML private Text sign8;
	
	private Game game;
	private MultiLayerPerceptron iaNet;
	private String level;
	private static String playerSign = "⚔️";
	private static String iaSign = "O";
	private static boolean canPlay = true;
	
	
	@FXML public void initialize() {
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
			System.out.println("Human win");
			return true;
		} 
		if(resultGame == 1)
		{
			System.out.println("IA win");
			return true;
		}
		return false;
	}

	
	@FXML public void handlePlay(MouseEvent event) {
		System.out.println(canPlay);
		if(!canPlay || gameResult()) {
			return;
		}
		System.out.println("Get pane clicked");
		Text textPlayed;
		if(event.getTarget() instanceof Pane) {
			textPlayed = (Text)((Pane) event.getTarget()).getChildren().get(0);
		} else {
			textPlayed = ((Text) event.getTarget());
		}
		
		int posPlayed = Character.getNumericValue( textPlayed.getId().charAt(textPlayed.getId().length() - 1));
		if(!game.play(posPlayed, -1))
		{
			return;
		}
		System.out.print(game);
		textPlayed.setText(playerSign);
		
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
		Text sign = (Text) parent.lookup("#sign" + posPlayed);
		Timeline timeline  = new Timeline();
		Duration delayBetweenMessages = Duration.millis(200);
		timeline.getKeyFrames().add(new KeyFrame(delayBetweenMessages, e -> sign.setText(iaSign)));
		timeline.play();
	}
}