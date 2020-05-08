package smacmill_CSCI201L_Assignment1;

import java.util.*;

public class AZComparator implements Comparator<Datum> {
	public int compare(Datum d1, Datum d2) {
		Datum dat1 = d1;
		Datum dat2 = d2;
		return dat1.getName().compareTo(dat2.getName());
	}
}
