package com.mebed.cards;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Deck {
	private static final int MAX_CARDS = 14;
	private static final int MAX_SUITES = 4;
	
	List<Card> cards = new ArrayList<Card>();
	public Deck() {
		for (int i = 2; i <= MAX_CARDS; i++) {
			for (int j = 0; j < MAX_SUITES; j++) {
				cards.add(new Card(i, AbstractCard.Suite.getSuiteByOrder(j)));
			}
		}
	}
	
	public Card dealCard() {
		if (cards.size() == 0) {
			return null;
		}
		Card card = cards.get(0);
		cards.remove(0);
		return card;
	}
	
	public boolean isEmpty() {
		return cards.size() == 0;
	}
	
	public void shuffle() {
		long seed = System.nanoTime();
		Collections.shuffle(cards, new Random(seed));
	}

}
