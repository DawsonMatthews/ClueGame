package tests;

/*
 * This program tests that, when loading config files, exceptions 
 * are thrown appropriately.
 */

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import clueGame.BadConfigFormatException;
import clueGame.Board;

public class ExceptionTests306 {

	// Test that an exception is thrown for a layout file that does not
	// have the same number of columns for each row
	@Test
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		assertThrows(BadConfigFormatException.class, () -> {
			// Note that we are using a LOCAL Board variable, because each
			// test will load different files
			Board board = Board.getInstance();
			board.setConfigFiles("ClueLayoutBadColumns306.csv", "ClueSetup306.txt");
			// Instead of initialize, we call the two load functions directly.
			// This is necessary because initialize contains a try-catch.
			board.loadSetupConfig();
			// This one should throw an exception
			board.loadLayoutConfig();
		});
	}

	// Test that an exception is thrown for a Layout file that specifies
	// a room that is not in the legend. 
	@Test
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		assertThrows(BadConfigFormatException.class, () -> {
			Board board = Board.getInstance();
			board.setConfigFiles("ClueLayoutBadRoom306.csv", "ClueSetup306.txt");
			board.loadSetupConfig();
			board.loadLayoutConfig();
		});
	}

	// Test that an exception is thrown for a bad format Setup file
	@Test
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		assertThrows(BadConfigFormatException.class, () -> {
			Board board = Board.getInstance();
			board.setConfigFiles("ClueLayout306.csv", "ClueSetupBadFormat306.txt");
			board.loadSetupConfig();
			board.loadLayoutConfig();
		});
	}

}
