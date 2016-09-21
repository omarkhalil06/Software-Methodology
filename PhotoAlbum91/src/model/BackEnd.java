package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * BackEnd allows for storing and retrieving user data.
 *  
 * @author Omar Khalil
 * @author Michelle Hwang
 *
 */
public class BackEnd implements Serializable {
	
	/**
	 * Generated SUID
	 */
	private static final long serialVersionUID = 7878048212514926262L;
	
	
	private ArrayList<User> users;
	
	/**
	 * Initializes the backend.
	 */
	public BackEnd() {
		users = new ArrayList<User>();

		User admin = new User("admin");
		users.add(admin);

		User omar = new User("omar");
		users.add(omar);
		
		User michele = new User("michele");
		users.add(michele);
	}
	
	/**
	 * Adds a new user to the user ArrayList.
	 * 
	 * @param username
	 */
	public void addUser(String username) {
		User user = new User(username);
		users.add(user);
	}
	
	/**
	 * Deletes user from the use ArrayList.
	 * 
	 * @param username
	 */
	public void deleteUser(String username) {
		Iterator<User> iter = users.iterator();
		
		while (iter.hasNext()) {
			User u = iter.next();
			
			if (u.getUsername().equals(username)) {
				users.remove(u);
				return;
			}
		}
	}
	
	/**
	 * Retrieves a user.
	 * 
	 * @param username
	 * @return User
	 */
	public User getUser(String username) {
		for (User u : users) {
			if (u.getUsername().equals(username)) {
				return u;
			}
		}
		return null;
	}
	
	/**
	 * Retrieves the user ArrayList.
	 * 
	 * @return user ArrayList
	 */
	public ArrayList<User> getUserList() {
		return this.users;
	}
	
}
