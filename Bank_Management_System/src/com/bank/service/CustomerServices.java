package com.bank.service;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import com.bank.DAO.CustomerDAO;
import com.bank.DAO.CustomerStatementDAO;
import com.bank.DTO.CustomerDetails;
import com.bank.DTO.CustomerStatement;

import customerExceptions.DuplicateEmailIdException;
import customerExceptions.InvalidCustomerDetailsException;

//business logic layer
public class CustomerServices {

	CustomerDetails cd = new CustomerDetails();
	CustomerDAO customerDao = new CustomerDAO();
	CustomerStatementDAO csDao = new CustomerStatementDAO();
	Scanner sc = new Scanner(System.in);

	// customer registration
	public void customerRegistration() throws InvalidCustomerDetailsException {

		// Customer Name
		while (true) {
			try {
				System.out.println("Enter the Customer Name");
				String name = sc.nextLine();
				if (name.matches("^[A-Za-z ]{2,50}$")) {
					cd.setName(name);
					break;
				} else {
					throw new InvalidCustomerDetailsException("Invalid Customer Name");
				}
			} catch (InvalidCustomerDetailsException n) {
				System.err.println("Invalid Name");
			}
		}

		// email id
		while (true) {
			try {
				System.out.println("Enter Customer Email Id");
				String emailId = sc.next();
				if (emailId.matches("[a-z]+[0-9]*@[a-z]+.[a-z]{2,3}")) {
					boolean uniqueEmailId = true;
					for (CustomerDetails cd : customerDao.getAllCustomerDetails()) {
						if (cd.getEmailId().equalsIgnoreCase(emailId)) {
							uniqueEmailId = false;
						}
					}
					if (uniqueEmailId) {
						cd.setEmailId(emailId);
						break;
					} else {
						throw new DuplicateEmailIdException();
					}
				} else {
					throw new InvalidCustomerDetailsException("Invalid Email Id");
				}
			} catch (InvalidCustomerDetailsException n) {
				System.err.println("Invalid Email");
			} catch (DuplicateEmailIdException e) {
				System.err.println("Email Id Already Exists");
			}
		}

		// Mobile Number
		while (true) {
			try {
				System.out.println("Enter Customer Mobile");
				long mobileNumber = sc.nextLong();
				String mobile = String.valueOf(mobileNumber);
				if ((mobile).matches("[6789][0-9]{9}")) {
					cd.setMobileNumber(mobileNumber);
					break;
				} else {
					throw new InvalidCustomerDetailsException("Invalid Mobile Number");
				}
			} catch (InvalidCustomerDetailsException | InputMismatchException n) {
				System.err.println("Invalid Mobile Number");
				sc.nextLine();
			}
		}

		// Aadhar Number
		while (true) {
			try {
				System.out.println("Enter Customer Aadhar Number");
				long aadharNumber = sc.nextLong();
				String aadhar = "";
				if ((aadhar + aadharNumber).matches("[0-9][0-9]{11}")) {
					cd.setAadharNumber(aadharNumber);
					break;
				} else {
					throw new InvalidCustomerDetailsException("Invalid Aadhar Number");
				}
			} catch (InvalidCustomerDetailsException | InputMismatchException i) {
				System.err.println("Invalid Aadhar Number");
			}
		}

		// Customer Address
		while (true) {
			try {
				System.out.println("Enter Customer Address");
				sc.nextLine();
				String address = sc.nextLine();
				if (!address.isEmpty() && address.matches("^[A-Za-z0-9.,#/\\- ]{5,100}$")) {
					cd.setAddress(address);
					break;
				} else {
					throw new InvalidCustomerDetailsException("Invalid Customer Address");
				}
			} catch (InvalidCustomerDetailsException n) {
				System.err.println(n.getExceptionMsg());
			}
		}

		// Gender
		while (true) {
			try {
				System.out.println("Enter Customer Gender");
				String gender = sc.next();
				if (gender.matches("(?i)male|female|others")) {
					cd.setGender(gender);
					break;
				} else {
					throw new InvalidCustomerDetailsException("Invalid Customer Gender");
				}
			} catch (InvalidCustomerDetailsException n) {
				System.err.println("Invalid Customer Gender");
			}
		}

		// Amount
		while (true) {
			try {
				System.out.println("Enter the Amount");
				double amount = sc.nextInt();
				String a = "";
				if ((a + amount).matches("[0-9/.]*")) {
					cd.setAmount(amount);
					break;
				} else {
					throw new InvalidCustomerDetailsException("Invalid Customer Amount");
				}
			} catch (InvalidCustomerDetailsException | InputMismatchException a) {
				System.err.println("Invalid Customer Amount");
				sc.nextLine();
			}
		}

		cd.setStatus("pending");

		if (customerDao.insertCustomerDetails(cd)) {
			System.out.println("Registration Successful");
		} else {
			System.out.println("Server Error");
		}

	}

