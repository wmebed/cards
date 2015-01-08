package com.mebed.betting;

import java.util.Collections;
import java.util.Random;

import com.mebed.cards.Hand;
import com.mebed.cards.PokerGame;

public class BettingStrategy {

	public static Strategy getStrategy(HandStatus handStatus, Hand hand, Account account, Account playerAccount, Account pot, double opponentBet) {
		Strategy strategy = new Strategy();
		long seed = System.nanoTime();
		HandCategory category = PokerGame.getCategory(hand);
		if (opponentBet > 9 && handStatus != HandStatus.call) {
			if (new Random(seed).nextInt() % 2 == 0) {
				strategy.setHandStatus(HandStatus.fold);
			} else {
				strategy.setHandStatus(HandStatus.call);
				setCallBet(account, strategy, opponentBet);
			}
		} else if (opponentBet == 0) {
			if (new Random(seed).nextInt() % 3 > 1) {
				strategy.setHandStatus(HandStatus.call);
				// Don't set bet when opponenet is calling
			} else if (handStatus != HandStatus.call) {
				strategy.setHandStatus(HandStatus.raise);
				setRaiseBet(account, playerAccount, strategy, seed);
			} else {
				strategy.setHandStatus(HandStatus.call);
				setCallBet(account, strategy, opponentBet);
			}
		} else {
			if (category.ordinal() > 1 && handStatus != HandStatus.call) {
				int amount = Math.abs(new Random(seed).nextInt() % 50);
				setRaiseBet(account, playerAccount, strategy, seed);
				strategy.setHandStatus(HandStatus.raise);
			} else if (hand.getCards().size() == 2 && handStatus != HandStatus.call) {
				strategy.setHandStatus(HandStatus.call);
				setCallBet(account, strategy, opponentBet);
			} else if (hand.getCards().size()  > 5 && handStatus == HandStatus.raise) {
				if (new Random(seed).nextInt() % 2 == 0) {
					strategy.setHandStatus(HandStatus.fold);
				} else {
					strategy.setHandStatus(HandStatus.call);
					setCallBet(account, strategy, opponentBet);
				}
			} else if (handStatus == HandStatus.call) {
				strategy.setHandStatus(HandStatus.call);
				// Don't set bet when opponenet calls
			} else {
				strategy.setHandStatus(HandStatus.call);
				setCallBet(account, strategy, opponentBet);
			}
		}
		return strategy;
	}

	private static void setRaiseBet(Account account, Account playerAccount, Strategy strategy,
			long seed) {
		double amount = Math.abs(new Random(seed).nextInt() % 50);
		if (amount > playerAccount.getBalance()) {
			amount = playerAccount.getBalance();
		} 
		if (amount > account.getBalance()) {
			amount = account.getBalance();
		} 
		strategy.setBet(amount);
	}
	
	private static void setCallBet(Account account, Strategy strategy, Double opponentBet) {
		Double amount = null;
		if (opponentBet > account.getBalance()) {
			amount = account.getBalance();
		} else {
			amount = opponentBet;
		}
		strategy.setBet(amount);
	}
	

}
