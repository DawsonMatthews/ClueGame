package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener{

	private int columns;
	private int rows;
	private String layoutConfigFile = "ClueLayout.csv";
	private String setupConfigFile = "ClueSetup.txt";
	private static Board theInstance = new Board();
	private BoardCell[][] griddy;
	private Map<Character, Room> roomMap;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private ArrayList<Card> initialDecku;
	private ArrayList<Card> decku;// u stands for upper oriented (implemented in upper orientated fashion)
	private ArrayList<Card> playerCards;
	private ArrayList<Card> weaponCards;
	private Player[] playerList = new Player[6];
	private int currentPlayerIndex = 5;
	private Solution theAnswer;
	
	private boolean playerFinished = true;
	private int roll;
		
	public boolean isPlayerFinished() {
		return playerFinished;
	}

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
		
		if (targets != null) {
			for (BoardCell cell : targets) {
				cell.setTarget(false);
			}
		}
		
		
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
		
		deal();
			
		AdjacencyListCalculator.SetAdjacencyList(rows, columns, griddy, roomMap);
	
		addMouseListener(this);
	}
	
	/*
	 * Creates the map of rooms
	 */
	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException{
		roomMap = new HashMap<Character, Room>();
		initialDecku = new ArrayList<Card>();
		decku = new ArrayList<Card>();
		boolean playerCreated = false;
		int playerIndex = 0;

		FileReader reader = new FileReader("data/" + setupConfigFile);
		Scanner in = new Scanner(reader);
		
		playerCards = new ArrayList<Card>();
		weaponCards = new ArrayList<Card>();
		ArrayList<Card> roomCards = new ArrayList<Card>();
		
		while (in.hasNextLine()) {
			String roomInfo = in.nextLine();
			if (roomInfo.charAt(0) == '/') {
				continue;
			}
			
			String[] infoArray = roomInfo.split(", ");
			
			// If anything is written other than 'Room' or 'Space', throw an exception
			String objectType = infoArray[0];

			
			Card newCard;
			if (objectType.equals("Room") || objectType.equals("Space")) {
				
				String name = infoArray[1];
				Room newRoom = new Room(name);
				
				String roomType = infoArray[0];
				if (roomType.equals("Room")) {
					newRoom.setRoom(true);
				}
				else {
					newRoom.setRoom(false);
				}
				char roomChar = infoArray[2].charAt(0);
				roomMap.put(roomChar, newRoom);
				
				if (objectType.equals("Room")) {
					newCard = new Card(name, CardType.ROOM);
					initialDecku.add(newCard);
					roomCards.add(newCard);
				}
				
			}
			else if (objectType.equals("Player")) {
				String name = infoArray[1];
				char color =  infoArray[2].charAt(0);
				
				if (color != 'R' && color != 'O' && color != 'G' && color != 'P' && color != 'B' && color != 'W') {
					throw new BadConfigFormatException("Color not recognized.");
				}
				
				int row = Integer.parseInt(infoArray[3]);
				int column = Integer.parseInt(infoArray[4]);
				Player newPlayer;
				
				if (playerCreated == false) {
					newPlayer = new HumanPlayer(name, color, row, column);
					playerCreated = true;
				}
				
				else {
					newPlayer = new ComputerPlayer(name, color, row, column);

				}
				
				playerList[playerIndex] = newPlayer;
				playerIndex++;
				
				newCard = new Card(name, CardType.PERSON);
				initialDecku.add(newCard);
				playerCards.add(newCard);
			}
			else if (objectType.equals("Weapon")) {
				String name = infoArray[1];
				newCard = new Card(name, CardType.WEAPON);
				initialDecku.add(newCard);
				weaponCards.add(newCard);
			}
			else {
				throw new BadConfigFormatException("Room labelled as neither room nor space nor person nor weapon");
			}
		}
		
		Random random = new Random();
		
		int roomIndex = random.nextInt(roomCards.size());
		Card roomCard = roomCards.get(roomIndex);
		roomCards.remove(roomCard);
		decku.addAll(roomCards);
		
		int personIndex = random.nextInt(playerCards.size());
		Card personCard = playerCards.get(personIndex);
		decku.addAll(playerCards);
		decku.remove(personCard);
		
		int weaponIndex = random.nextInt(weaponCards.size());
		Card weaponCard = weaponCards.get(weaponIndex);
		decku.addAll(weaponCards);
		decku.remove(weaponCard);
		
		theAnswer = new Solution(personCard, roomCard, weaponCard);

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
					newCell.setRoomCard(roomMap.get(roomChar).getName());
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
	
	public void deal() {
		Random random = new Random();
		int playerIndex = 0;
		while (decku.isEmpty() == false) {
			int randIndex = random.nextInt(decku.size());
			Card randCard = decku.get(randIndex);
			playerList[playerIndex].updateHand(randCard);
			decku.remove(randCard);
			playerIndex++;
			if (playerIndex == 6) {
				playerIndex = 0;
			}
		}
	}
	
	public boolean checkAccusation(Card person, Card room, Card weapon)
	{
		Solution accusation = new Solution(person, room, weapon);
		return accusation.equals(theAnswer);
		
	}
	
	public Card handleSuggestion(int player, Card person, Card Room, Card weapon) {
		
		int index = player + 1;
		
		for (int i = 1; i < 6; i++) {
			
			if (index >= 6) {
				index = 0;
			}
			
			Player currPlayer = playerList[index];
			Card disproveCard = currPlayer.disproveSuggestion(person, Room, weapon);
			
			if ( disproveCard != null) {
				return disproveCard;
			}	
			index++;
		}
		
		return null;
	}
	
	/*
	 * Goes through each cell in the grid and has the cell draw itself based on position/ type. Then goes
	 * through each room and draws its label on label cell. Finally, goes through player and calls for them to 
	 * draw themself.
	 * 
	 */
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				griddy[i][j].draw(g, 25);
			}
		}
		
		for (Room room : roomMap.values()) {
			if (room.getRoom() == false) {
				continue;
			}
			g.setColor(Color.BLUE);
			g.setFont(new Font("Serif", Font.BOLD, 18));
			g.drawString(room.getName(), room.getLabelCell().getColumn() * 25, room.getLabelCell().getRow() * 25);
		}
		
		for (Player player : playerList) {
			player.draw(g, 25);
		}
		
	}	
	
	public int nextPlayer() {
		currentPlayerIndex++;
		if (currentPlayerIndex >= 6) {
			currentPlayerIndex = 0;
		}
		
		return currentPlayerIndex;
	}
	
	public int rollDice() {
		Random rand = new Random();
		return rand.nextInt(6) + 1;
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
	
	public Player getPlayer(int position) {
		return playerList[position];
	}
	
	/*
	 * FOR TESTING ONLY
	 */
	public ArrayList<Card> getInitialDecku() {
		return initialDecku;
	}
	
	public ArrayList<Card> getDecku() {
		return decku;
	}
	
	public Solution getSolution() {
		return theAnswer;
	}
	
	public void setTheAnswer(Card person, Card room, Card weapon) {
		theAnswer = new Solution(person, room, weapon);
	}
	
	public Room ctor(char c) {
		return roomMap.get(c);
	}
	
	public ArrayList<Card> getPlayerCards() {
		return (ArrayList<Card>) playerCards.clone();
	}
	
	public ArrayList<Card> getWeaponCards() {
		return (ArrayList<Card>) weaponCards.clone();
	}

	public void setIsFinished(boolean isFinished) {
		this.playerFinished= isFinished;
		
	}

	// Now back to real methods
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		Player player = playerList[currentPlayerIndex];
		if (player instanceof ComputerPlayer) {
			return;
		}
		if (playerFinished == true) {
			return;
		}
		
		BoardCell clickedCell = null;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (griddy[i][j].containsClick(e.getX(), e.getY())) {
					clickedCell = griddy[i][j];	
					break;
				}
			}
		}
		
		if (clickedCell == null) {
			return;
		}
		if (clickedCell.getIsTarget()) {
			playerFinished = true;
			player.setPosition(clickedCell.getRow(), clickedCell.getColumn());
			
			if (clickedCell.isRoom()) {
				// Make suggestion
				player.createSuggestion(ctor(clickedCell.getInitial()), playerCards, weaponCards);
				// Update seen
			}
			
			for (BoardCell cell : targets) {
				cell.setTarget(false);
			}
			
			repaint();
		}
		
		else {
			JOptionPane.showMessageDialog(this, "Erm, actually, you can't pick this spot...");
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
