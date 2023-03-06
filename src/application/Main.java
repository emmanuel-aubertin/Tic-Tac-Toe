package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent parent =   FXMLLoader.load(getClass().getResource("training.fxml"));
			
			
			Scene MainScene = new Scene(parent);		
			primaryStage.setScene(MainScene);
			primaryStage.setTitle("Хрестики_нулики");
			primaryStage.show();
			
			

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
