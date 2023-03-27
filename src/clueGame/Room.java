package clueGame;

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private boolean isRoom;
	
	public Room(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public BoardCell getLabelCell() {
		// TODO Auto-generated method stub
		return labelCell;
	}
	
	public void setLabelCell(BoardCell cell) {
		// TODO Auto-generated method stub
		this.labelCell = cell;
	}
	
	public void setCenterCell(BoardCell cell) {
		// TODO Auto-generated method stub
		this.centerCell = cell;
	}

	public BoardCell getCenterCell() {
		// TODO Auto-generated method stub
		return centerCell;
	}
	
	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public boolean getRoom() {
		return isRoom;
	}

}
