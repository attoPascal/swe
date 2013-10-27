package swe2013.um.users.dao;

import java.util.ArrayList;

import swe2013.um.users.AbstractUser;

/**
 * Persistent storage of users
 * @author Pascal Attwenger (1200595)
 *
 */
public interface UserDAO {
	
	/**
	 * Returns ArrayList consisting of all users in storage
	 * @return
	 */
	public ArrayList<AbstractUser> getUserList();
	
	/**
	 * Returns user with the specified username
	 * @param username
	 * @return
	 */
	public AbstractUser getUserByUsername(String username);
	
	/**
	 * Saves user
	 * @param user
	 */
	void saveUser(AbstractUser user);
	
	/**
	 * Deletes user
	 * @param user
	 */
	void deleteUser(AbstractUser user);
	
	/**
	 * Updates user with the same username
	 * @param user
	 */
	void updateUser(AbstractUser user);
}
