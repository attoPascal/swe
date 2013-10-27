package swe2013.um.test;

import java.util.ArrayList;
import java.util.Calendar;

import swe2013.um.users.AbstractUser;
import swe2013.um.users.RegisteredUser;
import swe2013.um.users.SuperUser;
import swe2013.um.users.dao.SerializedUserDAO;
import swe2013.um.users.dao.UserDAO;

public class TestSerialized {
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		
		cal.set(1992, Calendar.OCTOBER, 17);
		RegisteredUser u1 = new RegisteredUser("atto", "pass9999999", "Pascal A", "p@atto.com", 'm', cal.getTime());
		
		cal.set(1970, Calendar.JANUARY, 1);
		SuperUser u2 = new SuperUser("god", "holymolydude", "Mr. Almighty", "god@heaven.com", 'f', cal.getTime(), true, true, true);
		
		UserDAO dao = new SerializedUserDAO("users.ser");
		dao.saveUser(u1);
		dao.saveUser(u2);
		
		ArrayList<AbstractUser> list = dao.getUserList();
		for (AbstractUser user : list) {
			System.out.println(user);
			System.out.println();
		}
		
		AbstractUser u3 = dao.getUserByUsername("atto");
		System.out.println(u3.verifyPassword("pass9999999"));
		System.out.println("u3: " + u3);
		
		dao.deleteUser(u3);
		System.out.println("Deleted.");
		
		u2.setSex('M');
		dao.updateUser(u2);
		
		list = dao.getUserList();
		for (AbstractUser user : list) {
			System.out.println(user);
			System.out.println();
		}
		
		cal.set(2000, Calendar.JANUARY, 29);
		RegisteredUser u4 = new RegisteredUser("jul", "idontevencare", "Julchen", "j@atto.com", 'f', cal.getTime());
		
		System.out.println(u1.verifyAge() ? "yeah" : "nooo");
		System.out.println(u4.verifyAge() ? "yeah" : "nooo");
		
		dao.saveUser(u4);
		dao.deleteUser(u4);
		dao.deleteUser(u2);
	}

}
