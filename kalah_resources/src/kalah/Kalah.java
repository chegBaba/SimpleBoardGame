package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {
	
//	private static GameBoard gameBoard;
//	public static MockIO m_io = new MockIO();
	
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	
	public void play(IO io) {
		GameBoard gameBoard = new GameBoard(io);
		
		while(gameBoard.getGameStatus() == true)
		{
			gameBoard.showGameBoard();
			String keyPress = gameBoard.showInstruction();
//			String keyPress = io.readFromKeyboard(gameBoard.getActivePlayer()+"\'s turn - Specify house number or 'q' to quit:"); 
			switch (keyPress)
			{
			case "q":
				gameBoard.endGame();
				break;
			case "1":
			case "2":
			case "3":
			case "4":
			case "5":
			case "6":
				gameBoard.moveStoneInSelectHouse(Integer.parseInt(keyPress));
				break;
			case "0":
			case "7":
//				System.out.println("You can not select player store!");
				break;
			}
			
		}
	}
}
