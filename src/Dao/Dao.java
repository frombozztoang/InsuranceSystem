package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dao {
	private static String dburl = "jdbc:mysql://localhost:3306/insuranceSystem?serverTimezone=UTC";
	private static String dbUser = "root";
	private static String dbpasswd = "dlqhgus1";

	protected Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public void connect() throws Exception {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection(dburl, dbUser, dbpasswd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void create(String query) {
		try {
			statement = connect.createStatement();
			if (!statement.execute(query))
				System.out.println("insert OK!!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet retrieve(String query) {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;

	}

	public void update(String query) {
		try {
			statement = connect.createStatement();
			if (!statement.execute(query))
				System.out.println("update OK!!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(String query) {
		try {
			statement = connect.createStatement();
			if (!statement.execute(query))
				System.out.println("delete OK!!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
