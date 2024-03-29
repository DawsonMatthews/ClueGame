package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;

public abstract class Player extends JPanel{

	private String name;
	private Color color;
	private int row;
	private int column;
	private ArrayList<Card> hand = new ArrayList<Card>();
	private Set<Card> seenCards = new HashSet<Card>();
	
	public Player(String name, char color, int row, int column) {
		this.name = name;
		
		Color myColor;
		
		if (color == 'R') {
			myColor = Color.RED;
		}
		else if(color == 'O') {
			myColor = Color.ORANGE;
		}
		else if (color == 'G') {
			myColor = Color.GREEN;
		}
		else if (color == 'P') {
			myColor = Color.PINK;
		}
		else if (color == 'B') {
			myColor = Color.BLUE;
		}
		else {
			myColor = Color.WHITE;
		}
		
		this.color = myColor;
		this.row = row;
		this.column = column;
	}
	
	public void updateHand(Card card) {
		hand.add(card);
	}

	public void updateSeen(Card seenCard) {
		seenCards.add(seenCard);
	}
	
	private boolean isInHand(Card card) {
		for (Card c : hand) {
			if (card.equals(c)) {
				return true;
			}
		}
		return false;
	}
	
	public Card disproveSuggestion(Card person, Card room, Card weapon) {

		
		ArrayList<Card> suggestedCardsInHand = new ArrayList<Card>();
		
		if (isInHand(person)) {
			suggestedCardsInHand.add(person);
		}
		if (isInHand(room)) {
			suggestedCardsInHand.add(room);
		}
		if (isInHand(weapon)) {
			suggestedCardsInHand.add(weapon);
		}
		
		int cardCount = suggestedCardsInHand.size();
		if (cardCount == 0) {
			return null;
		}
		
		Random random = new Random();
		int cardIndex = random.nextInt(cardCount);
		return suggestedCardsInHand.get(cardIndex);
	}
	
	public void draw(Graphics g, int cellSize) {
		g.setColor(color);
		g.fillOval(cellSize * column, (cellSize * row) + 1, cellSize, cellSize);
	}
	
	protected Set<Card> getSeenCards() {
		return seenCards;
	}
	
	/*
	 * Getters/ Setters for testing
	 */
	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public void SetHand(Card card1, Card card2, Card card3) {
		hand.clear();
		hand.add(card1);
		hand.add(card2);
		hand.add(card3);
	}
	
	public void SetHand(Card card) {
		hand.clear();
		hand.add(card);
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	public void setPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public void clearSeen() {
		seenCards.clear();
	}

	protected abstract Solution createSuggestion(Room room, ArrayList<Card> players, ArrayList<Card> weapons);
}
