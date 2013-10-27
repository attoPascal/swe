package swe2013.um.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import swe2013.um.UserManagement;
import swe2013.um.users.SuperUser;

public class UserManagementTest {
	static UserManagement um;
	static String filename = "data/testusers.txt";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		um = new UserManagement(UserManagement.TEXT, filename);
		um.getUserDAO().saveUser(new SuperUser("god", "lettherebelight", "Mr. Almighty", "god@heaven.com", 'm', new Date(0), true, true, true));
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		new File(filename).delete();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testUM() {
		//login
		um.login("god", "lettherebelight");
		
		//initial number of users
		assertEquals(1, um.numberOfUsers());
		assertEquals(1, um.numberOfSuperUsers());
		assertEquals(0, um.numberOfRegisteredUsers());
		assertEquals(0, um.numberOfAdultRegisteredUsers());
		
		//edit signed on user
		//god is a girl
		assertEquals("user=(god,lettherebelight,Mr. Almighty,god@heaven.com,m,Thu Jan 01 01:00:00 CET 1970)", um.viewSignedOnUser());
		um.editSignedOnUser(null, null, null, 'f', null);
		assertEquals("user=(god,lettherebelight,Mr. Almighty,god@heaven.com,f,Thu Jan 01 01:00:00 CET 1970)", um.viewSignedOnUser());
		
		
		//register users
		um.registerUser("bill", "ctlr-alt-delete", "Bill Gates", "gates@hotmail.com", 'm', new Date(55,9,28));
		um.registerUser("steve", "turtleneck", "Steve Jobs", "jobs@icloud.com", 'm', new Date(55,1,24));
		assertEquals(3, um.numberOfUsers());
		assertEquals(1, um.numberOfSuperUsers());
		assertEquals(2, um.numberOfRegisteredUsers());
		assertEquals(2, um.numberOfAdultRegisteredUsers());
		assertEquals("allusers=(god,bill,steve)", um.viewAllUsers());
		
		//edit user
		assertEquals("user=(steve,turtleneck,Steve Jobs,jobs@icloud.com,m,Thu Feb 24 00:00:00 CET 1955)", um.viewUser("steve"));
		um.editUser("steve", "androidsucks", null, null, ' ', null);
		assertEquals("user=(steve,androidsucks,Steve Jobs,jobs@icloud.com,m,Thu Feb 24 00:00:00 CET 1955)", um.viewUser("steve"));
		
		//delete user
		um.deleteUser("bill");
		assertEquals(2, um.numberOfUsers());
		assertEquals(1, um.numberOfSuperUsers());
		assertEquals(1, um.numberOfRegisteredUsers());
		assertEquals(1, um.numberOfAdultRegisteredUsers());
	}
}
