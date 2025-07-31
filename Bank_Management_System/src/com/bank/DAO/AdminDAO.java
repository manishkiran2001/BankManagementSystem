package com.bank.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bank.service.AdminServiceLayer;
import com.bank.steps.DatabaseConnection;

public class AdminDAO {
	private static final String adminLogin = "select * from admindetails where AdminEmail=? and AdminPassword=?;";
	private static final String selection = "select *from customer_details";
	private static final String selectByStatus = "select * from customer_details where Customer_Status=?";
	private static final String checkValidId = "select * from customer_details where Customer_Id=?";
	private static final String isUniqueAccNum = "select * from customer_details where Customer_Account_Number=?";
	private static final String changeAccStatus = "update customer_details set Customer_Status='active',Customer_Account_Number=?,Customer_Pin=? where Customer_Id=? and Customer_Status='pending'";
	private static final String accClosingRequsts = "select * from customer_details where Customer_Status='close'";
	private static final String closeAccById = "update customer_details set Customer_Status='closed' where Customer_Id=?";
	private static final String closeAllAcc="update customer_details set Customer_Status='closed' where Customer_Status='close'";
	CustomerDAO customerDao = new CustomerDAO();

	public boolean selectAdmindetailsByUsingEmailAndPassword(String adminEmail, String adminPassword) {
		try {
			Connection connect = DatabaseConnection.jdbcSteps();
			PreparedStatement ps = connect.prepareStatement(adminLogin);
			ps.setString(1, adminEmail);
			ps.setString(2, adminPassword);
			ResultSet rs = ps.executeQuery();
			if (rs.isBeforeFirst()) {
				return true;
			}
			return false;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Display all customer details
	public ResultSet displayAllCustomerDetails() {
		try {
			Connection connect = DatabaseConnection.jdbcSteps();
			PreparedStatement ps = connect.prepareStatement(selection);
			ResultSet rs = ps.executeQuery();
			if (rs.isBeforeFirst()) {
				return rs;
			}
			return null;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Display pending request details
	public ResultSet displayCustomerDetailsByStatus(String status) {
		try {
			Connection connect = DatabaseConnection.jdbcSteps();
			PreparedStatement ps = connect.prepareStatement(selectByStatus);
			ps.setString(1, status);
			ResultSet rs = ps.executeQuery();
			if (rs.isBeforeFirst()) {
				return rs;
			}
			return null;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// checking if there are any pending requests
	public boolean getCustomerByStatus(String status) {
		try {
			Connection connect = DatabaseConnection.jdbcSteps();
			PreparedStatement ps = connect.prepareStatement(selectByStatus);
			ps.setString(1, status);
			ResultSet rs = ps.executeQuery();
			return rs.isBeforeFirst();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Checking Id is present in the Database before Activating
	public boolean isCustomerIdValid(int id) {
		try {
			Connection connect = DatabaseConnection.jdbcSteps();
			PreparedStatement ps = connect.prepareStatement(checkValidId);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			return rs.isBeforeFirst();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Activate Account Using Id
	public boolean activateAccountAndAssignAccNumberAndPin(int customerId, long accountNumber, int pin) {
		try (Connection con = DatabaseConnection.jdbcSteps();
				PreparedStatement ps = con.prepareStatement(changeAccStatus)) {
			ps.setLong(1, accountNumber);
			ps.setInt(2, pin);
			ps.setInt(3, customerId);
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Activate All Accounts
	public void activateAllAccounts(String status) {
		try {
			Connection connect = DatabaseConnection.jdbcSteps();
			PreparedStatement ps = connect.prepareStatement(selectByStatus);
			ps.setString(1, status);
			ResultSet rs = ps.executeQuery();
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					long accountNumber;
					int pin;
					do {
						accountNumber = AdminServiceLayer.generateAccountNumber();
					} while (isAccountNumberExists(accountNumber));
					pin = AdminServiceLayer.generatePin();
					int id = rs.getInt("Customer_Id");
					activateAccountAndAssignAccNumberAndPin(id, accountNumber, pin);
				}
				System.out.println("All Accounts Activated Successfully");
			} else {
				System.err.println("No Pending Requests");
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println("Error While Activating Accounts" + e.getMessage());
			e.printStackTrace();
		}
	}

	// Duplicate Account Number Check
	public boolean isAccountNumberExists(long accountNumber) {
		try (Connection con = DatabaseConnection.jdbcSteps();
				PreparedStatement ps = con.prepareStatement(isUniqueAccNum)) {
			ps.setLong(1, accountNumber);
			ResultSet rs = ps.executeQuery();
			return rs.isBeforeFirst();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Get All Closing Requests
	public ResultSet getClosingAccountReqests() {
		try {
			Connection connect = DatabaseConnection.jdbcSteps();
			PreparedStatement ps = connect.prepareStatement(accClosingRequsts);
			ResultSet rs = ps.executeQuery();
			if (rs.isBeforeFirst()) {
				return rs;
			}
			return null;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// close account by id
	public boolean closeCustomerAccById(int customerId) {
		try (Connection con = DatabaseConnection.jdbcSteps();
				PreparedStatement ps = con.prepareStatement(closeAccById)) {
			ps.setInt(1, customerId);
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//close all Accounts
	public boolean closeAllCustomerAcc() {
		try (Connection con = DatabaseConnection.jdbcSteps();
				PreparedStatement ps = con.prepareStatement(closeAllAcc)) {
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
}
