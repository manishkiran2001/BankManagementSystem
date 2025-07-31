package com.bank.DTO;

public class CustomerStatement {

	private String transactionType;
	private int transactionAmount;
	private int balanceAmount;
	private Long customerAccNum;
	private String transactionDateAndTime;

	public CustomerStatement() {
	}

	public CustomerStatement(String transactionType, int transactionAmount, int balanceAmount, Long customerAccNum,
			String transactionDateAndTime) {
		this.transactionType = transactionType;
		this.transactionAmount = transactionAmount;
		this.balanceAmount = balanceAmount;
		this.customerAccNum = customerAccNum;
		this.transactionDateAndTime = transactionDateAndTime;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public int getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(int transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public int getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(int balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Long getCustomerAccNum() {
		return customerAccNum;
	}

	public void setCustomerAccNum(Long customerAccNum) {
		this.customerAccNum = customerAccNum;
	}

	public String getTransactionDateAndTime() {
		return transactionDateAndTime;
	}

	public void setTransactionDateAndTime(String transactionDateAndTime) {
		this.transactionDateAndTime = transactionDateAndTime;
	}

	
	@Override
	public String toString() {
		return "CustomerStatement [transactionType=" + transactionType + ", transactionAmount=" + transactionAmount
				+ ", balanceAmount=" + balanceAmount + ", customerAccNum=" + customerAccNum
				+ ", transactionDateAndTime=" + transactionDateAndTime + "]";
	}
}
