package components;

public class Player extends AbstractObject{
	String myName;
	int	playerID;
	private boolean preWine;
	public Player(String name,int ID) {
		myName=name;
		playerID= ID;
		boolean preWine = false;
	}
	
	public int getID() {
		return (playerID == 1)? 0:1;
	}
	
	public void setPreWin(boolean tf) {
		this.preWine = tf;
	}
	
	public boolean getPreWin() {
		return this.preWine;
	}
}
