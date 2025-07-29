package com.bank.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bank.DTO.CustomerStatement;
import com.bank.steps.DatabaseConnection;

public class CustomerStatementDAO {

	private final static String insertCustomerStatement = "insert into customer_statement(Transaction_Type, Transaction_Ammount, Balance_Ammount, Customer_AccNum, Transaction_Date_And_Time) values(?,?,?,?,?)";
	private final static String getCustomerStatement = "select * from customer_statement where Customer_AccNum=?";

	// insert Account Statement
	public void insertCustomerStatement(String transactionType, int transactionAmmount, int balanceAmmount,
			long customerAccNum, String transactionDateAndTime) {
		try {
			Connection connect = DatabaseConnection.jdbcSteps();
			PreparedStatement ps = connect.prepareStatement(insertCustomerStatement);
			ps.setString(1, transactionType);
			ps.setInt(2, transactionAmmount);
			ps.setInt(3, balanceAmmount);
			ps.setLong(4, customerAccNum);
			ps.setString(5, transactionDateAndTime);
			ps.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Get Statement By AccNum
	public ArrayList<CustomerStatement> getStatementByAccNum(long accNum) {
		try {
			Connection connect = DatabaseConnection.jdbcSteps();
			PreparedStatement ps = connect.prepareStatement(getCustomerStatement);
			ps.setLong(1, accNum);
			ResultSet rs = ps.executeQuery();
			ArrayList<CustomerStatement> customerTransactions = new ArrayList<>();
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					CustomerStatement cs = new CustomerStatement();
					cs.setTransaction_Type(rs.getString("Transaction_Type"));
					cs.setTransaction_Ammount(rs.getInt("Transaction_Ammount"));
					cs.setBalance_Ammount(rs.getInt("Balance_Ammount"));
					cs.setCustomer_AccNum(rs.getLong("Customer_AccNum"));
					cs.setTransaction_Date_And_Time(rs.getString("Transaction_Date_And_Time"));
					customerTransactions.add(cs);
				}
				return customerTransactions;
			}
			return null;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
