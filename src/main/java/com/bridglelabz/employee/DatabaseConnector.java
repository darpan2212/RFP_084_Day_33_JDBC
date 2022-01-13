package com.bridglelabz.employee;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class DatabaseConnector {

	private static Connection connection;

	private DatabaseConnector() {

	}

	public static Connection getConnection() {
		if (connection == null) {
			String jdbcUrl = "jdbc:mysql://localhost:3306/employee_db";
			String username = "root";
			String password = "root";

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("JDBC Driver class is loaded.");
			} catch (Exception e) {
				System.out.println("mysql connector is not loaded");
				e.printStackTrace();
			}

			listDbDrivers();

			try {
				connection = DriverManager.getConnection(jdbcUrl,
						username, password);
				System.out.println(
						"Connection established successfully.");
			} catch (SQLException e) {
				System.out.println("Connection establish failed.");
				e.printStackTrace();
			}
		}
		return connection;
	}

	private static void listDbDrivers() {
		Enumeration<Driver> listDrivers = DriverManager.getDrivers();

		while (listDrivers.hasMoreElements()) {
			Driver d = listDrivers.nextElement();
			System.out.println(d.getClass().getName());
		}
	}

}