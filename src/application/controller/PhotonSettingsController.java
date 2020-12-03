package application.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
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
	private ColorPicker colorPicker;
	@FXML
	private RadioButton darkRadioButton, lightRadioButton;
	@FXML
	private ToggleGroup themeGroup;
	

	
	public void okButtonPushed(ActionEvent event) throws IOException {
		
		Scene scene = themeLabel.getScene();
		String currentCSS = "";
		final String darkCSS = "/css/dark.css";
		final String lightCSS = "/css/light.css";
		
		//when ok button is pushed, apply the new css and switch back to the main view
		//applies just in case the user did not press apply beforehand
		if (themeGroup.getSelectedToggle() != null)
		{
			
			RadioButton selectedRadioButton = (RadioButton) themeGroup.getSelectedToggle();
			String toggleGroupValue = selectedRadioButton.getText();
			
			if (toggleGroupValue.equals("Dark"))
			{
				apply(darkCSS, scene);
				currentCSS = "/css/dark.css";
				System.out.println("Dark");
			}
			else if (toggleGroupValue.equals("Light"))
			{
				apply(lightCSS, scene);
				currentCSS = "/css/light.css";
				System.out.println("Light");
			}
			//return back to the main view
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/PhotonMain.fxml"));
			Parent mainViewParent = loader.load();
			Scene mainViewScene = new Scene(mainViewParent);
			apply(currentCSS, mainViewScene);
			
			//gets stage information
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(mainViewScene);
			window.setTitle("Photon Editor");
			window.show();
		}
	}
	
	
	public void applyButtonPushed(ActionEvent event) throws IOException {
		
		Scene scene = themeLabel.getScene();
		String currentCSS = "";
		final String darkCSS = "/css/dark.css";
		final String lightCSS = "/css/light.css";
		
		//when ok button is pushed, apply the new css and switch back to the main view
		//applies just in case the user did not press apply beforehand
		if (themeGroup.getSelectedToggle() != null)
		{
			
			RadioButton selectedRadioButton = (RadioButton) themeGroup.getSelectedToggle();
			String toggleGroupValue = selectedRadioButton.getText();
			
			if (toggleGroupValue.equals("Dark"))
			{
				apply(darkCSS, scene);
				currentCSS = "/css/dark.css";
				System.out.println("Dark");
			}
			else if (toggleGroupValue.equals("Light"))
			{
				apply(lightCSS, scene);
				currentCSS = "/css/light.css";
				System.out.println("Light");
			}
			//return back to the main view
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/PhotonMain.fxml"));
			Parent mainViewParent = loader.load();
			Scene mainViewScene = new Scene(mainViewParent);
			apply(currentCSS, mainViewScene);
		}
	}
	
	public void cancelButtonPushed(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/PhotonMain.fxml"));
		Parent mainRoot = loader.load();
		Scene mainScene = new Scene(mainRoot);
		
		//get current css information
		String currentCSS = themeLabel.getScene().getStylesheets().toString();
		System.out.println(currentCSS);
		if (currentCSS.contains("dark.css"))
		{
			apply("/css/dark.css", mainScene);
		}
		else if (currentCSS.contains("light.css"))
		{
			apply("/css/light.css", mainScene);
		}
		
		//gets stage information
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(mainScene);
		window.setTitle("Photon Editor");
		window.show();
	}
	
	public void apply(String applyCSS, Scene scene) throws IOException
	{
		String lightCSS = "/css/light.css";
		String darkCSS = "/css/dark.css";
		
		scene.getStylesheets().remove(getClass().getResource(lightCSS).toExternalForm());
		scene.getStylesheets().remove(getClass().getResource(darkCSS).toExternalForm());
		scene.getStylesheets().add(getClass().getResource(applyCSS).toExternalForm());
	}

}
