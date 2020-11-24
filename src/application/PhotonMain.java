package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PhotonMain extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/application/view/PhotonMain.fxml"));
		Parent mainRoot = mainLoader.load();
		Scene mainScene = new Scene(mainRoot);
		
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("Photon Editor");
		primaryStage.show();
		
	}

}
