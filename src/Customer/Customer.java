package Customer;

import java.util.ArrayList;

import Contract.Contract;

public class Customer {

	private String address;
	private int customerID;
	private String customerName;
	private String job;
	private int pnumber;
	private int SSN;
	public Contract m_Contract;
	public FamilyHistory m_FamilyHistory;

	public Customer(){

	}

	public void finalize() throws Throwable {

	}
	public boolean deleteCustomer(){
		return false;
	}

	public boolean exceptCustomer(){
		return false;
	}

	public ArrayList<Customer> retreiveMaturityCustomer(){
		return null;
	}

	public ArrayList<Customer> retreiveNonPaymentCustomer(){
		return null;
	}

	public ArrayList<Customer> retreiveResurrectionCustomer(){
		return null;
	}

	public Customer retrieveCustomer(){
		return null;
	}

	public boolean updateCustomer(){
		return false;
	}
}//end Customer