package clueGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	private BoardCell prevRoom; // tracks the last room player was in
	
	
	
	
	
	
	/**
	 * Returns the suggestion based on what the computerPlayer has seen and the players location
	 * @return Solution
	 */
	
	public Solution createSuggestion(){
		Solution suggestion = new Solution();
		suggestion.room = "dag";
		suggestion.weapon = "sdg";
		
		Random r = new Random();
		
		// puts room based on current location
		
		suggestion.room = Board.legend.get(Board.getCellAt(getRow(), getColumn()).getInitial());
		// chooses weapon
		ArrayList<Card> unseenWeapons = new ArrayList<Card>();
		for(Card c: Board.getCards()){ // for everycard, adds to array if weapon and not seen
			if(c.getCardType() == CardType.WEAPON && !getSeenCards().contains(c)){
				unseenWeapons.add(c);
			}
		}
		// randomly selects weapon from array of unseen weapons
		int randomIndex = r.nextInt(unseenWeapons.size());
		suggestion.weapon = unseenWeapons.get(randomIndex).getCardName();
		
		
		// chooses person
		ArrayList<Card> unseenPerson = new ArrayList<Card>();
		for(Card c: Board.getCards()){ // for everycard, adds to array if weapon and not seen
			if(c.getCardType() == CardType.PERSON && !getSeenCards().contains(c)){
				unseenPerson.add(c);
			}
		}
		randomIndex = r.nextInt(unseenPerson.size());
		suggestion.person = unseenPerson.get(randomIndex).getCardName();
		
		return suggestion;
	}
	
	// chooses the target location based on a given targets set
	public BoardCell pickLocation(Set<BoardCell> targets){
		BoardCell target = null; 
		// selects target randomly and stores in target variable
		Random r = new Random();
		int targetIndex = r.nextInt(targets.size());
		int indexCheck = 0;
		for(BoardCell bc: targets){
			if(indexCheck == targetIndex){
				target = bc;
				break;
			}
			indexCheck++;
		}
		// overrides previous target selection if set contains a door that hasnt been previously visited
		for(BoardCell b: targets){
			if(b.isDoorway() && b != prevRoom){
				target = b;
			}
		}
		
		return target;
	}
	
	public void makeAccusation(){
		
	}

	public BoardCell getPrevRoom() {
		return prevRoom;
	}

	public void setPrevRoom(BoardCell prevRoom) {
		this.prevRoom = prevRoom;
	}

	//Checks the suggestion to see if the player has any matching cards
	@Override
	public Card disprove(Card card1, Card card2, Card card3) {
		ArrayList<Card> matchingCards = new ArrayList<Card>();
		for(Card c: ownedCards) {
			if(c.equals(card1)) matchingCards.add(c);
			else if(c.equals(card2)) matchingCards.add(c);
			else if(c.equals(card3)) matchingCards.add(c);
		}
		//System.out.println(matchingCards.get(0).getCardName());
		Random r = new Random();
		if(matchingCards.size() == 1) {
			return matchingCards.get(0);
		} else if(matchingCards.size() == 2) {
			return matchingCards.get(r.nextInt(2));
		} else if(matchingCards.size() == 3) {
			return matchingCards.get(r.nextInt(3));
		} else return null;
	}
	
	
}
