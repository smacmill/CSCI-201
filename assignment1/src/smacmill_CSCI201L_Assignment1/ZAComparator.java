package smacmill_CSCI201L_Assignment1;

import java.util.*;

public class ZAComparator implements Comparator<Datum> {
	public int compare(Datum d1, Datum d2) {
		Datum dat1 = d1;
		Datum dat2 = d2;
		return dat2.getName().compareTo(dat1.getName());
	}
}
