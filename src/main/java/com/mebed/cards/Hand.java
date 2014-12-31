package com.mebed.cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand implements Serializable {
	List<Card> cards = new ArrayList<Card>();
	
	public void addCard(Card card) {
		cards.add(card);
	}
	
	public AbstractCard removeCard(AbstractCard card) {
		cards.remove(card);
		return card;
	}
	
	public void showHand() {
		for (AbstractCard card : cards) {
			System.out.println(card);
		}
	}
	
	public void orderHand() {
		Collections.sort(this.cards);
	}
	
	
	public List<Card> getCards() {
		return cards;
	}

	public static Hand orderHand(Hand hand) {
		Hand newHand = null;
		try {
			newHand = (Hand) hand.clone();
		} catch (CloneNotSupportedException ex) {
			// Noop
		}
		Collections.sort(newHand.cards);
		return newHand;
	}

}
