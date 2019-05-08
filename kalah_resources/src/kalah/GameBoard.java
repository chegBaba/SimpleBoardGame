package kalah;

//import com.qualitascorpus.testsupport.MockIO;
import com.qualitascorpus.testsupport.IO;

public class GameBoard {
	//Game Board 
	//|p1| 1 | 2 | 3| 4| 5| 6| P2| 1| 2| 3| 4| 5| 6|...|p1|
	//	>+----+-------+-------+-------+-------+-------+-------+----+
	//	>| P2 | 6[ 4] | 5[ 4] | 4[ 4] | 3[ 4] | 2[ 4] | 1[ 4] |  0 |
	//	>|    |-------+-------+-------+-------+-------+-------|    |
	//	>|  0 | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 4] | 5[ 4] | 6[ 4] | P1 |
	//	>+----+-------+-------+-------+-------+-------+-------+----+
	IO _io;
	House[] _gameBoard;
	String _activePlayer = "Player P1";
	boolean _isGaming = true;
	int _stonesInTotal = 48;
	
	public GameBoard(IO io) 
	{
		this._io = io;
		gameInit();
	}
	
	private void gameInit() 
	{
		_gameBoard = new House[14];
		//initiate with 12 houses, each houses have 4 stones
		for (int i = 0; i<_gameBoard.length; ++i)
		{
			if (i == 0 || i == 7)
			{
				_gameBoard[i] = new Store();
			}else {
				_gameBoard[i] = new House(4);
			}
			//Set owner property
			if (i<=7 && i >=1)
				_gameBoard[i].setOwner("Player P2");
			else
				_gameBoard[i].setOwner("Player P1");
		}
	}
	
	public String showInstruction()
	{
//		_io.println(_activePlayer+"'s turn - Specify house number or 'q' to quit: ");
		return _io.readFromKeyboard(this.getActivePlayer()+"\'s turn - Specify house number or 'q' to quit:");
	}
	public void showGameBoard()
	{
		_io.println("+----+-------+-------+-------+-------+-------+-------+----+");
		_io.println("| P2 | 6[ "+ _gameBoard[6].getStoneNumb()+ "] "
				+"| 5[ "+_gameBoard[5].getStoneNumb()+"] "
				+"| 4[ "+_gameBoard[4].getStoneNumb()+"] "
				+"| 3[ "+_gameBoard[3].getStoneNumb()+"] "
				+"| 2[ "+_gameBoard[2].getStoneNumb()+"] "
				+"| 1[ "+_gameBoard[1].getStoneNumb()+"] "
				+"|  "+_gameBoard[0].getStoneNumb()+ " |");//score of P1
		_io.println("|    |-------+-------+-------+-------+-------+-------|    |");
		_io.println("|  "+_gameBoard[7].getStoneNumb()//score of P2
						+ " | 1[ "+_gameBoard[8].getStoneNumb()+"]"
						+ " | 2[ "+_gameBoard[9].getStoneNumb()
						+"] | 3[ "+_gameBoard[10].getStoneNumb()
						+"] | 4[ "+_gameBoard[11].getStoneNumb()
						+"] | 5[ "+_gameBoard[12].getStoneNumb()
						+"] | 6[ "+_gameBoard[13].getStoneNumb()
						+"] | P1 |");
		_io.println("+----+-------+-------+-------+-------+-------+-------+----+");
	}
	
