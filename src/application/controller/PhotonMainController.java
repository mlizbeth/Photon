package application.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import javax.imageio.ImageIO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Font;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Photon Photo Editor - AKA the next Photoshop! A simple paint program that allows you to import and image for editing, and create your own from scratch. Supports undo/redo operations, custom brush sizes,
 * fonts, etc.
 * @author Madeline Kotara - duk128
 * @version 1.0
 * @since 12-3-2020
 * @see java.util.Stack
 * @see javafx.scene.canvas.Canvas
 * @see javafx.scene.Node
 */

public class PhotonMainController implements EventHandler<ActionEvent> {

	@FXML
	private Label fontLabel, fontSizeLabel, fontSizeCountLabel, brushSizePickerLabel;
	@FXML
	private Slider fontSizePicker, brushSizePicker;
	@FXML
	private Button saveButton, undoButton, redoButton; 
	@FXML
	private MenuItem settingsMenu, menuItemOpen, menuItemSaveAs, helpMenuAbout;
	@FXML
	private MenuBar menuBar;
	@FXML
	private ToggleButton selectorTool, dropperTool, bucketTool, brushTool, eraserTool, stampTool, circleTool, squareTool, triangleTool;
	@FXML
	private ToggleGroup toolsToggleGroup;
	@FXML
	private ColorPicker colorPicker;
	@FXML
	private Canvas drawZone;
	@FXML
	private TextField stampText;
	@FXML
	private ComboBox<String> fontPicker;
	private final ObservableList<String> fonts = FXCollections.observableArrayList(Font.getFamilies());
	private ImageView saveImage, undoImage, redoImage, selectorToolImage, dropperToolImage, bucketToolImage, brushToolImage, eraserToolImage, stampToolImage;
	private ImageView circleToolImage, squareToolImage, triangleToolImage;
	private GraphicsContext gc;
	private Stack<Image> undoStack = new Stack<>();
	private Stack<Image> redoStack = new Stack<>();
	
	/**
	 * Custom event handlers to respond to mouse clicks
	 */
	
	@Override
	public void handle(ActionEvent event) {
		if(event.getSource().equals(undoButton)) {
			if(undoStack.size() == 1) {
				Image temp = drawZone.snapshot(new SnapshotParameters(), null);
				//WritableImage temp = scaleUpImage(drawZone, 2);
				gc.clearRect(0, 0, drawZone.getWidth(), drawZone.getHeight());
				redoStack.push(temp);
				undoStack.pop(); //remove the last undo
			}
			else if(undoStack.size() > 1) {
				Image temp = drawZone.snapshot(new SnapshotParameters(), null);
				//WritableImage temp = scaleUpImage(drawZone, 2);
				gc.clearRect(0, 0, drawZone.getWidth(), drawZone.getHeight());
				gc.drawImage(undoStack.pop(), 0, 0);
				redoStack.push(temp);
			}
		}
		else if(event.getSource().equals(redoButton)) {
			if(redoStack.size() != 0) {
				Image temp = drawZone.snapshot(new SnapshotParameters(), null);
				//WritableImage temp = scaleUpImage(drawZone, 2);
				undoStack.push(temp);
				gc.drawImage(redoStack.pop(), 0, 0);
			}
		}
		else if(event.getSource().equals(this.menuItemSaveAs)) {
			
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Photon - Save");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Portable Network Graphics", "*.png"));
			File file = fileChooser.showSaveDialog((Stage)circleTool.getScene().getWindow());
			
