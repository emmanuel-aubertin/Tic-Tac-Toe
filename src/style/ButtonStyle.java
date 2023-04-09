package style;

import javafx.animation.FadeTransition;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.skin.ButtonSkin;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ButtonStyle extends ButtonSkin {

	public ButtonStyle(Button control) {
		super(control);
		
        final FadeTransition fadeIn = new FadeTransition(Duration.millis(300));
        fadeIn.setNode(control);
        fadeIn.setToValue(0.7);
        control.setOnMouseEntered(e -> fadeIn.playFromStart());

        final FadeTransition fadeOut = new FadeTransition(Duration.millis(300));
        fadeOut.setNode(control);
        fadeOut.setToValue(1);
        control.setOnMouseExited( e ->  {
        	fadeOut.playFromStart();
        	control.setCursor(Cursor.HAND);
        });

        control.setOpacity(1);
        control.setStyle( "-fx-background-color: #6C3B18;"
        				+ "-fx-text-fill: #fff;"
        				+ "-fx-alignment: center;"
        				+ "-fx-text-color: #fff;"
        				+ "-fx-padding: 6px;"
        				+ "-fx-border-insets: 3px;"
        				+ "-fx-background-insets: 3px;");
	}

}
