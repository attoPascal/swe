package swe2013.um;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Date;

import swe2013.um.users.AbstractUser;
import swe2013.um.users.RegisteredUser;
import swe2013.um.users.SuperUser;
import swe2013.um.users.dao.SerializedUserDAO;
import swe2013.um.users.dao.TextUserDAO;
import swe2013.um.users.dao.UserDAO;

/**
 * Class for user management
 * @author Pascal Attwenger (1200595)
 */
public class UserManagement {
	private UserDAO userDAO;
	private AbstractUser session;
	
	public final static int TEXT       = 0;
	public final static int SERIALIZED = 1;
	
	/**
	 * Constructs a UserManagement object with the specified DAO and filename
	 * @param dao UserManagement.TEXT or UserManagement.SERIALIZED
	 */
	public UserManagement(int dao, String filename) {
		switch (dao) {
			case 0:  userDAO = new TextUserDAO(filename);
					 break;
			case 1:  userDAO = new SerializedUserDAO(filename);
					 break;
			default: throw new IllegalArgumentException("No valid DAO type specified");
		}
		
		session = null;
	}
	
	/**
	 * Returns current DAO
	 * @return current DAO
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}
	
	/**
	 * Sets DOA
	 * @param userDAO new DAO
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	/**
	 * Registers a new user with the specified parameters
	 * @param username
	 * @param password
	 * @param fullName
	 * @param email
	 * @param sex
	 * @param birthday
	 * @return null
	 */
	public String registerUser(String username, String password, String fullName, String email, char sex, Date birthday) {
		userDAO.saveUser(new RegisteredUser(username, password, fullName, email, sex, birthday));
		return null;
	}
	
	/**
	 * Login for the specified user/password pair
	 * @param username
	 * @param password
	 * @return null
	 */
	public String login(String username, String password) {
		AbstractUser user = userDAO.getUserByUsername(username);
		if (user != null) {
			if (user.verifyPassword(password)) {
				session = user;
			} else {
				throw new IllegalArgumentException("Wrong password");
			}
		} else {
			throw new IllegalArgumentException("User does not exist");
		}
		
		return null;
	}
	
	/**
	 * Returns the number of user (registered and super)
	 * @return number of users
	 */
	public int numberOfUsers() {
		ArrayList<AbstractUser> list = userDAO.getUserList();
		return list.size();
	}
	
	/**
	 * Returns the number of registered users
	 * @return number of registered users
	 */
	public int numberOfRegisteredUsers() {
		ArrayList<AbstractUser> list = userDAO.getUserList();
		int count = 0;
		
		for (AbstractUser u : list) {
			if (u instanceof RegisteredUser) {
				count++;
			}
		}
		
		return count;
	}
	
	/**
	 * Returns the number of superusers
	 * @return number of superusers
	 */
	public int numberOfSuperUsers() {
		ArrayList<AbstractUser> list = userDAO.getUserList();
		int count = 0;
		
		for (AbstractUser u : list) {
			if (u instanceof SuperUser) {
				count++;
			}
		}
		
		return count;
	}
	
	/**
	 * Returns a String representing the currently logged-in user
	 * @return
	 */
	public String viewSignedOnUser() {
		if (session instanceof RegisteredUser || session instanceof SuperUser) {
			return "user=("
				+ session.getUsername() + ","
				+ session.getPassword() + ","
				+ session.getFullName() + ","
				+ session.getEmail() + ","
				+ session.getSex() + ","
				+ session.getBirthday() + ")";
		} else {
			throw new AccessControlException("I'm afraid I can't let you do that.");
		}
	}
	
