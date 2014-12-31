package com.mebed.cards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.mebed.cards.AbstractCard.Suit;

/**
 * @author wmebed
 *
 */
public class PokerGame {
	
	public static final int MAX_HAND = 7;
	public static final int PLAYER_HAND = 2;
	private static final int MAX_GAMES = 10;
	enum HandCategory {
		HighCard, 
		OnePair,
		TwoPair,
		ThreeOfAKind,
		Straight,
		FullHouse,
		Flush,
		FourOfAKind,
		StraightFlush,
		RoyalFlush
	}

	public static void main(String[] args) throws IOException 
	{
		int score = 0;
		for (int i=0; i < MAX_GAMES; i++) {
			score += playHandVsComputer(false);
		}
		System.out.println("Final score was: " + score);
		
	}

	private static int playHandVsComputer(boolean silent) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
		int winner = 0;
		Deck deck = new Deck();
		deck.shuffle();
		
		Hand computerHand = new Hand();
		Hand playerHand = new Hand();
		Hand riverHand = new Hand();
		
		for (int i=0; i < PLAYER_HAND; i++) {
			computerHand.addCard(deck.dealCard());
			playerHand.addCard(deck.dealCard());
		}
		
		playerHand.showHand();
		
		System.out.println("Please Bet.");
        String response = br.readLine();
		
		for (int i=0; i < 3; i++) {
			Card riverCard = deck.dealCard();
			computerHand.addCard(riverCard);
			playerHand.addCard(riverCard);
			riverHand.addCard(riverCard);
		}
		
		System.out.println();
		System.out.println("Flop:");
		riverHand.showHand();
		System.out.println("Please Bet.");
        response = br.readLine();
		
        // turn
		Card riverCard = deck.dealCard();
		computerHand.addCard(riverCard);
		playerHand.addCard(riverCard);
		riverHand.addCard(riverCard);
		
		System.out.println();
		System.out.println("Turn hand:");
		riverHand.showHand();
		System.out.println("Please Bet.");
        response = br.readLine();
        
        // river
		riverCard = deck.dealCard();
		computerHand.addCard(riverCard);
		playerHand.addCard(riverCard);
		riverHand.addCard(riverCard);
		
		System.out.println();
		System.out.println("River hand:");
		riverHand.showHand();
		System.out.println("Please Bet.");
        response = br.readLine();

		
		double scoreComputer = scoreHand(computerHand);
		double scoreYou = scoreHand(playerHand);
		
		if (!silent) {
			System.out.println("Computer hand is:");
			computerHand.showHand();
				
			System.out.println("\nYour hand is:");
			playerHand.showHand();
			
			System.out.println();
			
			if (scoreYou == 6 || scoreComputer == 6) {
				System.out.println("Flush");
			} else if (scoreYou == 5 || scoreComputer == 5) {
				System.out.println("FullHouse");
			} else if (scoreYou == 4 || scoreComputer == 4) {
				System.out.println("ThreeOfAKind");
			} else if (scoreYou == 7 || scoreComputer == 7) {
				System.out.println("FourOfAKind");
			}
			
			System.out.println("Your score was: " + scoreYou);
			System.out.println("Computer score was: " + scoreComputer);
		}
		
		if (scoreYou > scoreComputer) {
			if (!silent)
				System.out.println("You won!");
			winner = 1;
		} else if (scoreYou < scoreComputer) {
			if (!silent)
				System.out.println("Computer won!");
			winner = -1;
		} 
		
		System.out.println();
		System.out.println();

