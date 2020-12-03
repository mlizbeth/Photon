package application.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class PhotonSettingsController {
	
	@FXML
	private Label themeLabel, customThemeLabel;
	@FXML
	private Button okButton, applyButton, cancelButton;
	@FXML
	private RadioButton darkRadioButton, lightRadioButton;
	@FXML
	private ToggleGroup themeGroup;
	

	/**
	 * This is the method that is called when the OK button is pushed on the settings view. It applies
	 * the selected theme, then switches back to the main view.
	 * The ActionEvent event argument sends which type of event was triggered in regards to the Node.
	 * 
	 * @param event  an ActionEvent giving what type of event has occurred
	 * @throws IOException  If an input or output exception occurred
	 * @see String
	 * @see #apply(String, Scene)
	 * @see javafx.event.ActionEvent
	 * @see javafx.fxml.FXML
	 * @see javafx.fxml.FXMLLoader
	 * @see javafx.scene.Node
	 * @see javafx.scene.Parent
	 * @see javafx.scene.Scene
	 * @see javafx.scene.control.Button
	 * @see javafx.scene.control.RadioButton
	 * @see javafx.scene.control.ToggleGroup
	 * @see javafx.stage.Stage
	 */
	public void okButtonPushed(ActionEvent event) throws IOException {
		
		Scene scene = okButton.getScene();
		String currentCSS = "";
		final String darkCSS = "/css/dark.css";
		final String lightCSS = "/css/light.css";
		
		//Verify that a toggle is selected
		if (themeGroup.getSelectedToggle() != null)
		{
			
			//Get which toggle is selected, input into a String
			RadioButton selectedRadioButton = (RadioButton) themeGroup.getSelectedToggle();
			String toggleGroupValue = selectedRadioButton.getText();
			
			//If 'Dark' is selected, apply the dark theme
			if (toggleGroupValue.equals("Dark"))
			{
				apply(darkCSS, scene);
				currentCSS = "/css/dark.css";
				System.out.println("Dark");
			}
			//If 'Light' is selected, apply the light theme
			else if (toggleGroupValue.equals("Light"))
			{
				apply(lightCSS, scene);
				currentCSS = "/css/light.css";
				System.out.println("Light");
			}
			
			//Return back to the main view and apply the new theme
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/PhotonMain.fxml"));
			Parent mainViewParent = loader.load();
			Scene mainViewScene = new Scene(mainViewParent);
			apply(currentCSS, mainViewScene);
			
			//Get stage information
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			
			//Switch back to main view
			window.setScene(mainViewScene);
			window.setTitle("Photon Editor");
			window.show();
		}
	}
	
	/**
	 * This is the method that is called when the Apply button is pushed on the settings view. It applies the selected theme,
	 * but does not return to the main view.
	 * The ActionEvent event argument sends which type of event has occurred. 
	 * 
	 * @param event  The type of ActionEvent that occurred
	 * @throws IOException If an input or output exception occurred
	 * @see String
	 * @see #apply(String, Scene)
	 * @see javafx.event.ActionEvent
	 * @see javafx.fxml.FXML
	 * @see javafx.fxml.FXMLLoader
	 * @see javafx.scene.Parent
	 * @see javafx.scene.Scene
	 * @see javafx.scene.control.Button
	 * @see javafx.scene.control.RadioButton
	 * @see javafx.scene.control.ToggleGroup
	 */
	public void applyButtonPushed(ActionEvent event) throws IOException {
		
		Scene scene = themeLabel.getScene();
		String currentCSS = "";
		final String darkCSS = "/css/dark.css";
		final String lightCSS = "/css/light.css";
		
		//Verify that a toggle is selected
		if (themeGroup.getSelectedToggle() != null)
		{
			//Get which toggle is selected, store in a String
			RadioButton selectedRadioButton = (RadioButton) themeGroup.getSelectedToggle();
			String toggleGroupValue = selectedRadioButton.getText();
			
			//If 'Dark' is selected, apply the dark theme
			if (toggleGroupValue.equals("Dark"))
			{
				apply(darkCSS, scene);
				currentCSS = "/css/dark.css";
				System.out.println("Dark");
			}
			//Else if 'Light' is selected, apply the light theme
			else if (toggleGroupValue.equals("Light"))
			{
				apply(lightCSS, scene);
				currentCSS = "/css/light.css";
				System.out.println("Light");
			}
			
			//Load up the main view, apply the new theme to that view
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/PhotonMain.fxml"));
			Parent mainViewParent = loader.load();
			Scene mainViewScene = new Scene(mainViewParent);
			apply(currentCSS, mainViewScene);
		}
	}
	
	
	/**
	 * This is the method that is called when the Cancel button is pushed on the settings view. It returns to the main
	 * view without applying any selected theme.
	 * The ActionEvent event argument sends which type of event has occurred. 
	 * 
	 * @param event  The type of ActionEvent that occurred
	 * @throws IOException If an input or output exception occurred
	 * @see String
	 * @see #apply(String, Scene)
	 * @see javafx.event.ActionEvent
	 * @see javafx.fxml.FXML
	 * @see javafx.fxml.FXMLLoader
	 * @see javafx.scene.Node
	 * @see javafx.scene.Parent
	 * @see javafx.scene.Scene
	 * @see javafx.scene.control.Button
	 * @see javafx.scene.control.RadioButton
	 * @see javafx.scene.control.ToggleGroup
	 * @see javafx.stage.Stage
	 */
	public void cancelButtonPushed(ActionEvent event) throws IOException {
		
		//Load up the main view with FXMLLoader
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/PhotonMain.fxml"));
		Parent mainRoot = loader.load();
		Scene mainScene = new Scene(mainRoot);
		
		//Get the information for the current CSS theme, store it in a String
		String currentCSS = themeLabel.getScene().getStylesheets().toString();
		System.out.println(currentCSS);
		
		//If the current CSS theme is dark.css, apply it to the main view
		if (currentCSS.contains("dark.css"))
		{
			apply("/css/dark.css", mainScene);
		}
		//Else if the current CSS theme is light.css, apply it to the main view
		else if (currentCSS.contains("light.css"))
		{
			apply("/css/light.css", mainScene);
		}
		
		//Retrieves the stage information using the event source
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		//Switch back to the main view
		window.setScene(mainScene);
		window.setTitle("Photon Editor");
		window.show();
	}
	
	
	/**
	 * This method applies the selected theme to the scene sent in through the arguments.
	 * 
	 * @param applyCSS A String containing the file path to the CSS file to be applied
	 * @param scene A scene for the CSS file to be applied to
	 * @throws IOException If an input or output exception occurs
	 * @see String
	 * @see #apply(String, Scene)
	 * @see javafx.event.ActionEvent
	 * @see javafx.scene.Scene
	 */
	public void apply(String applyCSS, Scene scene) throws IOException
	{
		String lightCSS = "/css/light.css";
		String darkCSS = "/css/dark.css";
		
		scene.getStylesheets().remove(getClass().getResource(lightCSS).toExternalForm());
		scene.getStylesheets().remove(getClass().getResource(darkCSS).toExternalForm());
		scene.getStylesheets().add(getClass().getResource(applyCSS).toExternalForm());
	}

}
