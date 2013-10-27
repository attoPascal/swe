package swe2013.um.users;

import java.io.Serializable;
import java.util.Date;

/**
 * Abstract user
 * @author Pascal Attwenger (1200595)
 *
 */
public abstract class AbstractUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String fullName;
	private String email;
	private char sex = ' ';
	private Date birthday;
	
	/**
	 * Constructor
	 * @param username
	 * @param password
	 * @param fullName
	 * @param email
	 * @param sex
	 * @param birthday
	 */
	public AbstractUser(String username, String password, String fullName, String email, char sex, Date birthday) {
		this.username = username;
		this.fullName = fullName;
		this.email = email;
		
		if (password.length() > 6 && !password.contains(" ")) {
			this.password = password;
		} else {
			throw new IllegalArgumentException("Invalid password");
		}
		
		sex = Character.toLowerCase(sex);
		if (sex == 'm' || sex == 'f') {
			this.sex = sex;
		} else {
			throw new IllegalArgumentException("Invalid sex");
		}
		
		Date now = new Date();
		if (birthday.before(now)) {
			this.birthday = birthday;
		} else {
			throw new IllegalArgumentException("Invalid birthday");
		}
	}
	
	/**
	 * Returns username
	 * @return
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets username
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Returns password
	 * return password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets password 
	 * @param password
	 */
	public void setPassword(String password) {
		if (password.length() > 6 && !password.contains(" ")) {
			this.password = password;
		} else {
			throw new IllegalArgumentException("Invalid password");
		}
	}
	
	/**
	 * Returns full name
	 * @return fullName
	 */
	public String getFullName() {
		return fullName;
	}
	
	/**
	 * Sets full name
	 * @param fullName
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	/**
	 * Returns email
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Returns sex
	 * @return sex
	 */
	public char getSex() {
		return sex;
	}
	
	/**
	 * Sets sex
	 * @param sex
	 */
	public void setSex(char sex) {
		sex = Character.toLowerCase(sex);
		if (sex == 'm' || sex == 'f') {
			this.sex = sex;
		} else {
			throw new IllegalArgumentException("Invalid sex");
		}
	}
	
	/**
	 * Returns birthday
	 * @return birthday
	 */
	public Date getBirthday() {
		return birthday;
	}
	
	/**
	 * Sets birthday
	 * @param birthday
	 */
	public void setBirthday(Date birthday) {
		Date now = new Date();
		if (birthday.before(now)) {
			this.birthday = birthday;
		} else {
			throw new IllegalArgumentException("Invalid birthday");
		}
	}
	
	/**
	 * Verifies password
	 * @param password
	 * @return true if verified, else false
	 */
	public boolean verifyPassword(String password) {
		if (this.password.equals(password)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks for equality
	 * @param user
	 * @return true if equal, else false
	 */
	public boolean equals(AbstractUser user) {
		return this.saveAsString().equals(user.saveAsString());
	}
	
	/**
	 * Returns human-readable String representation of user (without password)
	 */
	public abstract String toString();
	
	/**
	 * Returns full String representation of user (with password)
	 * @return
	 */
	public abstract String saveAsString();
}
