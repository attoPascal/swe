package swe2013.um.users.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import swe2013.um.users.AbstractUser;
import swe2013.um.users.RegisteredUser;
import swe2013.um.users.SuperUser;

/**
 * Saves users in a textfile
 * @author Pascal Attwenger (1200595)
 *
 */
public class TextUserDAO implements UserDAO {
	private File file;
	
	/**
	 * Constructs TextUserDAO with specified filename
	 * @param filename
	 */
	public TextUserDAO(String filename) {
		try {
			file = new File(filename);
			if (!file.exists()) {
				file.createNewFile();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<AbstractUser> getUserList() {
		try {
			ArrayList<AbstractUser> list = new ArrayList<AbstractUser>();
			Scanner scan = new Scanner(file);

			while (scan.hasNextLine()) {
				AbstractUser user;
				String line = scan.nextLine();
				String[] fields = line.split("\\|");
				
				if (fields[0].equals("RegisteredUser") && fields.length == 8) {
					String username = fields[1];
					String password = fields[2];
					String fullName = fields[3];
					String email    = fields[4];
					char sex        = fields[5].charAt(0);
					Date birthday       = new Date(Long.parseLong(fields[6]));
					Date registeredDate = new Date(Long.parseLong(fields[7]));
					
					user = new RegisteredUser(username, password, fullName, email, sex, birthday, registeredDate);
					
				} else if (fields[0].equals("SuperUser") && fields.length == 10) {
					String username = fields[1];
					String password = fields[2];
					String fullName = fields[3];
					String email    = fields[4];
					char sex        = fields[5].charAt(0);
					Date birthday   = new Date(Long.parseLong(fields[6]));
					boolean canDeleteUsers = Boolean.parseBoolean(fields[7]);
					boolean canEditUsers   = Boolean.parseBoolean(fields[8]);
					boolean canViewUsers   = Boolean.parseBoolean(fields[9]);
					
					user = new SuperUser(username, password, fullName, email, sex, birthday, canDeleteUsers, canEditUsers, canViewUsers);
				
				} else {
					scan.close();
					throw new IllegalArgumentException("Text line is no valid user");
				}
				
				list.add(user);
			}
			
			scan.close();
			return list;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public AbstractUser getUserByUsername(String username) {
		ArrayList<AbstractUser> list = getUserList();
		
		for (AbstractUser u : list) {
			if (u.getUsername().equals(username)) {
				return u;
			}
		}
		
		return null;
	}

	@Override
	public void saveUser(AbstractUser user) {
		ArrayList<AbstractUser> list = getUserList();
		
		for (AbstractUser u : list) {
			if (u.getUsername().equals(user.getUsername())) {
				throw new IllegalArgumentException("User is already saved");
			}
		}
		
		try {
			FileWriter writer = new FileWriter(file, true);
			writer.write(user.saveAsString() + System.getProperty("line.separator"));
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUser(AbstractUser user) {
		ArrayList<AbstractUser> list = getUserList();
		boolean userExists = false;
		
		for (AbstractUser u : list) {
			if (u.getUsername().equals(user.getUsername())) {
				userExists = true;
				break;
			}
		}
		
		if (!userExists) {
			throw new IllegalArgumentException("User does not exist");
		}
		
		try {
			File temp = new File(file.getAbsolutePath() + ".tmp");
			Scanner scan = new Scanner(file);
			FileWriter writer = new FileWriter(temp);
			
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				if (!line.equals(user.saveAsString())) {
					writer.write(line + System.getProperty("line.separator"));
				}
			}
			
			scan.close();
			writer.close();
			file.delete();
			temp.renameTo(file);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateUser(AbstractUser user) {
		try {
			File temp = new File(file.getAbsolutePath() + ".tmp");
			Scanner scan = new Scanner(file);
			FileWriter writer = new FileWriter(temp);
			boolean userExists = false;
			
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				String username = line.split("\\|")[1];
				
				if (!username.equals(user.getUsername())) {
					writer.write(line + System.getProperty("line.separator"));
				} else {
					writer.write(user.saveAsString() + System.getProperty("line.separator"));
					userExists = true;
				}
			}
			
			scan.close();
			writer.close();
			file.delete();
			temp.renameTo(file);
			
			if (!userExists) {
				throw new IllegalArgumentException("User does not exist");
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
