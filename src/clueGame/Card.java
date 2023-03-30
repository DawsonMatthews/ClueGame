package clueGame;

public class Card {

	private String cardName;
	private CardType type;
	
	public Card(String cardName, CardType type) {
		this.cardName = cardName;
		this.type = type;
		// TODO Auto-generated constructor stub
	}
	
	public boolean equals(Card target) {
		return false;
	}

	public String getName() {
		return cardName;
	}
	
	public CardType getType() {
		return type;
	}
}
