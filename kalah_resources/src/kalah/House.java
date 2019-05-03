/**
 * 
 */
package kalah;

/**
 * @author jh654
 *
 */
public class House {
	int iStock = 0; 
	
	//To Start the game, each house holds 4 stones
	public House() {
		this.iStock = 4;
	}
	
	public House(int numb) {
		this.iStock = numb;
	}
	
	//Selected house give all the stone it holds
	public int getStoneNumb() {
		return this.iStock;
	}
	
	public void emptyHouse() {
		this.iStock = 0;
	}
	
	void addStone() {
		this.iStock += 1;
	}
	
	void subStone() {
		this.iStock -= 1;
	}
}
