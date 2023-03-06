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

public class ConfigVueController {
	
	@FXML private VBox VBoxView;
	
	@FXML public void initialize() {
		ConfigFileLoader confFromFile = new ConfigFileLoader();
		confFromFile.loadConfigFile("./resources/config.txt");
		
		for(int i = 0; i < confFromFile.size(); i++)
		{
			
			HBox confLine = new HBox();
			confLine.setId(confFromFile.getConfFromInt(i).level);
			// NAME OF THE LVL
			Text confName = new Text(confFromFile.getConfFromInt(i).level);
			confName.setId("numberOfLayer" + i);
			confLine.getChildren().add(confName);
			
			// HIDDEN LAYER SIZE
			TextField layerSize = new TextField();
			layerSize.setText(String.valueOf(confFromFile.getConfFromInt(i).hiddenLayerSize));
			layerSize.setId("layerSize" + i);
			confLine.getChildren().add(layerSize);
			
			// LEARNING RATE
			TextField learningRate = new TextField();
			learningRate.setText(String.valueOf(confFromFile.getConfFromInt(i).learningRate));
			layerSize.setId("learningRate" + i);
			confLine.getChildren().add(learningRate);
			
			// NUMBER OF HIDDEN LAYER
			TextField numberOfLayer = new TextField();
			numberOfLayer.setText(String.valueOf(confFromFile.getConfFromInt(i).numberOfhiddenLayers));
			layerSize.setId("numberOfLayer" + i);
			confLine.getChildren().add(numberOfLayer);
			
			
			VBoxView.getChildren().add(confLine);
			System.out.println("Level " + confFromFile.getConfFromInt(i).level);
		}
	}
	
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
		System.out.println("-- Writting config.txt");
		try {
			BufferedWriter confFile = new BufferedWriter(new FileWriter("./resources/config.txt"));
			confFile.write(newConf);
			confFile.close();
		} catch (IOException e) {
			System.out.println("ERROR - FAIL TO WRITE ");
			e.printStackTrace();
		}
	}
}
