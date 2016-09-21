/*
 * Created by: Omar Khalil & Michele Hwang
 */
package application;
	

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.Controller;
import view.EditController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public class SongLib extends Application {
	
	private Stage primaryStage;
	private AnchorPane root;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initLayout();
	}
	
	public void initLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/SongLibraryView.fxml"));
			root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Song Library");
			primaryStage.show();
			
			Controller controller = loader.getController();
			controller.setSongLib(this);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean showEditDialog(List list) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/EditSong.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialog = new Stage();
			dialog.setTitle("Edit Song");
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(primaryStage);
			
			Scene scene = new Scene(page);
			dialog.setScene(scene);
			EditController controller = loader.getController();
			controller.setDialog(dialog);
			controller.setSong(list);
			dialog.showAndWait();
			return controller.clickedOK();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private ObservableList<List> songs = FXCollections.observableArrayList();
	
	public SongLib() {
		songs = loadSongs();
	}
	
	public ObservableList<List> getSongs() {
		return songs;
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
		
	public static void main(String[] args) {
		launch(args);
	}
	
	private ObservableList<List> loadSongs() {
		ObservableList<List> songFile = FXCollections.observableArrayList();
		String title, artist, album, year;
    	title = "";
    	artist = "";
    	album = "";
    	year = "";
    	String line = null;
     	
     	boolean t, a, l;	
    	t = true;
    	a = true;
    	l = true;
    	
    	try {
    		 FileReader fr = new FileReader("music.txt");
             BufferedReader br = new BufferedReader(fr);
             while ((line = br.readLine()) != null) {
            	 if (line.isEmpty()) {
            		 break; // empty line at end
            	 }
            	 StringBuilder sbTitle = new StringBuilder();
            	 StringBuilder sbArtist = new StringBuilder();
            	 StringBuilder sbAlbum = new StringBuilder();
            	 StringBuilder sbYear = new StringBuilder();
            	 for (int i = 0; i < line.length(); i++) {
            		 char ch = line.charAt(i);
            		 if (t) {
            			 if (ch == '|') {
            				 t = false;
            			 } else {
            				 sbTitle.append(ch);
            			 }
            		 } else if (a) {
            			 if (ch == '|') {
            				 a = false;
            			 } else {
            				 sbArtist.append(ch);
            			 }
            		 } else if (l) {
            			 if (ch == '|') {
            				 l = false;
            			 } else {
            				 sbAlbum.append(ch);
            			 }
            		 } else {
            			 sbYear.append(ch);
            		 }
            	 }
            	 title = sbTitle.toString();
            	 artist = sbArtist.toString();
            	 album = sbAlbum.toString();
            	 year = sbYear.toString();
            	 List newSong = new List();
        		 newSong.setSong(title.trim());
        		 newSong.setArtist(artist.trim());
        		 newSong.setAlbum(album.trim());
        		 newSong.setYear(year.trim());
        		 songFile.add(newSong);
        		 t = true;
        		 a = true;
        		 l = true;
        		 title = "";
        		 artist = "";
        		 album = "";
        		 year = "";
             }
             br.close();  
             fr.close();
    	} catch (IOException e) {
    		System.out.printf("Error Reading File");
    		e.printStackTrace();
    	} 
    	return songFile;
	}
	
}
