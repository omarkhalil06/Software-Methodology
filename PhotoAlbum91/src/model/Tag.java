package model;

import java.io.Serializable;

/**
 * Describes the tag object. Contains a type-value pair.
 * 
 * @author Omar Khalil
 * @author Michelle Hwang
 *
 */
public class Tag implements Serializable {

	/**
	 * Generated SUID
	 */
	private static final long serialVersionUID = 467418373787730049L;
	
	private String type;
	private String value;
	
	/**
	 * Initializes a new tag instance.
	 * 
	 * @param type The type of the tag
	 * @param value The value of the tag
	 */
	public Tag(String type, String value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Returns the tag's type
	 * 
	 * @return Tag type
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Sets the tag's type.
	 * 
	 * @param type Tag type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Returns the value of the tag.
	 * 
	 * @return Tag value
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Sets the tag's value
	 * 
	 * @param value Tag value
	 */
	public void setValue(String value) {
		this.value = value;
	}	
}
