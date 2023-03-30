package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class GameSetupTests {

	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}
	
	@Test
	public void testLoadedPeople() {
		for (int i = 0; i < 6; i++) {
			assertTrue(board.getPlayer(i) != null);
		}
		Player player = board.getPlayer(5);
		assertEquals(player.getName(), "Kathleen");
		assertEquals(Color.ORANGE, player.getColor());
		assertEquals(player.getRow(), 0);
		assertEquals(player.getColumn(), 23);
		
	}

	@Test
	public void testPlayerType() {
		Player player = board.getPlayer(0);
		assertTrue(player instanceof HumanPlayer);
		for (int i = 1; i < 6; i++) {
			player = board.getPlayer(i);
			assertTrue(player instanceof ComputerPlayer);
		}
	}
}
