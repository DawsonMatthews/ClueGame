package clueGame;

import java.util.Map;

public class AdjacencyListCalculator implements SetWalkwayAdjacency {

	public void SetAdjacencyList(int ROWS, int COLUMNS, BoardCell[][] griddy, Map<Character, Room> roomMap) {
		
		ClearAllCellAdjacencyLists(ROWS, COLUMNS, griddy);
		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				
				BoardCell cell = griddy[i][j];
				
				if (roomMap.get(cell.getInitial()).getName().equals("Walkway")) {
					SetWalkwayAdjacency(ROWS, COLUMNS, griddy, roomMap, i, j, cell);
				}
				
				if (cell.getSecretPassage() != ' ') {
					SetSecretPassageAdjacency(roomMap, cell);
				}
			}
		}
	}

	private void SetSecretPassageAdjacency(Map<Character, Room> roomMap, BoardCell cell) {
		char roomChar = cell.getInitial();
		Room startRoom = roomMap.get(roomChar);
		BoardCell startCenterCell = startRoom.getCenterCell();
		
		char targetRoomChar = cell.getSecretPassage();
		Room targetRoom = roomMap.get(targetRoomChar);
		BoardCell targetCenterCell = targetRoom.getCenterCell();
		
		startCenterCell.addAdjacency(targetCenterCell);
	}

	private void SetWalkwayAdjacency(int ROWS, int COLUMNS, BoardCell[][] griddy, Map<Character, Room> roomMap, int i, int j, BoardCell cell) {
		
		BoardCell adjacentCell;
		if (cell.isDoorway() == true) {
			Room adjacentRoom;
			if (cell.getDoorDirection() == DoorDirection.LEFT) {
				adjacentRoom = roomMap.get(griddy[i][j-1].getInitial());
			}
			else if (cell.getDoorDirection() == DoorDirection.RIGHT) {
				adjacentRoom = roomMap.get(griddy[i][j+1].getInitial());
			}
			
			else if (cell.getDoorDirection() == DoorDirection.UP) {
				adjacentRoom = roomMap.get(griddy[i-1][j].getInitial());
			}
			
			else {
				adjacentRoom = roomMap.get(griddy[i+1][j].getInitial());
			}
			
			BoardCell roomCenterCell = adjacentRoom.getCenterCell();
			cell.addAdjacency(roomCenterCell);
			roomCenterCell.addAdjacency(cell);
		}
		if ((i+1) < ROWS) {
			adjacentCell = griddy[i+1][j];
			
			if (roomMap.get(adjacentCell.getInitial()).getName().equals("Walkway") && adjacentCell.getOccupied() == false) {
				cell.addAdjacency(adjacentCell);
			}
		}
		
		if ((i-1) >= 0) {
			adjacentCell = griddy[i-1][j];
			
			if (roomMap.get(adjacentCell.getInitial()).getName().equals("Walkway") && adjacentCell.getOccupied() == false) {
				cell.addAdjacency(adjacentCell);
			}
		}
		
		if ((j+1) < COLUMNS) {
			adjacentCell = griddy[i][j+1];
			
			if (roomMap.get(adjacentCell.getInitial()).getName().equals("Walkway") && adjacentCell.getOccupied() == false) {
				cell.addAdjacency(adjacentCell);
			}
		}
		
		if ((j-1) >= 0) {
			adjacentCell = griddy[i][j-1];
			
			if (roomMap.get(adjacentCell.getInitial()).getName().equals("Walkway") && adjacentCell.getOccupied() == false) {
				cell.addAdjacency(adjacentCell);
			}
		}
	}

	private void ClearAllCellAdjacencyLists(int ROWS, int COLUMNS, BoardCell[][] griddy) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				BoardCell cell = griddy[i][j];
				cell.clearAdjacencyList();
			}
		}
	}


}
