package com.bank.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bank.DTO.CustomerDetails;
import com.bank.steps.DatabaseConnection;

public class CustomerDAO {

	private final static String insert = "insert into customer_details(Customer_Name, Customer_Email, "
			+ "Customer_MobileNum, Customer_Aadhar_Number, Customer_Address, Customer_Gender,"
			+ "Customer_Amount,Customer_Status) values (?,?,?,?,?,?,?,?)";

	private final static String selectAllCustomers = "SELECT * FROM customer_details";
	private final static String getCdetails = "select * from customer_details where (Customer_Email=? or Customer_Account_Number=?) and Customer_Pin=? and Customer_Status='active'";
	private final static String getCustomerBalance = "Select * from customer_details where Customer_Account_Number=?";
	private final static String depositAmount = "update customer_details set Customer_Amount=? where Customer_Account_Number=?";
	private final static String updatePin = "update customer_details set Customer_Pin=? where Customer_Account_Number=? and Customer_Pin=?";
	private final static String closeAccountRequest = "update customer_details set Customer_Status='close' where Customer_Account_Number=?";

	private static Connection connect = null;
	private static PreparedStatement ps = null;

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/bank_management_system?user=root&password=Manish@918473");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	// Insert Customer Details
	public boolean insertCustomerDetails(CustomerDetails c) {
		// insertion
		try {
			ps = connect.prepareStatement(insert);
			ps.setString(1, c.getName());
			ps.setString(2, c.getEmailId());
			ps.setLong(3, c.getMobileNumber());
			ps.setLong(4, c.getAadharNumber());
			ps.setString(5, c.getAddress());
			ps.setString(6, c.getGender());
			ps.setDouble(7, c.getAmount());
			ps.setString(8, c.getStatus());
			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// getAllCustomerDetails
	public ArrayList<CustomerDetails> getAllCustomerDetails() {
		ArrayList<CustomerDetails> listOfCustomers = new ArrayList<>();

		try (PreparedStatement ps = connect.prepareStatement(selectAllCustomers); ResultSet rs = ps.executeQuery()) {
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					CustomerDetails cd = new CustomerDetails();
					cd.setEmailId(rs.getString("Customer_Email"));
					cd.setAadharNumber(rs.getLong("Customer_Aadhar_Number"));
					cd.setMobileNumber(rs.getLong("Customer_MobileNum"));
					cd.setAccountNumber(rs.getLong("Customer_Account_Number"));
					listOfCustomers.add(cd);
				}
				return listOfCustomers;
			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// checkEmailIdOrAccountNumAndPasswordValid
	public CustomerDetails checkEmailIdOrAccountNumAndPasswordValid(String emailid, int pin) {
		try {
			Connection connect = DatabaseConnection.jdbcSteps();
			PreparedStatement ps = connect.prepareStatement(getCdetails);
			ps.setString(1, emailid);
			ps.setString(2, emailid);
			ps.setInt(3, pin);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				CustomerDetails customer = new CustomerDetails();
				customer.setName(rs.getString("Customer_Name"));
				customer.setGender(rs.getString("Customer_Gender"));
				customer.setAccountNumber(rs.getLong("Customer_Account_Number"));
				return customer;
			}
			return null;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Check Balance of Customer
	public int getBalance(long accNum) {
		try {
			Connection connect = DatabaseConnection.jdbcSteps();
			PreparedStatement ps = connect.prepareStatement(getCustomerBalance);
			ps.setLong(1, accNum);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("Customer_Amount");
			}
			return 0;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// Deposit amount
	public boolean depositAmount(int amount, long accNum) {
		try {
			Connection connect = DatabaseConnection.jdbcSteps();
			PreparedStatement ps = connect.prepareStatement(depositAmount);
			amount = amount + getBalance(accNum);
			ps.setInt(1, amount);
			ps.setLong(2, accNum);
			return ps.executeUpdate() > 0;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	// withdraw amount
	public boolean withdrawAmmount(int amount, long accNum) {
		try {
			Connection connect = DatabaseConnection.jdbcSteps();
			PreparedStatement ps = connect.prepareStatement(depositAmount);
			amount = getBalance(accNum) - amount;
			ps.setInt(1, amount);
			ps.setLong(2, accNum);
			return ps.executeUpdate() > 0;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	// update Pin
	public boolean updatePin(int oldPin, int newPin, long accNum) {
		try {
			Connection connect = DatabaseConnection.jdbcSteps();
			PreparedStatement ps = connect.prepareStatement(updatePin);
			ps.setInt(1, newPin);
			ps.setLong(2, accNum);
			ps.setInt(3, oldPin);
			return ps.executeUpdate() > 0;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Account Closing Request
	public boolean closeAccount(long accNum) {
		try {
			Connection connect = DatabaseConnection.jdbcSteps();
			PreparedStatement ps = connect.prepareStatement(closeAccountRequest);
			ps.setLong(1, accNum);
			return ps.executeUpdate() > 0;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
}
