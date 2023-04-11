package clueGame;

import java.awt.BorderLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class KnownCardsPanel extends JPanel {
	
	private JPanel peoplePanel;
	private JPanel roomPanel;
	private JPanel weaponPanel;
	
	private Map<Card, Color> cardColorMap = new HashMap<Card, Color>();
	private Set<Card> seenCards = new HashSet<Card>();
	private ArrayList<Card> hand = new ArrayList<Card>();
	private static Card confCard, rebuggerCard, inductorCard, eigenCard, binomialCard, normalizerCard, joelCard, markCard, dejunCard, terryCard, liamCard, kathleenCard, physicsCard;

	
	public KnownCardsPanel() {
		setLayout(new GridLayout(3, 0));
		setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
		
		confCard = new Card("95% Confidence Interval", CardType.WEAPON);
		rebuggerCard = new Card("The Rebugger", CardType.WEAPON);
		inductorCard = new Card("The Inductor", CardType.WEAPON);
		eigenCard = new Card("Eigen Knife", CardType.WEAPON);
		binomialCard = new Card("Binomial Distributor", CardType.WEAPON);
		normalizerCard = new Card("The Normalizer", CardType.WEAPON);

		joelCard = new Card("Joel", CardType.PERSON);
		markCard = new Card("Mark", CardType.PERSON);
		dejunCard = new Card("Dejun", CardType.PERSON);
		terryCard = new Card("Terry", CardType.PERSON);
		liamCard = new Card("Liam", CardType.PERSON);
		kathleenCard = new Card("Kathleen", CardType.PERSON);
		
		physicsCard = new Card("Physics", CardType.ROOM);
		
		hand.add(confCard);
		hand.add(dejunCard);
		hand.add(rebuggerCard);
		hand.add(joelCard);
		hand.add(markCard);
		seenCards.add(terryCard);
		seenCards.add(physicsCard);
		seenCards.add(normalizerCard);
		seenCards.add(physicsCard);
		seenCards.add(eigenCard);


		peoplePanel = makePanel("People", CardType.PERSON);
		roomPanel = makePanel("Rooms", CardType.ROOM);
		weaponPanel = makePanel("Weapons", CardType.WEAPON);
		
		add(peoplePanel);
		add(roomPanel);
		add(weaponPanel);
	}
	
	private JPanel makePanel(String name, CardType type) {
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(128, 576));
		panel.setLayout(new GridLayout(2, 1));
		panel.setSize(1, 2);
		panel.setBorder(new TitledBorder (new EtchedBorder(), name));
		JPanel handPanel = new JPanel();
		handPanel.setLayout(new GridLayout(0, 1));
		handPanel.setSize(1, 2);
		JLabel handLabel = new JLabel("In Hand:");
		handPanel.add(handLabel, BorderLayout.NORTH);
		for (Card card : hand) {
			if (card.getType() == type) {
				JTextField cardText = new JTextField(card.getName());
				cardText.setEditable(false);
				handPanel.add(cardText, BorderLayout.SOUTH);
			}
		}
		panel.add(handPanel, BorderLayout.NORTH);
		JPanel seenPanel = new JPanel();
		seenPanel.setLayout(new GridLayout(0, 1));
		seenPanel.setSize(1, 2);
		JLabel seenLabel = new JLabel("Seen:");
		seenPanel.add(seenLabel, BorderLayout.NORTH);

		Iterator<Card> value = seenCards.iterator();
		while (value.hasNext()) {
			Card nextCard = value.next();
			if (nextCard.getType() == type) {
				JTextField cardText = new JTextField(nextCard.getName());
				cardText.setEditable(false);
				cardText.setBackground(cardColorMap.get(nextCard));
				seenPanel.add(cardText, BorderLayout.SOUTH);
			}
		}
		panel.add(seenPanel, BorderLayout.SOUTH);
		add(panel, BorderLayout.NORTH);
		
		return panel;
	}
	
	public void updateSeen(Card card, Color color) {
		seenCards.add(card);
		cardColorMap.put(card, color);
		removeAll();
		peoplePanel = makePanel("People", CardType.PERSON);
		roomPanel = makePanel("Rooms", CardType.ROOM);
		weaponPanel = makePanel("Weapons", CardType.WEAPON);
		
		add(peoplePanel);
		add(roomPanel);
		add(weaponPanel);
		
		validate();
		repaint();
	}
	
	public static void main(String[] args) {
		KnownCardsPanel panel = new KnownCardsPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 24 * 24);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
				
		// test filling in the data
		panel.updateSeen(kathleenCard, Color.GREEN);
		panel.updateSeen(inductorCard, Color.RED);
	}
	
}
