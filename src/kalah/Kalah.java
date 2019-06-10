package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import gamecontrol.GameCore;
import gamecontrol.GameFlow;
import gamecontrol.GamePrinter;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */


public class Kalah {
	GameCore core;
	GamePrinter printer;
	GameFlow centerFlow;
	
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
		
	}
	public void play(IO io) {
		core = new GameCore();
		printer = new GamePrinter(io,core.getGameBoard());
		centerFlow = new GameFlow(core,printer,io);
		
		centerFlow.startGame();
		
	}
	
}
