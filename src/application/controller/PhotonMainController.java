package application.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;
import static javafx.collections.FXCollections.observableList;

public class PhotonMainController implements EventHandler<ActionEvent> {

	@FXML
	private Button saveButton, undoButton, redoButton, settingsButton, dropperTool, bucketTool, selectorTool, brushTool, eraserTool, stampTool; 
	
	@FXML
	private Canvas drawZone;
		
	@FXML
	private ImageView drawImage;
	
	@FXML
	private ComboBox<String> fontPicker = new ComboBox<>(
            observableList(Font.getFontNames().stream().distinct().collect(toList())));
	
	private ImageView saveImage, undoImage, redoImage, selectorToolImage, dropperToolImage, bucketToolImage, brushToolImage, eraserToolImage, stampToolImage;
	
	@Override
	public void handle(ActionEvent event) {
		System.out.println("TEST");
		
	}
	
	@FXML
	void initialize() {
		
		initializeImages();
		initializeComponents();
		
	
		/*
		 * TODO
		 * Shapes/Tools/Stuff
		 * Start with a blank canvas that you can draw on
		 * Add layers to canvas
		 * load image onto canvas
		 * save image + canvas contents to 1 image file
		 */
		
		/*
		GraphicsContext gc = drawZone.getGraphicsContext2D();
		gc.setFill(Color.GRAY);
		gc.fillRect(0, 0, 750, 600);
		*/
		
		
			
	}
	
	private void initializeImages() {				
		saveImage = new ImageView(new Image(getClass().getResourceAsStream("/img/save-white.png")));
		saveImage.setFitHeight(25);
		saveImage.setFitWidth(25);
		saveButton.setGraphic(saveImage);
		
		undoImage = new ImageView(new Image(getClass().getResourceAsStream("/img/undo-white.png")));
		undoImage.setFitHeight(25);
		undoImage.setFitWidth(25);
		undoButton.setGraphic(undoImage);
		
		redoImage = new ImageView(new Image(getClass().getResourceAsStream("/img/redo-white.png")));
		redoImage.setFitHeight(25);
		redoImage.setFitWidth(25);
		redoButton.setGraphic(redoImage);
		
		selectorToolImage = new ImageView(new Image(getClass().getResourceAsStream("/img/cursor-white.png")));
		selectorToolImage.setFitHeight(30);
		selectorToolImage.setFitWidth(30);
		selectorTool.setGraphic(selectorToolImage);
		
		dropperToolImage = new ImageView(new Image(getClass().getResourceAsStream("/img/dropper-white.png")));
		dropperToolImage.setFitHeight(30);
		dropperToolImage.setFitWidth(30);
		dropperTool.setGraphic(dropperToolImage);
		
		bucketToolImage = new ImageView(new Image(getClass().getResourceAsStream("/img/bucket-white.png")));
		bucketToolImage.setFitHeight(30);
		bucketToolImage.setFitWidth(30);
		bucketTool.setGraphic(bucketToolImage);
		
		brushToolImage = new ImageView(new Image(getClass().getResourceAsStream("/img/brush-white.png")));
		brushToolImage.setFitHeight(30);
		brushToolImage.setFitWidth(30);
		brushTool.setGraphic(brushToolImage);
		
		eraserToolImage = new ImageView(new Image(getClass().getResourceAsStream("/img/eraser-white.png")));
		eraserToolImage.setFitHeight(30);
		eraserToolImage.setFitWidth(30);
		eraserTool.setGraphic(eraserToolImage);
		
		stampToolImage = new ImageView(new Image(getClass().getResourceAsStream("/img/text-white.png")));
		stampToolImage.setFitHeight(30);
		stampToolImage.setFitWidth(30);
		stampTool.setGraphic(stampToolImage);
		
		
	}
	
	private void createTooltips() {
		
	}
	
	private void initializeComponents() {
		//fontPicker.getSelectionModel().select(BindableValue<Font>);
	}
	
	public void settingsButtonPushed(ActionEvent event) throws IOException {
		
		FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("/application/view/PhotonSettings.fxml"));
		Parent settingsViewParent = settingsLoader.load();
		Scene settingsViewScene = new Scene(settingsViewParent);
		
		//gets stage information
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(settingsViewScene);
		window.setTitle("Settings");
		window.show();
		
	}

}
