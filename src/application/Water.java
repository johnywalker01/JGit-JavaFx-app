package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Water {
	
	public static void showWarning(final String title, final String headerText, final String content) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(content);

		alert.showAndWait();
	}

}
