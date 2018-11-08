package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Group root = new Group();
			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);

			// Setting title to the Stage
			primaryStage.setTitle("JGIT OPERATIONS");

			// creating custom children
			root = buildUI(root, primaryStage);
			primaryStage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Group buildUI(Group root, Stage primaryStage) {
		Frog blue = new Frog();

		root.getChildren().add(blue.createComponent(primaryStage));

		return root;
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
