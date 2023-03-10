package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import ai.Config;
import ai.ConfigFileLoader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
		System.out.println("|----------------------------|");
		System.out.println("|-- Current config           |");
		System.out.println("|----------------------------|");
		ConfigFileLoader confFromFile = new ConfigFileLoader();
		confFromFile.loadConfigFile("./resources/config.txt");
		
		Pattern regexFloalt = Pattern.compile("[+-]?([0-9]*[.])?[0-9]+");
		Pattern regexInt = Pattern.compile("[0-9]*");
		
		for(int i = 0; i < confFromFile.size(); i++)
		{
			
			HBox confLine = new HBox();
			confLine.setId(confFromFile.getConfFromInt(i).level);

			
			// NAME OF THE LVL
			confLine.getChildren().add(new TextField(confFromFile.getConfFromInt(i).level));
			System.out.print(confFromFile.getConfFromInt(i).level + ":");
			
			// HIDDEN LAYER SIZE
			TextField hiddenSize = new TextField(String.valueOf(confFromFile.getConfFromInt(i).hiddenLayerSize));
			hiddenSize.setTextFormatter(new TextFormatter<>(newValue -> {
			    if (regexInt.matcher(newValue.getControlNewText()).matches()) {
			        return newValue;
			    }
			    return null;
			}));
			confLine.getChildren().add(hiddenSize);
			System.out.print(confFromFile.getConfFromInt(i).hiddenLayerSize + ":");
			
			// LEARNING RATE
			TextField learningRate = new TextField(String.valueOf(confFromFile.getConfFromInt(i).learningRate));
			learningRate.setTextFormatter(new TextFormatter<>(newValue -> {
			    if (regexFloalt.matcher(newValue.getControlNewText()).matches()) {
			        return newValue;
			    }
			    return null;
			}));
			confLine.getChildren().add(learningRate);
			//System.out.print(confFromFile.getConfFromInt(i).learningRate + ":");
			
			// NUMBER OF HIDDEN LAYER
			TextField nbHiddenLayer = new TextField(String.valueOf(confFromFile.getConfFromInt(i).numberOfhiddenLayers));
			nbHiddenLayer.setTextFormatter( new TextFormatter<>(newValue -> {
			    if (regexInt.matcher(newValue.getControlNewText()).matches()) {
			        return newValue;
			    }
			    return null;
			}));
			confLine.getChildren().add(nbHiddenLayer);
			System.out.print(confFromFile.getConfFromInt(i).numberOfhiddenLayers + "\n");
			
			// REMOVE BTN
			Button ereaseBtn =  new Button("X️");
			ereaseBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
		        public void handle(ActionEvent event) {
					VBoxView.getChildren().remove(((Node) event.getTarget()).getParent());
		        }
			});
			confLine.getChildren().add(ereaseBtn);
			
			VBoxView.getChildren().add(confLine);
		}
	}
	
	/**
	 * Write new config in config.txt
	 * WARNING THIS METHOD OVERWRITE THE FILE
	 * 
	 * @param newConf new configuration
	 * 
	 * @author Aubertin Emmanuel (aka aTHO_)
	 */
	private void writeConfig(String newConf)
	{
		try {
			BufferedWriter confFile = new BufferedWriter(new FileWriter("./resources/config.txt"));
			confFile.write(newConf); // Here we write in the config file
			confFile.close();
			System.out.println("OK - Config saved");
		} catch (IOException e) {
			System.out.println("ERROR - FAIL TO WRITE ");
			e.printStackTrace();
		}
	}
	
	/**
	 * Handle save config click
	 * 
	 * @author Aubertin Emmanuel (aka aTHO_)
	 */
	public void handleSaveBtn()
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
				System.out.print(((TextField) (confLine.getChildren().get(0))).getText()+ ":");
				newConf += ((TextField) (confLine.getChildren().get(0))).getText()+ ":";
				
				
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
		writeConfig(newConf);
	}

	/**
	 * Handle add new line to config
	 * 
	 * @author Aubertin Emmanuel (aka aTHO_)
	 */
	public void handleAddBtn() {
		HBox confLine = new HBox();
		
		// NAME OF THE LVL
		confLine.getChildren().add(new TextField("C"));
		
		// HIDDEN LAYER SIZE
		confLine.getChildren().add(new TextField("256"));

		// LEARNING RATE
		confLine.getChildren().add(new TextField("0.1"));
		
		// NUMBER OF HIDDEN LAYER
		confLine.getChildren().add(new TextField("2"));
		
		// REMOVE BTN
		Button ereaseBtn =  new Button("X️");
		ereaseBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
	        public void handle(ActionEvent event) {
				// Remove HBOX (current line) from the parent Vbox
				VBoxView.getChildren().remove(((Node) event.getTarget()).getParent());
	        }
		});
		confLine.getChildren().add(ereaseBtn);
		
		VBoxView.getChildren().add(confLine);
	}
}
