package models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Restaurant {
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private int drivers;
    private transient ArrayList<Driver> idle;
    private transient ArrayList<Driver> busy;
    private transient double distance;
    private ArrayList<String> menu;

    public Restaurant(String name, String address, double latitude, double longitude, int drivers, double distance, ArrayList<String> menu) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.drivers = drivers;
        this.menu = menu;
        this.distance = distance;
    }

//    public void init() {
//    	this.idle = new ArrayList<Driver>();
//        this.busy = new ArrayList<Driver>();
//    	for (int i = 0; i < this.drivers; i++) {
//        	// insert drivers into idle list
//        	Driver temp = new Driver(this.name, "", (long) (distance*2), 0);
//        	this.idle.add(temp);
//        }
//    }
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
    
    public int getDrivers() {
    	return drivers;
    }

    public ArrayList<String> getMenu() {
        return menu;
    }
    
	public double getDistance() {
		return distance;
	}
	
	public double getOrderCompletionTime() {
		return distance*2;
	}
	
	public ArrayList<Driver> getIdle(){
		return idle;
	}
	
	public ArrayList<Driver> getBusy(){
		return busy;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}

    @SuppressWarnings("unchecked")
    public JSONObject toJson() {
        // Initialize new JSONObject
        JSONObject jsonObject = new JSONObject();

        // Add String array to JSONArray
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(this.menu);

        // Insert components into jsonObject
        jsonObject.put("name", this.name);
        jsonObject.put("address", this.address);
        jsonObject.put("latitude", this.latitude);
        jsonObject.put("longitude", this.longitude);
        jsonObject.put("drivers", this.drivers);
        jsonObject.put("menu", jsonArray);

        return jsonObject;
    }

}
