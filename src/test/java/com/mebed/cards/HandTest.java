package com.mebed.cards;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import com.mebed.cards.PokerGame.HandCategory;

public class HandTest {


	@Test
    public void testHand() {
		Deck deck = new Deck();
		deck.shuffle();
		
		Hand computerHand = new Hand();
		Hand playerHand = new Hand();
		
		for (int i=0; i < PokerGame.MAX_HAND; i++) {
			playerHand.addCard(deck.dealCard());
		}
		playerHand.orderHand();
		playerHand.showHand();
		if (PokerGame.getCategory(playerHand) == HandCategory.OnePair) {
			System.out.println(HandCategory.OnePair);
		}
		
    }
	
	@Test
	public void testPair() {
		Hand hand = new Hand();
		Card card1 = new Card(1, AbstractCard.Suit.Club);
		hand.addCard(card1);
		Card card2 = new Card(2, AbstractCard.Suit.Club);
		hand.addCard(card2);
		Card card3 = new Card(2, AbstractCard.Suit.Spade);
		hand.addCard(card3);
		Card card4 = new Card(13, AbstractCard.Suit.Heart);
		hand.addCard(card4);
		Card card5 = new Card(10, AbstractCard.Suit.Diamond);
		hand.addCard(card5);
		Card card6 = new Card(8, AbstractCard.Suit.Heart);
		hand.addCard(card6);
		Card card7 = new Card(11, AbstractCard.Suit.Spade);
		hand.addCard(card7);
		
		Assert.assertEquals(HandCategory.OnePair, PokerGame.getCategory(hand, true));
		
		double score = PokerGame.scoreHand(hand);
		Assert.assertEquals(1.02, score);
	}
	
	@Test
	public void testTwoPair() {
		Hand hand = new Hand();
		Card card1 = new Card(1, AbstractCard.Suit.Club);
		hand.addCard(card1);
		Card card2 = new Card(2, AbstractCard.Suit.Club);
		hand.addCard(card2);
		Card card3 = new Card(2, AbstractCard.Suit.Spade);
		hand.addCard(card3);
		Card card4 = new Card(13, AbstractCard.Suit.Heart);
		hand.addCard(card4);
		Card card5 = new Card(1, AbstractCard.Suit.Diamond);
		hand.addCard(card5);
		Card card6 = new Card(8, AbstractCard.Suit.Heart);
		hand.addCard(card6);
		Card card7 = new Card(11, AbstractCard.Suit.Spade);
		hand.addCard(card7);
		
		Assert.assertEquals(HandCategory.TwoPair, PokerGame.getCategory(hand, true));
	}
	
	@Test
	public void testThreeOfAKind() {
		Hand hand = new Hand();
		Card card1 = new Card(1, AbstractCard.Suit.Club);
		hand.addCard(card1);
		Card card2 = new Card(2, AbstractCard.Suit.Club);
		hand.addCard(card2);
		Card card3 = new Card(2, AbstractCard.Suit.Spade);
		hand.addCard(card3);
		Card card4 = new Card(13, AbstractCard.Suit.Heart);
		hand.addCard(card4);
		Card card5 = new Card(2, AbstractCard.Suit.Diamond);
		hand.addCard(card5);
		Card card6 = new Card(8, AbstractCard.Suit.Heart);
		hand.addCard(card6);
		Card card7 = new Card(11, AbstractCard.Suit.Spade);
		hand.addCard(card7);
		
		Assert.assertEquals(HandCategory.ThreeOfAKind, PokerGame.getCategory(hand, true));
	}
	
	@Test
	public void testFullHouse() {
		Hand hand = new Hand();
		Card card1 = new Card(1, AbstractCard.Suit.Club);
		hand.addCard(card1);
		Card card2 = new Card(2, AbstractCard.Suit.Club);
		hand.addCard(card2);
		Card card3 = new Card(2, AbstractCard.Suit.Spade);
		hand.addCard(card3);
		Card card4 = new Card(1, AbstractCard.Suit.Heart);
		hand.addCard(card4);
		Card card5 = new Card(2, AbstractCard.Suit.Diamond);
		hand.addCard(card5);
		Card card6 = new Card(8, AbstractCard.Suit.Heart);
		hand.addCard(card6);
		Card card7 = new Card(3, AbstractCard.Suit.Spade);
		hand.addCard(card7);
		
		Assert.assertEquals(HandCategory.FullHouse, PokerGame.getCategory(hand, true));
	}
	
	@Test
	public void testStraight() {
		Hand hand = new Hand();
		Card card1 = new Card(8, AbstractCard.Suit.Heart);
		hand.addCard(card1);
		Card card2 = new Card(1, AbstractCard.Suit.Club);
		hand.addCard(card2);
		Card card3 = new Card(2, AbstractCard.Suit.Club);
		hand.addCard(card3);
		Card card4 = new Card(3, AbstractCard.Suit.Spade);
		hand.addCard(card4);
		Card card5 = new Card(4, AbstractCard.Suit.Heart);
		hand.addCard(card5);
		Card card6 = new Card(5, AbstractCard.Suit.Diamond);
		hand.addCard(card6);
		Card card7 = new Card(9, AbstractCard.Suit.Heart);
		hand.addCard(card7);

		
		Assert.assertEquals(HandCategory.Straight, PokerGame.getCategory(hand, true));
	}
	
	@Test
	public void testFlush() {
		Hand hand = new Hand();
		Card card1 = new Card(1, AbstractCard.Suit.Club);
		hand.addCard(card1);
		Card card2 = new Card(2, AbstractCard.Suit.Club);
		hand.addCard(card2);
		Card card3 = new Card(3, AbstractCard.Suit.Club);
		hand.addCard(card3);
		Card card4 = new Card(4, AbstractCard.Suit.Club);
		hand.addCard(card4);
		Card card5 = new Card(6, AbstractCard.Suit.Club);
		hand.addCard(card5);
		Card card6 = new Card(8, AbstractCard.Suit.Heart);
		hand.addCard(card6);
		Card card7 = new Card(11, AbstractCard.Suit.Spade);
		hand.addCard(card7);
		
		Assert.assertEquals(HandCategory.Flush, PokerGame.getCategory(hand, true));
	}
	
	@Test
	public void testStraightFlush() {
		Hand hand = new Hand();
		Card card1 = new Card(1, AbstractCard.Suit.Club);
		hand.addCard(card1);
		Card card2 = new Card(2, AbstractCard.Suit.Club);
		hand.addCard(card2);
		Card card3 = new Card(3, AbstractCard.Suit.Club);
		hand.addCard(card3);
		Card card4 = new Card(4, AbstractCard.Suit.Club);
		hand.addCard(card4);
		Card card5 = new Card(11, AbstractCard.Suit.Diamond);
		hand.addCard(card5);
		Card card6 = new Card(8, AbstractCard.Suit.Heart);
		hand.addCard(card6);
		Card card7 = new Card(5, AbstractCard.Suit.Club);
		hand.addCard(card7);
		
		Assert.assertEquals(HandCategory.StraightFlush, PokerGame.getCategory(hand, true));
	}
}
 