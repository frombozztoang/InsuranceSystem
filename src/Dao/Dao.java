package Dao;

import java.sql.*;

public class Dao {
		Connection connect = null;
		Statement statement = null;
		ResultSet resultSet = null;

	public void connect() throws Exception{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/insurance?serverTimezone=UTC&useSSL=false", "root", "sso8690@");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean create(String query) throws Exception{
		try {
			statement = connect.createStatement();
			if (!statement.execute(query))
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public ResultSet retrieve(String query) throws Exception{
		try { 
			statement = connect.createStatement(); 
			resultSet = statement.executeQuery(query); 
		} catch (SQLException e) { 
				e.printStackTrace(); 
		}	
		return resultSet;
	}
	
	public boolean update(String query) throws Exception{
		try { 
			statement = connect.createStatement(); 
			if(!statement.execute(query))
				return true;
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		return false;
	}
	
	public boolean delete(String query) throws Exception{
		try { 
			statement = connect.createStatement(); 
			if(!statement.execute(query))
				return true;
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		return false;
	}

		
	
		

	

//		try { 
//			statement = connect.createStatement(); 
//			resultSet = statement.executeQuery("select * from insurance;"); 
//			while(resultSet.next()) {
//				System.out.print(resultSet.getString("user_name") + ", ");
//				System.out.print(resultSet.getString("user_phoneno") + ", ");
//				System.out.println(resultSet.getInt("isAccepted")); } 
//		} catch (SQLException e) { 
//				e.printStackTrace(); 
//		}
//		
//
//		
//		try { 
//			statement = connect.createStatement(); 
//			resultSet = statement.executeQuery("select * from insurance;"); 
//			while(resultSet.next()) {
//				System.out.print(resultSet.getString("user_name") + ", ");
//				System.out.print(resultSet.getString("user_phoneno") + ", ");
//				System.out.println(resultSet.getInt("isAccepted")); } 
//		} catch (SQLException e) { 
//				e.printStackTrace(); 
//		}
	}



