package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class GameController {
	
	
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
	
	private ArrayList<Integer> game = new ArrayList<Integer>();
	private static String x = "⚔️";
	private static String o = "O️";

	
	@FXML public void handlePlay(MouseEvent event) {
		System.out.println("Get pane clicked");
		Text textPlayed;
		if(event.getTarget() instanceof Pane) {
			textPlayed = (Text)((Pane) event.getTarget()).getChildren().get(0);
		} else {
			textPlayed = ((Text) event.getTarget());
		}
		
		int posPlayed = Character.getNumericValue( textPlayed.getId().charAt(textPlayed.getId().length() - 1));
		
		textPlayed.setText(x);
		
		System.out.println("Get the id");
		System.out.println("You clicked ON " + posPlayed);
	}

}