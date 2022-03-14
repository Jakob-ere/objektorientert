package game2048;

import java.util.Comparator;

public class RowComparator implements Comparator<Tile> {

	@Override
	public int compare(Tile o1, Tile o2) {
		if (o1.getValue() == 0) return -1;
		if (o1.getValue() > 0 && o2.getValue() > 0) return 0;
        else return 1;
	}
    
}
