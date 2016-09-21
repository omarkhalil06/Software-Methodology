package controller;

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
import model.BackEnd;
import model.ResourceManager;
import model.User;

/**
 * The UserController class controls what a user wants to do with their albums and photos.
 * 
 * @author Omar Khalil
 * @author Michelle Hwang
 */
public class UserController {
	
	@FXML
	private Button logoutBtn;
	@FXML
	private Button addBtn;
	@FXML
	private Button deleteBtn;
	@FXML
	private Button editBtn;
	@FXML
	private Button searchBtn;
	@FXML
	private Button openBtn;
	@FXML
	private Button saveBtn;
	
	@FXML
	protected ListView<Album> albumList;
	
	@FXML
	private Label albumName;
	@FXML
	private Label numPhotos;
	@FXML
	private Label oldestPhotoDate;
	@FXML
	private Label dateRange;
	@FXML
	private Label welcome;
	
	BackEnd backend;
	
	private PhotoAlbum photoAlbum;
	private User user;	
	
	/**
	 * Highlights album if clicked on.
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
	 * Sets information for each corresponding label.
	 * 
	 * @param album
	 */
	private void showDetails(Album album) {
		if (album != null) {
			if (album.getNumPhotos() == 0) {
				albumName.setText(album.getAlbumName());
				numPhotos.setText(album.getNumPhotos() + "");
				oldestPhotoDate.setText("-");
				dateRange.setText("");
			} else {
				albumName.setText(album.getAlbumName());
				numPhotos.setText(album.getNumPhotos() + "");
				oldestPhotoDate.setText(album.getOldest());
				dateRange.setText(album.getOldest() + " to " + album.getNewest());
			}
		} else {
			albumName.setText("");
			numPhotos.setText("");
			oldestPhotoDate.setText("");
			dateRange.setText("");
		}
	}
	
	/**
	 * Saves the file.
	 * 
	 * @throws IOException
	 */
	@FXML
	protected void save() throws IOException {
		try {
			ResourceManager.writeUsers(this.photoAlbum.getBackend(), "userfile");
		} catch (Exception e) {
			//System.out.println("Could not save file: " + e.getMessage());
		}
	}
	
	/**
	 * Logouts the user and window changes to login screen.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void logout(ActionEvent event) throws IOException {
		//System.out.println("In UserController: logout");
		
		((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/LoginScreen.fxml"));
		loader.load();
		Parent p = loader.getRoot();
		
		LoginScreenController controller = loader.getController();
		controller.setPhotoAlbum(this.photoAlbum);
		
		Stage stage = new Stage();
		stage.setScene(new Scene(p));
		stage.setTitle("Photo App");
		stage.show();
	}
	
	/**
	 * Deletes the selected album.
	 */
	@FXML
	protected void delete() {
		//System.out.println("In UserController: delete");

		int item = albumList.getSelectionModel().getSelectedIndex();
		if (item >= 0) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setContentText("Click OK to delete this album.");
			Optional<ButtonType> click = alert.showAndWait();
			if ((click.isPresent()) && (click.get() == ButtonType.OK)) {
				user.deleteAlbum(user.getAlbums().get(item));
				albumList.getItems().remove(item);
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText("No item selected");
			alert.setContentText("Please select an album from the list to be deleted");
			alert.showAndWait();
		}
		
	}
	
	/**
	 * Opens add album window.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void add(ActionEvent event) throws IOException {
		//System.out.println("In UserController: add");
		
		((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/AddNewAlbumScreen.fxml"));
		loader.load();
		Parent p = loader.getRoot();		
		
		AddNewAlbumController controller = loader.getController();
		controller.setPhotoAlbum(this.photoAlbum);
		controller.setUser(user);

		Stage stage = new Stage();
		stage.setScene(new Scene(p));
		stage.setTitle("Add New Album");
		stage.show();
	}
	
	/**
	 * Opens edit album window to rename album.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void edit(ActionEvent event) throws IOException {
		//System.out.println("In UserController: edit");
				
		int item = albumList.getSelectionModel().getSelectedIndex();
		if (item >= 0) {
			((Node) event.getSource()).getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/EditAlbumScreen.fxml"));
			loader.load();
			Parent p = loader.getRoot();
			
			EditAlbumController controller = loader.getController();
			controller.setPhotoAlbum(this.photoAlbum);
			controller.setUser(user);
			controller.setAlbum(user.getAlbums().get(item));
			controller.setAlbumText();
			
			Stage stage = new Stage();
			stage.setScene(new Scene(p));
			stage.setTitle("Edit Album");
			stage.show();			
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText("No item selected");
			alert.setContentText("Please select an album from the list to be edited");
			alert.showAndWait();
		}
	}
	
	/**
	 * Opens album to view photos.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void open(ActionEvent event) throws IOException {
		//System.out.println("In UserController: open");
				
		int item = albumList.getSelectionModel().getSelectedIndex();
		if (item >= 0) {
			((Node) event.getSource()).getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/AlbumScreen.fxml"));
			loader.load();
			Parent p = loader.getRoot();
			
			AlbumController controller = loader.getController();
			controller.setPhotoAlbum(this.photoAlbum);
			controller.setUser(user);
			controller.setAlbum(user.getAlbums().get(item));
			
			Stage stage = new Stage();
			stage.setScene(new Scene(p));
			controller.setPhotoList();

			stage.setTitle("Album: " + user.getAlbums().get(item).getAlbumName());
			stage.show();			
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText("No item selected");
			alert.setContentText("Please select an album from the list to be edited");
			alert.showAndWait();
		}
	}
	
	/**
	 * Opens search window.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void search(ActionEvent event) throws IOException {
		//System.out.println("In UserController: search");

		((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/SearchScreen.fxml"));
		loader.load();
		Parent p = loader.getRoot();
		
		SearchController controller = loader.getController();
		controller.setPhotoAlbum(this.photoAlbum);
		controller.setUser(this.user);
		
		Stage stage = new Stage();
		stage.setScene(new Scene(p));
		stage.setTitle("Search");
		stage.show();
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
		albumList.getSelectionModel().select(0);
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
	 * Sets value of welcome label.
	 *  
	 * @param users 
	 */
	public void setWelcome(User user) {
		welcome.setText("Welcome " + user.getUsername());
	}
	
}
