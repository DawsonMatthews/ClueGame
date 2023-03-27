package clueGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {

	private int columns;
	private int rows;
	private String layoutConfigFile = "ClueLayout.csv";
	private String setupConfigFile = "ClueSetup.txt";
	private static Board theInstance = new Board();
	private BoardCell[][] griddy;
	private Map<Character, Room> roomMap;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
		
	private Board() {
		super();
	}
	
	// this method returns the only Board
    public static Board getInstance() {
    	return theInstance;
    }

    // Adds all targets with a length of pathLength to the targets list.
	public void calcTargets(BoardCell startCell, int pathLength) {
		AdjacencyListCalculator.SetAdjacencyList(rows, columns, griddy, roomMap);
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}
	
	
	private void findAllTargets(BoardCell thisCell, int numSteps) {
		for (BoardCell cell : thisCell.getAdjList()) {
			if (visited.contains(cell) || (cell.getOccupied() && cell.isRoom() == false)) {
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
	
	public BoardCell getCell(int row, int column) {
		return griddy[row][column];
	}
	
	/*
	 * loads the setup file
	 * loads the layout file
	 * adds all adjacent cells to each cell.
	 */
	public void initialize() {
				
		try {
			loadSetupConfig();
			loadLayoutConfig();
		}
		catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			return;
		}
		catch(BadConfigFormatException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		
		AdjacencyListCalculator.SetAdjacencyList(rows, columns, griddy, roomMap);
	}
	
	/*
	 * Creates the map of rooms
	 */
	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException{
		roomMap = new HashMap<Character, Room>();

		FileReader reader = new FileReader("data/" + setupConfigFile);
		Scanner in = new Scanner(reader);
		
		while (in.hasNextLine()) {
			String roomInfo = in.nextLine();
			if (roomInfo.charAt(0) == '/') {
				continue;
			}
			
			String[] infoArray = roomInfo.split(", ");
			
			// If anything is written other than 'Room' or 'Space', throw an exception
			if (!infoArray[0].equals("Room") && !infoArray[0].equals("Space")) {
				throw new BadConfigFormatException("Room labelled as neither room nor space.");
			}
			
			Room newRoom = new Room(infoArray[1]);
			
			String roomType = infoArray[0];
			if (roomType.equals("Room")) {
				newRoom.setRoom(true);
			}
			else {
				newRoom.setRoom(false);
			}
			char roomChar = infoArray[2].charAt(0);
			roomMap.put(roomChar, newRoom);
			
		}
		
		in.close();
	}
	
	/*
	 * Read in all cells from the file, add them to the grid
	 */
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		ArrayList<String []> rowList = new ArrayList<String[]>();
		FileReader reader = new FileReader("data/" + layoutConfigFile);
		Scanner in = new Scanner(reader);
		
		int previousRowSize = -1;
		
		while (in.hasNextLine()) {
			String rowString = in.nextLine();
			String[] rowArray = rowString.split(",");
			
			// If a row size is different from the previous row size, throw an exception
			if (previousRowSize != -1) {
				if (previousRowSize != rowArray.length) {
					throw new BadConfigFormatException("Improper row size.");
				}
			}
			previousRowSize = rowArray.length;
			
			rowList.add(rowArray);
		}
		
		in.close();
		
		rows = rowList.size();
		columns = rowList.get(0).length;
		griddy = new BoardCell[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				
				BoardCell newCell = new BoardCell(i, j);
				
				char roomChar = rowList.get(i)[j].charAt(0);
				if (roomMap.containsKey(roomChar)){
					// Sets the new cells room status to the room status of the character. 
					newCell.setRoom(roomMap.get(roomChar).getRoom());
				}
				else {
					throw new BadConfigFormatException();
				}
				
				newCell.setInitial(rowList.get(i)[j].charAt(0));
				
				if(rowList.get(i)[j].length() == 2) {
					if(rowList.get(i)[j].charAt(1) == '<') {
						newCell.setDoorDirection(DoorDirection.LEFT);
					}
					else if(rowList.get(i)[j].charAt(1) == '>') {
						newCell.setDoorDirection(DoorDirection.RIGHT);
					}
					else if(rowList.get(i)[j].charAt(1) == 'v') {
						newCell.setDoorDirection(DoorDirection.DOWN);
					}
					else if(rowList.get(i)[j].charAt(1) == '^') {
						newCell.setDoorDirection(DoorDirection.UP);
					}
					
					if(rowList.get(i)[j].charAt(1) == '#') {
						newCell.setLabel(true);
						roomMap.get(rowList.get(i)[j].charAt(0)).setLabelCell(newCell);
					}
					
					if(rowList.get(i)[j].charAt(1) == '*') {
						newCell.setCenter(true);
						char cellChar = rowList.get(i)[j].charAt(0);
						roomMap.get(cellChar).setCenterCell(newCell);
					}
					
					// If the second letter is A-Z
					if (rowList.get(i)[j].charAt(1) >= 'A' && rowList.get(i)[j].charAt(1) <= 'Z') {
						newCell.setSecretPassage(rowList.get(i)[j].charAt(1));
					}
					
					
				}
				
				griddy[i][j] = newCell;
			}
		}
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}

	public void setConfigFiles(String layoutName, String setupName) {
		layoutConfigFile = layoutName;
		setupConfigFile = setupName;
	}

	public Room getRoom(char c) {
		// TODO Auto-generated method stub
		return roomMap.get(c);
	}

	public int getNumRows() {
		// TODO Auto-generated method stub
		return rows;
	}

	public int getNumColumns() {
		// TODO Auto-generated method stub
		return columns;
	}

	public Room getRoom(BoardCell cell) {
		// TODO Auto-generated method stub
		return roomMap.get(cell.getInitial());
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		return griddy[i][j].getAdjList();
	}
}
