package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainSceneController {
	
	
	public void handleOpenConfiguration() {
		Parent root;
		try {
			Stage newStage = new Stage();
			Parent parent = FXMLLoader.load(getClass().getResource("learning_config.fxml"));

			
			Scene MainScene = new Scene(parent);		
			newStage.setScene(MainScene);
			newStage.setTitle("Хрестики_нулики");
			newStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void handleOpenModel() {
		Parent root;
		try {
			Stage newStage = new Stage();
			Parent parent = FXMLLoader.load(getClass().getResource("model_list.fxml"));

			
			Scene MainScene = new Scene(parent);		
			newStage.setScene(MainScene);
			newStage.setTitle("Хрестики_нулики");
			newStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
