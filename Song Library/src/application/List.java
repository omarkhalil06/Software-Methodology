/*
 * Created by: Omar Khalil & Michele Hwang
 */
package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class List {
	
	private final StringProperty songID;
	private final StringProperty artistID;
	private final StringProperty albumID;
	private final StringProperty yearID;
	
	public List() {
		this(null);
	}
	
	public List(String song) {
		this.songID = new SimpleStringProperty(song);
		this.artistID = new SimpleStringProperty("");
		this.albumID = new SimpleStringProperty("");
		this.yearID = new SimpleStringProperty("");
	}
	
	public String getSong() {
		return songID.get();
	}
	
	public void setSong(String song) {
		this.songID.set(song);
	}
	
	public StringProperty songProperty() {
		return songID;
	}
	
	public String getArtist() {
		return artistID.get();
	}
	
	public void setArtist(String artist) {
		artistID.set(artist);
	}
	
	public StringProperty artistProperty() {
		return artistID;
	}
	
	public String getAlbum() {
		return albumID.get();
	}
	
	public void setAlbum(String album) {
		albumID.set(album);
	}
	
	public StringProperty albumProperty() {
		return albumID;
	}
	
	public String getYear() {
		return yearID.get();
	}
	
	public void setYear(String year) {
		yearID.set(year);
	}
	
	public StringProperty yearProperty() {
		return yearID;
	}
	
}
