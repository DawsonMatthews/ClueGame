package clueGame;

import java.awt.BorderLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
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
		
		JOptionPane.showMessageDialog(this, "You are Joel The Last of Us 2023.\nCan you find the solution before the others?");
	}
	
	public static ClueFrame getInstance() {
		return theInstance;
	}
	
	public void nextButtonPressed() {
		int roll = 0;
		int currentPlayerIndex = 0;
		
		if (board.isPlayerFinished() == false) {
			JOptionPane.showMessageDialog(this, "Finish yo turn");
			return;
		}
		
		currentPlayerIndex = board.nextPlayer();
		Player currentPlayer = board.getPlayer(currentPlayerIndex);
		
		roll = board.rollDice();
		
		int playerRow = currentPlayer.getRow();
		int playerColumn = currentPlayer.getColumn();
		
		board.calcTargets(board.getCell(playerRow, playerColumn), roll);
				
		
		// Update control panel
		gameControlPanel.setTurn(currentPlayer, roll);
		// Might need more soon
		
		if (currentPlayer instanceof HumanPlayer) {
			// Display targets
			Set<BoardCell> targets = board.getTargets();
			for (BoardCell cell : targets) {
				cell.setTarget(true);
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
