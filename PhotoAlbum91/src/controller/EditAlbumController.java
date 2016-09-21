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
import model.Album;
import model.User;

/**
 * Manages the procedures to edit an album (renaming). 
 *  
 * @author Omar Khalil
 * @author Michelle Hwang
 *
 */
public class EditAlbumController {
	@FXML
	private TextField albumname;
	@FXML 
	private Button editBtn;
	@FXML
	private Button cancelBtn;
	
	private Album album;
	
	private User user;
	
	private PhotoAlbum photoAlbum;
	
	/**
	 * Sets the prompt text of the album name TextField.
	 */
	public void setAlbumText() {
		albumname.setText(this.album.getAlbumName());
	}

	/**
	 * Returns the user back to the User Screen.
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
		stage.show();
	}
	
	/**
	 * Saves the user's album name edit.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void edit(ActionEvent event) throws IOException {
		//System.out.println("In EditAlbumController: edit");

		String newAlbumName = albumname.getText();
		
		if (newAlbumName.compareTo("") == 0) {
			//System.out.println("\tERROR Must enter name of at least one character");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Invalid album name.");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			
		} else if (newAlbumName.compareTo(album.getAlbumName()) == 0) {
			//System.out.println("\tDid not change album name");
			backToUser(event);
			
		} else if (user.containsAlbum(newAlbumName)) {			
			//System.out.println("\tERROR Could not rename album");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Duplicate album name entered.");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			
		} else {
			user.editAlbum(newAlbumName, album);
			backToUser(event);
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
		//System.out.println("In EditAlbumController: cancel");
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
	
	/**
	 * Sets value of album.
	 * 
	 * @param album
	 */
	public void setAlbum(Album album) {
		this.album = album;
	}
	
}
