package gamecontrol;
import com.qualitascorpus.testsupport.IO;

public class GameFlow {
	
	final String QUIT = "q";
	
	GameCore _core;
	GamePrinter _printer;
	IO _io;
	
	boolean gameStatus = false;
	String input;
	
	
	public GameFlow(GameCore gc, GamePrinter gp,IO io) {
		_core = gc;
		_printer = gp;
		_io = io;
	}
	
	public void startGame() {
		gameStatus = true;
		_printer.printBoard();
		while(gameStatus) {
			this.takeInput();
			_printer.printBoard();
			_core.checkNextPlayer();
			isWinGame();
		}
	}
	
	private void isWinGame() {
		if ((_core.checkWinner() == 1)&&(_core.getActivePlayerPrevWin() == true)) {
			_printer.printWiner(_core.checkScore());
			gameStatus = false;
		}
	}
	public void endGame() {
		this.gameStatus = false;
		_printer.printGameOverRoutine();
	}
	
	private void chooseHouse(int houseNumber) {
		int loc_x = (_core.getActivePlayerNumber()==1)? 0:1 ;
		int loc_y = houseNumber-1;
		if (_core.pickHouse(loc_x, loc_y)!=0) {
			_printer.printEmptyHouse();
		}
	}
	public void takeInput() {
		input = _io.readFromKeyboard("Player P"+_core.getActivePlayerNumber()+"'s turn - Specify house number or 'q' to quit:");
		switch (input)
		{
		case "q":
			endGame();
			break;
		case "1":
		case "2":
		case "3":
		case "4":
		case "5":
		case "6":
			//make move
			chooseHouse(Integer.parseInt(input));
			break;
		case "0":
		case "7":
			break;
		}
		
		
	}
	

}
