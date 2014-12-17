package com.mebed.cards;

import static org.junit.Assert.*;

import org.junit.Test;

public class CardTest {

	@Test
    public void testCard() {
    	Card card = new Card(1, Card.Suite.Club);
    	System.out.println(card);
    	card = new Card(Card.KING, Card.Suite.Heart);
    	System.out.println(card);
    }
}
