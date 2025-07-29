package com.bank.DTO;

public class CustomerStatement {

	private String transaction_Type;
	private int transaction_Ammount;
	private int balance_Ammount;
	private Long customer_AccNum;
	private String transaction_Date_And_Time;

	public CustomerStatement() {
	}

	public CustomerStatement(String transaction_Type, int transaction_Ammount, int balance_Ammount,
			Long customer_AccNum, String transaction_Date_And_Time) {
		this.transaction_Type = transaction_Type;
		this.transaction_Ammount = transaction_Ammount;
		this.balance_Ammount = balance_Ammount;
		this.customer_AccNum = customer_AccNum;
		this.transaction_Date_And_Time = transaction_Date_And_Time;
	}

	public String getTransaction_Type() {
		return transaction_Type;
	}

	public void setTransaction_Type(String transaction_Type) {
		this.transaction_Type = transaction_Type;
	}

	public int getTransaction_Ammount() {
		return transaction_Ammount;
	}

	public void setTransaction_Ammount(int transaction_Ammount) {
		this.transaction_Ammount = transaction_Ammount;
	}

	public int getBalance_Ammount() {
		return balance_Ammount;
	}

	public void setBalance_Ammount(int balance_Ammount) {
		this.balance_Ammount = balance_Ammount;
	}

	public Long getCustomer_AccNum() {
		return customer_AccNum;
	}

	public void setCustomer_AccNum(Long customer_AccNum) {
		this.customer_AccNum = customer_AccNum;
	}

	public String getTransaction_Date_And_Time() {
		return transaction_Date_And_Time;
	}

	public void setTransaction_Date_And_Time(String transaction_Date_And_Time) {
		this.transaction_Date_And_Time = transaction_Date_And_Time;
	}

	@Override
	public String toString() {
		return "CustomerStatement [transaction_Type=" + transaction_Type + ", transaction_Ammount="
				+ transaction_Ammount + ", balance_Ammount=" + balance_Ammount + ", customer_AccNum=" + customer_AccNum
				+ ", transaction_Date_And_Time=" + transaction_Date_And_Time + "]";
	}

}
