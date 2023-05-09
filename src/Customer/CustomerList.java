package customer;

import java.util.ArrayList;

public interface CustomerList {

	public boolean add(Customer customer);
	public boolean delete(int customerID);
	public boolean update(Customer customer, int customerID);
	public ArrayList<Customer> retrieve();

}
