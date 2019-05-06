package kalah;

public class GameBoard {
	//Game Board 
	//|p1| 1 | 2 | 3| 4| 5| 6| P2| 1| 2| 3| 4| 5| 6|...|p1|
	//	>+----+-------+-------+-------+-------+-------+-------+----+
	//	>| P2 | 6[ 4] | 5[ 4] | 4[ 4] | 3[ 4] | 2[ 4] | 1[ 4] |  0 |
	//	>|    |-------+-------+-------+-------+-------+-------|    |
	//	>|  0 | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 4] | 5[ 4] | 6[ 4] | P1 |
	//	>+----+-------+-------+-------+-------+-------+-------+----+
	House[] _gameBoard = new House[14];
	String _activePlayer = "Player 1";
	int _stonesInTotal = 48;
	
	public GameBoard() 
	{
		//initiate the game board with 12 houses 
		//each houses have four stones
		for (int i = 0; i<_gameBoard.length; ++i)
		{
			if (i == 0 || i == 7)
			{
				_gameBoard[i] = new Store();
			}else {
				_gameBoard[i] = new House(4);
			}
		}
	}
	
	//Print current GameBoard
	public void showGameBoard()
	{
		System.out.println("+----+-------+-------+-------+-------+-------+-------+----+");
		System.out.print("| P2 | 6[ "+ _gameBoard[6].getStoneNumb()+ "] "
				+"| 5[ "+_gameBoard[5].getStoneNumb()+"] "
				+"| 4[ "+_gameBoard[4].getStoneNumb()+"] "
				+"| 3[ "+_gameBoard[3].getStoneNumb()+"] "
				+"| 2[ "+_gameBoard[2].getStoneNumb()+"] "
				+"| 1[ "+_gameBoard[1].getStoneNumb()+"] "
				+"|  "+_gameBoard[0].getStoneNumb()+ " |\n");//score of P1
		System.out.println("|    |-------+-------+-------+-------+-------+-------|    |");
		System.out.print("|  "+_gameBoard[7].getStoneNumb()//score of P2
						+ " | 1[ "+_gameBoard[8].getStoneNumb()+"]"
						+ " | 2[ "+_gameBoard[9].getStoneNumb()
						+"] | 3[ "+_gameBoard[10].getStoneNumb()
						+"] | 4[ "+_gameBoard[11].getStoneNumb()
						+"] | 5[ "+_gameBoard[12].getStoneNumb()
						+"] | 6[ "+_gameBoard[13].getStoneNumb()
						+"] | P1 |\n");
	//		System.out.println("|  0 | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 4] | 5[ 4] | 6[ 4] | P1 |");
		System.out.println("+----+-------+-------+-------+-------+-------+-------+----+");
		System.out.println(_activePlayer+"'s turn - Specify house number or 'q' to quit: ");
	}
	
	public int getStoneNumber(int houseNumber, String player)
	{
		if (player == "Player 1")
		{
			return _gameBoard[houseNumber].getStoneNumb();
		}else {
			if (player == "Player 2")
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
		if (_activePlayer == "Player 2")
		{
			selectedHouse+=7;
		}
		_gameBoard[selectedHouse].emptyStone();
		int index = selectedHouse+1;//
		if (stones ==0 )
		{
			System.out.println("You have select a empty house!");
			return -1;
		}
		while(stones!=0)
		{
			index=index%_gameBoard.length;
			//Do not move stones into another player's store
			if (_activePlayer == "Player 1" && index !=14)
			{
				_gameBoard[index].addStone();
				index+=1;
				stones-=1;
			}else if(_activePlayer == "Player 2" && index !=7)
			{
				_gameBoard[index].addStone();
				index+=1;
				stones-=1;
			}else {
				index+=1;
			}
		}
		return 1;
	}
	
	public void switchPlayer()
	{
		if(this._activePlayer == "Player 1")
		{
			this._activePlayer = "Player 2";
		}else {
			this._activePlayer = "Player 1";
		}
	}
	
	//Check whether the active player get another round
	private boolean isPlayerGetContinue(int startPoint, int stoneNumber)
	{
		//TODO
		return false;
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
	
	//Check game condition, terminate the game if winner occurs
	public boolean isTheGameEnded()
	{
		if (this.getTotalScore() >= this._stonesInTotal)
		{
			System.out.println("GameENDs");
			return true;
		}else {
			return false;
		}
	}
	
	public void endTheGame()
	{
		//print game board and present the winner 
		this.showGameBoard();
		//Show winner
		System.out.println("The winer is: ");//TODO:Address the winner 
		
	}
}
