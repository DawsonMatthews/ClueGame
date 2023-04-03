package clueGame;

public class Card {

	private String cardName;
	private CardType type;
	
	public Card(String cardName, CardType type) {
		this.cardName = cardName;
		this.type = type;
	}
	
	public boolean equals(Card target) {
		return cardName.equals(target.cardName) && type == target.type;
	}

	public String getName() {
		return cardName;
	}
	
	public CardType getType() {
		return type;
	}
}
