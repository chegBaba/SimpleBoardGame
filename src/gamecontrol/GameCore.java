package gamecontrol;
import com.qualitascorpus.testsupport.MockIO;
import components.Board;
import components.Concepts;
import components.House;
import components.Pocket;
import components.Stone;
import components.Player;

import java.awt.Point;
import java.util.ArrayList;
public class GameCore implements Concepts{
	
	ArrayList<Stone> allStones;//= new Stone[48];
	House[][] gameHouses;// = new House[12];
	Board gameBoard;// = new Board(gameHouses);
	House[] playerPocket;
	Player playerList[];
	
	int StoneIndex = 0;
	Player activePlayer;// = playerList[0];
	int playerSwapBit = 0;
	ArrayList<Point> lookuptable;
	ArrayList<Point> lookuptable_p1;
	ArrayList<Point> lookuptable_p2;
	
	public GameCore() {
		newGame();
	}
	
	
	public void newGame() {
		StoneIndex = 0;
		createLookupTable();
		allStones = new ArrayList<Stone>();
		createHouses();
		createBoard();
		collectPlayerPockets();
		setupPlayer();
	}
	
	private void createLookupTable(){
		lookuptable = new ArrayList<Point>();
//		lookuptable_p1 = new ArrayList<Point>();
//		lookuptable_p2 = new ArrayList<Point>();
		
		//counter clockwise General lookup table
		for (int itr = 0;itr<2;++itr) {	
			for (int x=0;x<2;++x) {	
				for (int y=0;y<7;++y) {
					Point pt = new Point(x,y);
					lookuptable.add(pt);
				}
			}
		}
//		Point p_player1_pocket = new Point(0,6);
//		Point p_player2_pocket = new Point(1,6);
//		for (Point pt: lookuptable) {
//			if(pt != p_player2_pocket) lookuptable_p1.add(pt);
//			if(pt != p_player1_pocket) lookuptable_p2.add(pt);
//		}
		
	}
	
	public void collectPlayerPockets() {
		this.playerPocket = new House[2];
		this.playerPocket[0] = this.gameHouses[0][6];
		this.playerPocket[1] = this.gameHouses[1][6];
	}
	
	public void createBoard() {
		this.gameBoard = new Board(this.gameHouses); 
	}
	
	public void createHouses() {
		this.gameHouses = new House[2][7];
		for (int x=0;x<2;++x) {
			//set Houses
			for (int y=0;y<BOARD_LENGTH;++y) {
				//set Player pocket
				if ( (x==0 && y==6)|| (x==1 && y==6) ){
					gameHouses[x][y] = new Pocket(x,y);
				}else {
					gameHouses[x][y]=new House(x,y);
					ArrayList<Stone> stone_set = createStoneSets(x,y,StoneIndex++);
					for(Stone s : stone_set) {
						gameHouses[x][y].addStone(s);
					}
				}
			}
		}
	}
	
	//Every set contains 4 stones
	public ArrayList<Stone> createStoneSets(int House_x, int House_y, int Stone_ID) {
		ArrayList<Stone> stones= new ArrayList<Stone>();
		for (int idx=0;idx<4;++idx) {
			Stone stn = new Stone(House_x,House_y,Stone_ID);
			stones.add(stn);
			//register into allStones
			allStones.add(stn);
		}
		return stones;
	}
	
	public Board getGameBoard() {
		return gameBoard;
	}
	
	public void setupPlayer() {
		playerList = new Player[2];
		playerList[0] = new Player("P",1);
		playerList[1] = new Player("P",2);
		activePlayer = playerList[0];
	}
	
	public void swtichPlayer() {
		activePlayer = (activePlayer == this.playerList[0])? playerList[1]:playerList[0];
		this.playerSwapBit = 0;
	}
	
	public int getActivePlayerNumber() {
		return activePlayer.getID()+1;
	}
	public boolean getActivePlayerPrevWin() {
		return activePlayer.getPreWin();
	}
	
	public int pickHouse(int house_x,int house_y) {
		House targetHouse = gameHouses[house_x][house_y];		
		ArrayList<Stone> houseStone = targetHouse.getStones();
		//filter out for empty house
		if (targetHouse.isEmpty()) {
			return 1;
		}
		//relocate stones
		for (int i=0,offset=1; i<houseStone.size();++i,++offset) {
			Stone stn = houseStone.get(i);
			Point org_stone_loc = stn.getLocation();

			//new coordinate
			Point desti_point = findNewCoordinate(org_stone_loc, offset);
//			Point desti_point = findNewCoordinate_tb(org_stone_loc, offset);
			
			
			// filter out the other player's pocket
			House notify_house = gameHouses[(int) desti_point.getX()][(int) desti_point.getY()];
			if (notify_house instanceof Pocket) {//inside pockets
				if (notify_house.getOwner() != activePlayer.getID() ) {
					desti_point = findNewCoordinate(org_stone_loc, ++offset);
					notify_house = gameHouses[(int) desti_point.getX()][(int) desti_point.getY()];
				}
//				notify_house = gameHouses[(int) desti_point.getX()][(int) desti_point.getY()];
			}
			//move stone
			notify_house.addStone(stn);
			stn.moveTo(desti_point.getX(),desti_point.getY());
			
			
			// deal with last stone drop
			if (i == houseStone.size()-1) {
				
				arrive_empty_house(desti_point);
				//switch player?
				//last drop in own pocket
				check_player_continues(desti_point);
			}
		}
		targetHouse.empty();
//		checkNextPlayer();
		return 0;
	}
	
