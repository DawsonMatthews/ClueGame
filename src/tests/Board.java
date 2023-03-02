package tests;

import java.util.HashSet;
import java.util.Set;

public class Board {

	private final static int COLUMNS = 4;
	private final static int ROWS = 4;
	private String layoutConfigFile = "ClueLayout.csv";
	private String setupConfigFile = "ClueSetup.txt";
	//Map<Character, Room>roomMap;
	private static Board theInstance = new Board();
	
	private BoardCell[][] griddy;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	
	private Board() {
		super();
		
	}
	
	// this method returns the only Board
    public static Board getInstance() {
    	return theInstance;
    }

	void calcTargets(BoardCell startCell, int pathLength) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}
	
	private void findAllTargets(BoardCell thisCell, int numSteps) {
		for (BoardCell cell : thisCell.getAdjList()) {
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
	
	BoardCell getCell(int row, int column) {
		return griddy[row][column];
	}
	
	public void initialize() {
		griddy = new BoardCell[ROWS][COLUMNS];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				BoardCell newCell = new BoardCell(i, j);
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
	
	public void loadSetupConfig() {
		
	}
	
	public void loadLayoutConfig() {
		
	}
	
	Set<BoardCell> getTargets() {
		return targets;
	}

	public void setConfigFiles(String string, String string2) {
		
		
	}

	public Room getRoom(char c) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getNumRows() {
		// TODO Auto-generated method stub
		return ROWS;
	}

	public int getNumColumns() {
		// TODO Auto-generated method stub
		return COLUMNS;
	}

	public Room getRoom(BoardCell cell) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
