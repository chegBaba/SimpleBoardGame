package components;

import java.util.ArrayList;

public class Pocket extends House {
	
	public Pocket(int x, int y) {
		super(x,y);
		inventory = new ArrayList<Stone>();
	}
}