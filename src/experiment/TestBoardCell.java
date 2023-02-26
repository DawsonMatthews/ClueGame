package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	
	public TestBoardCell(int row, int column) {
		super();
		
	}
	
	void addAdjacency(TestBoardCell cell) {
		
	}
	
	Set<TestBoardCell> getAdjList() {
		Set<TestBoardCell> mySet = new HashSet<TestBoardCell>();
		return mySet;
	}

	void setRoom(boolean partOfRoom) {
		
	}
	
	boolean isRoom() {
		return true;
	}
	
	void setOccupied(boolean occupied) {
		
	}
	
	boolean getOccupied() {
		return true;
	}
}
