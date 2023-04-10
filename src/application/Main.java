package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			Parent parent = FXMLLoader.load(getClass().getResource("mainScene.fxml"));

			Scene MainScene = new Scene(parent);		
			primaryStage.setScene(MainScene);
			primaryStage.setTitle("Tic tac toe");
			primaryStage.setResizable(false);
			primaryStage.show();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
