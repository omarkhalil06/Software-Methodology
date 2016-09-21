package controller;

import java.io.IOException;

import app.PhotoAlbum;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;

/**
 * The LoginScreenControllerClass implements a login screen interface for the user
 * to either sign in as admin or user.
 * 
 * @author Omar Khalil
 * @author Michelle Hwang
 */

public class LoginScreenController {

	private Stage stage;
	
	@FXML
	private TextField username;
	@FXML
	private Button login;
	
	private PhotoAlbum photoAlbum;
	
	@FXML
	private void initialize() {
		Platform.runLater(new Runnable() {

            @Override
            public void run() {
                username.requestFocus();
            }
        });
	}

	/**
	 * Logs in as either admin or user. Login screen closes and a new window corresponding
	 * to username input opens up.
	 * 
	 * @param event
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@FXML
	protected void login(ActionEvent event) throws IOException, ClassNotFoundException {
		
		boolean error = false;
		
		if (username.getText() != null) {
			String name = username.getText();
			//System.out.println(name);
			if (photoAlbum.getBackend().getUser(name) != null) {
				User user = photoAlbum.getBackend().getUser(name);
				if (name.equals("admin")) {
					((Node) event.getSource()).getScene().getWindow().hide();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/AdminScreen.fxml"));
					loader.load();
					Parent p = loader.getRoot();
					
					AdminController controller = loader.getController();
					controller.setPhotoAlbum(this.photoAlbum);
					controller.setUser(user);
					controller.setUserList(user);
					
					Stage stage = new Stage();
					stage.setScene(new Scene(p));
					stage.setTitle("Admin");
					stage.show();

				} else {
					((Node) event.getSource()).getScene().getWindow().hide();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/UserScreen.fxml"));
					loader.load();
					Parent p = loader.getRoot();
					
					UserController controller = loader.getController();
					controller.setAlbumList(user);
					controller.setUser(user);
					controller.setPhotoAlbum(this.photoAlbum);
					controller.setWelcome(user);
					
					Stage stage = new Stage();
					stage.setScene(new Scene(p));
					stage.setTitle("Logged in as: " + name);
					stage.show();
				}
			} else {
				error = true;
			}
		}
		
		if (error) {
			stage = (Stage) login.getScene().getWindow();
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(stage);
			alert.setTitle("Error!");
			alert.setHeaderText("User not found!");
			alert.setContentText("Please try again.");
			alert.showAndWait();
		}
		
	}
	
	/**
	 * Sets instance of PhotoAlbum.
	 * 
	 * @param photoAlbum
	 */
	public void setPhotoAlbum(PhotoAlbum photoAlbum) {
		this.photoAlbum = photoAlbum;
	}
	
}
