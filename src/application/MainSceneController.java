package application;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Objects;

import ai.Config;
import ai.ConfigFileLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;


/**
 * Controller of the main vue
 * 
 * @author Aubertin Emmanuel (aka aTHO_)
 * @version 0.1
 * @since 0.1
 */
public class MainSceneController {
	
	@FXML private ComboBox<String> difficultySelector;
	
	public String level;
	
	
	@FXML public void initialize() {
		System.out.println("|----------------------------|");
		System.out.println("|-- Current config           |");
		System.out.println("|----------------------------|");
		ConfigFileLoader confFromFile = new ConfigFileLoader();
		
		confFromFile.loadConfigFile("./resources/config.txt");
		ObservableList<String> options = FXCollections.observableArrayList();
		for(int i = 0; i < confFromFile.size(); i++) {
			System.out.println(confFromFile.getConfFromInt(i).level);
			options.add(confFromFile.getConfFromInt(i).level);
		}
		difficultySelector.getItems().addAll(options);
	}
	
	/**
	 * Open the AI configuration window
	 * 
	 * @author Aubertin Emmanuel (aka aTHO_)
	 */
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
	
	/**
	 * Open the window for manage trainned model
	 * 	 
	 *  @author Aubertin Emmanuel (aka aTHO_)
	 */
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
	
	/**
	 * Opens a window to start AI training, before checking for the corresponding file. 
	 * An argument is passed to the function and to the modal window to select the desired level of complexity.
	 * 
	 * @author Svitlana Temkaieva (lanebx)
	 */
	public void handleOpenTrainingWindow(String variableValue) {
		Parent root;
		try {
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("training.fxml"));
			Stage newStage = new Stage();
			
			Parent parent = loader.load();
		    TrainingController controller = loader.getController();
		    controller.setVariable(variableValue);

			
			Scene MainScene = new Scene(parent);		
			newStage.setScene(MainScene);
			newStage.setTitle("Хрестики_нулики");
			newStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Launch game againts IA or train IA before.
	 * 
	 * @author Aubertin Emmanuel (aka aTHO_), Svitlana Temkaieva (lanebx)
	 */
	public void handleLaunchIA(ActionEvent event) {
		System.out.println("|----------------------------|");
		System.out.println("|-- NEW GAME                 |");
		System.out.println("|----------------------------|");
		
		// GET LEVEL SELECTED
		level = difficultySelector.getSelectionModel().getSelectedItem().toString();
		System.out.println("\t" + level);
		
		// GET CONF
		ConfigFileLoader confFromFile = new ConfigFileLoader();
		confFromFile.loadConfigFile("./resources/config.txt");
		Config iaConf = confFromFile.get(level);
		System.out.println("\t" + iaConf);
		
		// Check if IA has already been trained
		File trainFolder = new File("./resources/train/");

		// This filter will only include files ending with .srl
		FilenameFilter filter = new FilenameFilter() {
	        @Override
	        public boolean accept(File f, String name) {
	            return name.endsWith(".srl");
	        }
	    };
	    
	    // CHECK IF THE MODEL HAS BEEN TRAINED.
	    //Example of file : model_hiddenLayerSize_learningRate_numberOfhiddenLayers.srl
	   String fileName = "model_" + iaConf.numberOfhiddenLayers + "_" + iaConf.hiddenLayerSize + "_" + iaConf.learningRate + ".srl";
	   for(String file : trainFolder.list(filter)) {
		   if(Objects.equals(file, fileName)) {
			   System.out.println("\tModel existe " + file);
			   System.out.println("\tLainching the GAME");
			   System.out.println("\tMODEL NOT NEED TO BE TRAINED");
			   return;
		   }	   
	   }
	   
	   System.out.println("\tMODEL NEED TO BE TRAINED");
	   
	   handleOpenTrainingWindow(level);
	      
	   return;
	}
}
