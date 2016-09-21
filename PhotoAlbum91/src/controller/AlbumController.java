package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import app.PhotoAlbum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;

/**
 * Manages the album screen and operations to add, edit, delete, display, 
 * or enlarge a photo, displays a selected photo's tags, as well as 
 * thumbnails and captions of all photos in the album.
 * 
 * @author Omar Khalil
 * @author Michelle Hwang
 */
public class AlbumController {

	@FXML
	private Label caption;
	@FXML 
	private Label date;
	
	@FXML
	private TextField type;
	@FXML
	private TextField value;
	
	@FXML
	private Button addBtn;
	@FXML
	private Button deleteBtn;
	@FXML
	private Button editBtn;
	@FXML
	private Button moveBtn;
	@FXML
	private Button openBtn;
	@FXML
	private Button nextBtn;
	@FXML
	private Button prevBtn;
	@FXML
	private Button exitBtn;
	
	@FXML
	private ListView<Tag> tagList;
	@FXML
	private TilePane gallery;
	@FXML
	private Pane preview;
	
	private PhotoAlbum photoAlbum;
	private User user;
	private Album album;
	private Photo photo;
	
	/**
	 * Initializes a selected photo's list of tags in ListView tagList.
	 */
	@FXML
	protected void initialize() {
		tagList.setCellFactory(new Callback<ListView<Tag>, ListCell<Tag>>() {

			@Override
			public ListCell<Tag> call(ListView<Tag> param) {
				ListCell<Tag> cell = new ListCell<Tag>() {
					protected void updateItem(Tag t, boolean bool) {
						super.updateItem(t, bool);
						if (t != null) {
							setText(t.getType() + " - " + t.getValue());
						} else {
							setText("");
						}
					}
				};
				return cell;
			}
			
		});
	}
	
	/**
	 * Displays selected photo and caption.
	 * 
	 * @param photo Photo to be displayed
	 */
	private void showDetails(Photo photo) {
		if (photo != null) {
			caption.setText(photo.getCaption());	
			date.setText(photo.printDate());
			
			preview.getChildren().clear();
			ImageView imageView = new ImageView();
			Image image = new Image(photo.getFileName().toURI().toString(), 245, 174, true, true);
			imageView.setImage(image);
			imageView.setPreserveRatio(true);
			preview.getChildren().add(imageView);
			this.photo = photo; // set currently displaying photo

			setTagList();			
		} else {
			caption.setText("");
			date.setText("");
		}
	}
	