		return winner;
	}
	
	public static double scoreHand(Hand hand) {
		double score = 0;
		hand.orderHand();
		HandCategory category = getCategory(hand);
		
		if (category == HandCategory.HighCard) {
			score = ((double)getHighCard(hand).value) / 100 + ((double)getHighCard(hand).suit.ordinal()/1000) ;
		} else if (category.ordinal() == 1) {
			List<Card> nonPairedHighCards = getNonPairedCards(hand);
			score =  category.ordinal() + (((double)getPairValue(hand)) / 100 +  
					((double) nonPairedHighCards.get(0).getValue()) / 1000 + ((double) nonPairedHighCards.get(1).getValue())  / 10000 + ((double) nonPairedHighCards.get(2).getValue()) /100000);
		} else if (category.ordinal() == 2) {
			List<Card> nonPairedHighCards = getNonPairedCards(hand);
			score =  category.ordinal() + (((double)getTwoPairValue(hand)) / 100 + ((double) nonPairedHighCards.get(0).getValue()) / 1000);
		} else if (category.ordinal() == 3) {
			List<Card> nonPairedHighCards = getNonPairedCards(hand);
			score =  category.ordinal() + (((double)getThreeOfAKindValue(hand)) / 100 + ((double) nonPairedHighCards.get(0).getValue()) / 1000 + ((double) nonPairedHighCards.get(1).getValue()) / 10000);
		} else {
			score = category.ordinal() + ((double)getHighCard(hand).value) / 100;
		}

		
		return score;
	}

	public static HandCategory getCategory(Hand hand, boolean needsOrder) {
		if (needsOrder) {
			hand.orderHand();
		}
		HandCategory category = null;
		if (hasStraightFlush(hand)) {
			category = HandCategory.StraightFlush;
		} else if (hasFourOfAKind(hand)) {
			category = HandCategory.FourOfAKind;
		} else if (hasFlush(hand)) {
			category = HandCategory.Flush;
		} else if (hasStraight(hand)) {
			category = HandCategory.Straight;
		} else if (hasFullHouse(hand)) {
			category = HandCategory.FullHouse;
		}  else if (hasThreeOfAKind(hand)) {
			category = HandCategory.ThreeOfAKind;
		}  else if (hasTwoPair(hand)) {
			category = HandCategory.TwoPair;
		} else if (hasPair(hand)) {
			category = HandCategory.OnePair;
		} else {
			category = HandCategory.HighCard;
		}
		return category;
	}
	
	public static HandCategory getCategory(Hand hand) {
		return getCategory(hand, false);
	}

	private static boolean hasPair(Hand hand) {
		int oldValue = 0;
		boolean hasPair = false;
		for (AbstractCard card : hand.cards) {
			if (card.value == oldValue) {
				hasPair = true;
			}
			oldValue = card.value;
		}
		if (hasPair) {
			if (hasThreeOfAKind(hand)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	
	private static int getPairValue(Hand hand) {
		int oldValue = 0;
		boolean hasPair = false;
		int pairValue = 0;
		for (AbstractCard card : hand.cards) {
			if (card.value == oldValue) {
				hasPair = true;
				pairValue = card.value;
			}
			oldValue = card.value;
		}
		if (hasPair) {
			if (hasThreeOfAKind(hand)) {
				return 0;
			} else {
				return pairValue;
			}
		}
		return 0;
	}
	
	public static List<Card> getNonPairedCards(Hand hand) {
		Card oldCard = null;
		boolean hasPair = false;
		HashSet<Card> paired = new HashSet<Card>();
		for (Card card : hand.cards) {
			if (oldCard != null && card.value == oldCard.value) {
				hasPair = true;
				paired.add(card);
				paired.add(oldCard);
			}
			oldCard = card;
		}
		Hand nonPairedHand = new Hand();
		if (hasPair) {
			for (Card card : hand.cards) {
				if (!paired.contains(card)) {
					nonPairedHand.addCard(card);
				}
			}
			return nonPairedHand.getCards();
		}
		return null;
	}
	
	
	private static boolean hasTwoPair(Hand hand) {
		int oldValue = 0;
		boolean onePair = false;
		for (AbstractCard card : hand.cards) {
			if (card.value == oldValue) {
				if (onePair)
					return true;
				else
					onePair = true;
			}
			oldValue = card.value;
		}
		return false;
	}
	
	private static double getTwoPairValue(Hand hand) {
		int oldValue = 0;
		boolean onePair = false;
		int highPair = 0;
		int lowPair = 0;
		int[] pairValues = new int[2];
		for (AbstractCard card : hand.cards) {
			if (card.value == oldValue) {
				if (onePair) {
					pairValues[1] = card.value;
					if (pairValues[0] > pairValues[1]) {
						return (HandCategory.TwoPair.ordinal() + (pairValues[0]/100) +  (pairValues[1]/1000));
					} else {
						return (HandCategory.TwoPair.ordinal() + (pairValues[1]/100) +  (pairValues[0]/1000));
					}
				}
				else {
					pairValues[0] = card.value;
					onePair = true;
				}
			}
			oldValue = card.value;
		}
		return 0;
	}
	
	private static boolean hasThreeOfAKind(Hand hand) {
		int oldValue = 0;
		boolean onePair = false;
		int pairValue = 0;
		for (AbstractCard card : hand.cards) {
			if (card.value == pairValue)
				return true;
			else if (card.value == oldValue) {
				onePair = true;
				pairValue = card.value;
			}
			oldValue = card.value;
		}
		return false;
	}
	
	private static int getThreeOfAKindValue(Hand hand) {
		int oldValue = 0;
		boolean onePair = false;
		int pairValue = 0;
		for (AbstractCard card : hand.cards) {
			if (card.value == pairValue)
				return card.value;
			else if (card.value == oldValue) {
				onePair = true;
				pairValue = card.value;
			}
			oldValue = card.value;
		}
		return 0;
	}
	
	private static boolean hasFourOfAKind(Hand hand) {
		int oldValue = 0;
		boolean onePair = false;
		boolean threeOfAKind = false;
		int pairValue = 0;
		for (AbstractCard card : hand.cards) {
			if (card.value == pairValue && threeOfAKind == true)
				return true;
			else if (card.value == pairValue && threeOfAKind == false)
				threeOfAKind = true;
			else if (card.value == oldValue) {
				onePair = true;
				pairValue = card.value;
			}
			oldValue = card.value;
		}
		return false;
	}
	
	private static int getFourOfAKindValue(Hand hand) {
		int oldValue = 0;
		boolean onePair = false;
		boolean threeOfAKind = false;
		int pairValue = 0;
		for (AbstractCard card : hand.cards) {
			if (card.value == pairValue && threeOfAKind == true)
				return card.value;
			else if (card.value == pairValue && threeOfAKind == false)
				threeOfAKind = true;
			else if (card.value == oldValue) {
				onePair = true;
				pairValue = card.value;
			}
			oldValue = card.value;
		}
		return 0;
	}
	
	private static boolean hasFullHouse(Hand hand) {
		if (hasThreeOfAKind(hand)) {
			int threeOfAKindValue = getThreeOfAKindValue(hand);
			Hand newHand = new Hand();
			for (Card card : hand.cards) {
				if (card.value != threeOfAKindValue) {
					newHand.addCard(card);
				}
			}
			if (hasPair(newHand)) {
				return true;
			}

		} 
		return false;
	}
	
	private static boolean hasStraight(Hand hand) {
		hand.orderHand();
		int oldValue = 0;
		int straight = 0;
		for (AbstractCard card : hand.cards) {
			if (oldValue == 0 || card.value == oldValue - 1) {
				straight++;
			} else {
				straight = 0;
			}
			oldValue = card.value;
		}
			if (straight >= 4) {
				return true;
			} else {
				return false;
			}
			
	}
	
	private static boolean hasFlush(Hand hand) {
		int numberSpades = 0;
		int numberDiamonds = 0;
		int numberHearts = 0;
		int numberClubs = 0;
		for (AbstractCard card : hand.cards) {
			if (card.getSuit() == AbstractCard.Suit.Spade) {
				numberSpades++;
			}
			if (card.getSuit() == AbstractCard.Suit.Diamond) {
				numberDiamonds++;
			}
			if (card.getSuit() == AbstractCard.Suit.Heart) {
				numberHearts++;
			}
			if (card.getSuit() == AbstractCard.Suit.Club) {
				numberClubs++;
			}			
		}
		if (numberSpades >= 5 || numberDiamonds >= 5 || numberHearts >= 5 || numberClubs >= 5) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean hasStraightFlush(Hand hand) {
		if (hasStraight(hand) && hasFlush(hand)) {
			return true;
		}
		return false;
	}
	
	private static AbstractCard getHighCard(Hand hand) {
		return hand.cards.get(0);
		
	}
	
	
	/*
	 * Given two hands have the same score, do a tie break.  
	 * This may be easier than computing scores that work in tiebreakers.
	 */
	public static int tieBreakHands(Hand hand1, Hand hand2) {
		return 0;
	}

}
