package components;
import components.AbstractObject;

public class Stone extends AbstractObject{
	//location of stone is same as house
	//where x=0 is in the column of player1
	//x=1 is in the player2
	int stoneID = 0;
	public Stone(int x, int y, int ID) {
		this.setLocation(x, y);
		this.setOwner();
		this.stoneID = ID;
	}
	
	public void moveTo(double x, double y) {
		this.coordinate.move((int)x, (int)y);
	}
	
	public int getOwner() {
		return owner;
	}
	public void changeOwner(int playerNumber) {
		this.owner = playerNumber;
	}
	public int getStoneID() {
		return this.stoneID;
	}
}
