package swe2013.um.users.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import swe2013.um.users.AbstractUser;

/**
 * Saves users in a serialized file
 * @author Pascal Attwenger (1200595)
 *
 */
public class SerializedUserDAO implements UserDAO {
	private File file;
	
	/**
	 * Constructs SerializedUserDAO with specified filename
	 * @param filename
	 */
	public SerializedUserDAO(String filename) {
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

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<AbstractUser> getUserList() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			ArrayList<AbstractUser> list = (ArrayList<AbstractUser>) ois.readObject();
			ois.close();
			
			return list;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			if (e instanceof EOFException) {
				return new ArrayList<AbstractUser>();
			} else {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
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
			list.add(user);
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(list);
			oos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUser(AbstractUser user) {
		try {
			ArrayList<AbstractUser> list = getUserList();
			boolean userExists = false;
			
			for (AbstractUser u : list) {
				if (u.equals(user)) {
					userExists = true;
					list.remove(u);
					
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
					oos.writeObject(list);
					oos.close();
					
					break;
				}
			}
		
			if (!userExists) {
				throw new IllegalArgumentException("User does not exist");
			}
			
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateUser(AbstractUser user) {
		try {
			ArrayList<AbstractUser> list = getUserList();
			boolean userExists = false;
			
			for (AbstractUser u : list) {
				if (u.getUsername().equals(user.getUsername())) {
					userExists = true;
					int i = list.indexOf(u);
					list.set(i,  user);
					
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
					oos.writeObject(list);
					oos.close();
					
					break;
				}
			}
		
			if (!userExists) {
				throw new IllegalArgumentException("User does not exist");
			}
			
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
