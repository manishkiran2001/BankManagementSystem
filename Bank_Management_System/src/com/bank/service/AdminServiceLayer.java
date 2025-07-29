package com.bank.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.bank.DAO.AdminDAO;

public class AdminServiceLayer {
	Scanner sc = new Scanner(System.in);
	AdminDAO adminDAO = new AdminDAO();

	// admin login and operations
	public void adminLogin() {
		// email validation
		System.out.println("Enter Email Id");
		String email = sc.next();
		// password validation
		System.out.println("Enter Password");
		String password = sc.next();

		boolean start = true;
		if (adminDAO.selectAdmindetailsByUsingEmailAndPassword(email, password)) {
			while (start) {
				System.out.println("==============Enter Your Choice==============");
				System.out.println(
						" 1.To Get All Customer Details \n 2.Manage Customer Accounts \n 3.Manage Account Closing Requests \n 4.Exit From Admin Dashboard");
				switch (sc.nextInt()) {
				case 1: {
					System.out.println("*************All Customer Details**************");
					try {
						AdminServiceLayer.printCustomerDetails(adminDAO.displayAllCustomerDetails());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case 2: {
					boolean activateAccs = true;
					while (activateAccs) {
						System.out.println("======Enter Your Choice======");
						System.out.println(
								" 1.Get All Account Request Details \n 2.Activate Account By Using Id \n 3.Activate Account All Accounts \n 4.Exit");
						switch (sc.nextInt()) {
						case 1: {
							if (adminDAO.displayCustomerDetailsByStatus("pending") != null) {
								try {
									AdminServiceLayer
											.printCustomerDetails(adminDAO.displayCustomerDetailsByStatus("pending"));
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								System.out.println("No Data Found");
							}
						}
							break;
						case 2: {
							if (adminDAO.getCustomerByStatus("pending")) {
								System.out.println("Enter Id To Activate Account");
								int id = sc.nextInt();
								if (adminDAO.isCustomerIdValid(id)) {
									long accountNumber;
									int pin;
									do {
										accountNumber = generateAccountNumber();
									} while (adminDAO.isAccountNumberExists(accountNumber));
									pin = generatePin();

									if (adminDAO.activateAccountAndAssignAccNumberAndPin(id, accountNumber, pin)) {
										System.out.println(" Account Activated Successfully");
									} else {
										System.err.println("Account Is Already Active...");
									}
								} else {
									System.err.println("Invalid Id...Enter Valid Id...");
								}
							} else {
								System.err.println("No Pending Requests...");
							}
							break;
						}
						case 3: {
							adminDAO.activateAllAccounts("pending");
							break;
						}
						case 4: {
							activateAccs = false;
							break;
						}
						default: {
							System.err.println("Invalid Choice...");
							break;
						}
						}
					}
				}
				case 3: {
					boolean closeAccounts = true;
					while (closeAccounts) {
						System.out.println(
								" 1.Get All Account Closing Requests \n 2.Close Account By Id \n 3.Close All Accounts \n 4.Exit");
						switch (sc.nextInt()) {
						case 1: {
							if (adminDAO.displayCustomerDetailsByStatus("close") != null) {
								try {
									AdminServiceLayer
											.printCustomerDetails(adminDAO.displayCustomerDetailsByStatus("close"));
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								System.out.println("No Data Found");
							}
						}
							break;
						case 2: {
							System.out.println("Enter the ID");
							int id = sc.nextInt();
							if (adminDAO.isCustomerIdValid(id)) {
								if (adminDAO.closeCustomerAccById(id)) {
									System.out.println("  Account closed Successfylly");
									break;
								} else {
									System.err.println("Account is Already Closed...");
								}
							} else {
								System.err.println("Enter a valid Id...");
							}
						}
							break;
						case 3: {
							System.out.println("Close All Accounts");
						}
							break;
						case 4: {
							closeAccounts = false;
						}
							break;
						default: {
							System.out.println("Enter valid choice");
						}
							break;
						}
					}
					break;
				}
				case 4: {
					start = false;
					break;
				}
				default: {
					System.out.println("Invalid Request");
					break;
				}
				}
			}
		} else {
			System.out.println("Invalid Login Details");
		}
	}

	// Generate Account Number
	public static long generateAccountNumber() {
		long min = 100000000000L;
		long max = 999999999999L;
		return min + (long) (Math.random() * (max - min + 1));
	}

	// generate pin
	public static int generatePin() {
		int min = 1000;
		int max = 9999;
		return min + (int) (Math.random() * (max - min + 1));
	}

	public static void printCustomerDetails(ResultSet rs) throws SQLException {
		if (rs.isBeforeFirst()) {
			while (rs.next()) {
				System.out.println("Customer Id         : " + rs.getInt("Customer_Id"));
				System.out.println("Customer Name       : " + rs.getString("Customer_Name"));
				System.out.println("Customer Email      : " + rs.getString("Customer_Email"));
				System.out.println("Customer Mobile Num : " + rs.getLong("Customer_MobileNum"));
				System.out.println("Customer Aadhar Num : " + rs.getLong("Customer_Aadhar_Number"));
				System.out.println("Customer Address    : " + rs.getString("Customer_Address"));
				System.out.println("Customer Gender     : " + rs.getString("Customer_Gender"));
				System.out.println("Customer Acc Number : " + rs.getLong("Customer_Account_Number"));
				System.out.println("Customer Pin        : " + rs.getLong("Customer_Pin"));
				System.out.println("Customer Ammount    : " + rs.getInt("Customer_Ammount"));
				System.out.println("Customer Status     : " + rs.getString("Customer_Status"));
				System.out.println("=====================================================");
			}
		} else {
			System.out.println("No Data Found");
		}
	}
}
