package smacmill_CSCI201L_Assignment1;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;



public class Main {
	
	static double userLat = 0;
	static double userLong = 0;
	static Restaurant restList = null;
	
	public static void printMenu() {
		System.out.print("\n\t1) Display all restaurants\n\t2) Search for a restaurant\n\t3) Search for a menu item\n\t4) Add a new restaurant\n\t5) Remove a restaurant\n\t6) Sort restaurants\n\t7) Exit\n");
	}
	
	public static void printPrompt() {
		System.out.print("What would you like to do? ");
	}
	
	public static void AZsort() {
		Collections.sort(restList.getData(), new AZComparator());
	}
	
	public static void ZAsort() {
		Collections.sort(restList.getData(), new ZAComparator());
	}
	
	public static void closeFarSort() {
		Collections.sort(restList.getData(), new closeFarComparator());
	}
	
	public static void farCloseSort() {
		Collections.sort(restList.getData(), new farCloseComparator());
	}
	
	public static void main(String[] args) {
		int userNum = 0;
		int userNum6 = 0;
		int userNum7 = 0;
		String newAddy = "";
		
		ArrayList<Datum> toAdd = new ArrayList<Datum>();
		
		String userFile = "";
		
		// create instance of Gson
		Gson gson = new Gson();
		
		// make scanner
		Scanner userInput = new Scanner(System.in);
		
		// initialization questions
		
		boolean userFileOK = false;
		while (!userFileOK) {
			System.out.print("What is the name of your restaurant file? ");
			try {
				userFile = userInput.nextLine();
				Reader reader;
				reader = Files.newBufferedReader(Paths.get(userFile));
				restList = gson.fromJson(reader, Restaurant.class);
				if (restList == null) {
					System.out.println("The file " + userFile + " is not formatted properly.");
					userFileOK = false;
				}
				else {
					userFileOK = true;
				}
				

			} catch (IOException e) {
				System.out.print("The file " + userFile + " could not be found.\n");
				userFileOK = false;
			} catch (JsonSyntaxException je) {
				System.out.println("The file " + userFile + " is not formatted properly.");
				userFileOK = false;
			}
			
		}
		System.out.println("The file has been properly read.");
		
		boolean userLatOK = false;
		while (!userLatOK) {
			
			System.out.print("\nWhat is your latitude? ");
			try{
				userLat = Double.parseDouble(userInput.nextLine());
				userLatOK = true;
			}
			catch (NumberFormatException ex) {
				System.out.println("\nLatitude must be numeric.");
				userLatOK = false;
			}
		}
		
		boolean userLongOK = false;
		while (!userLongOK) {
			
			System.out.print("\nWhat is your longitude? ");
			try{
				userLong = Double.parseDouble(userInput.nextLine());
				userLongOK = true;
			}
			catch (NumberFormatException ex) {
				System.out.println("\nLongitude must be numeric.");
				userLongOK = false;
			}
		}
		
		// show menu only once
		printMenu();
		
		boolean programOn = true;
		
		
		while (programOn) {
			// loop while user input is not OK
			boolean userInputOK = false;
			while (!userInputOK) {
				printPrompt();
				// user input
				
				try {
					userNum = Integer.parseInt(userInput.nextLine());
					if (userNum < 1 || userNum > 7) {
						System.out.println("\nThat is not a valid option.\n");
					}
					else {
						userInputOK = true;
					}
				}
				catch (NumberFormatException ex) {
					System.out.println("\nThat is not a valid option.\n");
					userInputOK = false;
				}
				
			}
			
			if (userNum == 1) { // print all the rests
				// loop through list
				System.out.println();
				for (int i = 0; i < restList.getData().size(); i++) {
					System.out.println(restList.getData().get(i).printDistLoc());
				}
				printMenu();
			}
			if (userNum == 2) { // search for a restaurant
				boolean found = false;
				
				while (!found) { // keep prompting for a new restaurant while the restaurant they input has not been found
					
					System.out.println();
					System.out.print("What is the name of the restaurant you would like to search for? ");
					
					String searchName = userInput.nextLine();
					
					String searchNameUC = searchName.toUpperCase();
					
					// loop thru list of restaurants
					for (int i = 0; i < restList.getData().size(); i++) {
						String tempUC = restList.getData().get(i).getName().toUpperCase(); // uppercase version of each rest name
						if (tempUC.contains(searchNameUC)) {
							System.out.println(restList.getData().get(i).printDistLoc());
							found = true;
						}
					}
					
					if (found == false) {
						System.out.println(searchName + " could not be found.");
					}
				}
				printMenu();
			}
			if (userNum == 3) { // search for a menu item
				
				boolean found3 = false;
				while (!found3) {
					
					System.out.print("What menu item would you like to search for? ");
					
					String searchName3 = userInput.nextLine();
					
					String searchName3UC = searchName3.toUpperCase();
					
					System.out.println();
					
					for (int i = 0; i < restList.getData().size(); i++) {
						for (int j = 0; j < restList.getData().get(i).getMenu().size(); j++) {
							if (restList.getData().get(i).getMenu().get(j).toUpperCase().contains(searchName3UC)) {
								System.out.println(restList.getData().get(i).getName() + " serves " + restList.getData().get(i).getMenu().get(j) + ".");
								found3 = true;
							}
						}
					}
					
					if (found3 == false) {
						System.out.println("No restaurant nearby serves " + searchName3 + ".\n");
					}
				}
			
				
				printMenu();
			}
			if (userNum == 4) {
				boolean isNew = false;
				
				String newName = "";
				double newLat = 0;
				double newLong = 0;
				
				while (!isNew) { // keep prompting for a new restaurant while the restaurant they input exists there
					
					System.out.print("What is the name of the restaurant you would like to add? ");
					
					newName = userInput.nextLine();
					String newRestUC = newName.toUpperCase();
					
					// loop thru list of restaurants
					for (int i = 0; i < restList.getData().size(); i++) {
						String tempUC = restList.getData().get(i).getName().toUpperCase();
						if (newRestUC.equals(tempUC)) {
							System.out.print("There is already an entry for " + newName + ".\n");
							isNew = false;
							break;
						}
						else {
							isNew = true;
						}
					}

				}
				
				boolean isNewAddy = false;
				while (!isNewAddy) {
					System.out.print("What is the address for " + newName + "? ");
					newAddy = userInput.nextLine();
					String newAddyUC = newAddy.toUpperCase();
					
					for (int i = 0; i < restList.getData().size(); i++) {
						String tempAddyUC = restList.getData().get(i).getAddress().toUpperCase();
						if (newAddyUC.equals(tempAddyUC)) {
							System.out.println("A restaurant already exists at address " + newAddy + ".\n");
							isNewAddy = false;
							break;
						}
						else {
							isNewAddy = true;
						}
					}
				}
				
				
				userLatOK = false;
				while (!userLatOK) {
					
					System.out.print("What is the latitude for " + newName + "? ");
					try{
						newLat = Double.parseDouble(userInput.nextLine());
						userLatOK = true;
					}
					catch (NumberFormatException ex) {
						System.out.println("\nLatitude must be numeric.");
						userLatOK = false;
					}
				}
				
				userLongOK = false;
				while (!userLongOK) {
					
					System.out.print("What is the longitude for " + newName + "? ");
					try{
						newLong = Double.parseDouble(userInput.nextLine());
						userLongOK = true;
					}
					catch (NumberFormatException ex) {
						System.out.println("\nLongitude must be numeric.");
						userLongOK = false;
					}
				}
				
				
				List<String> newMenu = new ArrayList<String>();
				System.out.print("What does " + newName + " serve? ");
				String newItem = userInput.nextLine();
				newMenu.add(newItem);
				
				boolean more = true;
				while (more) {
					System.out.print("\t1) Yes\n\t2) No\n");
					System.out.print("Does " + newName + " serve anything else? ");
					int anythingElse = Integer.parseInt(userInput.nextLine());
					try {
						if (anythingElse == 1) { // there is more
							System.out.print("What does " + newName + " serve? ");
							newItem = userInput.nextLine();
							newMenu.add(newItem);
						}
						else if (anythingElse == 2) { // no more
							more = false;
							break;
						}
						else {
							System.out.println("\nThat is not a valid option.\n");
							more = true;
						}
					}
					catch(NumberFormatException ex) {
						System.out.println("\nThat is not a valid option.\n");
						more = true;
					}
					
				}
				
				Datum newDatum = new Datum();
				newDatum.setName(newName);
				newDatum.setAddress(newAddy);
				newDatum.setLatitude(newLat);
				newDatum.setLongitude(newLong);
				newDatum.setMenu(newMenu);
				//toAdd.add(newDatum);
				restList.getData().add(newDatum);
				
				printMenu();
				
			}
			if (userNum == 5) {
				// remove a restaurant
				for (int i = 0; i < restList.getData().size(); i++) {
					System.out.println("\t" + (i+1) + ") " + restList.getData().get(i).getName());
				}
				
				
				userInputOK = false;
				int userNum5 = 0;
				while (!userInputOK) {
					System.out.println("Which restaurant would you like to remove? ");
					try {
						userNum5 = Integer.parseInt(userInput.nextLine());
						if (userNum5 < 1 || userNum5 > (restList.getData().size() + 1)) {
							System.out.println("\nThat is not a valid option.\n");
						}
						else {
							userInputOK = true;
						}
					}
					catch(NumberFormatException ex){
						System.out.println("\nThat is not a valid option.\n");
						userInputOK = false;
					}
				}
				
				System.out.println(restList.getData().get(userNum5 - 1).getName() + " is now removed.");
				restList.getData().remove(userNum5 - 1);
				
				printMenu();
			}
			
			
			if (userNum == 6) {
				System.out.println("\t1) A to Z\n\t2) Z to A\n\t3) Closest to farthest\n\t4) Farthest to closest");
				userInputOK = false;
				
				while (!userInputOK) {
					System.out.println("How would you like to sort by? ");
					
					try {
						userNum6 = Integer.parseInt(userInput.nextLine());
						if (userNum6 < 1 || userNum6 > 7) {
							System.out.println("\nThat is not a valid option.\n");
						}
						else {
							userInputOK = true;
						}
					}
					catch (NumberFormatException ex) {
						System.out.println("\nThat is not a valid option.\n");
						userInputOK = false;
					}
					
				}
				
				if (userNum6 == 1) {
					AZsort();
					System.out.println("Your restaurants are now sorted from A to Z.");
				}
				if (userNum6 == 2) {
					ZAsort();
					System.out.println("Your restaurants are now sorted from Z to A.");
				}
				if (userNum6 == 3) {
					closeFarSort();
					System.out.println("Your restaurants are now sorted from closest to farthest.");
				}
				if (userNum6 == 4) {
					farCloseSort();
					System.out.println("Your restaurants are now sorted from farthest to closest.");
				}
				
				printMenu();
			}
			
			
			if (userNum == 7) {
				System.out.println("\t1) Yes\n\t2) No");
				System.out.println("Would you like to save your edits? ");
				boolean badInput = true;
				while (badInput) {
					try {
						userNum7 = Integer.parseInt(userInput.nextLine());
						if (userNum7 != 1 && userNum7 != 2) {
							System.out.println("\nThat is not a valid option.\n");
							badInput = true;
						}
						else {
							badInput = false;
						}
					}
					catch (NumberFormatException ex) {
						System.out.println("\nThat is not a valid option.\n");
						userInputOK = true;
					}
				}
				
				if (userNum7 == 1) {
					// add everything
					//for (int i = 0; i < toAdd.size(); i++) {
						//restList.addData(toAdd.get(i));
					//}
					//remove everything
					
					try {
						FileWriter fr = new FileWriter(userFile);
						gson.toJson(restList, fr);
						fr.flush();
						System.out.println("Your edits have been saved to restaurant.txt.\nThank you for using my program!");
					} catch (JsonIOException e) {
						System.out.println("Failed to save changes.");
					} catch (IOException e) {
						System.out.println("Failed to save changes.");
					}
					
					
				}
				else if (userNum7 == 2) {
					System.out.println("Your edits have not been saved to restaurant.txt.\nThank you for using my program!");
				}
				programOn = false;
			}
		}
		
		
		
	}

}
