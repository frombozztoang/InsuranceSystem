package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Customer.Customer;
import Customer.CustomerList;

public class CustomerDao extends Dao {
	public CustomerDao() throws Exception {
		super.connect();
	}

	public void create(Customer customer) {
		String query = "INSERT INTO Customer (customerID, customerName, birth, eGender, pnumber, job, address) VALUES ('"
				+ customer.getCustomerID() + "', '" + customer.getCustomerName() + "', '" + customer.getBirth() + "', '"
				+ customer.getEGender() + "', '" + customer.getPnumber() + "', '" + customer.getJob() + "', '"
				+ customer.getAddress() + "')";
		super.create(query);
	}

	public ArrayList<Customer> retrieveAll() {
		String query = "SELECT * FROM Customer";
		ResultSet results = super.retrieve(query);
		ArrayList<Customer> customerList = new ArrayList<Customer>();

		try {
			while (results.next()) {
				Customer customer = new Customer();
				customer.setCustomerID(results.getString("customerID"));
				customer.setCustomerName(results.getString("customerName"));
				customer.setBirth(results.getString("birth"));
				customer.setEGender(results.getString("eGender"));
				customer.setPnumber(results.getString("pnumber"));
				customer.setJob(results.getString("job"));
				customer.setAddress(results.getString("address"));

				customerList.add(customer);
			}

			return customerList;
		} catch (SQLException e) {
			e.printStackTrace();
			// 예외 처리 필요한 경우에 대한 코드 추가
		} finally {
			// ResultSet, Statement, Connection 등 리소스를 닫는 코드 추가
		}

		return null;
	}

	public CustomerList retrieveById(Customer customer) {
		String query = "SELECT * FROM Customer WHERE customerID = '" + customer.getCustomerID() + "'";
		super.retrieve(query);
		// CustomerList에 ResultSet 값을 채우는 부분 구현
		return null;
	}

	public void update(Customer customer) {
		String query = "UPDATE Customer SET " + "customerName = '" + customer.getCustomerName() + "', " + "birth = '"
				+ customer.getBirth() + "', " + "eGender = '" + customer.getEGender() + "', " + "pnumber = '"
				+ customer.getPnumber() + "', " + "job = '" + customer.getJob() + "', " + "address = '"
				+ customer.getAddress() + "' " + "WHERE customerID = '" + customer.getCustomerID() + "'";
		super.update(query);
	}

	public void deleteAll(Customer customer) {
		String query = "DELETE FROM Customer";
		super.delete(query);
	}

	public void deleteById(Customer customer) {
		String query = "DELETE FROM Customer WHERE customerID = '" + customer.getCustomerID() + "'";
		super.delete(query);
	}

}