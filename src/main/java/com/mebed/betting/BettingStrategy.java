package com.mebed.betting;

import com.mebed.cards.Hand;
import com.mebed.cards.PokerGame;

public class BettingStrategy {

	public static Strategy getStrategy(Hand hand, Account account, Account pot) {
		Strategy strategy = new Strategy();
		HandCategory category = PokerGame.getCategory(hand);
		if (category.ordinal() > 1) {
			strategy.setBet(5);
			strategy.setHandStatus(HandStatus.raise);
		} else if (hand.getCards().size() == 2) {
			strategy.setHandStatus(HandStatus.call);
		} else if (hand.getCards().size()  > 6) {
			strategy.setHandStatus(HandStatus.fold);
		} else {
			strategy.setHandStatus(HandStatus.call);
		}
		return strategy;
	}
}
