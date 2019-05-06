/**
 * 
 */
package kalah;

/**
 * @author jh654
 *
 */
public class House {
	private int _stock = 0; 
	
	//To Start the game, each house holds 4 stones
	public House() {
		this._stock = 4;
	}
	
	public House(int numb) {
		this._stock = numb;
	}
	
	//Selected house give all the stone it holds
	public int getStoneNumb() {
		return this._stock;
	}
	
	public void emptyStone() {
		this._stock = 0;
	}
	
	public void addStone() {
		this._stock += 1;
	}
	
	public void reduceStone() {
		this._stock -= 1;
	}
}
