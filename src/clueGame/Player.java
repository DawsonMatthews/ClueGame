package clueGame;

import java.awt.Color;

public abstract class Player {

	private String name;
	private Color color;
	private int row;
	private int column;
	
	public Player(String name, char color, int row, int column) throws BadConfigFormatException {
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
		else if (color == 'W') {
			myColor = Color.WHITE;
		}
		else {
			throw new BadConfigFormatException("Color not recognized.");
		}
		this.color = myColor;
		this.row = row;
		this.column = column;
	}
	
	public void updateHand(Card card) {
		
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
}
