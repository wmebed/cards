package com.mebed.cards;

public class Card implements Comparable {
	enum Suite {
		Club,
		Diamond,
		Heart,
		Spade;
		
		public static Suite getSuiteByOrder(int order) {
			switch (order) {
			case 3: 
				return Spade;
			case 2: 
				return Heart;
			case 1:
				return Diamond;
			case 0:
				return Club;
			default:
				throw new RuntimeException("Invalid suite order");
				
			}
		}
	}

	public static int JACK = 11;
	public static int QUEEN = 12;
	public static int KING = 13;
	public static int ACE_LOW = 1;
	public static int ACE_HIGH = 14;

	Suite suite;
	int value;
	
	public Card(int value, Suite suite) {
		if (value < ACE_LOW || value > ACE_HIGH || suite == null) {
			throw new RuntimeException("Invalid card constructor");
		}
		this.value = value;
		this.suite = suite;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (value != other.value)
			return false;
		return true;
	}

	public Suite getSuite() {
		return suite;
	}

	public void setSuit(Suite suite) {
		this.suite = suite;
	}

	public int getValue() {
		return value;
	}

	public int compareTo(Object object) {
		Card card = null;
		if (object instanceof Card) {
			card = (Card) object;
		} else {
			throw new RuntimeException("Not a card");
		}
	    final int BEFORE = -1;
	    final int EQUAL = 0;
	    final int AFTER = 1;
	    
	    if (this.getValue() > card.getValue()) {
	    	return BEFORE;
	    } else if (this.getValue() < card.getValue()) {
	    	return AFTER;
	    } else {
	    	if (this.getSuite().ordinal() > card.getSuite().ordinal()) {
	    		return BEFORE;
	    	} else if (this.getSuite().ordinal() < card.getSuite().ordinal()) {
	    		return AFTER;
	    	} else {
	    	return EQUAL;
	    	}
	    }
	}
	
	public String toString() {
		String displayValue = null;
		
		switch (this.getValue()) {
			case 1: 
				displayValue = "Ace";
				break;
			case 2: 
			case 3: 
			case 4: 
			case 5: 
			case 6:
			case 7: 
			case 8:
			case 9:
			case 10:
				displayValue = Integer.toString(this.getValue());
				break;
			case 11:
				displayValue = "Jack";
				break;
			case 12:
				displayValue = "Queen";
				break;
			case 13:
				displayValue = "King";
				break;
			case 14:
				displayValue = "Ace";
				break;
		}
		return displayValue + "-" + this.getSuite() + "s";
		
	}


}
