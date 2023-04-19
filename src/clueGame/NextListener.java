package clueGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class NextListener implements ActionListener {
			
	ClueFrame clueFrame = ClueFrame.getInstance();
	
	public void actionPerformed(ActionEvent e) {
		clueFrame.nextButtonPressed();
	}
	
}