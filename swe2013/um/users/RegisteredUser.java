package swe2013.um.users;

import java.util.Calendar;
import java.util.Date;

/**
 * RegisteredUser
 * @author Pascal Attwenger (1200595)
 */
public class RegisteredUser extends AbstractUser {
	private static final long serialVersionUID = 1L;
	private Date registeredDate;
	
	/**
	 * Constructs a RegisteredUser with time of creation as registeredDate.
	 * @param username
	 * @param password
	 * @param fullName
	 * @param email
	 * @param sex
	 * @param birthday
	 */
	public RegisteredUser(String username, String password, String fullName, String email, char sex, Date birthday) {
		super(username, password, fullName, email, sex, birthday);
		registeredDate = new Date();
	}
	
	/**
	 * Constructs a RegisteredUser with specified registeredDate.
	 * @param username
	 * @param password
	 * @param fullName
	 * @param email
	 * @param sex
	 * @param birthday
	 * @param registeredDate
	 */
	public RegisteredUser(String username, String password, String fullName, String email, char sex, Date birthday, Date registeredDate) {
		super(username, password, fullName, email, sex, birthday);
		this.registeredDate = registeredDate;
	}
	
	/**
	 * Check if user is older than 18
	 * @return true if older than 18, false if not
	 */
	public boolean verifyAge() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -18);
		Date eighteenYearsAgo = cal.getTime();
		
		if (getBirthday().before(eighteenYearsAgo)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns date of registration
	 * @return registrationDate
	 */
	private Date getRegisteredDate() {
		return registeredDate;
	}

	@Override
	public String toString() {
		String nl = System.getProperty("line.separator");
		String string =
				"RegisteredUser" + nl
				+ "username:  " + getUsername() + nl
				+ "full name: " + getFullName() + nl
				+ "mail:      " + getEmail() + nl
				+ "sex:       " + getSex() + nl
				+ "birthday:  " + getBirthday() + nl
				+ "reg date:  " + getRegisteredDate();
		return string;
	}

	@Override
	public String saveAsString() {
		return "RegisteredUser|" + getUsername() + "|" + getPassword() + "|" + getFullName() + "|" + getEmail() + "|" + getSex() + "|" + getBirthday().getTime() + "|" + getRegisteredDate().getTime();
	}
}
