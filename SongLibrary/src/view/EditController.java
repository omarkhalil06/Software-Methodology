/*
 * Created by: Omar Khalil & Michele Hwang
 */
package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import application.List;

public class EditController {
	
	@FXML
	private TextField songField;
	@FXML
	private TextField artistField;
	@FXML
	private TextField albumField;
	@FXML
	private TextField yearField;
	
	private Stage dialog;
	private List list;
	private boolean okClicked = false;
	
	@FXML
	private void initialize() {}
	
	public void setDialog(Stage dialog) {
		this.dialog = dialog;
	}
	
	public void setSong(List list) {
		this.list = list;
		songField.setText(list.getSong());
		artistField.setText(list.getArtist());
		albumField.setText(list.getAlbum());
		yearField.setText(list.getYear());
	}
	
	public boolean clickedOK() {
		return okClicked;
	}
	
	private boolean validInput() {
		int error = 0;
		String missingSongInput = "";
		String missingArtistInput = "";
		String song = songField.getText();
		String artist = artistField.getText();
		if (song == null || song.length() == 0) {
			error = 1;
			missingSongInput = "Song\n";
		}
		if (artist == null || artist.length() == 0) {
			error = 1;
			missingArtistInput = "Artist";
		}
	
		if (error == 0) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialog);
			alert.setTitle("Error");
			alert.setHeaderText("Missing fields:");
			alert.setContentText(missingSongInput + missingArtistInput);
			alert.showAndWait();
			return false;
		}
	}
	
	@FXML
	private void ok() {
		if (validInput()) {
			list.setSong(songField.getText());
			list.setArtist(artistField.getText());
			list.setAlbum(albumField.getText());
			list.setYear(yearField.getText());
			
			okClicked = true;
			dialog.close();
		}
	}
	
	@FXML
	private void cancel() {
		dialog.close();
	}
	
	
}