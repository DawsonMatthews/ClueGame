package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class HumanPlayer extends Player {
	Solution sol;
	public HumanPlayer(String name, char color, int row, int column) {
		super(name, color, row, column);
	}
	@Override
	public Solution createSuggestion(Room room, ArrayList<Card> players, ArrayList<Card> weapons) {
		JFrame frame = new JFrame("Selector");
		frame.setSize(300, 128);
		frame.setLayout(new GridLayout(4, 2));
		JLabel currentRoomLabel = new JLabel("Current Room");
		frame.add(currentRoomLabel);
		
		JTextField currentRoomText = new JTextField(room.getName());
		currentRoomText.setEditable(false);
		currentRoomText.setEditable(false);
		frame.add(currentRoomText);
		
		
		JLabel playerLabel = new JLabel("Person");
		frame.add(playerLabel);
		
		String playerNames[] = new String[players.size()];
		for (int i = 0; i < playerNames.length; i++) {
			playerNames[i] = players.get(i).getName();
			
		}
		JComboBox personComboBox = new JComboBox(playerNames);
		frame.add(personComboBox);
		
		JLabel weaponLabel = new JLabel("Weapon");
		frame.add(weaponLabel);
		
		String weaponNames[] = new String[weapons.size()];
		for (int i = 0; i < weaponNames.length; i++) {
			weaponNames[i] = weapons.get(i).getName();
			
		}
		JComboBox weaponComboBox = new JComboBox(weaponNames);
		frame.add(weaponComboBox);
		
		
		class SubmitListener implements ActionListener {			
			public void actionPerformed(ActionEvent e) {
				Card selectedPlayerCard = null;
				String playerName = (String) personComboBox.getSelectedItem();
				for (Card playerCard : players) {
					if (playerCard.getName().equals(playerName)) {
						selectedPlayerCard = playerCard;
						break;
					}
				}
				
				Card selectedWeaponCard = null;
				String weaponName = (String) weaponComboBox.getSelectedItem();
				for (Card weaponCard : weapons) {
					if (weaponCard.getName().equals(weaponName)) {
						selectedWeaponCard = weaponCard;
						break;
					}
				}
				
				sol = new Solution(selectedPlayerCard, new Card(room.getName(), CardType.ROOM), selectedWeaponCard);
				
				frame.setVisible(false);
				frame.dispose();
			}
			
		}
		
		class CancelListener implements ActionListener {			
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
			}
			
		}
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new SubmitListener());
		frame.add(submitButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CancelListener());
		frame.add(cancelButton);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		return sol;
	}
}
