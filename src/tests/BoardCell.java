package tests;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	
	private int row, column;
	private Boolean isRoom, isOccupied;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjacencyList;
	
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
		
		isRoom = false;
		isOccupied = false;
		
		adjacencyList = new HashSet<BoardCell>();
	}
	
	void addAdjacency(BoardCell cell) {
		adjacencyList.add(cell);
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
	
	void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}
	
	boolean getOccupied() {
		return isOccupied;
	}

	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return false;
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isLabel() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return false;
	}

	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return 0;
	}
}
