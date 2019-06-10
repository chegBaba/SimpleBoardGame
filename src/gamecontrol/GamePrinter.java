package gamecontrol;

import java.util.ArrayList;

import com.qualitascorpus.testsupport.IO;

import components.Board;
import components.Concepts;

public class GamePrinter implements Concepts{
	
	final String GAMEBOARD_COVER = "+---------------+";
	final String GAMEBOARD_POCKET_COVER = "+-------+-------+";
	final String GAMEBOARD_POCKET_FRONT = "|       | ";
	final String GAMEBOARD_POCKET_END 	= " |       |";
	final String GAMEBOARD_HOUSE_FRONT = "| ";
	final String GAMEBOARD_HOUSE_MIDDLE = "] | ";
	final String GAMEBOARD_HOUSE_MIDDLE_END = "[";
//	final String QUIT = "q";
	final String HOUSE_EMPTY = "House is empty. Move again.";
	final String GAME_OVER = "Game over";
	
	Board gameBoard=null;
	IO io;
	String[][][] space_value_list;
	
	public GamePrinter(IO io,Board bd) {
		this.io = io;
		gameBoard = bd;
	}
	
//	public void setUpSpaceList() {
//		space_value_list = new String[PLAYER_NUMBER][HOUSE_LENGTH][PLAYER_NUMBER];
//		for(int x = 0; x < PLAYER_NUMBER; ++x)
//			for(int y = 0; y < PLAYER_NUMBER; ++y) 
//				space_value_list[x][y][x] = (gameBoard.getHouse(x, y).getStoneNumber()>9)? 
//						""+gameBoard.getHouse(x, y).getStoneNumber() + GAMEBOARD_HOUSE_MIDDLE + (6-x) +	GAMEBOARD_HOUSE_MIDDLE_END + gameBoard.getHouse((6-x), y).getStoneNumber():
//							" "+gameBoard.getHouse(x, y).getStoneNumber() + GAMEBOARD_HOUSE_MIDDLE + (6-x) +	GAMEBOARD_HOUSE_MIDDLE_END;
//			}
//			
		
	public String displayValue(int x, int y)
	{
		int value = gameBoard.getHouse(x,y).getStoneNumber();
		String str;
		if ((x==1 && y==6)||(x==0 && y==6)) {
			str = (value >=10)? 	" "	+	value :	"  "+	value;
		}else {
			str = (value >=10)? 	Integer.toString(value) :	" "	+	value;
		}
		return str;
	}
	
	public void printTOPboard() {
		io.println(GAMEBOARD_COVER);
		io.println(GAMEBOARD_POCKET_FRONT + "P2" + displayValue(1,6) +" |");
		io.println(GAMEBOARD_POCKET_COVER);
	}
	
	public void printBOTboard() {
		io.println(GAMEBOARD_POCKET_COVER);
		io.println("| "+ "P1" + displayValue(0,6) + GAMEBOARD_POCKET_END);
		io.println(GAMEBOARD_COVER);
	}
	
	public void printBoard() {
		printTOPboard();
		for (int x=0,y=0;y<HOUSE_LENGTH;++y) {
			io.println(GAMEBOARD_HOUSE_FRONT+(y+1)+GAMEBOARD_HOUSE_MIDDLE_END+displayValue(x,y)+GAMEBOARD_HOUSE_MIDDLE+(HOUSE_LENGTH-y)+GAMEBOARD_HOUSE_MIDDLE_END+displayValue(x+1,HOUSE_LENGTH-y-1)+"] |");
		}
		printBOTboard();
	}
	
	public void printGameOverRoutine() {
		io.println(GAME_OVER);
	}
	
	public void printEmptyHouse() {
		io.println(HOUSE_EMPTY);
	}
	
	public void printWiner(int[] scores) {
		io.println(GAME_OVER);
		printBoard();
		
		String ply1 ="	player 1:"+scores[0];
		String ply2 ="	player 2:"+scores[1];
		
		io.println(ply1);
		io.println(ply2);
		if (scores[0] == scores[1]) {
			io.println("A tie!");
		}else {
			io.println((scores[0]>scores[1])? "Player 1 wins!" : "Player 2 wins!");
		}
	}
}
