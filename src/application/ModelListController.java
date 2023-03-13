package application;


import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import style.ButtonStyle;


/**
 * Controller of the cleaning model vue 
 * 
 * @author Aubertin Emmanuel (aka aTHO_)
 * @version 0.1
 * @since 0.1
 */
public class ModelListController {
	
	@FXML VBox VBoxFileList;
	@FXML Button btnDelete;
	
	@FXML public void initialize() {
		File trainFolder = new File("./resources/train/");

		// This filter will only include files ending with .srl
		FilenameFilter filter = new FilenameFilter() {
	        @Override
	        public boolean accept(File f, String name) {
	            return name.endsWith(".srl");
	        }
	    };
	    btnDelete.setSkin(new ButtonStyle(btnDelete));
	    
	   for(String file : trainFolder.list(filter)) {
		   System.out.println(file);
		   
		   // CHECKBOX
		   CheckBox checkbox = new CheckBox(file);
		   checkbox.setId(file);
		   
		   VBoxFileList.getChildren().add(checkbox);
	   }
	}
	
	/**
	 * Delete model file & fx componement which are checked
	 * 
	 * @author Aubertin Emmanuel (aka aTHO_)
	 */
	public void handleDeleteModel()
	{
		System.out.println("|----------------------------|");
		System.out.println("|-- DELETE MODEL FILE        |");
		System.out.println("|----------------------------|");
		
		ArrayList<CheckBox> deleteCheckBox = new ArrayList<CheckBox>();
		
		for(Node currMod : VBoxFileList.getChildren())
		{
			if( currMod instanceof CheckBox && ((CheckBox) currMod).isSelected()) {
				System.out.println("Current deletion :\n\t./resources/train/" + currMod.getId());
				File ModelSrl = new File("./resources/train/" + currMod.getId());
				if(ModelSrl.delete()) {
					deleteCheckBox.add(((CheckBox) currMod));
				}
				
			}
		}
		
		VBoxFileList.getChildren().removeAll(deleteCheckBox);
	}
	

}