package com.mebed.betting;

import java.io.Serializable;

public class Account implements Serializable {
	double balance;
	
	public Account(double balance) {
		this.balance = balance;
	}
	
	public double withdraw(double amount) {
		balance -= amount;
		return balance;
	}
	
	public double deposit(double amount) {
		balance += amount;
		return balance;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
