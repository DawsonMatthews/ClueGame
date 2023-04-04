package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;
import clueGame.Solution;
import clueGame.Card;
import clueGame.CardType;

public class ComputerAITest {

	private static Board board;
	private static Card confCard, rebuggerCard, inductorCard, eigenCard, binomialCard, normalizerCard, joelCard, markCard, dejunCard, terryCard, liamCard, kathleenCard;

	@BeforeAll
	public static void setup() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		
		confCard = new Card("95% Confidence Interval", CardType.WEAPON);
		rebuggerCard = new Card("The Rebugger", CardType.WEAPON);
		inductorCard = new Card("The Inductor", CardType.WEAPON);
		eigenCard = new Card("Eigen Knife", CardType.WEAPON);
		binomialCard = new Card("Binomial Distributor", CardType.WEAPON);
		normalizerCard = new Card("The Normalizer", CardType.WEAPON);

		joelCard = new Card("Joel", CardType.PERSON);
		markCard = new Card("Mark", CardType.PERSON);
		dejunCard = new Card("Dejun", CardType.PERSON);
		terryCard = new Card("Terry", CardType.PERSON);
		liamCard = new Card("Liam", CardType.PERSON);
		kathleenCard = new Card("Kathleen", CardType.PERSON);
	}
	
	@Test
	public void testComputerCreateSuggestion() {
		/*
		 * Moves player 2 to the center of the Probability Room, check 
		 * what room they are in, and what room their suggestion 
		 * returns, and makes sure they represent the same room.
		 */
		ComputerPlayer player = (ComputerPlayer) board.getPlayer(1);
		
		player.setRow(3);
		player.setColumn(18);
		
		int playerRow = player.getRow();
		int playerColumn = player.getColumn();
		BoardCell playerCell = board.getCell(playerRow, playerColumn);
		char playerRoomChar = playerCell.getInitial();
		
		
		Solution playerSuggestion = player.createSuggestion(board.ctor(playerRoomChar), board.getPlayerCards(), board.getWeaponCards());
		Card roomCard = playerSuggestion.getRoomCard();
		String roomName = roomCard.getName();
		
		assertTrue(playerRoomChar == 'P' && roomName.equals("Probability"));
		
		
		player.clearSeen();
		player.updateSeen(joelCard);
		player.updateSeen(markCard);
		player.updateSeen(dejunCard);
		player.updateSeen(terryCard);
		player.updateSeen(liamCard);
		
		playerSuggestion = player.createSuggestion(board.ctor(playerRoomChar), board.getPlayerCards(), board.getWeaponCards());
		Card personCard = playerSuggestion.getPersonCard();
		
		assertTrue(kathleenCard.equals(personCard));
		
		player.clearSeen();
		player.updateSeen(confCard);
		player.updateSeen(rebuggerCard);
		player.updateSeen(inductorCard);
		player.updateSeen(eigenCard);
		player.updateSeen(binomialCard);
		
		playerSuggestion = player.createSuggestion(board.ctor(playerRoomChar), board.getPlayerCards(), board.getWeaponCards());
		Card weaponCard = playerSuggestion.getWeaponCard();
		
		assertTrue(normalizerCard.equals(weaponCard));
		
		// weapons
		player.clearSeen();
		player.updateSeen(confCard);
		player.updateSeen(rebuggerCard);
		player.updateSeen(inductorCard);
		player.updateSeen(eigenCard);
		// players
		player.updateSeen(joelCard);
		player.updateSeen(markCard);
		player.updateSeen(dejunCard);
		player.updateSeen(terryCard);
		
		int liamPicked = 0;
		int kathleenPicked = 0;
		int binomialPicked = 0;
		int normalizerPicked = 0;
		
		for (int i = 0; i < 1000; i++) {
			playerSuggestion = player.createSuggestion(board.ctor(playerRoomChar), board.getPlayerCards(), board.getWeaponCards());
			personCard = playerSuggestion.getPersonCard();
			weaponCard = playerSuggestion.getWeaponCard();
			
			if (personCard.getName().equals("Kathleen")) {
				kathleenPicked++;
			}
			else if (personCard.getName().equals("Liam")) {
				liamPicked++;
			}
			
			if (weaponCard.getName().equals("Binomial Distributor")) {
				binomialPicked++;
			}
			else if (weaponCard.getName().equals("The Normalizer")) {
				normalizerPicked++;
			}
		}
		
		assertTrue(kathleenPicked >= 100);
		assertTrue(liamPicked >= 100);
		assertTrue(binomialPicked >= 100);
		assertTrue(normalizerPicked >= 100);
		
	}

}
