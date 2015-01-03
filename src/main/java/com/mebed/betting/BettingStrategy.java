package com.mebed.betting;

import java.util.Collections;
import java.util.Random;

import com.mebed.cards.Hand;
import com.mebed.cards.PokerGame;

public class BettingStrategy {

	public static Strategy getStrategy(Hand hand, Account account, Account pot, double opponentBet) {
		Strategy strategy = new Strategy();
		long seed = System.nanoTime();
		HandCategory category = PokerGame.getCategory(hand);
		if (opponentBet > 9) {
			if (new Random(seed).nextInt() % 2 == 0) {
				strategy.setHandStatus(HandStatus.fold);
			} else {
				strategy.setHandStatus(HandStatus.call);
			}
		} else {
			if (category.ordinal() > 1) {
				strategy.setBet(5);
				strategy.setHandStatus(HandStatus.raise);
			} else if (hand.getCards().size() == 2) {
				strategy.setHandStatus(HandStatus.call);
			} else if (hand.getCards().size()  > 6) {
				if (new Random(seed).nextInt() % 2 == 0) {
					strategy.setHandStatus(HandStatus.fold);
				} else {
					strategy.setHandStatus(HandStatus.call);
				}
			} else {
				strategy.setHandStatus(HandStatus.call);
			}
		}
		return strategy;
	}
}
