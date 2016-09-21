package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * Manages the reading and writing of objects onto a file.
 *  
 * @author Omar Khalil
 * @author Michelle Hwang
 *
 */
public class ResourceManager {
	
	/**
	 * Reads a user list onto a file located in the data directory.
	 * 
	 * @param fileName
	 * @return Object
	 * @throws Exception
	 */
	public static Object readUsers(String fileName) throws Exception {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data" + File.separator + fileName))) {
			return ois.readObject();
		}
	}
	
	/**
	 * Writes to a file located in the data directory.
	 * 
	 * @param data
	 * @param fileName
	 * @throws IOException
	 */
	public static void writeUsers(Serializable data, String fileName) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data" + File.separator + fileName))) {
			oos.writeObject(data);
		}
	}

}
