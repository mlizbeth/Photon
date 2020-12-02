package application.controller;

import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PhotonMainController implements EventHandler<ActionEvent> {

	@FXML
	private Label fontLabel, fontSizeLabel, fontSizeCountLabel, brushSizePickerLabel;
	@FXML
	private Slider fontSizePicker, brushSizePicker;
	@FXML
	private Button saveButton, undoButton, redoButton, settingsButton; 
	@FXML
	private Button circleTool, squareTool, triangleTool;
	@FXML
	private ToggleButton selectorTool, dropperTool, bucketTool, brushTool, eraserTool, stampTool;
	@FXML
	private ToggleGroup toolsToggleGroup;
	@FXML
	private ColorPicker colorPicker;
	@FXML
	private Canvas drawZone;
	@FXML
	private ImageView drawImage;
	@FXML
	private ComboBox<String> fontPicker;
	private final ObservableList<String> fonts = FXCollections.observableArrayList(Font.getFamilies());
	//private final Color ERASER_COLOR = Color.rgb(40, 41, 35);
	private ImageView saveImage, undoImage, redoImage, selectorToolImage, dropperToolImage, bucketToolImage, brushToolImage, eraserToolImage, stampToolImage;
	private ImageView circleToolImage, squareToolImage, triangleToolImage;
	private GraphicsContext gc;
	
	@Override
	public void handle(ActionEvent event) {
		System.out.println("TEST");
		
	}
	
	public void paintBrushHandler(ActionEvent event) {
		System.out.println("Brush");
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
		 * Shapes/Tools/Stuff
		 * Add layers to canvas (if time permits)
		 * load image onto canvas
		 * save image + canvas contents to 1 image file
		 * Fix drawing to ignore the second mouse button if one is pressed
		 * Brush sizes
		 * Text Tool
		 * Filters (if time permits)
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
	
	private void initializeComponents() {
		fontPicker.setItems(fonts);
		fontSizeCountLabel.setText(String.valueOf((int)(fontSizePicker.getValue())));
		brushSizePickerLabel.setText(String.valueOf((int)brushSizePicker.getValue()));
		
		
	}
	
	private void initializeListeners() {
		
		fontSizePicker.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				fontSizeCountLabel.setText(String.valueOf((int)fontSizePicker.getValue()));	
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
	}
	
	private void initializeCanvas() {
		gc = drawZone.getGraphicsContext2D();
		
		//gc.setFill(Color.WHITE);
		//gc.fillRect(0, 0, 860, 634);
		
		gc.setLineWidth(brushSizePicker.getValue());
		
		
		Line line = new Line();
		Rectangle rect = new Rectangle();
		Circle circ = new Circle();
		
		
		drawZone.setOnMousePressed(e -> {
			if(brushTool.isSelected()) {
				gc.setStroke(colorPicker.getValue());
				gc.beginPath();
				gc.lineTo(e.getX(), e.getY());
			}
			else if(eraserTool.isSelected()) {
				double lineWidth = gc.getLineWidth();
				gc.clearRect((e.getX() - (lineWidth / 2)), (e.getY() - (lineWidth / 2)), lineWidth, lineWidth);
				/*
				gc.setStroke(ERASER_COLOR);
				gc.beginPath();
				gc.lineTo(e.getX(), e.getY());
				*/
			}
			else if(dropperTool.isSelected()) {
				Image snapshot = drawZone.snapshot(new SnapshotParameters(), null);
				colorPicker.getCustomColors().add(snapshot.getPixelReader().getColor((int)e.getX(), (int)e.getY()));
				colorPicker.setValue(snapshot.getPixelReader().getColor((int)e.getX(), (int)e.getY()));
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
				/*
				gc.lineTo(e.getX(), e.getY());
				gc.stroke();
				*/
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
				/*
				gc.lineTo(e.getX(), e.getY());
				gc.stroke();
				gc.closePath();
				*/
			}
		});
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
