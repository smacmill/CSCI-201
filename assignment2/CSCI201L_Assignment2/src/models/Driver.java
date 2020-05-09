package models;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Driver extends Thread {
	
	long roundtrip;
	long delay;
	String item;
	String restaurant;
	private Lock driverLock; // = new ReentrantLock();
	Semaphore sema;
	
	public Driver(String i, String r, long rt, long d, Semaphore sem) {
		item = i;
		restaurant = r;
		roundtrip = rt;
		delay = d;
		driverLock = new ReentrantLock();
		sema = sem;
	}
	
	public void run(){
		try {
			
			sleep(this.delay*1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		try {
			sema.acquire();
		} catch (InterruptedException e2) {
			System.out.println("An error occured!");
		}
		
		try {
			
			System.out.println("[" + LocalTime.now() + "] Starting delivery of " + item + " from " + restaurant + "!");
			Thread.sleep(roundtrip*1000);
			System.out.println("[" + LocalTime.now() + "] Finished delivery of " + item + " from " + restaurant + "!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			
			sema.release();
			
		}
		
	}
	
	public void printTimeStamp() {
    	LocalTime time = LocalTime.now();
    	int hr = time.getHour();
    	int min = time.getMinute();
    	int s = time.getSecond();
    	System.out.print("[" + hr + ":" + min + ":" + s + "]");
    }
	
	public void setItem(String i) {
		item = i;
	}
	
	
}
