package swe2013.um.users;

import java.util.Date;

/**
 * SuperUser
 * @author Pascal Attwenger (1200595)
 */
public class SuperUser extends AbstractUser {
	private static final long serialVersionUID = 1L;
	private boolean canDeleteUsers;
	private boolean canEditUsers;
	private boolean canViewUsers;
	
	/**
	 * Constructs a new SuperUser with all rigths set to false
	 * @param username
	 * @param password
	 * @param fullName
	 * @param email
	 * @param sex
	 * @param birthday
	 */
	public SuperUser(String username, String password, String fullName, String email, char sex, Date birthday) {
		super(username, password, fullName, email, sex, birthday);
		canDeleteUsers = false;
		canEditUsers   = false;
		canViewUsers   = false;
	}
	
	/**
	 * Constructs a new SuperUser with the specified rights
	 * @param username
	 * @param password
	 * @param fullName
	 * @param email
	 * @param sex
	 * @param birthday
	 * @param canDeleteUsers
	 * @param canEditUsers
	 * @param canViewUsers
	 */
	public SuperUser(String username, String password, String fullName, String email, char sex, Date birthday, boolean canDeleteUsers, boolean canEditUsers, boolean canViewUsers) {
		super(username, password, fullName, email, sex, birthday);
		this.canDeleteUsers = canDeleteUsers;
		this.canEditUsers = canEditUsers;
		this.canViewUsers = canViewUsers;
	}
	
	public boolean isCanDeleteUsers() {
		return canDeleteUsers;
	}

	public void setCanDeleteUsers(boolean canDeleteUsers) {
		this.canDeleteUsers = canDeleteUsers;
	}

	public boolean isCanEditUsers() {
		return canEditUsers;
	}

	public void setCanEditUsers(boolean canEditUsers) {
		this.canEditUsers = canEditUsers;
	}

	public boolean isCanViewUsers() {
		return canViewUsers;
	}

	public void setCanViewUsers(boolean canViewUsers) {
		this.canViewUsers = canViewUsers;
	}

	@Override
	public String toString() {
		String nl = System.getProperty("line.separator");
		String string =
				"SuperUser" + nl
				+ "username:  " + getUsername() + nl
				+ "full name: " + getFullName() + nl
				+ "mail:      " + getEmail() + nl
				+ "sex:       " + getSex() + nl
				+ "birthday:  " + getBirthday() + nl
				+ "rights:    delete=" + isCanDeleteUsers() + " edit=" + isCanEditUsers() + " view=" + isCanViewUsers() + ";";
		return string;
	}

	@Override
	public String saveAsString() {
		return "SuperUser|" + getUsername() + "|" + getPassword() + "|" + getFullName() + "|" + getEmail() + "|" + getSex() + "|" + getBirthday().getTime() + "|" + isCanDeleteUsers() + "|" + isCanEditUsers() + "|" + isCanViewUsers();
	}
}
