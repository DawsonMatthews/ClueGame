package clueGame;

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
	
	public void addAdjacency(BoardCell cell) {
		adjacencyList.add(cell);
	}
	
	public void clearAdjacencyList() {
		adjacencyList.clear();
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
		return roomLabel;
	}
	
	public void setLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}

	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return roomCenter;
	}
	
	public void setCenter(boolean center) {
		this.roomCenter = center;
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
}
