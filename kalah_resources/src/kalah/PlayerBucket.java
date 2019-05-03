package kalah;
import java.util.Vector;


/* Player class, 
 * Describes single player, 
 * @Score, each player have their own score 
 * Score can be either add up or reset to zero.
 */
public class PlayerBucket extends House {
	
	String name = "defaultName";
	public PlayerBucket() {

	}
	
	public PlayerBucket(String name) {
		this.name = name;
	}
	
	void printPlayerInfo() {
		System.out.println("Player: "+ this.name +" Score= " + this.iStock);
		System.out.println("Side:\n" + "+----+-------+-------+-------+-------+-------+-------+----+");
	}
}