	// Captcha Validation for c login
	public boolean captchaValidation() {
		char[] alphabetArray = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
				'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
				'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7',
				'8', '9', '0' };
		Random r = new Random();
		String captcha = "";
		for (int i = 0; i < 4; i++) {
			captcha = captcha + alphabetArray[r.nextInt(alphabetArray.length)];
		}
		System.out.println("Captcha Is : " + captcha);
		System.out.println("Enter the Above Captcha");
		return sc.next().equals(captcha);
	}

	// customer login
	public void customerLogin() {
		System.out.println("Enter Login Email OR AccNum");
		String cEmailId = sc.next();
		System.out.println("Enter The Pin");
		int cPin = sc.nextInt();
		CustomerDetails c = customerDao.checkEmailIdOrAccountNumAndPasswordValid(cEmailId, cPin);
		if (c != null) {
			String maleFemale = c.getGender().equalsIgnoreCase("Male") ? "Mr" : "Ms";
			if (captchaValidation()) {
				System.out.println("Login Successful");
				System.out.println("Hello " + maleFemale + " " + c.getName());
				customerOperations(c.getAccountNumber());
			} else {
				System.err.println("Invalid captcha");
			}
		} else {
			System.err.println("Invalid Credentials");
		}
	}

	// Customer Operations
	public void customerOperations(long accNum) {
		boolean start = true;
		while (start) {
			System.out.println("============Enter Your Choice==============");
			System.out.println(
					" 1.Deposit Amount \n 2.Withdraw Amount \n 3.Get Statement \n 4.Check Account Balance \n 5.Update Pin \n 6.Close Account \n 7.Exit");
			switch (sc.nextInt()) {
			case 1: {
				while (true) {
					System.out.println("Enter the Deposit Amount");
					int amount = sc.nextInt();
					if (amount > 0) {
						if (customerDao.depositAmount(amount, accNum)) {
							System.out.println("Deposit of " + amount + " is successful");
							System.out.println("Updated Balance is : " + customerDao.getBalance(accNum));
							csDao.insertCustomerStatement("credit", customerDao.getBalance(accNum), amount, accNum,
									getCurrentDateAndTime());
						} else {
							System.out.println("deposit failed");
						}
						break;
					} else {
						System.err.println("Amount Should be Greater than 0");
					}
				}
			}
				break;
			case 2: {
				while (true) {
					System.out.println("Enter the Withdraw Amount");
					int amount = sc.nextInt();
					if (amount > 0 && amount <= customerDao.getBalance(accNum)) {
						if (customerDao.withdrawAmmount(amount, accNum)) {
							System.out.println("Withdraw of " + amount + " is successful");
							System.out.println("Updated Balance is : " + customerDao.getBalance(accNum));
							csDao.insertCustomerStatement("debit", customerDao.getBalance(accNum), amount, accNum,
									getCurrentDateAndTime());
						} else {
							System.out.println("withdraw failed");
						}
						break;
					} else {
						System.err.println("Amount Should be Greater than 0 and less then available balance");
					}
				}
			}
				break;
			case 3: {

				if (csDao.getStatementByAccNum(accNum) != null) {
					System.out.printf("%-15s %-18s %-20s %-20s %-10s%n", "CustomerAccNum", "TransactionType",
							"TransactionAmmount", "BalanceAmmount", "TransactionDate&Time");
					System.out.println(
							"'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''");
					for (CustomerStatement c : csDao.getStatementByAccNum(accNum)) {
						System.out.printf("%-20s %-20s %-17s %-15s %-10s%n", c.getCustomerAccNum(),
								c.getTransactionType(), c.getBalanceAmount(), c.getTransactionAmount(),
								c.getTransactionDateAndTime());
					}
				} else {
					System.out.println("No Transactions Found");
				}
			}
				break;
			case 4: {
				System.out.println("Balance Is : " + customerDao.getBalance(accNum));
			}
				break;
			case 5: {
				while (true) {
					System.out.println("update pin");
					System.out.println("Enter the 4-digit New Pin");
					int newPin = sc.nextInt();
					System.out.println("Enter the 4-digit Old Pin");
					int oldPin = sc.nextInt();
					if (customerDao.updatePin(oldPin, newPin, accNum)) {
						System.out.println("Pin Updated Successfully");
						start = false;
						break;
					} else {
						System.err.println("Pin Updation Failed....");
					}
				}
			}
				break;
			case 6: {
				System.out.println("close account");
				if (customerDao.closeAccount(accNum)) {
					System.out.println("Account closing Request Sent Successfully");
				} else {
					System.out.println("Account Closing Request Failed Try Again.....");
				}
			}
				break;
			case 7: {
				start = false;
			}
				break;
			default: {
				System.out.println("Enter valid choice");
			}
				break;
			}
		}
	}

	// Generate Current Time
	public String getCurrentDateAndTime() {
		Date d = new Date();
		return d.toLocaleString();
	}

}
