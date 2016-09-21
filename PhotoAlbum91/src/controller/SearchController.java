package controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.JapaneseChronology;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.User;

public class SearchController {

	@FXML
	private TextField type;
	@FXML
	private TextField value;
	@FXML
	private Button searchTagBtn;
	
	@FXML
	private DatePicker oldest;
	@FXML
	private DatePicker newest;
	@FXML
	private Button searchDateBtn;
	
	@FXML
	private TilePane gallery;
	
	@FXML
	protected ListView<Photo> photoList;
	@FXML
	private Button addBtn;
	@FXML
	private Button deleteBtn;
	@FXML
	private Button clearBtn;
	
	@FXML
	private Button exitBtn;
	
	private List<Photo> resultList;
	private List<Photo> selectedList;
	
	private PhotoAlbum photoAlbum;
	private User user;
	
	/**
	 * Initializes selectedList and resultList. Prepares ListView of
	 * selected photos.
	 */
	@FXML
	protected void initialize() {
		selectedList = new ArrayList<Photo>();
		resultList = new ArrayList<Photo>();
		
		setSelectedList();
		photoList.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {
			@Override
			public ListCell<Photo> call(ListView<Photo> param) {
				ListCell<Photo> cell = new ListCell<Photo>() {
					protected void updateItem(Photo t, boolean bool) {
						super.updateItem(t, bool);
						if (t != null) {
							setText(t.getCaption());
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
	 * Search by tag type-value pairs. Retrieves photos with matching this
	 * criteria. Alert user of empty results set.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void searchTag(ActionEvent event) throws IOException {
		gallery.getChildren().clear();
		
		String type = this.type.getText().toLowerCase().trim();
		String value = this.value.getText().toLowerCase().trim();
		
		if (type.equals("") || value.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText("Invalid Input.");
			alert.setContentText("Tag type and value fields required. Please try again.");
			alert.showAndWait();
		} else {
			resultList = user.searchAlbum(type, value);
			if (resultList.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("No Photos Found");
				alert.setContentText("Please search and try again.");
				alert.showAndWait();
			} else {
				setGallery();
			}
		}
	}
	
	/**
	 * Allows the user to search his or her album library according
	 * to a range of dates restricted to a start and end date.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void searchDate(ActionEvent event) throws IOException {
		gallery.getChildren().clear();
		LocalDate d1 = oldest.getValue();
		LocalDate d2 = newest.getValue();

		if (d1 == null || d2 == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText("Invalid Input.");
			alert.setContentText("Please enter valid start and end date.");
			alert.showAndWait();
		} else if (d2.isBefore(d1) || d1 == d2) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText("Invalid Input.");
			alert.setContentText("Please enter valid start and end date.");
			alert.showAndWait();
		} else {
			// convert LocalDate to Date instance
			Date date1 = Date.from(d1.atStartOfDay(ZoneId.systemDefault()).toInstant());
			Date date2 = Date.from(d2.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
			resultList = user.searchAlbum(date1, date2);
			if (resultList.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("No Photos Found");
				alert.setContentText("Please search and try again.");
				alert.showAndWait();
			} else {
				setGallery();
			}
		}
		
	}
	
	/**
	 * Sets gallery ImageView to include search result photos. Enable user
	 * to click on a photo to include it in the list of selected photos.
	 */
	@FXML
	protected void setGallery() {
		gallery.getChildren().clear();
		
		List<Photo> photos = resultList;
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
							if (!selectedList.contains(p)) {
								selectedList.add(p);
								setSelectedList();	
							}
						}
						// two clicks --> enlarge photo in another window
					}
				}
			});				
			gallery.getChildren().add(imageView);
		}
	}

	/**
	 * Updates the selected photos list after user selections, deletions, 
	 * and clears.
	 */
	@FXML
	protected void setSelectedList() {
		photoList.getItems().clear();
		
		// should just add single selected photo into listview later
		List<Photo> photos = selectedList;
		ObservableList<Photo> list = FXCollections.observableArrayList();
		for (Photo p : photos) {
			list.add(p);
		}
		photoList.setItems(list);
	}
	
	/**
	 * Adds all photos in the selected photos list to a new album.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void add(ActionEvent event) throws IOException {
	
		if (selectedList.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText("Album list is empty");
			alert.setContentText("Please select one or more photos to be added to a new album");
			alert.showAndWait();
		} else {
	    	Dialog<Album> dialog = new Dialog<>();
	    	dialog.setTitle("Add New Album");
	    	dialog.setHeaderText("Enter Album Name: ");

	    	TextField albumname = new TextField();
	    	GridPane grid = new GridPane();
	    	grid.add(new Label("Name: "), 1, 1);
	    	grid.add(albumname, 2, 1);
	      	dialog.getDialogPane().setContent(grid);
	      	
	    	ButtonType add = new ButtonType("Add", ButtonData.OK_DONE);
	    	dialog.getDialogPane().getButtonTypes().add(add);
	    	ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	    	dialog.getDialogPane().getButtonTypes().add(cancel);
	    	
	    	dialog.showAndWait();
	    	String name = albumname.getText().trim();
	    	
	    	boolean status = user.addAlbum(name);
	    	if (status) {
	    		for (Album a : user.getAlbums()) {
	    			if (a.getAlbumName().compareTo(name) == 0) {
	    				a.setNumPhotos(selectedList.size());
	    	    		for (Photo p : selectedList) {
	    	    			p.setAlbum(a);
	    	    		}
	    	    		a.setPhotos(selectedList);
	    	    		a.findOldest();
	    	    		a.findNewest();
	    	    		
	    				Alert alert = new Alert(AlertType.INFORMATION);
	    				alert.setTitle("Success");
	    				alert.setHeaderText("Album Created Successfully");
	    				alert.setContentText("Returning to user screen.");
	    				alert.showAndWait();
	    				backToUser(event);
	    			}
	    		}
	    	} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("Duplicate Album Detected");
				alert.setContentText("Invalid album name. Please try again.");
				alert.showAndWait();
	    	}
		}
	}
	
	/**
	 * Removes the highlighted photo caption from the selected photo list.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void delete(ActionEvent event) throws IOException {
		int item = photoList.getSelectionModel().getSelectedIndex();
		if (item >= 0) {
			selectedList.remove(item);
			photoList.getItems().remove(item);
			setSelectedList();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No item selected");
			alert.setContentText("Please select a photo from the list to be deleted. It will be highlighted upon selection.");
			alert.showAndWait();
		}
	}
	
	/**
	 * Clears photos the user has added to the selected photos list.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void clear(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setContentText("Click OK to clear the selected photos list.");
		Optional<ButtonType> click = alert.showAndWait();
		if ((click.isPresent()) && (click.get() == ButtonType.OK)) {
			selectedList.clear();
			setSelectedList();
		}
	}
	
	/**
	 * Calls exit procedure.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void exit(ActionEvent event) throws IOException {
		backToUser(event);
	}
	
	/**
	 * Exit procedure to return to User Screen.
	 *  
	 * @param event
	 * @throws IOException
	 */
	private void backToUser(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/UserScreen.fxml"));
		loader.load();
		Parent p = loader.getRoot();
		
		UserController controller = loader.getController();
		controller.setPhotoAlbum(this.photoAlbum);
		controller.setUser(this.user);
		controller.setAlbumList(this.user);
		
		Stage stage = new Stage();
		stage.setScene(new Scene(p));
		stage.setTitle("Logged in as: " + user.getUsername());
		stage.show();
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
