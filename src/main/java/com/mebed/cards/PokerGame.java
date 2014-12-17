package com.mebed.cards;

import com.mebed.cards.Card.Suite;

/**
 * @author wmebed
 *
 */
public class PokerGame {
	
	public static final int MAX_HAND = 5;
	private static final int MAX_GAMES = 1;
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

	public static void main(String[] args) {
		int score = 0;
		for (int i=0; i < MAX_GAMES; i++) {
			score += playHandVsComputer(false);
		}
		System.out.println("Final score was: " + score);
		
	}

	private static int playHandVsComputer(boolean silent) {
		int winner = 0;
		Deck deck = new Deck();
		deck.shuffle();
		
		Hand computerHand = new Hand();
		Hand playerHand = new Hand();
		
		for (int i=0; i < MAX_HAND; i++) {
			computerHand.addCard(deck.dealCard());
			playerHand.addCard(deck.dealCard());
		}
		
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
		

		return winner;
	}
	
	public static double scoreHand(Hand hand) {
		double score = 0;
		hand.orderHand();
		HandCategory category = getCategory(hand);
		
		if (category == HandCategory.HighCard) {
			score = ((double)getHighCard(hand).value) / 100 + ((double)getHighCard(hand).suite.ordinal()/1000) ;
		} else if (category.ordinal() == 1) {
			score =  category.ordinal() + (((double)getPairValue(hand)) / 100);
		} else if (category.ordinal() == 3) {
			score =  category.ordinal() + (((double)getThreeOfAKindValue(hand)) / 100);
		} else {
			score = category.ordinal();
		}

		
		return score;
	}

	public static HandCategory getCategory(Hand hand) {
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

	private static boolean hasPair(Hand hand) {
		int oldValue = 0;
		boolean hasPair = false;
		for (Card card : hand.cards) {
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
		for (Card card : hand.cards) {
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
	
	private static boolean hasTwoPair(Hand hand) {
		int oldValue = 0;
		boolean onePair = false;
		for (Card card : hand.cards) {
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
	
	private static boolean hasThreeOfAKind(Hand hand) {
		int oldValue = 0;
		boolean onePair = false;
		int pairValue = 0;
		for (Card card : hand.cards) {
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
		for (Card card : hand.cards) {
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
		for (Card card : hand.cards) {
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
		for (Card card : hand.cards) {
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
		int oldValue = 0;
		for (Card card : hand.cards) {
			if (oldValue != 0 && card.value != oldValue - 1) {
				return false;
			}
			oldValue = card.value;
		}
		return true;
	}
	
	private static boolean hasFlush(Hand hand) {
		Suite oldSuite = null;
		for (Card card : hand.cards) {
			if (oldSuite != null && card.suite != oldSuite) {
				return false;
			}
			oldSuite = card.suite;
		}
		return true;
	}
	
	private static boolean hasStraightFlush(Hand hand) {
		if (hasStraight(hand) && hasFlush(hand)) {
			return true;
		}
		return false;
	}
	
	private static Card getHighCard(Hand hand) {
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
