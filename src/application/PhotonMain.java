package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PhotonMain extends Application {

	private final String DARK_CSS = "/css/dark.css";
	private final String APPLICATION_TITLE = "Photon Editor";
	private final String MAIN_FXML = "/application/view/PhotonMain.fxml";
	private final Image APPLICATION_ICON = new Image("file:src/img/icon.jpg");
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader mainLoader = new FXMLLoader(getClass().getResource(MAIN_FXML));
		Parent mainRoot = mainLoader.load();
		Scene mainScene = new Scene(mainRoot);
		
		mainScene.getStylesheets().add(getClass().getResource(DARK_CSS).toExternalForm());
		primaryStage.setScene(mainScene);
		primaryStage.setTitle(APPLICATION_TITLE);
		primaryStage.getIcons().add(APPLICATION_ICON);
		primaryStage.show();
		primaryStage.setResizable(false);
		
	}
}
