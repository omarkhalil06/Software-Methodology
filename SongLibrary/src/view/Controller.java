/*
 * Created by: Omar Khalil & Michele Hwang
 */
package view;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Optional;
import application.List;
import application.SongLib;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class Controller {
	
	@FXML
	ListView<List> list;
	@FXML
	private Label songName;
	@FXML
	private Label artistName;
	@FXML
	private Label albumName;
	@FXML
	private Label releaseYear;
	
	private SongLib songLib;
	
	public Controller() {}
	
	public int num = 0;
	
	@FXML
	private void initialize() {
		list.setCellFactory(new Callback<ListView<List>, ListCell<List>>() {

			@Override
			public ListCell<List> call(ListView<List> param) {
				ListCell<List> cell = new ListCell<List>() {
					protected void updateItem(List t, boolean bool) {
						super.updateItem(t, bool);
						if (t != null) {
							setText(t.getSong());
						} else {
							setText("");
						}
					}
				};
				return cell;
			}
			
		});
		list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showSongDetails(newValue));
	}
	
	public void setSongLib(SongLib songLib) {
		this.songLib = songLib;
		ObservableList<List> songs = songLib.getSongs();
		num = songs.size();
		
		list.setItems(songs);
		list.getSelectionModel().select(0);
	}
	
	private void showSongDetails(List list) {
		if (list != null) {
			songName.setText(list.getSong());
			artistName.setText(list.getArtist());
			albumName.setText(list.getAlbum());
			releaseYear.setText(list.getYear());
		} else {
			songName.setText("");
			artistName.setText("");
			albumName.setText("");
			releaseYear.setText("");
		}
	}
	
	@FXML
	private void deleteSong() {
		int item = list.getSelectionModel().getSelectedIndex();
		if (item >= 0) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setContentText("Click OK to delete this song.");
			Optional<ButtonType> click = alert.showAndWait();
			if ((click.isPresent()) && (click.get() == ButtonType.OK)) {
				list.getItems().remove(item);
				num--;
				if (num == 0) {
					songName.setText("");
					artistName.setText("");
					albumName.setText("");
					releaseYear.setText("");
				} else {
					list.getSelectionModel().select(item);
					//showSongDetails(list.getItems().get(item));
				}
			}
			
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(songLib.getPrimaryStage());
			alert.setTitle("Error");
			alert.setHeaderText("No item selected");
			alert.setContentText("Please select a song from the list to be deleted");
			alert.showAndWait();
		}
	}
	
	@FXML
	private void addSong() {
		List newSong = new List();
		boolean okClicked = songLib.showEditDialog(newSong);
		int temp = 0;
		if (okClicked) {
			if (num == 0) {
				songLib.getSongs().add(newSong);
				list.getSelectionModel().select(0);
				num++;
			} else {
				for (int i = 0; i < num; i++) {
					String artistName = list.getItems().get(i).getArtist();
					String songName = list.getItems().get(i).getSong();
					if (newSong.getSong().equals(songName) && newSong.getArtist().equals(artistName)) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.initOwner(songLib.getPrimaryStage());
						alert.setTitle("Error");
						alert.setHeaderText("Song and Artist name already taken.");
						alert.setContentText("Please enter a new name for either Song or Artist.");
						temp = 1;
						alert.showAndWait();
					}
				}
				if (temp == 0) {
					String newSongName = newSong.getSong();
					for (int i = 0; i < num; i++) {
						String name = list.getItems().get(i).getSong();
						int compare = newSongName.compareToIgnoreCase(name);
						if (compare <= 0) {
							num++;
							songLib.getSongs().add(i,newSong);
							list.getSelectionModel().select(i);
							return;
						} 
					}
					songLib.getSongs().add(newSong);
					list.getSelectionModel().select(num);
					num++;
				}
			}
		} 
	}
	
	@FXML
	private void editSong() {
		List selectedSong = list.getSelectionModel().getSelectedItem();
		int row = list.getSelectionModel().getSelectedIndex();
		if (selectedSong != null) {
			String tempName = selectedSong.getSong();
			String tempArtist = selectedSong.getArtist();
			String tempAlbum = selectedSong.getAlbum();
			String tempYear = selectedSong.getYear();
			boolean editClicked = songLib.showEditDialog(selectedSong);
			if (editClicked) {
				int i = 0;
				while (i < num) {
					if (i == row) {
						i++;
					}
					if (i == num) {
						break;
					}
					String artistName = list.getItems().get(i).getArtist();
					String songName = list.getItems().get(i).getSong();
					if (selectedSong.getSong().equals(songName) && selectedSong.getArtist().equals(artistName)) {
						selectedSong.setSong(tempName);
						selectedSong.setArtist(tempArtist);
						selectedSong.setAlbum(tempAlbum);
						selectedSong.setYear(tempYear);
						Alert alert = new Alert(AlertType.WARNING);
						alert.initOwner(songLib.getPrimaryStage());
						alert.setTitle("Error");
						alert.setHeaderText("Song and Artist name already taken.");
						alert.setContentText("Please enter a new name for either Song or Artist.");
						alert.showAndWait();
					}
					i++;
				}
				List tempSong = selectedSong;
				list.getItems().remove(row);
				num--;
				String newSongName = tempSong.getSong();
				for (int j = 0; j < num; j++) {
					String name = list.getItems().get(j).getSong();
					int compare = newSongName.compareToIgnoreCase(name);
					if (compare <= 0) {
						num++;
						songLib.getSongs().add(j,selectedSong);
						list.getSelectionModel().select(j);
						return;
					} 
				}
				songLib.getSongs().add(selectedSong);
				list.getSelectionModel().select(num);
				num++;
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(songLib.getPrimaryStage());
			alert.setTitle("Error");
			alert.setHeaderText("No song selected");
			alert.setContentText("Please select a song");
			alert.showAndWait();
		}
	}
	
	@FXML
	private boolean save() {
		String songName, artistName, albumName, releaseYear;
		try {
			PrintWriter writer = new PrintWriter("music.txt");
			if (num < 1) {
				writer.close();
				return true;
			}
			for (int i = 0; i < num; i++) {
				songName = list.getItems().get(i).getSong();
				artistName = list.getItems().get(i).getArtist();
				albumName = list.getItems().get(i).getAlbum();
				releaseYear = list.getItems().get(i).getYear();
				writer.println(songName + " | " + artistName + " | " + albumName + " | " + releaseYear);
			}
			writer.close();
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
}