	/**
	 * Opens selected photo in larger window. 
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void open(ActionEvent event) throws IOException {
		if (photo == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText("No item selected");
			alert.setContentText("Please select a photo to be deleted");
			alert.showAndWait();
			return;
		}
		Dialog<Photo> dialog = new Dialog<>();
    	dialog.setTitle("Photo: " + this.photo.getCaption());
		Image image = new Image(photo.getFileName().toURI().toString(), 500, 500, true, true);
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setPreserveRatio(true);
      	dialog.getDialogPane().setContent(imageView);
    	ButtonType cancel = new ButtonType("Close", ButtonData.CANCEL_CLOSE);
    	dialog.getDialogPane().getButtonTypes().add(cancel);
    	dialog.showAndWait();
	}
	
	/**
	 * Enables the selected photo to be moved into a different album. Sets up
	 * a move photo screen and controller.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void move(ActionEvent event) throws IOException {
		if (photo == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText("No item selected");
			alert.setContentText("Please select a photo to be deleted");
			alert.showAndWait();
			return;
		}	

		((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/MovePhotoScreen.fxml"));
		loader.load();
		Parent p = loader.getRoot();
		
		MovePhotoController controller = loader.getController();
		controller.setPhotoAlbum(this.photoAlbum);
		controller.setUser(this.user);
		controller.setAlbum(this.album);			// current album
		controller.setAlbumList(this.user); 		// list of albums
		controller.setPhoto(this.photo);			// photo to move
		
		Stage stage = new Stage();
		stage.setScene(new Scene(p));
		stage.show();
		
	}
	
	/**
	 * Exits album screen and returns to user screen.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void exit(ActionEvent event) throws IOException {
		//System.out.println("In AlbumController: exit");
		
		((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/UserScreen.fxml"));
		loader.load();
		Parent p = loader.getRoot();
		
		UserController controller = loader.getController();
		controller.setPhotoAlbum(this.photoAlbum);
		controller.setUser(this.user);
		controller.setAlbumList(this.user);
		controller.setWelcome(user);
		
		Stage stage = new Stage();
		stage.setTitle("Logged in as: " + user.getUsername());
		stage.setScene(new Scene(p));
		stage.show();
	}
	
	/**
	 * Removes a selected photo from the current album.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void delete(ActionEvent event) throws IOException {
		//System.out.println("In AlbumController: delete");
		
		if (this.photo != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setContentText("Click OK to delete this photo.");
			Optional<ButtonType> click = alert.showAndWait();
			if ((click.isPresent()) && (click.get() == ButtonType.OK)) {
				album.deletePhoto(photo);
				setPhotoList();
				preview.getChildren().clear();
				caption.setText("");
				date.setText("");
				tagList.getItems().clear();
				this.photo = null;
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText("No item selected");
			alert.setContentText("Please select a photo to be deleted");
			alert.showAndWait();
		} 
	} 
	
	/**
	 * Adds a photo to the current album.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void add(ActionEvent event) throws IOException {
		//System.out.println("In AlbumController: add");
		
		((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/AddNewPhotoScreen.fxml"));
		loader.load();
		Parent p = loader.getRoot();		
		
		AddNewPhotoController controller = loader.getController();
		controller.setPhotoAlbum(photoAlbum);
		controller.setUser(user);
		controller.setAlbum(album);

		Stage stage = new Stage();
		controller.setStage(stage);
		stage.setScene(new Scene(p));
		stage.setTitle("Add New Photo");
		stage.show();
		
	}
	
	/**
	 * Enables the selected photo to be edited. Sets up the edit photo screen
	 * and controller.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void edit(ActionEvent event) throws IOException {
		//System.out.println("In AlbumController: edit");
		
		if (photo != null) {
			((Node) event.getSource()).getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/EditPhotoScreen.fxml"));
			loader.load();
			Parent p = loader.getRoot();		
			
			EditPhotoController controller = loader.getController();
			
			// cleaner to do some of these in initialize() method
			controller.setPhotoAlbum(photoAlbum);
			controller.setUser(user);
			controller.setAlbum(album);
			controller.setPhoto(photo);
			controller.setFile(photo.getFileName());
			controller.setCaption(photo.getCaption());
			controller.setInitTags();

			Stage stage = new Stage();
			stage.setScene(new Scene(p));
			stage.setTitle("Edit Photo: " + photo.getFileName());
			stage.show();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText("No item selected");
			alert.setContentText("Please select a photo to be edited");
			alert.showAndWait();
		}
	
	}
	
	/**
	 * Retrieves the currently displayed photo and displays the next
	 * photo in the album.
	 */
	@FXML
	protected void next() {
		//System.out.println("In AlbumController: next");
		if (photo != null) {
			Photo nextPhoto;
			List<Photo> photos = album.getPhotos();
			int index = photos.indexOf(photo);
			
			// check if next is within array bounds
			if (index + 1 < photos.size()) {
				nextPhoto = photos.get(index + 1);
				showDetails(nextPhoto);
			}
		}
	}
	/**
	 * Retrieves the currently displayed photo and displays the previous
	 * photo in the album.
	 */
	@FXML
	protected void prev() {
		//System.out.println("In AlbumController: prev");
		if (photo != null) {
			Photo prevPhoto;
			List<Photo> photos = album.getPhotos();
			int index = photos.indexOf(photo);
			
			// check if prev is within array bounds
			if (index - 1 >= 0) {
				prevPhoto = photos.get(index - 1);
				showDetails(prevPhoto);
			}
		}
	}
	
	/**
	 * Sets tag list on listview for the user to see.
	 * 
	 * @param photo
	 */
	@FXML
	public void setTagList() {
		if (photo.getTags() == null) {
			return;
		}
		List<Tag> tags = photo.getTags();
		ObservableList<Tag> list = FXCollections.observableArrayList();
		for (Tag t : tags) {
			list.add(t);
		}
		tagList.setItems(list);
	}
	
	// need to add captions to thumbnails if time remaining
	@FXML
	public void setPhotoList() {
		gallery.getChildren().clear();
		
		List<Photo> photos = this.album.getPhotos();
		for (Photo p : photos) {
			File f = p.getFileName();
			Image image = new Image(f.toURI().toString(), 100, 0, true, true);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(100);
			
			imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
						if(mouseEvent.getClickCount() == 1){
							//System.out.println("Photo clicked: " + f.toString());
							showDetails(p);
						}
					}
				}
			});				

			gallery.getChildren().add(imageView);
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
