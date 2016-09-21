package model;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Describes the Photo object. Contains the location of the photo file
 * on the local machine, caption, list of tags, the date it was taken (
 * this is actually the last modified date), and a reference to the 
 * album it resides in, as well as methods to manage its tag list.
 *  
 * @author Omar Khalil
 * @author Michelle Hwang
 *
 */
public class Photo implements Serializable {

	/**
	 * Generated SUID
	 */
	private static final long serialVersionUID = 3253380940931743957L;
	
	private File fileName;
	private String caption;
	private List<Tag> tags;
	private Calendar dateTaken;
	private Album album;
	
	/**
	 * Constructs the photo object.
	 * 
	 * @param fileName Location of the photo file
	 * @param caption Description/name of the photo
	 * @param album Reference to the album it belongs to
	 * @param tags A list of tags
	 */
	public Photo(File fileName, String caption, Album album, List<Tag> tags) {
		this.fileName = fileName;
		this.caption = caption;
		this.tags = tags;
		this.album = album;
		this.dateTaken = Calendar.getInstance();
		dateTaken.setTime(new Date(fileName.lastModified()));		
	}
	
	/**
	 * Returns the path to the photo file.
	 * 
	 * @return The location of the photo
	 */
	public File getFileName() {
		return this.fileName;
	}
	
	/**
	 * Returns the caption as a string.
	 * 
	 * @return Description of the photo
	 */
	public String getCaption() {
		return this.caption;
	}
	
	/**
	 * Sets the photo's caption.
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	/**
	 * Returns a list of the photo's tags.
	 * 
	 * @return The photo's tag list
	 */
	public List<Tag> getTags() {
		return this.tags;
	}
	
	/**
	 * Returns the date the photo was taken.
	 * 
	 * @return The photo's date
	 */
	public Calendar getDate() {
		return this.dateTaken;
	}
	
	/**
	 * Prints out the photo's date taken.
	 * 
	 * @return The photo's date reformatted
	 */
	public String printDate() {
		Date toDate = this.dateTaken.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy MMM dd HH:mm:ss.SSS");	
		String date = format.format(toDate);
		return date;
	}
	
	/**
	 * Returns the album the photo belongs to.
	 * 
	 * @return Album instance
	 */
	public Album getAlbum() {
		return this.album;
	}
	
	/**
	 * Sets the album that the photo belongs to.
	 * 
	 * @param album The album that the photo will belong to
	 */
	public void setAlbum(Album album) {
		this.album = album;
	}
	
	/**
	 * Sets the photo's list of tags.
	 * 
	 * @param tags The list of tags to be set
	 */
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	/**
	 * Checks if the photo contains a tag.
	 * 
	 * @param type The type of the tag to be matched
	 * @param value The value of the tag to be matched
	 * @return True if the photo's tag list does not contains
	 * the specified tag type-value pair, false otherwise
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
	 * Adds the a new tag to the tag list.
	 * 
	 * @param type The type of the tag to be added
	 * @param value The value of the tag to be added
	 * @return True if the photo successfully added the tag to the tag list, 
	 * false otherwise.
	 */
	public boolean addTag(String type, String value) {
		if (containsTag(type, value)) {
			return false;
		} else {
			Tag t = new Tag(type, value);
			tags.add(t);
			return true;
		}
	}
}
