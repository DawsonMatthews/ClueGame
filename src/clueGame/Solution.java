package clueGame;

public class Solution {
	private Card person;
	private Card room;
	private Card weapon;

	public Solution(Card person, Card room, Card weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	public boolean equals(Solution target) {
		return person.equals(target.person) && room.equals(target.room) && weapon.equals(target.weapon);
	}
	
	/*
	 * TEST FUNCTION
	 */
	public Card getPersonCard() {
		return person;
	}
	
	public Card getRoomCard() {
		return room;
	}
	
	public Card getWeaponCard() {
		return weapon;
	}

}
