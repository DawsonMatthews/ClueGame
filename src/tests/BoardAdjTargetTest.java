package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// First, the physics
		Set<BoardCell> testList = board.getAdjList(3, 2);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(0, 5)));
		
		// now test the game development room
		testList = board.getAdjList(14, 18);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(13, 15)));
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(0, 7);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(1, 7)));
		assertTrue(testList.contains(board.getCell(3, 10)));

		testList = board.getAdjList(0, 15);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(1, 15)));
		assertTrue(testList.contains(board.getCell(3, 18)));

	}
	
	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test adjacent to walkways
		Set<BoardCell> testList = board.getAdjList(3, 22);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(3, 21)));
		assertTrue(testList.contains(board.getCell(3, 23)));
		assertTrue(testList.contains(board.getCell(2, 22)));
		assertTrue(testList.contains(board.getCell(4, 22)));
		
		// Test inside room, not in center of room
		testList = board.getAdjList(9, 21);
		assertEquals(0, testList.size());
		
		// Test on left edge of board, just one walkway piece
		testList = board.getAdjList(8, 0);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(7, 0)));
		assertTrue(testList.contains(board.getCell(9, 0)));

		// Test room with secret passage
		testList = board.getAdjList(14, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(10, 0)));
		assertTrue(testList.contains(board.getCell(3, 18)));
		
		// Test on left edge of board, just one walkway piece
		testList = board.getAdjList(8, 23);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(8, 16)));
		assertTrue(testList.contains(board.getCell(7, 23)));
		assertTrue(testList.contains(board.getCell(9, 23)));
	
	}
	
	
	// Tests out of room center, 1, 2, and 3
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInStats() {
		// test a roll of 1
		board.calcTargets(board.getCell(8, 16), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(19, 14)));
		assertTrue(targets.contains(board.getCell(8, 23)));	
		
		// test a roll of 2
		board.calcTargets(board.getCell(8, 16), 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(7, 23)));
		assertTrue(targets.contains(board.getCell(9, 23)));
		assertTrue(targets.contains(board.getCell(19, 14)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(8, 16), 3);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(6, 23)));
		assertTrue(targets.contains(board.getCell(10, 23)));	
		assertTrue(targets.contains(board.getCell(19, 14)));

	}
	
	@Test
	public void testTargetsInChemistry() {
		// test a roll of 1
		board.calcTargets(board.getCell(3, 10), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(0, 7)));
		assertTrue(targets.contains(board.getCell(0, 13)));	
		
		// test a roll of 2
		board.calcTargets(board.getCell(3, 10), 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(1, 13)));
		assertTrue(targets.contains(board.getCell(1, 7)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(3, 10), 3);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(2, 13)));
		assertTrue(targets.contains(board.getCell(2, 7)));		
	}

	// Tests out of room center, 1, 2 and 3
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(13, 15), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(14, 15)));
		assertTrue(targets.contains(board.getCell(13, 14)));	
		assertTrue(targets.contains(board.getCell(12, 15)));
		assertTrue(targets.contains(board.getCell(14, 18)));	

		
		// test a roll of 2
		board.calcTargets(board.getCell(13, 15), 2);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(15, 15)));
		assertTrue(targets.contains(board.getCell(14, 14)));
		assertTrue(targets.contains(board.getCell(13, 13)));	
		assertTrue(targets.contains(board.getCell(12, 14)));
		assertTrue(targets.contains(board.getCell(11, 15)));
		assertTrue(targets.contains(board.getCell(14, 18)));	

		
		// test a roll of 3
		board.calcTargets(board.getCell(13, 15), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(16, 15)));
		assertTrue(targets.contains(board.getCell(15, 14)));
		assertTrue(targets.contains(board.getCell(14, 13)));	
		assertTrue(targets.contains(board.getCell(12, 13)));
		assertTrue(targets.contains(board.getCell(11, 14)));
		assertTrue(targets.contains(board.getCell(10, 15)));	
		assertTrue(targets.contains(board.getCell(14, 18)));	

	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(22, 5), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(22, 4)));
		assertTrue(targets.contains(board.getCell(22, 6)));
		assertTrue(targets.contains(board.getCell(23, 5)));	
		assertTrue(targets.contains(board.getCell(21, 5)));	

		
		// test a roll of 2
		board.calcTargets(board.getCell(22, 5), 2);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(23, 4)));
		assertTrue(targets.contains(board.getCell(22, 3)));
		assertTrue(targets.contains(board.getCell(21, 4)));	
		assertTrue(targets.contains(board.getCell(21, 6)));	
		assertTrue(targets.contains(board.getCell(23, 6)));	
		assertTrue(targets.contains(board.getCell(22, 7)));	

		// test a roll of 3
		board.calcTargets(board.getCell(22, 5), 3);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(23, 3)));
		assertTrue(targets.contains(board.getCell(22, 2)));
		assertTrue(targets.contains(board.getCell(21, 3)));	
		assertTrue(targets.contains(board.getCell(19, 9)));	
		assertTrue(targets.contains(board.getCell(23, 7)));	
		assertTrue(targets.contains(board.getCell(22, 8)));
		assertTrue(targets.contains(board.getCell(21, 7)));	


	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 2 blocked 2 down
		board.getCell(13, 22).setOccupied(true);
		board.calcTargets(board.getCell(13, 23), 2);
		board.getCell(13, 22).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(15, 23)));
		assertTrue(targets.contains(board.getCell(14, 22)));
		assertTrue(targets.contains( board.getCell(12, 22)));
		assertTrue(targets.contains( board.getCell(11, 23)));
		assertFalse(targets.contains(board.getCell(13, 21)));	

	
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(14, 10).setOccupied(true);
		board.calcTargets(board.getCell(10, 10), 1);
		board.getCell(14, 10).setOccupied(false);
		targets= board.getTargets();
		assertTrue(targets.contains(board.getCell(10, 9)));	
		assertTrue(targets.contains(board.getCell(10, 11)));	
		assertTrue(targets.contains(board.getCell(14, 10)));
		assertEquals(3, targets.size());
			
		
		// check leaving a room with a blocked doorway
		board.getCell(19, 12).setOccupied(true);
		board.calcTargets(board.getCell(19, 14), 6);
		board.getCell(19, 12).setOccupied(false);
		targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(8, 16)));
		assertFalse(targets.contains(board.getCell(17, 15)));	
	}
}
