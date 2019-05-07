package kalah;

import java.util.Scanner;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
//import kalah.GameBoard;
/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {
	
	GameBoard gameBoard = new GameBoard();
//	String input;
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}
	public void play(IO io) {
		while(gameBoard.isTheGameEnd() == false)
		{
			gameBoard.showGameBoard();
			Scanner input = new Scanner(System.in); 
			String keyPress = input.nextLine(); 
			switch (keyPress)
			{
			case "q":
				gameBoard.endTheGame();
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
				System.out.println("You can not select player store!");
				break;
			}
			
		}
	}
}
