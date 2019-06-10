package components;
import components.Stone;
import java.util.ArrayList;
public class House extends AbstractObject{
	
	//A House object can holds none or number of Stones
	ArrayList<Stone> inventory;
	
	
	public House(int x, int y) {
		
		inventory = new ArrayList<Stone>(4);
		this.setLocation(x, y);
		this.setOwner();
	}
	
	public void empty() {
		this.inventory.clear();
	}
	public boolean isEmpty() {
		return (inventory.size() == 0)? true:false;
	}
	
	//drop stones into this house
	public void addStone(Stone stn) {
		this.inventory.add(stn);
	}
	
	public void addStones(ArrayList<Stone> stone_arr) {
		for (Stone s: stone_arr) {
			this.addStone(s);
			s.moveTo(this.coordinate);
		}
	}
	
	public ArrayList<Stone> getStones() {
		return this.inventory;
	}
	
	public int getStoneNumber() {
		return this.inventory.size();
	}
	
	public int getOwner() {
		return this.owner;
	}
	

}
