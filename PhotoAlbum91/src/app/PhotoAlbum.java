package app;

import java.io.IOException;

import controller.LoginScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.BackEnd;
import model.ResourceManager;

/**
 * The PhotoAlbum class is the main method of this program. This class creates
 * a new backend interface to store user objects and starts with the login
 * screen.
 * 
 * @author Omar Khalil
 * @author Michelle Hwang
 */

public class PhotoAlbum extends Application {
	
	private Stage primaryStage;
	private AnchorPane root;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		loginScreen();
		
	}

	/**
	 * Opens up the login screen.
	 */
	public void loginScreen() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/LoginScreen.fxml"));
			root = (AnchorPane) loader.load();
			
			LoginScreenController controller = loader.getController();
			controller.setPhotoAlbum(this);

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Photo App");
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private BackEnd backend = new BackEnd();
	
	
	/**
	 * Initializes the photo album and loads up the file that contains
	 * the user objects.
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public PhotoAlbum() throws Exception {
		try {
			backend = (BackEnd) ResourceManager.readUsers("userfile");
		} catch (Exception e) {
			//System.out.println("File does not exist or could not load data: " + e.getMessage());
		}
	}
	
	/**
	 * Returns backend interface.
	 * 
	 * @return BackEnd
	 */
	public BackEnd getBackend() {
		return backend;
	}
	
	/**
	 * Returns PhotoAlbum instance.
	 * 
	 * @return
	 */
	public PhotoAlbum getPhotoAlbum() {
		return this;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
