package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	
	private boolean isRoom;
	private boolean isOccupied;
	private boolean isLabel;
	private boolean isCenter;
	private boolean isTarget;
	private char initial;
	private char secretPassage;
	private int row;
	private int column;
	private DoorDirection doorDirection;
	private Set<BoardCell> adjacencyList;
	private String roomCard;
	
	

	public BoardCell(int row, int column) {
		super();
		
		this.row = row;
		this.column = column;
		secretPassage = ' ';
		isRoom = false;
		isOccupied = false;
		isLabel = false;
		isCenter = false;
		isTarget = false;
		doorDirection = DoorDirection.NONE;
		adjacencyList = new HashSet<BoardCell>();
		roomCard = null;
	}
	
	public void addAdjacency(BoardCell cell) {
		adjacencyList.add(cell);
	}
	
	public void clearAdjacencyList() {
		adjacencyList.clear();
	}
	
	public void draw(Graphics graphics, int cellSize) {
		if (isTarget) {
			graphics.setColor(Color.GREEN);
		}
		else if (isRoom) {
			graphics.setColor(Color.GRAY);
		}
		else if (initial == 'X') {
			graphics.setColor(Color.BLACK);
		}
				
		else {
			graphics.setColor(Color.YELLOW);
		}
		graphics.fillRect(cellSize * column, (cellSize * row) + 1, cellSize, cellSize);
		graphics.setColor(Color.BLACK);
		graphics.drawRect(cellSize * column, (cellSize * row) + 1, cellSize, cellSize);

		if (doorDirection == DoorDirection.UP) {
			graphics.setColor(Color.BLUE);
			graphics.fillRect(cellSize * column, cellSize * row + 1, cellSize, 8);
		}
		
		else if (doorDirection == DoorDirection.LEFT) {
			graphics.setColor(Color.BLUE);
			graphics.fillRect(cellSize * column, cellSize * row + 1, 8, cellSize);
		}
		
		else if (doorDirection == DoorDirection.RIGHT) {
			graphics.setColor(Color.BLUE);
			graphics.fillRect((cellSize * column) + cellSize - 8, cellSize * row + 1, 8, cellSize);
		}
		
		else if (doorDirection == DoorDirection.DOWN) {
			graphics.setColor(Color.BLUE);
			graphics.fillRect(cellSize * column, (cellSize * row) + cellSize - 7, cellSize, 8);
		}
		
		
		
	}
	
	Set<BoardCell> getAdjList() {
		return adjacencyList;
	}

	void setRoom(boolean partOfRoom) {
		isRoom = partOfRoom;
	}
	
	boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean occupied) {
		this.isOccupied = occupied;
	}
	
	boolean getOccupied() {
		return isOccupied;
	}
	
	public boolean isDoorway() {
		if (doorDirection == DoorDirection.NONE) {
			return false;
		}
		else {
			return true;
		}
	}

	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return doorDirection;
	}
	
	public void setDoorDirection(DoorDirection direction) {
		// TODO Auto-generated method stub
		this.doorDirection = direction;
	}

	public boolean isLabel() {
		// TODO Auto-generated method stub
		return isLabel;
	}
	
	public void setLabel(boolean roomLabel) {
		this.isLabel = roomLabel;
	}

	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return isCenter;
	}
	
	public void setCenter(boolean center) {
		this.isCenter = center;
	}

	public void setSecretPassage(char destinationChar) {
		this.secretPassage = destinationChar;
	}
	
	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return secretPassage;
	}

	public char getInitial() {
		return initial;
	}
	
	public void setInitial(char initial) {
		this.initial = initial;
	}
	
	public void setRoomCard(String roomCard) {
		this.roomCard = roomCard;
	}

	public String getRoomCard() {
		return roomCard;
	}

	public void setTarget(boolean isTarget) {
		this.isTarget = isTarget;
	}
	
	
}
