package swe2013.um;

import java.util.Scanner;

/**
 * UserManagement environment on the console
 * @author Pascal Attwenger (1200595)
 */
public class RunUserManagement {
	private static Scanner console = new Scanner(System.in);
	private static String filename = "data/users.txt";
	private static UserManagement um = new UserManagement(UserManagement.TEXT, filename);
	
	public static void main(String[] args) {
		System.out.println("UserManagement started");
		System.out.println("Saving users as textfile in '" + filename + "'");
		System.out.println("List commands with 'help'");
		
		boolean quit = false;
		
		do {
			System.out.print("> ");
			String command = console.next();
			
			try {
				switch (command.toLowerCase()) {
				case "login":
					login();
					break;
				case "logout":
					logout();
					break;
				case "help":
					help();
					break;
				case "num":
				case "numberofusers":
					numberOfUsers();
					break;
				case "numsuper":
				case "numberofsuperusers":
					numberOfSuperUsers();
					break;
				case "numreg":
				case "numberofregisteredusers":
					numberOfRegisteredUsers();
					break;
				case "num18":
				case "numberofadultregisteredusers":
					numberOfAdultRegisteredUsers();
					break;
				case "viewme":
				case "viewsignedonuser":
					viewSignedOnUser();
					break;
				case "viewall":
				case "viewallusers":
					viewAllUsers();
					break;
				case "view":
				case "viewuser":
					viewUser();
					break;
				case "quit":
					quit = true;
					break;
				default:
					System.out.println("Illegal command.");
					help();
				}
			}
			
			catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
		} while (!quit);
	}

	private static void login() {
		System.out.print("username: ");
		String username = console.next();
		
		System.out.print("password: ");
		String password = console.next();
		
		um.login(username, password);
		
		System.out.println("Logged in as '" + username + "'");
	}
	
	private static void logout() {
		um.logout();
		System.out.println("Logged out");
	}
	
	private static void numberOfUsers() {
		System.out.println(um.numberOfUsers());
	}

	private static void numberOfSuperUsers() {
		System.out.println(um.numberOfSuperUsers());
	}

	private static void numberOfRegisteredUsers() {
		System.out.println(um.numberOfRegisteredUsers());
	}

	private static void numberOfAdultRegisteredUsers() {
		System.out.println(um.numberOfAdultRegisteredUsers());
	}
	
	private static void viewSignedOnUser() {
		System.out.println(um.viewSignedOnUser());
		
	}
	
	private static void viewAllUsers() {
		System.out.println(um.viewAllUsers());
	}

	private static void viewUser() {
		System.out.println("username: ");
		String username = console.next();
		System.out.println(um.viewUser(username));
	}
	
	private static void help() {
		System.out.println("Possible commands:");
		System.out.println("login");
		System.out.println("logout");
		System.out.println("num / numberOfUsers");
		System.out.println("numSuper / numberOfSuperUsers");
		System.out.println("numReg / numberOfRegisteredUsers");
		System.out.println("num18 / numberOfAdultRegisteredUsers");
		System.out.println("viewMe / viewSignedOnUser");
		System.out.println("viewAll / viewAllUsers");
		System.out.println("view / viewUser");
		System.out.println("quit");
	}
}
