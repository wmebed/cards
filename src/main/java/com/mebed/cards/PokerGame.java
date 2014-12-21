package com.mebed.cards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

import com.mebed.cards.Card.Suite;

/**
 * @author wmebed
 *
 */
public class PokerGame {
	
	public static final int MAX_HAND = 7;
	public static final int PLAYER_HAND = 2;
	private static final int MAX_GAMES = 1000;
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
		
//		System.out.println("Please Bet.");
//        String response = br.readLine();
		
		for (int i=0; i < MAX_HAND - PLAYER_HAND; i++) {
			Card riverCard = deck.dealCard();
			computerHand.addCard(riverCard);
			playerHand.addCard(riverCard);
			riverHand.addCard(riverCard);
		}
		
		riverHand.showHand();
		
		
//		System.out.println("Please Bet.");
//		response = response = br.readLine();
		
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
			score = ((double)getHighCard(hand).value) / 100 + ((double)getHighCard(hand).suite.ordinal()/1000) ;
		} else if (category.ordinal() == 1) {
			score =  category.ordinal() + (((double)getPairValue(hand)) / 100);
		} else if (category.ordinal() == 2) {
			score =  category.ordinal() + (((double)getTwoPairValue(hand)) / 100);
		} else if (category.ordinal() == 3) {
			score =  category.ordinal() + (((double)getThreeOfAKindValue(hand)) / 100);
		} else {
			score = category.ordinal();
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
	
	private static double getTwoPairValue(Hand hand) {
		int oldValue = 0;
		boolean onePair = false;
		int highPair = 0;
		int lowPair = 0;
		int[] pairValues = new int[2];
		for (Card card : hand.cards) {
			if (card.value == oldValue) {
				if (onePair) {
					pairValues[1] = card.value;
					if (pairValues[0] > pairValues[1]) {
						return (HandCategory.TwoPair.ordinal() + (pairValues[0]/10) +  (pairValues[1]/100));
					} else {
						return (HandCategory.TwoPair.ordinal() + (pairValues[1]/10) +  (pairValues[0]/100));
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
		hand.orderHand();
		int oldValue = 0;
		int straight = 0;
		for (Card card : hand.cards) {
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
		for (Card card : hand.cards) {
			if (card.getSuite() == Card.Suite.Spade) {
				numberSpades++;
			}
			if (card.getSuite() == Card.Suite.Diamond) {
				numberDiamonds++;
			}
			if (card.getSuite() == Card.Suite.Heart) {
				numberHearts++;
			}
			if (card.getSuite() == Card.Suite.Club) {
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
