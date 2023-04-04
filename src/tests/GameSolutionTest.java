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
	private static Card kathleenCard, statsCard, confCard, wrongPerson, wrongRoom, wrongWeapon, baitCard;

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
		baitCard = new Card ("Bait", CardType.WEAPON);

		board.setTheAnswer(kathleenCard, statsCard, confCard);
	}
	
	@Test

	public void testCheckAccusation() {
		// test correct solution
		assertTrue(board.checkAccusation(kathleenCard, statsCard, confCard));
		// test solution with wrong person
		assertFalse(board.checkAccusation(wrongPerson, statsCard, confCard));
		// test solution with wrong weapon
		assertFalse(board.checkAccusation(kathleenCard, statsCard, wrongWeapon));
		// test solution with wrong room
		assertFalse(board.checkAccusation(kathleenCard, wrongRoom, confCard));
	}
	
	@Test
	public void testDisproveSuggestion() {
		/*
		 * Give the player only the person card of the suggestion,
		 * make sure it is returned every time
		 */
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
		
		/*
		 * When the player has more than one matching card,
		 * the matching cards should be returned randomly
		 */
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
		
		/*
		 * If player no matching cards, null is returned
		 */
		assertEquals(null, player.disproveSuggestion(wrongPerson, wrongRoom, wrongWeapon));
	}
	
	@Test
	public void testHandleSuggestion() {
		Player player1 = board.getPlayer(0);
		player1.SetHand(kathleenCard);
		Player player2 = board.getPlayer(1);
		player2.SetHand(statsCard);
		Player player3 = board.getPlayer(2);
		player3.SetHand(confCard);
		Player player4 = board.getPlayer(3);
		player4.SetHand(wrongPerson);
		Player player5 = board.getPlayer(4);
		player5.SetHand(wrongRoom);
		Player player6 = board.getPlayer(5);
		player6.SetHand(wrongWeapon);
		
		// Suggestion of cards that no one has returns null
		assertEquals(null, board.handleSuggestion(0, baitCard, baitCard, baitCard));
		// suggestion that only the suggesting player can disprove returns null
		assertEquals(null, board.handleSuggestion(0, kathleenCard, kathleenCard, kathleenCard));
		// suggestion that only human player can disprove is disproved
		assertEquals(kathleenCard, board.handleSuggestion(1, kathleenCard, kathleenCard, kathleenCard));
		// A suggestion that two players can disprove is disproven by the correct player
		assertEquals(kathleenCard, board.handleSuggestion(4, kathleenCard, statsCard, confCard));
		
	}

}
