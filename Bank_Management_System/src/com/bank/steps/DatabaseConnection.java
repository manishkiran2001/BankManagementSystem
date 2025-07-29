package com.bank.steps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	public static Connection jdbcSteps() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connect = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/bank_management_system?user=root&password=Manish@918473");
		return connect;
	}
}
