package kalah;


/* Player class, 
 * Describes single player, 
 * @Score, each player have their own score 
 * Score can be either add up or reset to zero.
 */
public class Store extends House {
	
	private String _player = "playerName";
	public Store() {
		this.emptyStone(); 
	}
	
	public Store(String name) {
		this.setName(name);
	}
	
	public void setName(String name) {
		this._player = name;
	}
}
