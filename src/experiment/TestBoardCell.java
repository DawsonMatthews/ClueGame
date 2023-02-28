package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	
	private int row, column;
	private Boolean isRoom, isOccupied;
	
	Set<TestBoardCell> adjacencyList;
	
	public TestBoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
		
		isRoom = false;
		isOccupied = false;
		
		adjacencyList = new HashSet<TestBoardCell>();
	}
	
	void addAdjacency(TestBoardCell cell) {
		adjacencyList.add(cell);
	}
	
	Set<TestBoardCell> getAdjList() {
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
}
