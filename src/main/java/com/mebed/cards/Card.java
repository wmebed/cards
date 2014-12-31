package com.mebed.cards;

public class Card extends AbstractCard implements Comparable {
	public Card(int value, Suit suite) {
		if (value < ACE_LOW || value > ACE_HIGH || suite == null) {
			throw new RuntimeException("Invalid card constructor");
		}
		this.value = value;
		this.suit = suite;
	}

	@Override
	public String getImageName() {
		return  this.suit.toString().toLowerCase() + String.valueOf(this.value) + ".png";
	}

	@Override
	public void setImageName(String imageName) {
		// TODO Auto-generated method stub
		
	}


}
