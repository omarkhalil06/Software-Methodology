package controller;

import java.io.IOException;

import app.PhotoAlbum;
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
 * Manages the procedure to add a new album to the user's library.
 * 
 * @author Omar Khalil
 * @author Michelle Hwang
 *
 */
public class AddNewAlbumController {
	@FXML
	private TextField albumname;
	@FXML 
	private Button addBtn;
	@FXML
	private Button cancelBtn;
		
	private User user;
	
	private PhotoAlbum photoAlbum;

	/**
	 * Brings the user back to the User Screen.
	 * 
	 * @param event
	 * @throws IOException
	 */
	protected void backToUser(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/UserScreen.fxml"));
		loader.load();
		Parent p = loader.getRoot();
		
		UserController controller = loader.getController();
		controller.setPhotoAlbum(this.photoAlbum);
		controller.setUser(user);
		controller.setAlbumList(this.user);
		
		Stage stage = new Stage();
		stage.setScene(new Scene(p));
		stage.setTitle("Logged in as: " + user.getUsername());
		stage.show();
	}
	
	/**
	 * Adds a new user.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void add(ActionEvent event) throws IOException {
		//System.out.println("In AddNewAlbumController: add");
		
		String albumName = albumname.getText();
		
		if (albumName.compareTo("") == 0) {
			//System.out.println("\tERROR Must enter name of at least one character");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Invalid album name.");
			alert.setContentText("Please try again.");
			alert.showAndWait();
		} else {
			boolean status = user.addAlbum(albumname.getText());
		
			if (!status) {
				//System.out.println("ERROR Could not add album");

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error!");
				alert.setHeaderText("Duplicate album name entered.");
				alert.setContentText("Please try again.");
				alert.showAndWait();			
			} else {
				backToUser(event);
			}
		} 
	}

	/**
	 * Cancels the action.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void cancel(ActionEvent event) throws IOException  {
		//System.out.println("In AddNewAlbumController: cancel");
		backToUser(event);
	}
	
	/**
	 * Sets instance of PhotoAlbum.
	 * 
	 * @param photoAlbum
	 */
	public void setPhotoAlbum(PhotoAlbum photoAlbum) {
		this.photoAlbum = photoAlbum;
	}
	
	/**
	 * Sets value of user.
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
}
