package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import app.PhotoAlbum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.User;

/**
 * Manages the procedure to move a photo from one album
 * into a different album. Displays a list of albums that the user
 * can select to move the photo into.
 * 
 * @author Omar Khalil
 * @author Michelle Hwang
 * 
 */
public class MovePhotoController {

	@FXML
	private Button exitBtn;
	@FXML
	private Button moveBtn;
	
	@FXML
	ListView<Album> albumList;
	
	@FXML
	private Label destName;
	@FXML
	private Label originName;
	@FXML
	private Label fileName;
	
	private PhotoAlbum photoAlbum;
	private User user;
	private Album album; // origin album
	private Photo photo; // photo to be moved
	
	/**
	 * Prepares the album list and highlights an album if clicked on.
	 */
	@FXML
	private void initialize() {
		albumList.setCellFactory(new Callback<ListView<Album>, ListCell<Album>>() {

			@Override
			public ListCell<Album> call(ListView<Album> param) {
				ListCell<Album> cell = new ListCell<Album>() {
					protected void updateItem(Album t, boolean bool) {
						super.updateItem(t, bool);
						if (t != null) {
							setText(t.getAlbumName());
						} else {
							setText("");
						}
					}
				};
				return cell;
			}
			
		});
		albumList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showDetails(newValue));
	}
	
	/**
	 * Returns the user back to the Album Screen.
	 * 
	 * @param event
	 * @throws IOException
	 */
	protected void backToAlbum(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/AlbumScreen.fxml"));
		loader.load();
		Parent p = loader.getRoot();
		
		AlbumController controller = loader.getController();
		controller.setPhotoAlbum(photoAlbum);
		controller.setUser(this.user);
		controller.setAlbum(this.album);
		controller.setPhotoList();
		
		Stage stage = new Stage();
		stage.setScene(new Scene(p));
		stage.setTitle("Album: " + album.getAlbumName());
		stage.show();
	}
	
	/**
	 * Performs the photo move.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void move(ActionEvent event) throws IOException {
		int item = albumList.getSelectionModel().getSelectedIndex();
		if (item >= 0) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setContentText("Click OK to move this photo to this album.");
			Optional<ButtonType> click = alert.showAndWait();
			if ((click.isPresent()) && (click.get() == ButtonType.OK)) {
				
				Album dest = user.getAlbums().get(item);
				if (album.equals(dest)) {
					// destination is origin album
					Alert origin = new Alert(AlertType.WARNING);
					origin.setTitle("Error");
					origin.setHeaderText("Invalid Destination Album Selection");
					origin.setContentText("Selected origin. Photo already there. Try again.");
					origin.showAndWait();
				} else {
					File fileName = photo.getFileName();
					album.deletePhoto(photo);
					if (dest.addPhoto(fileName, photo.getCaption(), photo.getTags())) {
						// everything ok
						backToAlbum(event);
					} else {
						// duplicate in destination album detected
						Alert duplicate = new Alert(AlertType.WARNING);
						duplicate.setTitle("Error");
						duplicate.setHeaderText("Duplicate Detected");
						duplicate.setContentText("Duplicate photo in destination album. Try again.");
						duplicate.showAndWait();
					}					
				}
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText("No item selected");
			alert.setContentText("Please select an album as the destination.");
			alert.showAndWait();
		}
	}
	
	/**
	 * Returns to Album Screen.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void exit(ActionEvent event) throws IOException {
		backToAlbum(event);
	}
	
	/**
	 * Sets information for each corresponding label.
	 * 
	 * @param album
	 */
	private void showDetails(Album album) {
		if (album != null) {
			destName.setText(album.getAlbumName());
		} else {
			destName.setText("");
		}
	}
	
	/**
	 * Sets album list on listview for the user to see.
	 * 
	 * @param user
	 */
	public void setAlbumList(User user) {
		//System.out.println("In UserController: setAlbumList()");
		List<Album> albums = user.getAlbums();
		ObservableList<Album> list = FXCollections.observableArrayList();
		for (Album a : albums) {
			//System.out.println(a);
			list.add(a);
		}
		albumList.setItems(list);
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
	 * Sets value of current (origin) album.
	 * 
	 * @param album
	 */
	public void setAlbum(Album album) {
		this.album = album;
		originName.setText(album.getAlbumName());
	}
	
	/**
	 * Sets value of photo.
	 * 
	 * @param photo
	 */
	public void setPhoto(Photo photo) {
		this.photo = photo;
		fileName.setText(this.photo.getFileName().toString());
	}
	
}