	/**
	 * Edits the signed on user. null values mean no change
	 * @param password
	 * @param fullName
	 * @param email
	 * @param sex
	 * @param birthday
	 * @return null
	 */
	public String editSignedOnUser(String password, String fullName, String email, char sex, Date birthday) {
		if (session instanceof RegisteredUser || session instanceof SuperUser) {
			if (password != null) {
				session.setPassword(password);
			}
			if (fullName != null) {
				session.setFullName(fullName);
			}
			if (email != null) {
				session.setEmail(email);
			}
			if (sex != ' ') {
				session.setSex(sex);
			}
			if (birthday != null) {
				session.setBirthday(birthday);
			}
			
			return null;
			
		} else {
			throw new AccessControlException("I'm afraid I can't let you do that.");
		}
	}
	
	/**
	 * Logout. Resets the session
	 * @return
	 */
	public String logout() {
		if (session instanceof RegisteredUser || session instanceof SuperUser) {
			session = null;
			return null;
		} else {
			throw new AccessControlException("I'm afraid I can't let you do that.");
		}
	}
	
	/**
	 * Returns a String representing the user with the specified username
	 * @param username
	 * @return
	 */
	public String viewUser(String username) {
		if (session instanceof SuperUser && ((SuperUser) session).isCanViewUsers()) {
			AbstractUser user = userDAO.getUserByUsername(username);
			
			if (user != null) {
				return "user=("
						+ user.getUsername() + ","
						+ user.getPassword() + ","
						+ user.getFullName() + ","
						+ user.getEmail() + ","
						+ user.getSex() + ","
						+ user.getBirthday() + ")";
			} else {
				return null;
			}
			
		} else {
			throw new AccessControlException("I'm afraid I can't let you do that.");
		}
	}
	
	/**
	 * Returns a String representing all usernames
	 * @return
	 */
	public String viewAllUsers() {
		if (session instanceof SuperUser && ((SuperUser) session).isCanViewUsers()) {
			ArrayList<AbstractUser> list = userDAO.getUserList();
			
			StringBuffer buf = new StringBuffer();
			buf.append("allusers=(");
			
			boolean first = true;
			
			for (AbstractUser u : list) {
				if (first) {
					first = false;
				} else {
					buf.append(",");
				}
				
				buf.append(u.getUsername());
			}
			
			buf.append(")");
			return buf.toString();
			
		} else {
			throw new AccessControlException("I'm afraid I can't let you do that.");
		}
	}
	
	/**
	 * Edits the user with the specified username. null values mean no change
	 * @param username
	 * @param password
	 * @param fullName
	 * @param email
	 * @param sex
	 * @param birthday
	 * @return
	 */
	public String editUser(String username, String password, String fullName, String email, char sex, Date birthday) {
		if (session instanceof SuperUser && ((SuperUser) session).isCanEditUsers()) {
			AbstractUser user = userDAO.getUserByUsername(username);
			
			if (password != null) {
				user.setPassword(password);
			}
			if (fullName != null) {
				user.setFullName(fullName);
			}
			if (email != null) {
				user.setEmail(email);
			}
			if (sex != ' ') {
				user.setSex(sex);
			}
			if (birthday != null) {
				user.setBirthday(birthday);
			}
			
			userDAO.updateUser(user);
			
			return null;
			
		} else {
			throw new AccessControlException("I'm afraid I can't let you do that.");
		}
	}
	
	/**
	 * Deletes the user with the specified username
	 * @param username
	 * @return null
	 */
	public String deleteUser(String username) {
		if (session instanceof SuperUser && ((SuperUser) session).isCanDeleteUsers()) {
			userDAO.deleteUser(userDAO.getUserByUsername(username));
			return null;
			
		} else {
			throw new AccessControlException("I'm afraid I can't let you do that.");
		}
	}
	
	/**
	 * Returns the number of user over the age of 18
	 * @return
	 */
	public int numberOfAdultRegisteredUsers() {
		if (session instanceof SuperUser) {
			ArrayList<AbstractUser> list = userDAO.getUserList();
			int count = 0;
			
			for (AbstractUser u : list) {
				if (u instanceof RegisteredUser && ((RegisteredUser) u).verifyAge()) {
					count++;
				}
			}
			
			return count;
		} else {
			throw new AccessControlException("I'm afraid I can't let you do that.");
		}
	}
}
