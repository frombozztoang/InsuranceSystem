package Customer;

import java.util.ArrayList;

public interface CustomerList {
	public ArrayList<Customer> retrieve();

	public boolean delete(String customerID);
	public boolean update(Customer customer, String customerID);
}