			if(file != null) {
				String stringName = file.getName();
				String stringExtension = stringName.substring(1+stringName.lastIndexOf(".")).toLowerCase();
				BufferedImage bufferedImage = SwingFXUtils.fromFXImage(drawZone.snapshot(new SnapshotParameters(), null), null);
				
				try {
					ImageIO.write(bufferedImage, stringExtension, file);
				} catch (IOException ioException) {
					
					ioException.printStackTrace();
				}
			}			
		}
		else if(event.getSource().equals(menuItemOpen)) {

			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Photon - Open");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));

			File file = fileChooser.showOpenDialog((Stage)circleTool.getScene().getWindow());			
			if(file != null) {

				Image drawingImage = new Image(file.toURI().toString());
				gc.drawImage(scaleImage(drawingImage, 886, 646, true), 0, 0);
			}						
			
		} else if(event.getSource().equals(saveButton)) {

			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Photon - Save");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Portable Network Graphics", "*.png"));
			File file = fileChooser.showSaveDialog((Stage)circleTool.getScene().getWindow());
			
			if(file != null) {
				String stringName = file.getName();
				String stringExtension = stringName.substring(1+stringName.lastIndexOf(".")).toLowerCase();
				BufferedImage bufferedImage = SwingFXUtils.fromFXImage(drawZone.snapshot(new SnapshotParameters(), null), null);
				
				try {
					ImageIO.write(bufferedImage, stringExtension, file);
				} catch (IOException ioException) {
					
					ioException.printStackTrace();
				}
			}			
		}
		else if(event.getSource().equals(helpMenuAbout)) {
			System.out.println("Test");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Photon Photo Editor");
			alert.setHeaderText(null);
			alert.setContentText("This program was created by: " + "\n" 
						+ "Madeline 'Maddie' Kotara - MainController/Paint Logic/Main GUI/Dark mode" + "\n" 
						+ "Graeson Smith - File saving/File opening/Canvas filters" + "\n" 
						+ "Samantha 'Cee' Jackman - Settings Menu/Theme Switcher/Light mode");
			alert.showAndWait();
		}
	}

	@FXML
	void initialize() {
		
		initializeImages();
		initializeComponents();
		initializeListeners();
		createTooltips();
		initializeCanvas();
	
		/*
		 * TODO
		 * Shapes
		 * Add image layers (unlikely)
		 * Fix drawing to ignore the second mouse button if one is pressed 
		 * Filters (unlikely)
		 * selection tool (unlikely)
		 * undo/redo options for other tool operations
		 * paint bucket tool (unlikely)
		 * fix quality loss on undo/redo (unlikely)
		 */
		
	}
	
	/**
	 * Initializes all buttons and loads the related image as the graphic for the button
	 */
	
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
		
		circleToolImage = new ImageView(new Image(getClass().getResourceAsStream("/img/circle.png")));
		circleToolImage.setFitHeight(50);
		circleToolImage.setFitWidth(50);
		circleTool.setGraphic(circleToolImage);
		
		squareToolImage = new ImageView(new Image(getClass().getResourceAsStream("/img/square.png")));
		squareToolImage.setFitHeight(50);
		squareToolImage.setFitWidth(50);
		squareTool.setGraphic(squareToolImage);
		
		triangleToolImage = new ImageView(new Image(getClass().getResourceAsStream("/img/triangle.png")));
		triangleToolImage.setFitHeight(50);
		triangleToolImage.setFitWidth(50);
		triangleTool.setGraphic(triangleToolImage);
		
	}
	
	/**
	 * Applies tooltips to the previous buttons so the user can see what each tool does by holding their mouse over it
	 */
	
	private void createTooltips() {
		Tooltip selectorTooltip = new Tooltip("Allows you to select and move objects on the canvas");
		Tooltip eyedropperTooltip = new Tooltip("Returns the color value of the specified pixel and changes the color picker to that value");
		Tooltip bucketTooltip = new Tooltip("Fills an enclosed area with the currently selected color in the color picker");
		Tooltip brushTooltip = new Tooltip("Allows you to draw on the canvas with the currently selected color in the color picker");
		Tooltip eraserTooltip = new Tooltip("Allows you to erase any drawings on the canvas");
		Tooltip stampTooltip = new Tooltip("Allows you to insert text on the canvas using the specified font style, size, and color as selected in the properties area");
		
		Tooltip.install(selectorTool, selectorTooltip);
		Tooltip.install(dropperTool, eyedropperTooltip);
		Tooltip.install(bucketTool, bucketTooltip);
		Tooltip.install(brushTool, brushTooltip);
		Tooltip.install(eraserTool, eraserTooltip);
		Tooltip.install(stampTool, stampTooltip);
	}
	
	/**
	 * Initializes the font and brush size sliders, loads all system fonts into the FontPicker Combobox
	 */
	
	private void initializeComponents() {
		fontPicker.setItems(fonts);
		fontSizeCountLabel.setText(String.valueOf((int)(fontSizePicker.getValue())));
		brushSizePickerLabel.setText(String.valueOf((int)brushSizePicker.getValue()));
	}
	
	/**
	 * Adds event listeners to all the tools so the UI can react an update properly
	 */
	
	private void initializeListeners() {
		
		fontSizePicker.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				fontSizeCountLabel.setText(String.valueOf((int)fontSizePicker.getValue()));	
				gc.setLineWidth(fontSizePicker.getValue());
			}
		});
		
		brushSizePicker.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				brushSizePickerLabel.setText(String.valueOf((int)brushSizePicker.getValue()));	
				gc.setLineWidth(brushSizePicker.getValue());
			}
		});
		
		selectorTool.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Not implemented");
				alert.setHeaderText(null);
				alert.setContentText("This feature is not yet implemented.");
				alert.showAndWait();
				selectorTool.setStyle("-fx-background-color: white;");
			}
			else {
				selectorTool.setStyle(null);
			}
		});
		
		dropperTool.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
				dropperTool.setStyle("-fx-background-color: white;");
			}
			else {
				dropperTool.setStyle(null);
			}
		});
		
		bucketTool.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Not implemented");
				alert.setHeaderText(null);
				alert.setContentText("This feature is not yet implemented.");
				alert.showAndWait();
				bucketTool.setStyle("-fx-background-color: white;");
			}
			else {
				bucketTool.setStyle(null);
			}
		});
		
		brushTool.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
				brushTool.setStyle("-fx-background-color: white;");
			}
			else {
				brushTool.setStyle(null);
			}
		});
		
		eraserTool.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
				eraserTool.setStyle("-fx-background-color: white;");
			}
			else {
				eraserTool.setStyle(null);
			}
		});
		
		stampTool.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
				stampTool.setStyle("-fx-background-color: white;");
			}
			else {
				stampTool.setStyle(null);
			}
		});
		
		squareTool.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Not implemented");
				alert.setHeaderText(null);
				alert.setContentText("This feature is not yet implemented.");
				alert.showAndWait();
				squareTool.setStyle("-fx-background-color: white;");
			}
			else {
				squareTool.setStyle(null);
			}
		});
		
		triangleTool.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Not implemented");
				alert.setHeaderText(null);
				alert.setContentText("This feature is not yet implemented.");
				alert.showAndWait();
				triangleTool.setStyle("-fx-background-color: white;");
			}
			else {
				triangleTool.setStyle(null);
			}
		});
		
		circleTool.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Not implemented");
				alert.setHeaderText(null);
				alert.setContentText("This feature is not yet implemented.");
				alert.showAndWait();
				circleTool.setStyle("-fx-background-color: white;");
			}
			else {
				circleTool.setStyle(null);
			}
		});
		
	}
	
	/**
	 * Initializes the canvas as well as includes the primary logic for drawing objects and pushing new images on to the undoStack so undo/redo operations can work
	 * @see undoStack
	 */
	
	private void initializeCanvas() {
		gc = drawZone.getGraphicsContext2D();
		
		gc.setLineWidth(brushSizePicker.getValue());
		//Image snapshot2 = drawZone.snapshot(new SnapshotParameters(), null);
		//undoStack.push(snapshot2);
		
		//Image testImg = new Image("file:src/img/test.jpg");
		//gc.drawImage(scaleImage(testImg, 886, 646, true), 0, 0);
		
		drawZone.setOnMousePressed(e -> {
			if(brushTool.isSelected()) {
				Image snapshot = drawZone.snapshot(new SnapshotParameters(), null);
				//WritableImage snapshot = scaleUpImage(drawZone, 2);
				undoStack.push(snapshot);
				gc.setStroke(colorPicker.getValue());
				gc.beginPath();
				gc.lineTo(e.getX(), e.getY());
			}
			else if(eraserTool.isSelected()) {
				Image snapshot = drawZone.snapshot(new SnapshotParameters(), null);
				//WritableImage snapshot = scaleUpImage(drawZone, 2);
				undoStack.push(snapshot);
				double lineWidth = gc.getLineWidth();
				gc.clearRect((e.getX() - (lineWidth / 2)), (e.getY() - (lineWidth / 2)), lineWidth, lineWidth);
			}
			else if(dropperTool.isSelected()) {
				Image snapshot = drawZone.snapshot(new SnapshotParameters(), null);
				colorPicker.getCustomColors().add(snapshot.getPixelReader().getColor((int)e.getX(), (int)e.getY()));
				colorPicker.setValue(snapshot.getPixelReader().getColor((int)e.getX(), (int)e.getY()));
			}
			else if(stampTool.isSelected()) {
				//TODO
				//create the textbox at starting coordinate
				Image snapshot = drawZone.snapshot(new SnapshotParameters(), null);
				//WritableImage snapshot = scaleUpImage(drawZone, 2);
				undoStack.push(snapshot);
				gc.setLineWidth(1);
				gc.setFont(Font.font(fontPicker.getValue(), fontSizePicker.getValue()));
				System.out.println(fontSizePicker.getValue());
				gc.setStroke(colorPicker.getValue());
				gc.setFill(colorPicker.getValue());
				gc.fillText(stampText.getText(), e.getX(), e.getY());
			}
		});
		
		drawZone.setOnMouseDragged(e -> {
	 		if(brushTool.isSelected()) {
				gc.lineTo(e.getX(), e.getY());
				gc.stroke();
			}
			else if(eraserTool.isSelected()) {
				double lineWidth = gc.getLineWidth();
				gc.clearRect((e.getX() - (lineWidth / 2)), (e.getY() - (lineWidth / 2)), lineWidth, lineWidth);
			}
			else if(stampTool.isSelected()) {
				//resize the text box to the new mouse coordinates
			}
		});
		
		drawZone.setOnMouseReleased(e -> {
			if(brushTool.isSelected()) {
				gc.lineTo(e.getX(), e.getY());
				gc.stroke();
				gc.closePath();
			}
			else if(eraserTool.isSelected()) {
				double lineWidth = gc.getLineWidth();
				gc.clearRect((e.getX() - (lineWidth / 2)), (e.getY() - (lineWidth / 2)), lineWidth, lineWidth);
			}
			else if(stampTool.isSelected()) {
				//set focus to the text box, allow user to type and apply the proper font settings
			}
		});
	}

	/**
	 * Logic responsible for switching the MainView to the SettingsView so themes can be applied.
	 * @param event fired by clicking on the object
	 * @throws IOException if the CSS or FXML file cannot be found
	 */
	public void settingsButtonPushed(ActionEvent event) throws IOException {
		
		FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("/application/view/PhotonSettings.fxml"));
		Parent settingsViewParent = settingsLoader.load();
		Scene settingsViewScene = new Scene(settingsViewParent);
		settingsViewScene.getStylesheets().add(getClass().getResource("/css/dark.css").toExternalForm());
		
		//gets stage information
		Stage window = (Stage) circleTool.getScene().getWindow();
		
		window.setScene(settingsViewScene);
		window.setTitle("Settings");
		window.show();
		
	}
	
	/**
	 * Scales down an image so it fits properly on the canvas and maintains aspect ratio
	 * @param sourceImg The source image being loaded from the disk
	 * @param targetWidth The width to be applied to the image, ideally the same as canvas width or smaller
	 * @param targetHeight The height to be applied to the image, ideally the same as canvas height or smaller
	 * @param preserveRatio true if the image ratio should be preserved so it doesn't look stretched
	 * @return Image that has been scaled down to the canvas dimensions
	 */
	private Image scaleImage(Image sourceImg, int targetWidth, int targetHeight, boolean preserveRatio) {
		ImageView imageView = new ImageView(sourceImg);
		imageView.setPreserveRatio(preserveRatio);
		imageView.setFitWidth(targetWidth);
		imageView.setFitHeight(targetHeight);
		return imageView.snapshot(null, null);
	}
	
	
	/**
	 * Currently not used/working. The undo/redo operations suffer from quality loss because of how snapshotting a canvas works. Any screen higher than 256 dpi will experience this loss of quality.
	 * @param canvas the canvas used to draw images to
	 * @param pixelScale the multiplier to upscale images
	 * @return WritableImage that has been scaled up without losing quality
	 */
	private WritableImage scaleUpImage(Canvas canvas, double pixelScale) {
		WritableImage img = new WritableImage((int)Math.rint(pixelScale*canvas.getWidth()), (int)Math.rint(pixelScale*canvas.getHeight()));
		SnapshotParameters params = new SnapshotParameters();
		params.setTransform(Transform.scale(pixelScale, pixelScale));
		System.out.println(img.getWidth() + " " + img.getHeight());
		return canvas.snapshot(params, img);
	}
	
	/**
	 * Alternative implementation
	 * Currently not used/working. The undo/redo operations suffer from quality loss because of how snapshotting a canvas works. Any screen higher than 256 dpi will experience this loss of quality.
	 * @param canvas the canvas used to draw images to
	 * @param pixelScale the multiplier to upscale images
	 */
	private void scaleUpImageTest(Canvas canvas, double pixelScale) {
		WritableImage img = new WritableImage((int)Math.rint(pixelScale*canvas.getWidth()), (int)Math.rint(pixelScale*canvas.getHeight()));
		SnapshotParameters params = new SnapshotParameters();
		params.setTransform(Transform.scale(pixelScale, pixelScale));
		System.out.println(img.getWidth() + " " + img.getHeight());
		Image test = canvas.snapshot(params, img);
		
	}
	
	/**
	 * Test implementation of scaleUpImage and scaleUpImageTest
	 * @param node the source node containing the image you want to scale, in this case, the canvas
	 * @param scale the multiplier used to upscale the images
	 * @return Image that has been scaled up without losing quality
	 */
	private Image scaleUpImage2(Node node, int scale) {
		
		final Bounds bounds = node.getLayoutBounds();

		System.out.println(bounds.getWidth() + " " + bounds.getHeight());

		WritableImage scaledWritableImage = new WritableImage(
				(int) Math.round(bounds.getWidth() * scale),
				(int) Math.round(bounds.getHeight() * scale));

		System.out.println(scaledWritableImage.getHeight() + " " + scaledWritableImage.getWidth());

		SnapshotParameters params = new SnapshotParameters();
		params.setTransform(Transform.scale(scale, scale));

		ImageView scaledImageView = new ImageView(node.snapshot(params, scaledWritableImage));
		scaledImageView.setFitWidth(bounds.getWidth());
		scaledImageView.setFitHeight(bounds.getHeight());

		return scaledImageView.snapshot(null, null);
	}
}
