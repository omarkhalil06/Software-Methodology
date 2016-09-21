package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Describes the User object, each of which has a username and
 * a list of albums that the user has created. 
 *  
 * @author Omar Khalil
 * @author Michelle Hwang
 *
 */
public class User implements Serializable {
	
	/**
	 * Generated SUID
	 */
	private static final long serialVersionUID = 6578893320390944828L;
	
	private String username;
	private List<Album> albums;
	
	public User(String username) {
		this.username = username;
		this.albums = new ArrayList<Album>();
	}
	
	public User() {
		this(null);
	}
	
	/**
	 * Returns the username of the current user.
	 * 
	 * @return The username of the user
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Returns the user's list of albums.
	 * 
	 * @return All of the user's albums
	 */
	public List<Album> getAlbums() {
		return this.albums;
	}
	
	/**
	 * Detects a duplicate album name in the user's list of albums.
	 * 
	 * @param albumName The name to be matched
	 * @return True if the library contains the album name, false otherwise
	 */
	public boolean containsAlbum(String albumName) {
		//System.out.println("In User: containsAlbum");

		for(Album album : albums) {
			if (album.getAlbumName().compareTo(albumName) == 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds a new album to a user's library. Will refuse to add an album with a 
	 * duplicate album name.
	 * 
	 * @param albumName The name of the album to be created
	 * @return True if the album was added, false otherwise.
	 */
	public boolean addAlbum(String albumName) {		
		if (containsAlbum(albumName)) {
			return false;
		}
		albums.add(new Album(albumName));
		return true;
	}
	
	/**
	 * Renames the album.
	 * 
	 * @param albumName The new album name
	 * @param album The album instance that is renamed
	 */
	public void editAlbum(String albumName, Album album) {
		album.setAlbumName(albumName);
	}
	
	/**
	 * Removes an album from the user's library.
	 * 
	 * @param album The album object to be removed
	 */
	public void deleteAlbum(Album album) {
		this.getAlbums().remove(album);
	}
	
	/**
	 * Searches a user's album library for all photos with a match
	 * with the specified tag type-value pair.
	 * 
	 * @param type The type of the tag to be matched
	 * @param value The value of the tag to be matched
	 * @return A list of photos whose tag matches the requested tag
	 */
	public List<Photo> searchAlbum(String type, String value) {
		List<Photo> results = new ArrayList<Photo>();
		for (Album album : this.getAlbums()) {
			for (Photo photo : album.getPhotos()) {
				for (Tag tag : photo.getTags()) {
					if (tag.getType().compareTo(type) == 0 && tag.getValue().compareTo(value) == 0) {
						results.add(photo);
					}
				}
			}
		}
		return results;
	}
	
	/**
	 * Searches album library for photos taken between in the specified
	 * range of dates.
	 * 
	 * @param date1 Oldest date
	 * @param date2 Newest date
	 * @return A list of photos whose date taken property satisfies the 
	 * date range.
	 */
	public List<Photo> searchAlbum(Date date1, Date date2) {
		List<Photo> results = new ArrayList<Photo>();
		for (Album album : this.getAlbums()) {
			for (Photo photo : album.getPhotos()) {
				Date d = photo.getDate().getTime();
				if (d.after(date1) && d.before(date2)) {
					results.add(photo);
				}
			}
		}
		return results;
	}
	
	/**
	 * Prints the album object.
	 */
	public void printAlbums() {
		//System.out.println(this.getAlbums());
	}
	
}
