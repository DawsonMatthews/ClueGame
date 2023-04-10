package clueGame;

import java.awt.BorderLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;

public class GameControlPanel extends JPanel {
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	
	private JTextField whoseTurnText;
	private JTextField rolledText;
	private JTextField guessText;
	private JTextField resultText;
	
	public GameControlPanel()  {
		
		// Top panel
		JPanel topPanel = new JPanel();
		topPanel.setSize(4, 1);
		//topPanel.setPreferredSize(new Dimension(750, 60));
		
		//Whose turn panel
		JPanel whoseTurnPanel = new JPanel();
		JLabel whoseTurnLabel = new JLabel("Whose Turn?");
		whoseTurnText = new JTextField("Player Name");
		whoseTurnText.setEditable(false);
		whoseTurnText.setBackground(Color.YELLOW);
		whoseTurnPanel.add(whoseTurnLabel, BorderLayout.NORTH);
		whoseTurnPanel.add(whoseTurnText, BorderLayout.SOUTH);
		topPanel.add(whoseTurnPanel);
		
		//roll Panel
		JPanel rollPanel = new JPanel();
		JLabel rollLabel = new JLabel("Roll:");
		rolledText = new JTextField("3");
		rollPanel.add(rollLabel, BorderLayout.WEST);
		rollPanel.add(rolledText, BorderLayout.EAST);
		topPanel.add(rollPanel);
		
		//Make accusation button
		JButton accusationButton = new JButton("Make Accusation");
		topPanel.add(accusationButton);
		add(topPanel, BorderLayout.NORTH);
		
		// Next button
		JButton nextButton = new JButton("NEXT!");
		topPanel.add(nextButton);
		
		add(topPanel);
		
		// Lower Panel
		JPanel lowerPanel = new JPanel();
		lowerPanel.setSize(2, 0);
		//lowerPanel.setPreferredSize(new Dimension(750, 60));
		
		// Guess Panel
		JPanel guessPanel = new JPanel();
		guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		guessPanel.setSize(1, 0);
		guessText = new JTextField("I have no guess! I guess...");
		guessText.setEditable(false);
		
		guessPanel.add(guessText);
		lowerPanel.add(guessPanel);
		
		// Result Panel
		JPanel resultPanel = new JPanel();
		resultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		resultPanel.setSize(1, 0);
		resultText = new JTextField("So you have nothing?");
		resultText.setEditable(false);
		resultPanel.add(resultText);
		lowerPanel.add(resultPanel);
		
		
		add(lowerPanel, BorderLayout.SOUTH);
	}
	
	public void setTurn(Player player, int rollValue) {
		whoseTurnText.setText(player.getName());
		whoseTurnText.setBackground(player.getColor());
		rolledText.setText(String.valueOf(rollValue));
	}
	
	public void setGuess(String message) {
		guessText.setText(message);
	}
	
	public void setGuessResult(String message) {
		resultText.setText(message);
	}
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
				
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", 'O', 0, 0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}
}
