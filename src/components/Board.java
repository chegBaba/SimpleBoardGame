package components;
import java.awt.Point;

import components.House;


public class Board extends AbstractObject{
	
	House[][] inventory;
	public Board(House[][] hs) {
		setupHouse(hs);
	}
	
	public void setupHouse(House[][] hs) {
		inventory = hs;
	}
	
	public House getHouse(int x, int y) {
		return inventory[x][y];
	}
	public House getHouse(Point pt) {
		return getHouse(pt.x,pt.y);
	}
	public House getHouse(House hs) {
		return getHouse(hs.getLocation());
	}
}