package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Solution;

public class GameSolutionTest {
	
	private static Board board;
	private static Card kathleenCard, statsCard, confCard, wrongPerson, wrongRoom, wrongWeapon;

	@BeforeAll
	public static void setup() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		kathleenCard = new Card("Kathleen", CardType.PERSON);
		statsCard = new Card("Statistics", CardType.ROOM);
		confCard = new Card("95% Confidence Interval", CardType.WEAPON);
		wrongPerson = new Card("Liam", CardType.PERSON);
		wrongRoom = new Card("Physics", CardType.ROOM);
		wrongWeapon = new Card("The Rebugger", CardType.WEAPON);

		board.setTheAnswer(kathleenCard, statsCard, confCard);
	}
	
	@Test
	public void testCheckAccusation() {
		assertTrue(board.checkAccusation(kathleenCard, statsCard, confCard));
		assertFalse(board.checkAccusation(wrongPerson, statsCard, confCard));
		assertFalse(board.checkAccusation(kathleenCard, wrongRoom, confCard));
		assertFalse(board.checkAccusation(kathleenCard, statsCard, wrongWeapon));

	}
	
	@Test
	public void testDisproveSuggestion() {
		
		Player player = board.getPlayer(0);
		player.SetHand(kathleenCard, wrongPerson, wrongRoom);
		
		int timesCard1Returned = 0;
		int timesCard2Returned = 0;
		int timesCard3Returned = 0;
		
		for (int i = 0; i < 1000; i++) {
			if (player.disproveSuggestion(kathleenCard, statsCard, confCard).equals(kathleenCard)) {
				timesCard1Returned++;
			}
			else if (player.disproveSuggestion(kathleenCard, statsCard, confCard).equals(wrongPerson)) {
				timesCard2Returned++;
			}
			else if (player.disproveSuggestion(kathleenCard, statsCard, confCard).equals(wrongRoom)) {
				timesCard3Returned++;
			}
		}
		
		assertEquals(1000, timesCard1Returned);
		assertEquals(0, timesCard2Returned);
		assertEquals(0, timesCard3Returned);
		
		
		
		player.SetHand(kathleenCard, statsCard, confCard);
		
		timesCard1Returned = 0;
		timesCard2Returned = 0;
		timesCard3Returned = 0;
		
		for (int i = 0; i < 1000; i++) {
			if (player.disproveSuggestion(kathleenCard, statsCard, confCard).equals(kathleenCard)) {
				timesCard1Returned++;
			}
			else if (player.disproveSuggestion(kathleenCard, statsCard, confCard).equals(statsCard)) {
				timesCard2Returned++;
			}
			else if (player.disproveSuggestion(kathleenCard, statsCard, confCard).equals(confCard)) {
				timesCard3Returned++;
			}
		}
		
		assertTrue(timesCard1Returned >= 100);
		assertTrue(timesCard2Returned >= 100);
		assertTrue(timesCard3Returned >= 100);
		
		assertEquals(null, player.disproveSuggestion(wrongPerson, wrongRoom, wrongWeapon));
	}
	

}
