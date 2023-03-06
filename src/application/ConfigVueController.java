package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import ai.Config;
import ai.ConfigFileLoader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Controller of Config Vue
 * 
 * @author Aubertin Emmanuel (aka aTHO_)
 * @version 0.1
 * @since 0.1
 */

public class ConfigVueController {
	
	@FXML private VBox VBoxView;
	
	
	// Init of the config window with the data of config.txt
	@FXML public void initialize() {
		System.out.println("----------------------------");
		System.out.println("-- Current config");
		System.out.println("----------------------------");
		ConfigFileLoader confFromFile = new ConfigFileLoader();
		confFromFile.loadConfigFile("./resources/config.txt");
		
		for(int i = 0; i < confFromFile.size(); i++)
		{
			
			HBox confLine = new HBox();
			confLine.setId(confFromFile.getConfFromInt(i).level);

			
			// NAME OF THE LVL
			confLine.getChildren().add(new Text(confFromFile.getConfFromInt(i).level));
			System.out.print(confFromFile.getConfFromInt(i).level + ":");
			
			// HIDDEN LAYER SIZE
			TextField layerSize = new TextField();
			layerSize.setText(String.valueOf(confFromFile.getConfFromInt(i).hiddenLayerSize));
			layerSize.setId("layerSize" + i);
			confLine.getChildren().add(layerSize);
			System.out.print(confFromFile.getConfFromInt(i).hiddenLayerSize + ":");
			
			// LEARNING RATE
			TextField learningRate = new TextField();
			learningRate.setText(String.valueOf(confFromFile.getConfFromInt(i).learningRate));
			layerSize.setId("learningRate" + i);
			confLine.getChildren().add(learningRate);
			System.out.print(confFromFile.getConfFromInt(i).learningRate + ":");
			
			// NUMBER OF HIDDEN LAYER
			TextField numberOfLayer = new TextField();
			numberOfLayer.setText(String.valueOf(confFromFile.getConfFromInt(i).numberOfhiddenLayers));
			layerSize.setId("numberOfLayer" + i);
			confLine.getChildren().add(numberOfLayer);
			System.out.print(confFromFile.getConfFromInt(i).numberOfhiddenLayers + "\n");
			
			VBoxView.getChildren().add(confLine);
			
		}
	}
	
	/**
	 * Handle save config click
	 * @param event
	 * 
	 * @author Aubertin Emmanuel (aka aTHO_)
	 */
	public void handleSaveBtn(ActionEvent event )
	{
		// CONFIG FILE FORMAT
		// ID:LayerSize:LearningRate:NumberOfLayer
		
		System.out.println("----------------------------");
		System.out.println("-- Config to save");
		System.out.println("----------------------------");
		String newConf = "";
		for(Node currComp : VBoxView.getChildrenUnmodifiable())
		{
			if( currComp instanceof HBox )
			{
				
				HBox confLine = (HBox) currComp;
				
				// ID
				System.out.print(confLine.getId() + ":");
				newConf += confLine.getId()+ ":";
				
				
				// LayerSize
				System.out.print(((TextField) (confLine.getChildren().get(1))).getText()+ ":");
				newConf += ((TextField) (confLine.getChildren().get(1))).getText()+ ":";
				
				// LearningRate
				System.out.print(((TextField) (confLine.getChildren().get(2))).getText()+ ":");
				newConf += ((TextField) (confLine.getChildren().get(2))).getText()+ ":";
				
				// LearningRate
				System.out.print(((TextField) (confLine.getChildren().get(3))).getText()+ "\n");
				newConf += ((TextField) (confLine.getChildren().get(3))).getText()+ "\n";

			}
		}
		System.out.println("-- Writting config.txt --");
		try {
			BufferedWriter confFile = new BufferedWriter(new FileWriter("./resources/config.txt"));
			confFile.write(newConf);
			confFile.close();
			System.out.println("OK - Config saved");
		} catch (IOException e) {
			System.out.println("ERROR - FAIL TO WRITE ");
			e.printStackTrace();
		}
	}
}
