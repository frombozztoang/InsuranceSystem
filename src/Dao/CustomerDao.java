package Dao;

import java.util.ArrayList;

import Contract.Contract;
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

	public CustomerList retrieveAll(Customer customer) {
		String query = "SELECT * FROM Customer";
		super.retrieve(query);
		// CustomerList에 ResultSet 값을 채우는 부분 구현
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