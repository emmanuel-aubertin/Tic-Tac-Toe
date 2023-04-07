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
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.control.Button;

import javafx.scene.image.ImageView;

/**
 * Controller of the game P2P vue
 * 
 * @author Svitlana Temkaieva (lanebx)
 */
public class GameController {
	private static final int ROWS = 3;
    private static final int COLUMNS = 3;
    
	
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
	@FXML private ImageView signImg0;
	
	@FXML 
	private Text playersSwitch;
	
	List<FillTransition> transitionList = new ArrayList<>();
	List<ScaleTransition> scaleTransitionList = new ArrayList<>();
	
	@FXML
	GridPane gridPane;
	
	@FXML 
	private Button btnNewGame;
	
	private ArrayList<String> game = new ArrayList<>();
	{
		for (int i = 0; i < ROWS * COLUMNS; i++) {
	        game.add("");
	    }
	}
	
	private Game gameLogic = new Game();
	
	private static final String X = "X";
	private static final String O = "O";
	private boolean playerX = true;

	
	/**
	 * When the game is over, clicking on this button allows you to start a new game.
	 * The field with the players' moves is cleared, the result is reset
	 * 
	 * @author Svitlana Temkaieva (lanebx)
	 */
	@FXML 
	public void handleNewGame(ActionEvent event) {
		gridPane.setDisable(false);
	    // Очистить все поля Text внутри Pane
	    for (Node node : pane0.getParent().getChildrenUnmodifiable()) {
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
	    
	    // Сбросить игру
	    game.clear();
	    for (int i = 0; i < ROWS * COLUMNS; i++) {
	        game.add("");
	    }

	    playerX = true;
	    playersSwitch.setText("Player : X");
	    btnNewGame.setVisible(false);
	    gameLogic =  new Game();
	    

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
		Text textPlayed = (Text) clickedPane.getChildren().get(0);
	    int posPlayed = Integer.parseInt(clickedPane.getId().substring(4));
	    clickedPane.setDisable(true);
		
		if (playerX) {
	        textPlayed.setText(X);
	        game.set(posPlayed, X);
	        playersSwitch.setText("Player : O");
	        gameLogic.play(posPlayed, -1);
	    } else {
	        textPlayed.setText(O);
	        game.set(posPlayed, O);
	        playersSwitch.setText("Player : X");
	        gameLogic.play(posPlayed, 1);
	    }
		
		playerX = !playerX;

		String winner = checkWin();
		
		if (winner != null) {
		    if (winner.equals(X) || winner.equals(O)) {
		    	System.out.println(winner + " wins!");
		        playersSwitch.setText(winner + " wins!");
		        btnNewGame.setVisible(true);
		        gridPane.setDisable(true);
		    } else if (winner.equals("Tie")) {
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
			    	Text winner_field = (Text) ((Pane) gridPane.getChildren().get(e)).getChildren().get(0);
			    	//winner_field.

			        FillTransition fillTransition = new FillTransition(duration, winner_field, startColor, endColor);
			        fillTransition.setCycleCount(cycleCount);
			        fillTransition.setAutoReverse(true);
			        fillTransition.play();
			        transitionList.add(fillTransition);
			        
			        
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
	private String checkWin() {
		 // Check verticals
        for (int col = 0; col < COLUMNS; col++) {
            if (!game.get(col).isEmpty() &&
                game.get(col).equals(game.get(col + ROWS)) &&
                game.get(col).equals(game.get(col + ROWS * 2))) {
                return game.get(col);
            }
        }
	    // Проверяем горизонтали
		for (int row = 0; row < ROWS; row++) {
			int start = row * COLUMNS;
			if (game.get(start).equals(game.get(start + 1)) && game.get(start).equals(game.get(start + 2)) && !game.get(start).isEmpty()) {
			return game.get(start);
	        }
	    }

	    // Проверяем вертикали
	    for (int col = 0; col < COLUMNS; col++) {
	        if (game.get(col).equals(game.get(col + COLUMNS)) && game.get(col).equals(game.get(col + COLUMNS * 2))) {
	        	System.out.println("winner 2" + col);
	            return game.get(col);
	        }
	    }

	    // Проверяем диагонали
	    if (game.get(0).equals(game.get(4)) && game.get(0).equals(game.get(8))) {
	    	System.out.println("winner 3" + game.get(0));
	        return game.get(0);
	    }
	    
	    if (game.get(2).equals(game.get(4)) && game.get(2).equals(game.get(6))) {
	    	System.out.println("winner 4" + game.get(2));
	        return game.get(2);
	    }
	    

	    if (game.stream().anyMatch(String::isEmpty)) {
	        return null;
	    } else {
	        return "Tie";
	    }

	}

}