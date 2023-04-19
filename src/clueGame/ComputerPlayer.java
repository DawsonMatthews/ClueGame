package clueGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, char color, int row, int column) {
		super(name, color, row, column);
	}

	public Solution createSuggestion(Room room, ArrayList<Card> players, ArrayList<Card> weapons) {
		Card playerCard, roomCard, weaponCard;
		Random random = new Random();
		ArrayList<Card> allPlayers = (ArrayList<Card>) players.clone();
		ArrayList<Card> allWeapons = (ArrayList<Card>) weapons.clone();
		
		Set<Card> seenCards = getSeenCards();
		
		System.out.println(seenCards.size());
		
		for (int i = 0; i < allPlayers.size(); i++) {
			Card pc = allPlayers.get(i);
			Iterator<Card> value = seenCards.iterator();
			while (value.hasNext()) {
				Card nextCard = value.next();
				if (nextCard.equals(pc)) {
					players.remove(pc);
					break;
				}
			}
		}
		
		int numPlayers = players.size();
		if (numPlayers > 0) {
			int randInt = random.nextInt(numPlayers);
			playerCard = players.get(randInt);
		}
		else {
			int randInt = random.nextInt(6);
			playerCard = allPlayers.get(randInt);
		}
		
		for (int i = 0; i < allWeapons.size(); i++) {
			Card wc = allWeapons.get(i);
			Iterator<Card> value = seenCards.iterator();
			while (value.hasNext()) {
				Card nextCard = value.next();
				if (nextCard.equals(wc)) {
					weapons.remove(wc);
					break;
				}
			}
		}
		
		int numWeapons = weapons.size();
		if (numWeapons > 0) {
			int randInt = random.nextInt(numWeapons);
			weaponCard = weapons.get(randInt);
		}
		else {
			int randInt = random.nextInt(6);
			weaponCard = allWeapons.get(randInt);
		}
		
		// Evil ahead
		/*  |
		 * -+-
		 *  |
		 *  |
		 */
		roomCard = new Card(room.getName(), CardType.ROOM);
		
		return new Solution(playerCard, roomCard, weaponCard);
	}
	
	public BoardCell selectTarget(Set<BoardCell> targets) {
		ArrayList<BoardCell> rooms = new ArrayList<BoardCell>();
		Random random = new Random();
		
		Set<Card> seenCards = getSeenCards();
		Iterator<BoardCell> value = targets.iterator();
		while (value.hasNext()) {
			BoardCell nextCell = value.next();
			if (nextCell.isRoom()) {
				rooms.add(nextCell);
			}
		}
		
		for (BoardCell room : rooms) {
			Iterator<Card> seenIterator = seenCards.iterator();
			boolean roomInSeen = false;
			while (seenIterator.hasNext()) {
				Card seenCard = seenIterator.next();
				if (seenCard.getName().equals(room.getRoomCard())) {
					roomInSeen = true;
					break;
				}
			}
			
			if (roomInSeen == false) {
				return room;
			}
		}
		
		BoardCell[] targetsArray = targets.toArray(new BoardCell[targets.size()]);
		int randInt = random.nextInt(targetsArray.length);
		return targetsArray[randInt];
	}

	public Room getRoom() {
		return null;
	}
}
