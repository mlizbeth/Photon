package application.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PhotonMainController implements EventHandler<ActionEvent> {

	@FXML
	private Button saveButton, undoButton, redoButton; 
	
	private Image saveImage, undoImage, redoImage;
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	void initialize() {
		saveImage = new Image(getClass().getResourceAsStream("/img/save.png"));
		saveButton.setGraphic(new ImageView(saveImage));
		
		undoImage = new Image(getClass().getResourceAsStream("/img/undo-50.png"));
		undoButton.setGraphic(new ImageView(undoImage));
		
		redoImage = new Image(getClass().getResourceAsStream("/img/redo-50.png"));
		redoButton.setGraphic(new ImageView(redoImage));

	}

}