	private void playerCollectStones(int PlayerID, House hs) {
		ArrayList<Stone> stones = gameBoard.getHouse(hs).getStones();
		gameBoard.getHouse(PlayerID, 6).addStones(stones);
		gameBoard.getHouse(hs).empty();
	}
	
	//Find new coordinate for given Point, base on the lookuptable
	private Point findNewCoordinate(Point org, int offset) {
		int start_point = lookuptable.indexOf(org);
//		if ( (start_point+offset)>HOUSE_LENGTH ) //TODO: raise 
		int end_point = (start_point+offset)%(lookuptable.size());
		Point desti_point = lookuptable.get(end_point);
		return desti_point;
	}
	
//	//Find new coordiante with sperate lookuptable one for each play
//	private Point findNewCoordinate_tb(Point org, int offset) {
//		ArrayList<Point> tb = (activePlayer.getID()==0)? lookuptable_p1:lookuptable_p2;
//		int start_point = tb.indexOf(org);
//		//select lookuptable
//		int end_point = (start_point+offset)%(tb.size());
//		Point desti_point = tb.get(end_point);
//		return desti_point;
//	}
	
	// precondition: @Point hs must be a House point
	// belongs to the activeplayer
	public House findHouseCrossBorad(Point hs) {
		int x = (activePlayer.getID() == 0)? 1:0;
		int y = HOUSE_LENGTH-1-hs.y;
		House cross_house = gameBoard.getHouse(x, y);
		return cross_house;
	}
	
	public void checkNextPlayer() {
		if (playerSwapBit == 1) {
			this.swtichPlayer();
			playerSwapBit = 0;
		}
	}
	
	//check whether player manage get last drop into their pocket
	//known P1 pocket(x=0,y=6) P2 pocket(x=1,y=6)
	public void check_player_continues(Point p_dest) {
		if ( (activePlayer.getID() == 0) &&(p_dest.x == 0) && (p_dest.y==6) ) {
			return;
		}else if((activePlayer.getID() == 1) &&(p_dest.x == 1) && (p_dest.y==6)) {
			return;
		}else {
			playerSwapBit = 1;
		}
	}
	
	//check if the destination house is empty
	private void arrive_empty_house(Point p_dest) {
		House hs = gameBoard.getHouse(p_dest);
		//exclude arriving at pocket
		if (p_dest.distance(0.0, 6.0)==0 || p_dest.distance(1.0, 6.0)==0)
			return;
		//arriving at empty house
		if (hs.getStoneNumber()==1 ) {
			//make sure arrive at my own house
			if (hs.getOwner() == activePlayer.getID()) {
				//get house cross board
				House cross = findHouseCrossBorad(p_dest);
				//Player pocket collect all stones
				if (cross.isEmpty()) {

				}else {
					playerCollectStones(activePlayer.getID(),cross);
					playerCollectStones(activePlayer.getID(),hs);
				}

				//get all stone cross the house
//				hs.addStones(cross.getStones());
				cross.empty();
			}
		}
	}

	public int checkWinner() {
		House[] hr1	= gameHouses[0];
		House[] hr2 = gameHouses[1];
		boolean hr1_empty = true;
		boolean hr2_empty = true;
		
		for (int idx = 0; idx <hr1.length-1;++idx) {
			if (hr1[idx].isEmpty() == false) {
				hr1_empty = false;
			}
		}
		for (int idx = 0; idx <hr2.length-1;++idx) {
			if (hr2[idx].isEmpty() == false)
				hr2_empty = false;
		}
		if (hr1_empty || hr2_empty) {
			if (hr1_empty) {
				playerList[0].setPreWin(true);
			}else {
				playerList[1].setPreWin(true);
			}
			return 1;
		}else {
			playerList[0].setPreWin(false);
			playerList[1].setPreWin(false);
			return 0;
		}
	}
	
	public int[] checkScore() {
		House[] hr1	= gameHouses[0];
		House[] hr2 = gameHouses[1];
		int[] score= {0,0};
		for( House h : hr1) {
			score[0]+= h.getStoneNumber();
		}
		for( House h : hr2) {
			score[1]+= h.getStoneNumber();
		}
		return score;
	}
}
