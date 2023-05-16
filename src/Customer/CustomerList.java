package Customer;

import java.util.ArrayList;

public interface CustomerList {

	public boolean add(Customer customer);
	public boolean delete(String customerID);
	public boolean update(Customer customer, String customerID);
	public ArrayList<Customer> retrieve();

}
