package Contract;

import java.util.Date;

public class Payment {

	private int customerID;
	private Date dateOfPayment;
	private int insuranceID;
	private boolean whetherPayment;

	public Payment(){

	}

	public void finalize() throws Throwable {

	}
	public boolean updatePayment(){
		return false;
	}
}//end Payment