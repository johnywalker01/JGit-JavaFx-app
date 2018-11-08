package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class Frog {
	private static final String CHECK_AND_INIT_GIT = "Check and Init git folder";
	private static final String CLONE_REMOTE_REPO = "Clone Remote Repo";
	private static final String COMMIT_AND_PUSH = "Commit And Push";
	private static final String COMMIT_ONLY = "Commit";
	private static final String VALID = "valid";
	private static final String INVALID = "invalid";
	private static final String LOADING = "Loading";
	private static final String WARNING = "Warning";
	private static final String ERROR_MSG_INVALID_FOLDER = "Please Select a valid Folder.";
	private static final String ERROR_MSG_INVALID_REMOTE_URL = "Please Enter some valid Remote Repo URL";
	
	private File selectedFolder;
	private Stage primaryStage;
	private ImageView ivAction = new ImageView();
	private Image imageValidating = null;
	private Image imageValid = null;
	private Image imageInvalid = null;
	TextField txtGitRepo = new TextField();

	public Node createComponent(Stage primaryStage) {

		this.primaryStage = primaryStage;

		Button buttonGit = new Button(CHECK_AND_INIT_GIT);
		buttonGit.setOnAction(e -> {
			doGitAction(0);
		});
		Button buttonGitCommitOnly = new Button(COMMIT_ONLY);
		buttonGitCommitOnly.setOnAction(e -> {
			doGitAction(1);
		});
		Button buttonGitCommitAndPush = new Button(COMMIT_AND_PUSH);
		buttonGitCommitAndPush.setOnAction(e -> {
			doGitAction(2);
		});
		Button buttonClone = new Button(CLONE_REMOTE_REPO);
		buttonClone.setOnAction(e -> {
			doGitAction(3);
		});

		// Creating the layout.
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(10);
		buttonBox.setAlignment(Pos.CENTER_LEFT);
		buttonBox.getChildren().add(buttonGit);
		buttonBox.getChildren().add(buttonGitCommitOnly);
		buttonBox.getChildren().add(buttonGitCommitAndPush);
		buttonBox.getChildren().add(buttonClone);

		VBox mainContainer = new VBox();
		mainContainer.setPadding(new Insets(5, 10, 5, 10));
		mainContainer.setAlignment(Pos.CENTER_LEFT);
		mainContainer.setSpacing(10);
		mainContainer.prefHeightProperty().bind(primaryStage.getScene().heightProperty());
		mainContainer.prefWidthProperty().bind(primaryStage.getScene().widthProperty());

		mainContainer.getChildren().add(getFolderSelector());
		mainContainer.getChildren().add(getRemoteUrlComp());
		mainContainer.getChildren().add(buttonBox);

		return mainContainer;
	}

	private Node getFolderSelector() {
		Label info = new Label();
		info.setPadding(new Insets(2, 2, 2, 2));
		info.setStyle("-fx-min-width: 100px; -fx-border-color: black; -fx-border-width: 1px 1px 1px 0px;");
		
		DirectoryChooser folderChooser = new DirectoryChooser();

		Button button = new Button("Select a folder");
		button.setStyle("-fx-min-width: 100px;");
		button.setOnAction(e -> {
			selectedFolder = folderChooser.showDialog(primaryStage);
			
			if (selectedFolder != null) {
				System.out.println(selectedFolder.getAbsolutePath());
				info.setText(selectedFolder.getAbsolutePath());
				Tooltip tooltip = new Tooltip(selectedFolder.getAbsolutePath());
				info.setTooltip(tooltip);
			} else {
				System.out.println(ERROR_MSG_INVALID_FOLDER);
			}
		});
		
		HBox mainBox  = new HBox();
		mainBox.setAlignment(Pos.CENTER_LEFT);
		mainBox.setSpacing(1);
		
		mainBox.getChildren().add(button);
		mainBox.getChildren().add(info);
		
		return  mainBox;
	}

	private void doGitAction(int action) {
		if (selectedFolder != null) {
			switch (action) {
			case 0:
				checkAndInitGit();
				break;
			case 1:
				commitOnly();
				break;
			case 2:
				commitAndPush();
				break;
			case 3:
				cloneGitRepoToLocal();
				break;

			default:
				break;
			}
		}
		else {
			System.out.println(ERROR_MSG_INVALID_FOLDER);
			Water.showWarning(WARNING, null, ERROR_MSG_INVALID_FOLDER);
		}
	}

	private void cloneGitRepoToLocal() {
		if (!txtGitRepo.getText().isEmpty()) {
			GitTools.cloneToLocalRepo(selectedFolder.getAbsolutePath(), txtGitRepo.getText());
		}
		else {
			System.out.println(ERROR_MSG_INVALID_REMOTE_URL);
			Water.showWarning(WARNING, null, ERROR_MSG_INVALID_REMOTE_URL);
		}
	}

	private void checkAndInitGit() {
		GitTools.initGit(selectedFolder.getAbsolutePath());
	}

	private void commitOnly() {
		GitTools.doCommit(selectedFolder.getAbsolutePath());
	}

	private void commitAndPush() {
		if(!txtGitRepo.getText().isEmpty()) {
			GitTools.doICP(selectedFolder.getAbsolutePath(), txtGitRepo.getText());
		}else {
			System.out.println(ERROR_MSG_INVALID_REMOTE_URL);
			Water.showWarning(WARNING, null, ERROR_MSG_INVALID_REMOTE_URL);
		}
	}

	private Node getRemoteUrlComp() {
		Image imageInternet = null;
		try {
			imageInternet = new Image(new FileInputStream("./images/Internet-Real-icon.png"));
			imageValid = new Image(new FileInputStream("./images/Accept-icon.png"));
			imageInvalid = new Image(new FileInputStream("./images/Extras-Close-icon.png"));
			imageValidating = new Image(new FileInputStream("./images/tenor.gif"));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

//		TextField txtGitRepo = new TextField();
		txtGitRepo.setPromptText("Enter Remote GIT Repo URL");
		txtGitRepo.setStyle("-fx-min-width: 400px;");

		Button buttonValidate = new Button("");
		buttonValidate.setStyle("-fx-max-width: 18px;");
		buttonValidate.setTooltip(new Tooltip("Click to Validate"));
		if (imageInternet != null) {
			buttonValidate.setGraphic(new ImageView(imageInternet));
		}

		buttonValidate.setOnAction(e -> {
			if (!txtGitRepo.getText().isEmpty()) {
				String remoteRepo = txtGitRepo.getText();
				System.out.println(remoteRepo);
				validateGitUrl(remoteRepo);
			}
			else {
				System.out.println(ERROR_MSG_INVALID_REMOTE_URL);
				Water.showWarning(WARNING, null, ERROR_MSG_INVALID_REMOTE_URL);
			}
		});
		
        ivAction.setFitWidth(18);
        ivAction.setPreserveRatio(true);
        ivAction.setSmooth(true);
        ivAction.setCache(true);
        
		HBox mainBox = new HBox();
		mainBox.setAlignment(Pos.CENTER_LEFT);
		mainBox.setSpacing(1);

		mainBox.getChildren().add(txtGitRepo);
		mainBox.getChildren().add(buttonValidate);
		mainBox.getChildren().add(ivAction);

		return mainBox;
	}

	private void displayProcessIcon(final String mojo) {
		switch (mojo) {
		case VALID:
			if (imageValid != null) {
				ivAction.setImage(imageValid);
			}
			break;
		case INVALID:
			if (imageInvalid != null) {
				ivAction.setImage(imageInvalid);
			}
			break;

		default:
			break;
		}
	}

	private void validateGitUrl(String remoteUrl) {
		boolean validURL = GitTools.validateRemoteUrl(remoteUrl);

		if (validURL) {
			displayProcessIcon(VALID);
		}
		else {
			displayProcessIcon(INVALID);
		}
	}

}
