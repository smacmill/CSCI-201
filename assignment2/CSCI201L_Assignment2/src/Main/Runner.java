package Main;
import java.util.concurrent.ExecutorService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.*;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;

import models.Driver;
import models.Order;
import models.OrderList;
import models.Restaurant;
import models.RestaurantList;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import comparators.AlphabeticalComparator;
import comparators.DistanceComparator;
import util.FieldNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
public class Runner {
    private static Scanner scan;
    private static String fileName;
    private static RestaurantList restaurants;
    private static OrderList orders;
    private static Double userLatitude;
    private static Double userLongitude;

    public static void main(String[] args) {
        
    	Runner obj = new Runner();
    	orders = new OrderList();
    	
    	scan = new Scanner(System.in);
        obj.loadRestaurantFile();
        obj.loadOrdersFile();
        obj.getUserLocation();
        System.out.println("Starting execution of program...");
        obj.loadDistances();
        
        HashMap<Restaurant, Semaphore> restSem = new HashMap<>();
        
        for (int i = 0; i < restaurants.getRestaurants().size(); i++) {
        	Semaphore sem = new Semaphore(restaurants.getRestaurants().get(i).getDrivers());
        	restSem.put(restaurants.getRestaurants().get(i), sem);
        }
        
        obj.setRoundtrips();
        
        Vector<Driver> allDrivers = new Vector<Driver>();
        
        // each driver tries to acquire the semaphore
        for (int i = 0; i < orders.getOrders().size(); i++) {
        	// load order from orders list
        	Semaphore currSem = restSem.get(findRestaurant(orders.getOrders().get(i).getRestaurant()));
        	String item = orders.getOrders().get(i).getFood();
			String rest = orders.getOrders().get(i).getRestaurant();
			long roundTrip = (long)orders.getOrders().get(i).getRoundtrip();
			long delay = (long)orders.getOrders().get(i).getWhenReady();
			
			
			Driver d = new Driver(item, rest, roundTrip, delay, currSem);
			allDrivers.add(d);
			d.start();
        }
       
       for (int i = 0; i < allDrivers.size(); i++) {
    	   try {
			allDrivers.get(i).join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }
       
       System.out.println("All orders complete!");
       
        
    }

    // figure out relationship between whenReady and getDrivers
    
    private void setRoundtrips() { // add the roundtrip time from the restaurant class to the order class
    	for (int i = 0; i < orders.getOrders().size(); i++) {
    		Order tempO = orders.getOrders().get(i);
    		String tempRestName = tempO.getRestaurant();
    		Restaurant tempQueryResult = findRestaurant(tempRestName);
    		double tempRoundtrip = tempQueryResult.getDistance();
    		tempRoundtrip *= 2;
    		orders.getOrders().get(i).setRoundtrip(tempRoundtrip);
    		}
    }
   
    private static Restaurant findRestaurant(String query) {
	   for (Restaurant r: restaurants.getRestaurants()) {
		   //System.out.println(r.getName());
		   //System.out.println(query);
           if (r.getName().equalsIgnoreCase(query)) return r;
       }
	System.out.println("No match found!");
	return null;
    }
    
    private void loadRestaurantFile() {
        boolean validFile = false;
        do {
            System.out.println("What is the name of the file containing the restaurant information?");
            fileName = scan.nextLine();
            try {
                restaurants = new RestaurantList(fileName);
                System.out.println("The file has been properly read");
                validFile = true;
            } catch (IOException e) {
                System.out.println("The file " + fileName + " could not be found.");
            } catch (ParseException e) {
                System.out.println("Cannot parse file.");
            } catch (FieldNotFoundException e) {
                System.out.println("Missing data parameters.");
            } catch (ClassCastException | JsonSyntaxException e){
                System.out.println("Data is malformed.");
            }
        }
        while (!validFile);
    }
    
    private void loadOrdersFile() {
    	boolean validFile = false;
    	boolean validData = true;
    	do {
    		System.out.println("What is the name of the file containing the schedule information?");
            fileName = scan.nextLine();
            FileReader fr;
        	BufferedReader br;
        	try {
        		fr = new FileReader(fileName);
        		br = new BufferedReader(fr);
        		String line = br.readLine();
        		while (line != null && validData) {
        			String[] lineArray = line.split(", ");
        			int temp0 = 0;
        			String temp1 = "";
        			String temp2 = "";
        			for (int i = 0; i < lineArray.length; i++) {
        				//System.out.println(lineArray[i]);
        				if (i == 0) {
        					try {
        						temp0 = Integer.parseInt(lineArray[i]);
        					} catch(NumberFormatException nfe){
        						System.out.println("Data is malformed.");
        						validData = false;
        						break;
        					}
        					
        				}
        				if (i == 1) {
        					temp1 = lineArray[i];
        				}
        				if (i == 2) {
        					temp2 = lineArray[i];
        				}
        			}
        			Order tempOrder = new Order(temp0, temp1, temp2);
        			orders.getOrders().add(tempOrder);
        			line = br.readLine();
        		}
        		validFile = true;
        		System.out.println("The file has been properly read");
        	} catch (FileNotFoundException fnfe){
        		System.out.println("The file " + fileName + " could not be found.");
        	} catch(IOException ioe) {
        		System.out.println("The file " + fileName + " could not be found.");
        	}
    	}
    	while(!validFile);
    }

    private void getUserLocation() {
        userLatitude = getLatitude("What is the latitude?");
        userLongitude = getLongitude("What is the longitude?");
    }
    
    private void loadDistances() {
    	for (Restaurant r : restaurants.getRestaurants()) {
    		r.setDistance(calculateDistance(r.getLatitude(), r.getLongitude()));
    	}
    }

    private double getLatitude(String query) {
        double latitude = 0.0;
        while (true) {
            System.out.println(query);
            String latitudeString = scan.nextLine();
            try {
                latitude = Double.parseDouble(latitudeString);
                if (latitude < -90.0 || latitude > 90.0) {
                	throw new NumberFormatException();
                }
                return latitude;
            } catch (NumberFormatException ignore) { }
        }
    }

    private double getLongitude(String query) {
        double longitude = 0.0;
        while (true) {
            System.out.println(query);
            String longitudeString = scan.nextLine();
            try {
                longitude = Double.parseDouble(longitudeString);
                if (longitude < -180.0 || longitude > 180.0) {
                	throw new NumberFormatException();
                }
                return longitude;
            } catch (NumberFormatException ignore) { }
        }
    }

    /**
     * Calculates distance from user to the specified coordinates
     *
     * @param latitude Latitude of restaurant
     * @param longitude Longitude of restaurant
     * @return Distance from user to restaurant
     */
    private double calculateDistance(double latitude, double longitude) {
    	return 3963.0 * Math.acos((Math.sin(Math.toRadians(userLatitude)) * Math.sin(Math.toRadians(latitude))) + Math.cos(Math.toRadians(userLatitude))
        * Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(userLongitude) - Math.toRadians(longitude)));
    }

    
    
}
