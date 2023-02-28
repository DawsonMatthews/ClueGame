package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {

	final static int COLUMNS = 4;
	final static int ROWS = 4;
	
	private TestBoardCell[][] griddy;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	public TestBoard() {
		super();
		griddy = new TestBoardCell[ROWS][COLUMNS];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				TestBoardCell newCell = new TestBoardCell(i, j);
				griddy[i][j] = newCell;
			}
		}
		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (i+1 < ROWS) {
					griddy[i][j].addAdjacency(griddy[i+1][j]);
				}
				if (i-1 >= 0) {
					griddy[i][j].addAdjacency(griddy[i-1][j]);
				}
				if (j+1 < COLUMNS) {
					griddy[i][j].addAdjacency(griddy[i][j+1]);
				}
				if (j-1 >= 0) {
					griddy[i][j].addAdjacency(griddy[i][j-1]);
				}
			}
		}
	}

	void calcTargets(TestBoardCell startCell, int pathLength) {
		visited = new HashSet<TestBoardCell>();
		targets = new HashSet<TestBoardCell>();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}
	
	private void findAllTargets(TestBoardCell thisCell, int numSteps) {
		for (TestBoardCell cell : thisCell.getAdjList()) {
			if (visited.contains(cell) || cell.getOccupied()) {
				continue;
			}
			
			visited.add(cell);
			if (cell.isRoom()) {
				targets.add(cell);
			}
			else {
				if (numSteps == 1) {
					targets.add(cell);
				}
				else {
					findAllTargets(cell, numSteps - 1);
				}
			}
			
			visited.remove(cell);
		}
	}
	
	TestBoardCell getCell(int row, int column) {
		return griddy[row][column];
	}
	
	Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	
}
