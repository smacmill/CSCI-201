package models;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonSyntaxException;

import util.FieldNotFoundException;
import util.GsonDataParser;

// for loadOrderFile()
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class OrderList {

	private ArrayList<Order> orders;

	public OrderList() {
		orders = new ArrayList<Order>();
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}
}
