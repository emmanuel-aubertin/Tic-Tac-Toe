package application;

import java.util.ArrayList;

import java.util.List;

import javafx.animation.FillTransition;

import javafx.animation.ScaleTransition;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller of the game P2P vue
 * 
 * @author Svitlana Temkaieva (lanebx)
 */
public class GameController {
	private static final int ROWS = 3;
    private static final int COLUMNS = 3;
    
    private static String XSign = "file:src/style/x.png";
	private static String OSign = "file:src/style/Opiece.png";
    
	
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
	
	@FXML 
	private Text playersSwitch;
	
	List<FillTransition> transitionList = new ArrayList<>();
	List<ScaleTransition> scaleTransitionList = new ArrayList<>();
	
	String strWinner;
	
	@FXML
	GridPane gridPane;
	
	@FXML 
	private Button btnNewGame;
	
	private Game gameLogic = new Game();
	
	private static final String X = "X";
	private static final String O = "O";
	private boolean playerX = true;
	
	private static String FONT_PATH = "file:resources/DeliciousHandrawn-Regular.ttf";
    private static final Font CUSTOM_FONT = Font.loadFont(GameController.class.getResourceAsStream(FONT_PATH), 24);

	
	/**
	 * When the game is over, clicking on this button allows you to start a new game.
	 * The field with the players' moves is cleared, the result is reset
	 * 
	 * @author Svitlana Temkaieva (lanebx)
	 */
	@FXML 
	public void handleNewGame(ActionEvent event) {
		gridPane.setDisable(false);
		playersSwitch.setFont(CUSTOM_FONT);
		
	    // Очистить все поля Text внутри Pane
		for (Node node : pane0.getParent().getChildrenUnmodifiable()) {
		    if (node instanceof Pane) {
		        for (Node childNode : ((Pane) node).getChildren()) {
		            if (childNode instanceof ImageView) {
		                ((ImageView) childNode).setImage(null);
		            }
		        }
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
	    
	    // Сбросить игру

	    playerX = true;
	    playersSwitch.setText("Player : X");
	    btnNewGame.setVisible(false);
	    gameLogic =  new Game();

	}
	
	
	@FXML 
	public void testClick() {
		System.out.println("testClickIMG");
	}
	
	@FXML 
	public void testClick2() {
		System.out.println("testClickPane");
	}
	
	/**
	 * The method is triggered when a user clicks on a cell in the game grid. 
	 * It updates the game state, checks for a winner, and adds animations for winning symbols.
	 * 
	 * @author Svitlana Temkaieva (lanebx), Aubertin Emmanuel (aka aTHO_)
	 */
	@FXML 
	public void handlePlay(MouseEvent event) {
		System.out.println("Get pane clicked");
		Pane clickedPane = (Pane) event.getSource();
	    int posPlayed = Integer.parseInt(clickedPane.getId().substring(4));
	    ImageView imgPlayed = (ImageView) clickedPane.getChildren().get(0);
	    clickedPane.setDisable(true);
		
		if (playerX) {
			imgPlayed.setImage(new Image(XSign));
	        playersSwitch.setText("Player : O");
	        gameLogic.play(posPlayed, -1);
	    } else {
	    	imgPlayed.setImage(new Image(OSign));
	        playersSwitch.setText("Player : X");
	        gameLogic.play(posPlayed, 1);
	    }
		
		playerX = !playerX;

		double winner = gameLogic.checkIfWin();
		
		if (winner != 0) {
			if (winner == 1.0)
				strWinner = O;
			else if (winner == -1.0) {
				strWinner = X;
			} else {
				strWinner = "Tie";
			}
			
			if (strWinner.equals(X) || strWinner.equals(O)) {
		    	System.out.println(strWinner + " wins!");
		        playersSwitch.setText(strWinner + " wins!");
		        btnNewGame.setVisible(true);
		        gridPane.setDisable(true);
		    } else if (strWinner.equals("Tie")) {
		        playersSwitch.setText("It's a tie!");
		        btnNewGame.setVisible(true);
		        gridPane.setDisable(true);
		    }
			
		    System.out.println("GameLogique");
		    System.out.println(gameLogic);
		    int[] posWin = gameLogic.getWinPos();
		    if(posWin != null) {
		        Duration duration = Duration.seconds(2);
		        Color startColor = Color.WHITE;
		        Color endColor = Color.GREEN;
		        int cycleCount = FillTransition.INDEFINITE;
		        
		        for(int e : posWin) {
			    	System.out.println("pos : " + e);
			    	ImageView winner_field = (ImageView) ((Pane) gridPane.getChildren().get(e)).getChildren().get(0);

			        
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

	}
	
	/**
	 * If a player has won, the method returns the player's symbol.
	 * If there is no winner, the method returns null.
	 */
	
}