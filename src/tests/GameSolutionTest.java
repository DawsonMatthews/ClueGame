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

}
