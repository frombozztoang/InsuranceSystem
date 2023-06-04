package Dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import Customer.Customer;
import Customer.Customer.EGender;
import Customer.CustomerList;

public class CustomerDao extends Dao {
	public CustomerDao() throws Exception {
		
	try {
		super.connect();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	public void create(Customer customer) {
		String query = "INSERT INTO Customer (customerID, customerName, job, pnumber, birth, eGender, address) VALUES ('"
				+ customer.getCustomerID() + "', '" + customer.getCustomerName() + "', '" + customer.getJob() + "', '"
				+ customer.getPnumber() + "', '" + customer.getBirth() + "', '" + customer.getEGender() + "', '"
				+ customer.getAddress() + "')";
		super.create(query);
	}

	public ArrayList<Customer> retrieveAll()throws Exception {
		String query = "SELECT * FROM Customer";
		ResultSet results = super.retrieve(query);
        ArrayList<Customer> customerList = new ArrayList<Customer>();
        Customer customer;
        while (results.next()){
        	customer = new Customer(); 
        	customer.setCustomerID(results.getString("customerID"));
        	customer.setCustomerName(results.getString("customerName"));
        	customer.setJob(results.getString("job"));
        	customer.setPnumber(results.getString("pnumber"));
        	customer.setBirth(results.getString("birth"));
        	for (EGender gender : EGender.values()) {
        		if (gender.getGenderStr().equals(results.getString("eGender")))
        			customer.setEGender(gender);
        	}
        	customer.setAddress(results.getString("address"));
        	customerList.add(customer);
        }  
		return customerList;		
	}

	public CustomerList retrieveByCustomerID(String customerID) {
		String query = "SELECT * FROM Customer WHERE customerID = '" + customerID + "'";
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

	public void deleteAll() throws Exception {
		String query = "DELETE FROM Customer";
		super.delete(query);
	}

	public void deleteById(String customerID)throws Exception {
		String query = "DELETE FROM Customer WHERE customerID = '" + customerID + "'";
		super.delete(query);
	}
	

}