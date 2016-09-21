package controller;

import java.io.IOException;
import java.util.ArrayList;
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
import model.ResourceManager;
import model.User;

/**
 * The AdminController class controls whether to add a new user or delete one.
 * 
 * @author Omar Khalil
 * @author Michelle Hwang
 */

public class AdminController {
	
	@FXML
	ListView<User> userList;
	@FXML
	private Label username;
	@FXML
	private Button logoutBtn;
	@FXML
	private Button addBtn;
	@FXML
	private Button deleteBtn;

	private User user;
	private PhotoAlbum photoAlbum;
	
	/**
	 * Highlights username if clicked on.
	 */
	@FXML
	private void initialize() {
		userList.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {

			@Override
			public ListCell<User> call(ListView<User> param) {
				ListCell<User> cell = new ListCell<User>() {
					protected void updateItem(User t, boolean bool) {
						super.updateItem(t, bool);
						if (t != null) {
							setText(t.getUsername());
						} else {
							setText("");
						}
					}
				};
				return cell;
			}
			
		});
		userList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showDetails(newValue));
		
	}
	
	/**
	 * Sets the label "username" as a user.
	 * 
	 * @param user
	 */
	private void showDetails(User user) {
		if (user != null) {
			username.setText(user.getUsername());
		} else {
			username.setText("");
		}
	}
	
	/**
	 * Logouts and moves back to login screen.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void logout(ActionEvent event) throws IOException {
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
	 * Opens up a new window for a new user to be added.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void add(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/AddNewUserScreen.fxml"));
		loader.load();
		Parent p = loader.getRoot();
		
		AddNewUserController controller = loader.getController();
		controller.setPhotoAlbum(this.photoAlbum);
		
		Stage stage = new Stage();
		stage.setScene(new Scene(p));
		stage.setTitle("Admin");
		stage.show();
	}
	
	/**
	 * Deletes the selected user.
	 */
	@FXML
	protected void delete() {
		int item = userList.getSelectionModel().getSelectedIndex();
		
		if (item >= 0) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setContentText("Click OK to delete this user.");
			Optional<ButtonType> click = alert.showAndWait();
			
			if ((click.isPresent()) && (click.get() == ButtonType.OK)) {
				String name = userList.getItems().get(item).getUsername();
				photoAlbum.getBackend().deleteUser(name);
				userList.getItems().remove(item);
				userList.getSelectionModel().select(item);
			}
			
			try {
				ResourceManager.writeUsers(this.photoAlbum.getBackend(), "userfile");
			} catch (Exception e) {
				//System.out.println("Could not save file: " + e.getMessage());
			}
			
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText("No item selected");
			alert.setContentText("Please select a user from the list to be deleted");
			alert.showAndWait();
		}
		
	}
	
	/**
	 * Fills up listview with the userlist.
	 * 
	 * @param user
	 */
	public void setUserList(User user) {
		ArrayList<User> users = photoAlbum.getBackend().getUserList();
		ObservableList<User> list = FXCollections.observableArrayList();
		for (User u : users) {
			if (!u.getUsername().equals("admin")) {
				list.add(u);
			}
		}
		userList.setItems(list);
		userList.getSelectionModel().select(0);
	}
	
	/**
	 * Sets value of User.
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
		//username.setText(user.getUsername());
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
