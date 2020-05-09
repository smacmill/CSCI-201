package models;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Order {
	private int whenReady;
	private String restaurant;
	private String food;
	private double roundtrip;
	private Driver driver;
	
	public Order(int wr, String rest, String f) {
		this.whenReady = wr;
		this.restaurant = rest;
		this.food = f;
		this.driver = null;
		this.roundtrip = 0;
	}
	
	public int getWhenReady() {
		return whenReady;
	}
	
	public String getRestaurant() {
		return restaurant;
	}
	
	public String getFood() {
		return food;
	}
	
	public Driver getDriver() {
		return driver;
	}
	
	public double getRoundtrip() {
		return roundtrip;
	}
	
	public void setDriver(Driver d) {
		driver = d;
	}
	
	public void setRoundtrip(double rt) {
		roundtrip = rt;
	}
	
}
