package smacmill_CSCI201L_Assignment1;

import java.util.*;

public class closeFarComparator implements Comparator<Datum> {
	public int compare(Datum d1, Datum d2) {
		Datum dat1 = d1;
		Datum dat2 = d2;
		
		return Double.compare(dat1.calcDist(Main.userLat, Main.userLong), dat2.calcDist(Main.userLat, Main.userLong));
	}
}
