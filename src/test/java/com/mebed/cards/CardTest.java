package com.mebed.cards;

import static org.junit.Assert.*;

import org.junit.Test;

public class CardTest {

	@Test
    public void testCard() {
    	AbstractCard card = new Card(1, AbstractCard.Suite.Club);
    	System.out.println(card);
    	card = new Card(AbstractCard.KING, AbstractCard.Suite.Heart);
    	System.out.println(card);
    }
}
