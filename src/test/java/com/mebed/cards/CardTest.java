package com.mebed.cards;

import static org.junit.Assert.*;

import org.junit.Test;

public class CardTest {

	@Test
    public void testCard() {
    	AbstractCard card = new Card(1, AbstractCard.Suit.Club);
    	System.out.println(card);
    	card = new Card(AbstractCard.KING, AbstractCard.Suit.Heart);
    	System.out.println(card);
    }
}
