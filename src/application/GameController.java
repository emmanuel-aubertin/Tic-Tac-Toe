package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	        }
	    }
	    // Сбросить игру
	    game.clear();
	    for (int i = 0; i < ROWS * COLUMNS; i++) {
	        game.add("");
	    }
	    playerX = true;
	    playersSwitch.setText("Player : X");
	    btnNewGame.setVisible(false);
	}
	
	@FXML 
	public void handlePlay(MouseEvent event) {
		System.out.println("Get pane clicked");
//		Image imageO = new Image("./resources/images/TicTacToe/circle.png");
//		Image imageX = new Image("./resources/images/TicTacToe/cross.png");
		
//		signImg0.setImage(imageO);
		
		Pane clickedPane = (Pane) event.getSource();
		Text textPlayed = (Text) clickedPane.getChildren().get(0);
	    int posPlayed = Integer.parseInt(clickedPane.getId().substring(4));
		
		if (playerX) {
	        textPlayed.setText(X);
	        game.set(posPlayed, X);
	        playersSwitch.setText("Player : O");
	    } else {
	        textPlayed.setText(O);
	        game.set(posPlayed, O);
	        playersSwitch.setText("Player : X");
	    }
		 playerX = !playerX;
		 
		String winner = checkWin();
		if (winner != null) {
		    if (winner.equals(X) || winner.equals(O)) {
		    	playersSwitch.setText(winner + " wins!");
		        btnNewGame.setVisible(true);
		        gridPane.setDisable(true);
		    } else if (game.stream().noneMatch(String::isEmpty)) {
		    	System.out.println("Нету ходов");
		        btnNewGame.setVisible(true);
		        gridPane.setDisable(true);
		    }
		}

	}
	
	private String checkWin() {
	    // Проверяем горизонтали
	    for (int row = 0; row < ROWS; row++) {
	        int start = row * COLUMNS;
	        if (game.get(start).equals(game.get(start + 1)) && game.get(start).equals(game.get(start + 2))) {
	            return game.get(start);
	        }
	    }

	    // Проверяем вертикали
	    for (int col = 0; col < COLUMNS; col++) {
	        if (game.get(col).equals(game.get(col + COLUMNS)) && game.get(col).equals(game.get(col + COLUMNS * 2))) {
	            return game.get(col);
	        }
	    }

	    // Проверяем диагонали
	    if (game.get(0).equals(game.get(4)) && game.get(0).equals(game.get(8))) {
	        return game.get(0);
	    }
	    if (game.get(2).equals(game.get(4)) && game.get(2).equals(game.get(6))) {
	        return game.get(2);
	    }

	    return null;
	}

}