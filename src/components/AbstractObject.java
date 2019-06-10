package components;

import java.awt.Point;

public abstract class AbstractObject implements Concepts{
	
	Point coordinate;
	int my_id=0;
	int owner;
	
	void setLocation(int x, int y) {
		coordinate = new Point(x,y);
	}
	
	void setOwner() {
		//0 for player1 1 for player2
		this.owner = (this.coordinate.getX()==0)? 0:1;
	}
	
	void setID(int id) {
		this.my_id = id;
	}
	
	public Point getLocation() {
		return coordinate;
	}
	public void moveTo(Point p) {
		this.coordinate.setLocation(p);
	}

}
