package Contract;

import java.util.ArrayList;

public interface PaymentList {

	public PaymentListImpl m_PaymentListImpl();

	public boolean add();

	public boolean delete();

	public ArrayList<Payment> retrieve();

	public boolean update();

}