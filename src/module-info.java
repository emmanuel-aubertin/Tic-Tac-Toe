/**
 * 
 */
/**
 * @author manu
 *
 */
module Хрестики_нулики {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.xml;
	requires java.desktop;
	requires javafx.base;

	
	opens application to javafx.graphics, javafx.fxml;
}