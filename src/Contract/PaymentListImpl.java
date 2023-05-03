package Contract;

import java.util.ArrayList;

public class PaymentListImpl {

	private ArrayList<Payment> paymentList;
	public Payment m_Payment;

	public PaymentListImpl(){

	}

	public void finalize() throws Throwable {

	}
	public boolean add(){
		return false;
	}

	public boolean delete(){
		return false;
	}

	public ArrayList<Payment> retrieve(){
		return null;
	}

	public boolean update(){
		return false;
	}
}//end PaymentListImpl