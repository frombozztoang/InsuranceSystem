package Customer;

import java.util.ArrayList;

public interface CustomerList {

	public CustomerListImpl m_CustomerListImpl();

	public boolean add();

	public boolean delete();

	public ArrayList<Customer> retrieve();

	public boolean update();

}