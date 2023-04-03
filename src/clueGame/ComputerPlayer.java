package clueGame;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, char color, int row, int column) throws BadConfigFormatException {
		super(name, color, row, column);
	}

	public Solution createSugestion() {
		return new Solution(new Card("Default", CardType.PERSON), new Card("Default", CardType.PERSON), new Card("Default", CardType.PERSON));
	}
	
	public BoardCell selectTarget() {
		return new BoardCell(0, 0);
	}
}
