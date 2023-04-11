package clueGame;

import java.awt.BorderLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;


public class ClueFrame extends JFrame {
	
	private static Board board;
	
	public ClueFrame(String name) {
		super(name);
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
		GameControlPanel gameControlPanel = new GameControlPanel();
		botPanel.add(gameControlPanel);
		botPanel.setPreferredSize(new Dimension(756, 128));
		panel.add(botPanel, BorderLayout.SOUTH);
		
		add(panel);
		
		JOptionPane.showMessageDialog(this, "You are Joel The Last of Us 2023.\nCan you find the solution before the others?");
	}
	
	public static void main(String args[]) {
		ClueFrame clueFrame = new ClueFrame("Clue Game");

		clueFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		clueFrame.setVisible(true); // make it visible
	}
}
