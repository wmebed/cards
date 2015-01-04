package com.mebed.betting;

import java.util.Collections;
import java.util.Random;

import com.mebed.cards.Hand;
import com.mebed.cards.PokerGame;

public class BettingStrategy {

	public static Strategy getStrategy(HandStatus handStatus, Hand hand, Account account, Account pot, double opponentBet) {
		Strategy strategy = new Strategy();
		long seed = System.nanoTime();
		HandCategory category = PokerGame.getCategory(hand);
		if (opponentBet > 9 && handStatus != HandStatus.call) {
			if (new Random(seed).nextInt() % 2 == 0) {
				strategy.setHandStatus(HandStatus.fold);
			} else {
				strategy.setHandStatus(HandStatus.call);
				strategy.setBet(opponentBet);
			}
		} else if (opponentBet == 0) {
			if (new Random(seed).nextInt() % 2 == 0) {
				strategy.setHandStatus(HandStatus.call);
				strategy.setBet(opponentBet);
			} else if (handStatus != HandStatus.call) {
				strategy.setHandStatus(HandStatus.raise);
				double amount = Math.abs(new Random(seed).nextInt() % 50);
				if (amount > account.getBalance()) {
					amount = account.getBalance();
				}
				strategy.setBet(amount + 5);
			} else {
				strategy.setHandStatus(HandStatus.call);
				strategy.setBet(opponentBet);
			}
		} else {
			if (category.ordinal() > 1 && handStatus != HandStatus.call) {
				int amount = Math.abs(new Random(seed).nextInt() % 50);
				strategy.setBet(amount + 5);
				strategy.setHandStatus(HandStatus.raise);
			} else if (hand.getCards().size() == 2) {
				strategy.setHandStatus(HandStatus.call);
				strategy.setBet(opponentBet);
			} else if (hand.getCards().size()  > 6 && handStatus == HandStatus.raise) {
				if (new Random(seed).nextInt() % 2 == 0) {
					strategy.setHandStatus(HandStatus.fold);
				} else {
					strategy.setHandStatus(HandStatus.call);
					strategy.setBet(opponentBet);
				}
			} else {
				strategy.setHandStatus(HandStatus.call);
				strategy.setBet(opponentBet);
			}
		}
		return strategy;
	}
}
