package model;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Describes the Album object. Contains properties album name, 
 * number of photos, a list of photos, the date of the oldest
 * photo taken, and the date of the most recently taken 
 * photo, as well as methods used to access, set, and calculate
 * these fields. Contains methods to add and delete its photos.
 *  
 * @author Omar Khalil
 * @author Michelle Hwang
 *
 */
public class Album implements Serializable {

	/**
	 * Generated SUID
	 */
	private static final long serialVersionUID = 3187980270116508175L;
	
	private String albumName;
	private int numPhotos;
	private List<Photo> photos;
	private Calendar oldest;
	private Calendar newest;
	
	/**
	 * Constructor initializes the fields of the Album object.
	 * 
	 * @param albumName Takes the name of the album as an argument
	 */
	public Album(String albumName) {
		this.albumName = albumName;
		this.numPhotos = 0;
		this.photos = new ArrayList<Photo>();
		this.oldest = null;
		this.newest = null;
	}
	
	/**
	 * Returns the name of the album.
	 * 
	 * @return The name of the album instance
	 */
	public String getAlbumName() {
		return this.albumName;
	}
	
	/**
	 * Sets the album name with the given string.
	 * 
	 * @param albumName The name of the album to be set
	 */
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	/**
	 * Returns the number of photos in the current album object.
	 * 
	 * @return The number of photos in the album
	 */
	public int getNumPhotos() {
		return this.numPhotos;
	}
	
	/**
	 * Sets the number of photos in the current album. Used only for 
	 * setting the number of photos in an album created from search
	 * results. Otherwise, this counter should increment and decrement
	 * with the user's additition and deletion of photographs.
	 * 
	 * @param numPhotos The number of photos the album possesses
	 */
	public void setNumPhotos(int numPhotos) {
		this.numPhotos = numPhotos;
	}
	
	/**
	 * Returns a list of photos in the current album.
	 * 
	 * @return A list of the album's photos
	 */
	public List<Photo> getPhotos() {
		return this.photos;
	}
	
	/**
	 * Sets an album's list of photos. Used only to create a new
	 * album from search results.
	 * 
	 * @param photos The list of photos to be set
	 */
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	
	/**
	 * Checks if a photo file already exists in an album.
	 * 
	 * @param fileName Path of the photo
	 * @return True if the album contains the photo, false otherwise
	 */
	public boolean containsPhoto(File fileName) {
		for(Photo p : photos) {
			if (fileName.equals(p.getFileName())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds a new photo to an existing album, along with its file name, 
	 * caption, and list of tags. Recalculates the album's oldest and newest
	 * photos according to their date taken.
	 * 
	 * @param fileName The path location to the photo file on the local machine
	 * @param caption A description of the photo
	 * @return True if the photo was added successfully, false otherwise
	 */
	public boolean addPhoto(File fileName, String caption, List<Tag> tags) {
		if (!this.containsPhoto(fileName)) {
			Photo photo = new Photo(fileName, caption, this, tags);
			photos.add(photo);			
			this.numPhotos++;
			findOldest();
			findNewest();
			return true;
		}
		return false;
	}
	
	/**
	 * Removes a photo from the photo album, decrements the counter recording
	 * the number of photos in the current album, and recalculates the oldest
	 * and newest photos according to their date taken.
	 * 
	 * @param photo Photo to be deleted
	 */
	public void deletePhoto(Photo photo) {
		photos.remove(photo);
		this.numPhotos--;		
		findOldest();
		findNewest();
	}
	
	/**
	 * Returns the date of the oldest photo as a string.
	 * 
	 * @return oldest
	 */
	public String getOldest() {
		findOldest();
		if (oldest == null) {
			return "";
		}
		Date date = this.oldest.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy MMM dd HH:mm:ss.SSS");	
		return format.format(date);
	}
	
	/**
	 * Returns the date of the newest photo as a string.
	 * 
	 * @return newest
	 */
	public String getNewest() {
		findNewest();
		if (newest == null) {
			return "";
		}
		Date date = this.newest.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy MMM dd HH:mm:ss.SSS");	
		return format.format(date);
	}
	
	/**
	 * Identifies the oldest photo in the album.
	 */
	public void findOldest() {
		Calendar oldest;
		if (numPhotos == 0) {
			return;
		}
		oldest = photos.get(0).getDate();		
		for (Photo p : photos) {
			if (oldest.compareTo(p.getDate()) > 0) {
				oldest = p.getDate();
			}
		}
		this.oldest = oldest;
	}
	
	/**
	 * Identifies the newest photo in the album.
	 */
	public void findNewest() {
		Calendar newest;
		if (numPhotos == 0) {
			return;
		}
		newest = photos.get(0).getDate();		
		for (Photo p : photos) {
			if (newest.compareTo(p.getDate()) < 0) {
				newest = p.getDate();
			}
		}
		this.newest = newest;
	}
}
