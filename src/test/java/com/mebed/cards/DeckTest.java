package com.mebed.cards;

import static org.junit.Assert.*;

import org.junit.Test;

public class DeckTest {

	@Test
    public void testDeck() {
    	Deck deck = new Deck();
//    	deck.shuffle();
    	do {
    		AbstractCard card = deck.dealCard();
    		System.out.println(card);
    	} while (!deck.isEmpty());
    }
}
 