	public int getStoneNumber(int houseNumber, String player)
	{
		if (player == "Player P2")
		{
			return _gameBoard[houseNumber].getStoneNumb();
		}else {
			if (player == "Player P1")
			{
				return _gameBoard[houseNumber+7].getStoneNumb();
			}else {
				System.out.println("Wrong player : "+ player);
				return -1;
			}
		}
	}
		
	
	public int moveStoneInSelectHouse(int selectedHouse)
	{
		int stones = getStoneNumber(selectedHouse, _activePlayer);
		int record = stones;
		if (_activePlayer == "Player P1")
		{
			selectedHouse+=7;
		}
		_gameBoard[selectedHouse].emptyStone();
		int index = selectedHouse+1;//offset point from P1 store
		if (stones == 0 )
		{
			System.out.println("You have select a empty house!");
			return -1;
		}
		
		//Stone relocation
		while(stones!=0)
		{
			index=index%_gameBoard.length;
			
			//Do not move stones into another player's store
			if (_activePlayer == "Player P2" && index !=0)
			{
				_gameBoard[index].addStone();
				index+=1;
				stones-=1;
			}else if(_activePlayer == "Player P1" && index !=7)
			{
				_gameBoard[index].addStone();
				index+=1;
				stones=stones-1;
			}else {
				index+=1;
			}
		}
		
		
		//remove offset
		if (index == 14)
			index = 0;
		else
			index-=1;
		
		//Make sure the interested spots are not player store
		if ( index != 0 && index !=7) 
		{
			if (_gameBoard[index].getStoneNumb() == 1) {
				this.getOppsiteHouseStock(index);	
			}
		}
		//Same Player get another route if they manage to 
		checkPlayerSwitch(selectedHouse, record);
		return 1;
	}
	
	public void switchPlayer()
	{
		if(this._activePlayer == "Player P1")
		{
			this._activePlayer = "Player P2";
		}else {
			this._activePlayer = "Player P1";
		}
	}
	
	//Check whether the active player get another round
	private void checkPlayerSwitch(int startPoint, int stoneNumber)
	{
		if ((startPoint + stoneNumber)%7 == 0 && _activePlayer=="Player 2")
		{
			System.out.println("Player 2 AGAIN!");
//			return true;
		}
		else if (((startPoint + stoneNumber)%14 == 0 && _activePlayer=="Player P1"))
		{
			System.out.println("Player P1 AGAIN!");
//			return true;
		}
		else {
			switchPlayer();
//			return false;
		}
	}
	
	//Get player score with player number
	public int getPlayerScore(int player)
	{
		if(player == 1)
		{
			return _gameBoard[0].getStoneNumb();
		}else {
			if(player == 2)
			{
				return _gameBoard[7].getStoneNumb();
			}else {
				System.out.println("Wrong player number: "+player);
				return -1;
			}
		}
	}
	
	public int getTotalScore()
	{
		return getPlayerScore(1) + getPlayerScore(2);
	}
	
	private boolean isPlayerHousesEmpty(int firstHouse) {
		for (int i=firstHouse ; i<firstHouse+6; ++i)
		{
			if (_gameBoard[i].getStoneNumb() != 0)
				return false;
		}
		return true;
	}
	
	//Check game condition, terminate the game if winner occurs
	public boolean isTheGameEnd()
	{
		if (this.getTotalScore() >= this._stonesInTotal)
		{
			System.out.println("Game over");
			return true;
		}else if(isPlayerHousesEmpty(1) || isPlayerHousesEmpty(8)){
			return true;
		}else {
			return false;
		}
	}
	
	//set game status to false to end the game
	public void endGame() {
		this._isGaming = false;
		_io.println("Game over");
		this.showGameBoard();
	}
	
	public boolean getGameStatus() {
		return this._isGaming;
	}

	//Find the opposite side house of the given house number
	private int getOppositeHouseNumber(int givenHouse)
	{
		return _gameBoard.length - givenHouse;
	}
	
	//get all stones in opposite house if it is not empty
	private void getOppsiteHouseStock(int givenHouse)
	{
		int oppoHouse = getOppositeHouseNumber(givenHouse);
		int stock = _gameBoard[oppoHouse].getStoneNumb();
		
		//Continue only if working on anamy's house
		if (stock > 0 && _gameBoard[oppoHouse].getOwner() != _activePlayer)
		{
			_gameBoard[givenHouse].addStones(stock);
			_gameBoard[oppoHouse].emptyStone();
		}
	}
	
	public void endTheGame()
	{
		this.showGameBoard();
//		@SuppressWarnings("unused")
//		String winner;
//				
//		if (this.getPlayerScore(1)<this.getPlayerScore(2))
//			winner = "Player P2";
//		else if (this.getPlayerScore(1)>this.getPlayerScore(2))
//			winner = "Player P1";
//		else
//			winner = "Nobody";
//		
//		//Show winner
//		_io.println("Game over"); 
////		showGameBoard();
	}
	
	public String getActivePlayer() {
		return _activePlayer;
	}
}
