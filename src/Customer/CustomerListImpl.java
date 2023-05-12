package Customer;
import java.util.ArrayList;

public class CustomerListImpl implements CustomerList{

	private ArrayList<Customer> customerList;


	public CustomerListImpl(){
		this.customerList = new ArrayList<Customer>();
	}

	public boolean add(Customer customer){
		if(this.customerList.add(customer)) return true;	
		else return false;
	}

	public boolean delete(String customerID){
		for(Customer customer : this.customerList) {
			if(customer.getCustomerID() == customerID) {
				if(this.customerList.remove(customer)) return true;
				break;
			}
		}
		return false;
	}


	public boolean update(Customer customer, String customerID){
		for(Customer uCustomer : this.customerList) {
			if(uCustomer.getCustomerID() == customerID) {
				uCustomer.setCustomerName(customer.getCustomerName());
				uCustomer.setPnumber(customer.getPnumber());
			}
		}
		return false;
	}
	public ArrayList<Customer> retrieve() {
		return customerList;
	}
	public void setRetrieve(ArrayList<Customer> customerList) {
		this.customerList = customerList;
	}

	
}
