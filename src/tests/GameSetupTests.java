package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameSetupTests {

	private static Board board;
	private static Card kathleenCard, statsCard, confCard;
	
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		
		kathleenCard = new Card("Kathleen", CardType.PERSON);
		statsCard = new Card("Statistics", CardType.ROOM);
		confCard = new Card("95% Confidence Interval", CardType.WEAPON);
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
	
	@Test
	public void testDeckLoaded() {
		ArrayList<Card> deck = board.getInitialDecku();
		assertEquals(21, deck.size());
		assertTrue(deck.get(14).equals(kathleenCard));
		assertTrue(deck.get(0).equals(statsCard));
		assertTrue(deck.get(15).equals(confCard));
	}
	
	@Test
	public void testSolutionDealt() {
		Solution solution = board.getSolution();
		assertEquals(CardType.PERSON, solution.getPersonCard().getType());
		assertEquals(CardType.ROOM, solution.getRoomCard().getType());
		assertEquals(CardType.WEAPON, solution.getWeaponCard().getType());
	}
	
	@Test
	public void testDeal() {
		int maxHand = 0;
		int minHand = board.getPlayer(0).getHand().size();
		ArrayList<Card> deck = board.getDecku();
		for (int i = 0; i < 5; i++) {
			int handSize = board.getPlayer(i).getHand().size();
			if (handSize > maxHand) {
				maxHand = handSize;
			}
			
			else if (handSize < minHand) {
				minHand = handSize;
			}	
		}
		assertTrue(maxHand - minHand <= 1);
		assertEquals(deck.size(), 0);
		assertTrue(minHand >= 1);
		
	}
}
