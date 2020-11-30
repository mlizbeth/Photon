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
		
		initalizeImages();
			
	}
	
	private void initalizeImages() {
		
		/*
		 * old
		 * 
		 * saveImage = new Image(getClass().getResourceAsStream("/img/save.png"));
		 * saveButton.setGraphic(new ImageView(saveImage));
		 */
		
		ImageView saveImage = new ImageView(new Image(getClass().getResourceAsStream("/img/save.png")));
		saveImage.setFitHeight(25);
		saveImage.setFitWidth(25);
		saveButton.setGraphic(saveImage);
		
		ImageView undoImage = new ImageView(new Image(getClass().getResourceAsStream("/img/undo-50.png")));
		undoImage.setFitHeight(25);
		undoImage.setFitWidth(25);
		undoButton.setGraphic(undoImage);
		
		ImageView redoImage = new ImageView(new Image(getClass().getResourceAsStream("/img/redo-50.png")));
		redoImage.setFitHeight(25);
		redoImage.setFitWidth(25);
		redoButton.setGraphic(redoImage);
	}

}
