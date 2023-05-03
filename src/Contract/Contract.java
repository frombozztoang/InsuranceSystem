package Contract;
import java.util.Date;

import CompensationClaim.CompensationClaim;


public class Contract {

	private boolean cancellation;
	private int customerID;
	private Date dateOfMaturity;
	private Date dateOfSubscription;
	private int insuranceID;
	private String insurancePeriod;
	private boolean maturity;
	private int maxCompensation;
	private String paymentCycle;
	private int premium;
	private boolean resurrection;
	public Payment m_Payment;
	public CompensationClaim m_CompensationClaim;

	public Contract(){

	}

	public void finalize() throws Throwable {

	}
	public boolean updateCancellation(){
		return false;
	}
}//end Contract