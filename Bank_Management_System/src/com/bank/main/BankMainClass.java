package com.bank.main;

import java.util.Scanner;

import com.bank.service.AdminServiceLayer;
import com.bank.service.CustomerServices;

public class BankMainClass {

	public static void main(String[] args) {
		CustomerServices cs = new CustomerServices();
		AdminServiceLayer as = new AdminServiceLayer();
		Scanner sc = new Scanner(System.in);
		boolean status = true;
		System.out.println("==============Welcome To JSP Bank================");
		while (status) {
			System.out.println("==============Enter Your Choice==============");
			System.out.println(" 1.Customer Registration \n 2.Customer Login \n 3.Admin Login \n 4.Exit");
			switch (sc.nextInt()) {
			case 1: {
				cs.customerRegistration();
				break;
			}
			case 2: {
				cs.customerLogin();
			}
				break;
			case 3: {
				as.adminLogin();
				break;
			}
			case 4: {
				status = false;
				System.out.println("================Thank You==================");
				break;
			}
			default: {
				System.err.println("Invalid Choice");
			}
			}
		}

	}
}
