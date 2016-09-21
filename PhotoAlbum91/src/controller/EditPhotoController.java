package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;

/**
 * Manages procedures to edit a photo. Displays photo details 
 * and options to add, delete, or edit a tag. The user cannot 
 * edit the photo's file name, however.
 * 
 * @author Omar Khalil
 * @author Michelle Hwang
 * 
 */
public class EditPhotoController {

	@FXML
	private Label filename;
	
	@FXML
	private TextField caption;
	@FXML
	private TextField type;
	@FXML
	private TextField value;
	
	@FXML
	private ListView<Tag> tagList;
	
	@FXML
	private Button addTag;
	@FXML
	private Button deleteTag;
	@FXML
	private Button editTag;
	@FXML
	private Button editBtn;
	@FXML
	private Button cancelBtn;
	
	private PhotoAlbum photoAlbum;
	private User user;
	private Album album;
	private Photo photo;
	private Tag tag;
	private List<Tag> tags;
	
	/**
	 * Initializes tags list and prepares tag ListView.
	 */
	@FXML
	protected void initialize() {
		tags = new ArrayList<Tag>();
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
		tagList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showDetails(newValue));
	}
	
	/**
	 * Displays tag details.
	 * 
	 * @param tag
	 */
	private void showDetails(Tag tag) {
		if (tag != null) {
			type.setText(tag.getType());
			value.setText(tag.getValue());
		} else {
			type.setText("");
			value.setText("");
		}
	}
	
	/**
	 * Sets tag list on listview for the user to see.
	 * 
	 * @param photo
	 */
	@FXML
	public void setTagList() {
		ObservableList<Tag> list = FXCollections.observableArrayList();
		for (Tag t : tags) {
			list.add(t);
		}
		tagList.setItems(list);
	}
	
	/**
	 * Retrieves data from TextFields and creates new tag.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void addTag(ActionEvent event) throws IOException {
		String type = this.type.getText().toLowerCase().trim();
		String value = this.value.getText().toLowerCase().trim();
		
		if (type.compareTo("") == 0 || value.compareTo("") == 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Invalid Tag Input");
			alert.setContentText("Type and value fields required. Please try again.");
			alert.showAndWait();
		} else if (!containsTag(type, value)) {
			//System.out.println("Does not contain tag");
			Tag t = new Tag(type, value);
			tags.add(t);
			setTagList();
		} else {
			Alert duplicate = new Alert(AlertType.WARNING);
			duplicate.setTitle("Error");
			duplicate.setHeaderText("Duplicate Tag Detected");
			duplicate.setContentText("Please enter a different tag.");
			duplicate.showAndWait();
		}
	}
	
	/**
	 * Deletes the selected tag.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void deleteTag(ActionEvent event) throws IOException {
		int item = tagList.getSelectionModel().getSelectedIndex();
		if (item >= 0) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setContentText("Click OK to delete this tag.");
			Optional<ButtonType> click = alert.showAndWait();
			if ((click.isPresent()) && (click.get() == ButtonType.OK)) {
				tags.remove(item); // remove tag from taglist
				tagList.getItems().remove(item);
				type.setText(""); // clear preview textfields
				value.setText(""); // clear preview textfields
				setTagList(); // update tag listview
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
	 * Checks if a tag exists in the photo's tag list.
	 * 
	 * @param type Type of the tag to be added
	 * @param value Value of the tag to be added
	 * @return True if the photo already contains the tag, false otherwise
	 */
	public boolean containsTag(String type, String value) {
		for (Tag t : tags) {
			if (t.getType().compareTo(type) == 0) {
				if (t.getValue().compareTo(value) == 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Opens a dialog to edit a selected tag.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void editTag(ActionEvent event) throws IOException {	
		int item = tagList.getSelectionModel().getSelectedIndex();
		if (item >= 0) {
			this.tag = tags.get(item);
			
	    	Dialog<Tag> dialog = new Dialog<>();
	    	dialog.setTitle("Edit Tag: ");
	    	dialog.setHeaderText("Enter Tag Details: ");

	    	Label newType = new Label("Type: ");
	    	Label newValue = new Label("Value: ");
	    	TextField newTypeText = new TextField(tag.getType());
	    	TextField newValueText = new TextField(tag.getValue());
			
	    	GridPane grid = new GridPane();
	    	grid.add(newType, 1, 1);
	    	grid.add(newTypeText, 2, 1);
	    	grid.add(newValue, 1, 2);
	    	grid.add(newValueText, 2, 2);
	      	dialog.getDialogPane().setContent(grid);
	      	
	    	ButtonType save = new ButtonType("Save", ButtonData.OK_DONE);
	    	dialog.getDialogPane().getButtonTypes().add(save);
	    	ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	    	dialog.getDialogPane().getButtonTypes().add(cancel);
	    	
	    	dialog.showAndWait();
	    	
	    	String type = newTypeText.getText().toLowerCase().trim();
	    	String value = newValueText.getText().toLowerCase().trim();
	    	
			if (type.compareTo("") == 0 || value.compareTo("") == 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error!");
				alert.setHeaderText("Invalid Tag Input");
				alert.setContentText("Type and value fields required. Please try again.");
				alert.showAndWait();
			} else {
				if (!containsTag(type, value)) {
					tag.setType(type);
					tag.setValue(value);
					tagList.getItems().clear();
					setTagList();
				} else {
					Alert duplicate = new Alert(AlertType.WARNING);
					duplicate.setTitle("Error");
					duplicate.setHeaderText("Duplicate Tag Detected");
					duplicate.setContentText("Please enter a different tag.");
					duplicate.showAndWait();
				}
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText("No item selected");
			alert.setContentText("Please select a tag to be edited");
			alert.showAndWait();
		} 
	}

	/**
	 * Sets up Album Screen and initializes variables for return.
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
	 * Inserts caption and tag list changes into photo.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void edit(ActionEvent event) throws IOException {
		//System.out.println("In EditPhotoDetails: edit");
	
		String cap = caption.getText();
		if (cap.compareTo("") == 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Invalid inputs: Caption required.");
			alert.setContentText("Please try again.");
			alert.showAndWait();	
		} else {
			photo.setTags(tags);
			photo.setCaption(cap);
			backToAlbum(event);
		}
	}
	
	/**
	 * Returns to Album Screen.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void cancel(ActionEvent event) throws IOException {
		//System.out.println("In PhotoDetailsController: cancel");
		backToAlbum(event);
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
	
	/**
	 * Sets value of photo to be edited.
	 * 
	 * @param photo
	 */
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	
	/**
	 * Sets currently editing file on screen.
	 * 
	 * @param file
	 */
	public void setFile(File file) {
		this.filename.setText(file.toString());
	}
	
	/**
	 * Sets currently editing caption in TextField.
	 * 
	 * @param caption
	 */
	public void setCaption(String caption) {
		this.caption.setText(caption);
	}
	
	/**
	 * Sets the tag list.
	 */
	public void setInitTags() {
		tags.addAll(photo.getTags());
		setTagList();
	}
	
}
