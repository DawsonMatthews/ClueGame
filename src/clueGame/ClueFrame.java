package clueGame;

import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.Dimension;
import java.util.Set;


public class ClueFrame extends JFrame {
	
	private static Board board;
	private static ClueFrame theInstance = new ClueFrame("Clue Game"); 
	private GameControlPanel gameControlPanel;
	
	private ClueFrame(String name) {
		super(name);
	}
	
	private void initialize() {
		setLayout(new BorderLayout());
		
		setSize(756, 756);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.setPreferredSize(new Dimension(756, 602));
		
		board = Board.getInstance();
		board.initialize();
		board.setPreferredSize(new Dimension(601, 576));
		topPanel.add(board, BorderLayout.WEST);
		
		KnownCardsPanel knownCardsPanel = new KnownCardsPanel();
		topPanel.add(knownCardsPanel, BorderLayout.EAST);
		
		panel.add(topPanel, BorderLayout.NORTH);
		
		JPanel botPanel = new JPanel();
		gameControlPanel = new GameControlPanel();
		botPanel.add(gameControlPanel);
		botPanel.setPreferredSize(new Dimension(756, 128));
		panel.add(botPanel, BorderLayout.SOUTH);
		
		add(panel);
		
		nextButtonPressed();
		
		JOptionPane.showMessageDialog(this, "You are Joel The Last of Us 2023.\nCan you find the solution before the others?");
	}
	
	public static ClueFrame getInstance() {
		return theInstance;
	}
	
	/*
	 * 
	 * Controls the overall logic for when the next button is pressed.
	 */
	
	public void nextButtonPressed() {
		int roll = 0;
		int currentPlayerIndex = 0;
		//prompts player to finish turn when applicable
		if (board.isPlayerFinished() == false) {
			JOptionPane.showMessageDialog(this, "Finish yo turn");
			return;
		}
		
		
		//rolls die and calculates targets
		currentPlayerIndex = board.nextPlayer();
		Player currentPlayer = board.getPlayer(currentPlayerIndex);
		
		roll = board.rollDice();
		
		int playerRow = currentPlayer.getRow();
		int playerColumn = currentPlayer.getColumn();
		
		board.calcTargets(board.getCell(playerRow, playerColumn), roll);
				
		
		// Update control panel
		gameControlPanel.setTurn(currentPlayer, roll);
		// Might need more soon
		Set<BoardCell> targets = board.getTargets();
			
		if (currentPlayer instanceof HumanPlayer) {
			// Display targets
			for (BoardCell cell : targets) {
				cell.setTarget(true);
			}
			board.setIsFinished(false);
			
			repaint();
		}
		
		//moves player and creates suggestion for computer player
		else if (currentPlayer instanceof ComputerPlayer){
			//accusation 
			ComputerPlayer computerPlayer = (ComputerPlayer)currentPlayer;
			BoardCell cell = computerPlayer.selectTarget(targets);
			currentPlayer.setPosition(cell.getRow(), cell.getColumn());
			
			BoardCell roomCell = board.getCell(computerPlayer.getRow(), computerPlayer.getColumn());
			char roomChar = roomCell.getInitial();
			Room room = board.ctor(roomChar);
			
			if (room.getRoom()) {
				Solution suggestion = computerPlayer.createSuggestion(room, board.getPlayerCards(), board.getWeaponCards());
				board.handleSuggestion(currentPlayerIndex, suggestion.getPersonCard(), suggestion.getRoomCard(), suggestion.getWeaponCard());
			}
			
			
			repaint();
		}
	}
	
	public static void main(String args[]) {
		ClueFrame clueFrame = ClueFrame.getInstance();
		clueFrame.initialize();

		clueFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		clueFrame.setVisible(true); // make it visible
	}
}
